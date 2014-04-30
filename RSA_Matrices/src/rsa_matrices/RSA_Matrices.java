/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_matrices;

import java.text.DecimalFormat;

/**
 *
 * @author valentin.zmiycharov
 */
public class RSA_Matrices {

    //Input simulation
    public static final int m = 8;
    public static final int n = 3;
    public static final int k = 5;
    public static final int NUM_OF_THREADS = 19;

    public static final int MAX_THREADS_COUNT = 5000;

    public static void main(String args[]) {
        int[] threadsAndActions = Helpers.CalculateOptimalThreadsCount(NUM_OF_THREADS, m * k, MAX_THREADS_COUNT);
        int numberOfThreads = threadsAndActions[0];
        int maxActionsPerThread = threadsAndActions[1];

        double A[][] = Helpers.GenerateMatrix(n, m);
        double B[][] = Helpers.GenerateMatrix(k, n);
        double C[][] = new double[m][k];
        Thread[] thrd = new Thread[numberOfThreads];

        try {
            int currentPosition = 0;
            int startRow = 0;
            int endRow = 0;
            int startCol = 0;
            int endCol = 0;
            for (int i = 0; i < numberOfThreads - 1; i++) {
                currentPosition = i * maxActionsPerThread;
                startRow = currentPosition / k;
                endRow = (currentPosition + maxActionsPerThread - 1) / k;
                startCol = currentPosition % k;
                endCol = (currentPosition + maxActionsPerThread - 1) % k;

                //System.out.printf("thread %d - [%d][%d] to [%d][%d] \n", i+1, startRow, startCol, endRow, endCol);
                thrd[i] = new Thread(new WorkerTh(startRow, endRow, startCol, endCol, A, B, C));
                thrd[i].start();
                thrd[i].join();
            }
            int i = numberOfThreads - 1;
            currentPosition = i * maxActionsPerThread;
            startRow = currentPosition / k;
            startCol = currentPosition % k;
            //System.out.printf("thread %d - [%d][%d] to [%d][%d] \n", i+1, startRow, startCol, m - 1, k - 1);
            thrd[i] = new Thread(new WorkerTh(startRow, m - 1, startCol, k - 1, A, B, C));
            thrd[i].start();
            thrd[i].join();

        } catch (InterruptedException ie) {
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        Helpers.PrintMatrix(A, "A Matrix:");
        Helpers.PrintMatrix(B, "B Matrix:");
        Helpers.PrintMatrix(C, "C = AxB Matrix:");
    }
}
