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
public class CalculationThread implements Runnable {

    private final int startRow;
    private final int endRow;
    private final int startCol;
    private final int endCol;

    public CalculationThread(int startRow, int endRow, int startCol, int endCol) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
    }

    @Override
    public void run() {
        for (int row = startRow; row <= endRow; row++) {
            int col = row == startRow ? startCol : 0;
            for (; col < C[row].length && (row != endRow || col <= endCol) ; col++) {
                for (int k = 0; k < B.length; k++) {
                    C[row][col] += A[row][k] * RSA_Matrices.B[k][col];
                }
            }
        }
    }
}
