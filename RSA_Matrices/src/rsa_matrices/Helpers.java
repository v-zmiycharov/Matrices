/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_matrices;

import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author valentin.zmiycharov
 */
public final class Helpers {

    public static double[][] GenerateMatrix(int columns, int rows) {
        double result[][];
        result = new double[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Random random = new Random();
                result[row][col] = 5 * random.nextDouble();
            }
        }
        return result;
    }

    public static void PrintMatrix(double[][] matrix, String matrixName) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        System.out.println(matrixName);
        for (int row = 0; row < matrix.length && row < 5; row++) {
            for (int col = 0; col < matrix[0].length && col < 5; col++) {
                System.out.print("  " + df.format(matrix[row][col]));
            }
            if(matrix[0].length>5) {
                System.out.print(" ... ");
            }
            System.out.println();
        }
        if(matrix.length>5) {
            System.out.print("  ... ... ... ");
        }
        System.out.println();
    }

}
