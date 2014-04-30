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

    public static final int m = 3;
    public static final int n = 2;
    public static final int k = 3;
    public static final int NUM_OF_THREADS = 9;

    public static void main(String args[]) {
        int row;
        int col;
        double A[][] = Helpers.GenerateMatrix(n, m);
        double B[][] = Helpers.GenerateMatrix(k, n);
        double C[][] = new double[m][k];
        int threadcount = 0;
        Thread[] thrd = new Thread[NUM_OF_THREADS];

        try {
            for (row = 0; row < C.length; row++) {
                for (col = 0; col < C[0].length; col++) {
                    // creating thread for multiplications
                    thrd[threadcount] = new Thread(new WorkerTh(row, col, A, B, C));
                    thrd[threadcount].start(); //thread start

                    thrd[threadcount].join(); // joining threads
                    threadcount++;
                }

            }

        } catch (InterruptedException ie) {
        }

        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        
        // printing matrix A
        System.out.println(" A Matrix : ");
        for (row = 0; row < 3; row++) {
            for (col = 0; col < 2; col++) {
                System.out.print("  " + df.format(A[row][col]));
            }
            System.out.println();
        }

        // printing matrix B
        System.out.println(" B Matrix : ");
        for (row = 0; row < 2; row++) {
            for (col = 0; col < 3; col++) {
                System.out.print("  " + df.format(B[row][col]));
            }
            System.out.println();
        }

        // printing resulting matrix C after multiplication
        System.out.println(" Resulting C Matrix : ");
        for (row = 0; row < 3; row++) {
            for (col = 0; col < 3; col++) {
                System.out.print("  " + df.format(C[row][col]));
            }
            System.out.println();
        }

    }

}
