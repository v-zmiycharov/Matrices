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

    private int row;
    private int col;
    private double A[][];
    private double B[][];
    private double C[][];

    public WorkerTh(int row, int col, double A[][], double B[][], double C[][]) {
        this.row = row;
        this.col = col;
        this.A = A;
        this.B = B;
        this.C = C;
    }

    @Override
    public void run() {
        for (int k = 0; k < B.length; k++) {
            C[row][col] += A[row][k] * B[k][col];
        }
    }
}
