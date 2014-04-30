/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_matrices;

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
                result[row][col] = 5*random.nextDouble();
            }
        }
        return result;
    }

}
