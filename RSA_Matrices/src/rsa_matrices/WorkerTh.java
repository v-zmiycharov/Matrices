/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_matrices;

/**
 *
 * @author valentin.zmiycharov
 */
public class WorkerTh implements Runnable {

    private int startRow;
    private int endRow;
    private int startCol;
    private int endCol;
    private double A[][];
    private double B[][];
    private double C[][];

    public WorkerTh(int startRow, int endRow, int startCol, int endCol, double A[][], double B[][], double C[][]) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
        this.A = A;
        this.B = B;
        this.C = C;
    }

    @Override
    public void run() {
        for (int row = startRow; row <= endRow; row++) {
            int col = row == startRow ? startCol : 0;
            for (; col < C[row].length && (row != endRow || col <= endCol) ; col++) {
                for (int k = 0; k < B.length; k++) {
                    C[row][col] += A[row][k] * B[k][col];
                }
            }
        }
    }
}
