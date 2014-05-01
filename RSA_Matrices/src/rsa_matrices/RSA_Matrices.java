/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_matrices;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
        if (!assignInputValues(options, args)) {
            System.out.println("Incorect input!");
            return;
        }
        showMessageIfNotQuiet("Input validated!");

        int cores = Runtime.getRuntime().availableProcessors();
        showMessageIfNotQuiet(cores + " cores found.");

        int[] threadsAndActions = Helpers.CalculateOptimalThreadsCount(threadsCount, m * k, cores);
        int numberOfThreads = threadsAndActions[0];
        int maxActionsPerThread = threadsAndActions[1];
        showMessageIfNotQuiet("Calculated optimal threads count based on input and cores - " + numberOfThreads);

        A = Helpers.GenerateMatrix(n, m);
        B = Helpers.GenerateMatrix(k, n);
        C = new double[m][k];

        showMessageIfNotQuiet("Matrices generated");
        if (!isQuietMode) {
            Helpers.PrintMatrix(A, "A Matrix: [" + n + "][" + m + "]");
            Helpers.PrintMatrix(B, "B Matrix: [" + k + "][" + n + "]");
        }

        Thread[] thrd = new Thread[numberOfThreads];

        showMessageIfNotQuiet("Starting calculations");
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

            thrd[i] = new Thread(new WorkerTh(startRow, endRow, startCol, endCol));
        }
        int index = numberOfThreads - 1;
        currentPosition = index * maxActionsPerThread;
        startRow = currentPosition / k;
        startCol = currentPosition % k;
        thrd[index] = new Thread(new WorkerTh(startRow, m - 1, startCol, k - 1));

        for (Thread thrd1 : thrd) {
            thrd1.start();
        }

        for (Thread thrd1 : thrd) {
            try {
                thrd1.join();
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

    public static boolean assignInputValues(Options options, String[] args) {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException ex) {
            return false;
        }

        //Validate input
        if (!cmd.hasOption("m") || !cmd.hasOption("n")
                || !cmd.hasOption("k") || !cmd.hasOption("t")) {
            return false;
        }

        try {
            m = Integer.parseInt(cmd.getOptionValue("m"));
            n = Integer.parseInt(cmd.getOptionValue("n"));
            k = Integer.parseInt(cmd.getOptionValue("k"));
            threadsCount = Integer.parseInt(cmd.getOptionValue("t"));
            isQuietMode = cmd.hasOption("q");

            if (m <= 0 || n <= 0 || k <= 0 || threadsCount <= 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static void showMessageIfNotQuiet(String message) {
        if (!isQuietMode) {
            System.out.println(message);
        }
    }
}
