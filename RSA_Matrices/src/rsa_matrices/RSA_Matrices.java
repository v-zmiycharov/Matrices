/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_matrices;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author valentin.zmiycharov
 */
public class RSA_Matrices {

    //Input simulation
    public static final int m = 927;
    public static final int n = 836;
    public static final int k = 1212;
    public static final int NUM_OF_THREADS = 100;
    
    public static double A[][];
    public static double B[][];
    public static double C[][];

    public static void main(String args[]) {
        int cores = Runtime.getRuntime().availableProcessors();
        
        int[] threadsAndActions = Helpers.CalculateOptimalThreadsCount(NUM_OF_THREADS, m * k, cores);
        int numberOfThreads = threadsAndActions[0];
        int maxActionsPerThread = threadsAndActions[1];

        A = Helpers.GenerateMatrix(n, m);
        B = Helpers.GenerateMatrix(k, n);
        C = new double[m][k];
        Thread[] thrd = new Thread[numberOfThreads];

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
        double elapsed = (double)(stopTime - startTime) / 1000000000.0;

        //Helpers.PrintMatrix(A, "A Matrix:");
        //Helpers.PrintMatrix(B, "B Matrix:");
        //Helpers.PrintMatrix(C, "C = AxB Matrix:");
        
        System.out.printf("%d thread(s), %d used - %f seconds \n", NUM_OF_THREADS, numberOfThreads, elapsed);
    }
}
