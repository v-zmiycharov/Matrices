/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_matrices;

import static rsa_matrices.RSA_Matrices.A;
import static rsa_matrices.RSA_Matrices.B;
import static rsa_matrices.RSA_Matrices.C;

/**
 *
 * @author valentin.zmiycharov
 */
public class WorkerTh implements Runnable {

    private int startRow;
    private int endRow;
    private int startCol;
    private int endCol;

    public WorkerTh(int startRow, int endRow, int startCol, int endCol) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        
        for (int row = startRow; row <= endRow; row++) {
            int col = row == startRow ? startCol : 0;
            for (; col < C[row].length && (row != endRow || col <= endCol) ; col++) {
                for (int k = 0; k < B.length; k++) {
                    C[row][col] += A[row][k] * RSA_Matrices.B[k][col];
                }
            }
        }
        
        long stopTime = System.nanoTime();
        double elapsed = (double)(stopTime - startTime) / 1000000000.0;
    }
}
