/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_matrices;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.Options;

/**
 *
 * @author valentin.zmiycharov
 */
public class RSA_Matrices {

    //Input simulation
    public static int m;
    public static int n;
    public static int k;
    public static int threadsCount;
    public static boolean isQuietMode;

    public static double A[][];
    public static double B[][];
    public static double C[][];

    public static void main(String args[]) {
        Options options = Helpers.CreateOptions();
        if (!Helpers.assignInputValues(options, args)) {
            System.out.println("Incorect input!");
            return;
        }
        Helpers.showMessageIfNotQuiet("Input validated.");

        int cores = Runtime.getRuntime().availableProcessors();
        Helpers.showMessageIfNotQuiet(cores + " cores found.");

        int[] threadsAndActions = Helpers.CalculateOptimalThreadsCount(threadsCount, m * k, cores);
        int numberOfThreads = threadsAndActions[0];
        int maxActionsPerThread = threadsAndActions[1];
        Helpers.showMessageIfNotQuiet("Calculated optimal threads count based on input and cores - " + numberOfThreads);

        A = Helpers.GenerateMatrix(n, m);
        B = Helpers.GenerateMatrix(k, n);
        C = new double[m][k];

        Helpers.showMessageIfNotQuiet("Matrices generated:");
        if (!isQuietMode) {
            Helpers.PrintMatrix(A, "A Matrix: [" + n + "][" + m + "]");
            Helpers.PrintMatrix(B, "B Matrix: [" + k + "][" + n + "]");
        }

        Thread[] threads = new Thread[numberOfThreads];

        Helpers.showMessageIfNotQuiet("Starting calculations");
        long startTime = System.nanoTime();

        int currentPosition;
        int startRow;
        int endRow;
        int startCol;
        int endCol;
        for (int i = 0; i < numberOfThreads - 1; i++) {
            currentPosition = i * maxActionsPerThread;
            startRow = currentPosition / k;
            endRow = (currentPosition + maxActionsPerThread - 1) / k;
            startCol = currentPosition % k;
            endCol = (currentPosition + maxActionsPerThread - 1) % k;

            threads[i] = new Thread(new CalculationThread(startRow, endRow, startCol, endCol));
        }
        int index = numberOfThreads - 1;
        currentPosition = index * maxActionsPerThread;
        startRow = currentPosition / k;
        startCol = currentPosition % k;
        threads[index] = new Thread(new CalculationThread(startRow, m - 1, startCol, k - 1));

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(RSA_Matrices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        long stopTime = System.nanoTime();
        double elapsed = (double) (stopTime - startTime) / 1000000000.0;

        if (!isQuietMode) {
            Helpers.PrintMatrix(C, "C = AxB Matrix:");
        }
        System.out.printf("%d thread(s) used - %f seconds \n", numberOfThreads, elapsed);
    }
}
