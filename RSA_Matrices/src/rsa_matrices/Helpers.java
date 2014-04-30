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
                result[row][col] = 2 * random.nextDouble();
            }
        }
        return result;
    }

    public static void PrintMatrix(double[][] matrix, String matrixName) {
        int MAXIMUM_ALLOWED_COUNT = 25;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        System.out.println(matrixName);
        for (int row = 0; row < matrix.length && row < MAXIMUM_ALLOWED_COUNT; row++) {
            for (int col = 0; col < matrix[0].length && col < MAXIMUM_ALLOWED_COUNT; col++) {
                System.out.print("  " + df.format(matrix[row][col]));
            }
            if (matrix[0].length > MAXIMUM_ALLOWED_COUNT) {
                System.out.print(" ... ");
            }
            System.out.println();
        }
        if (matrix.length > MAXIMUM_ALLOWED_COUNT) {
            System.out.print("  ... ... ... ");
        }
        System.out.println();
    }

    public static int[] CalculateOptimalThreadsCount(int suggestedMaxCount, int actionsCount, int maxPossible) {
        int[] result = new int[2];
        int resultThreads = suggestedMaxCount;
        if (resultThreads > maxPossible) {
            resultThreads = maxPossible;
        }
        if (resultThreads > actionsCount) {
            resultThreads = actionsCount;
        }
        if (actionsCount % resultThreads == 0) {
            result[0] = resultThreads;
            result[1] = actionsCount / resultThreads;
            return result;
        } else {
            int maxActionsPerThread = actionsCount / resultThreads + 1;
            resultThreads = actionsCount % maxActionsPerThread == 0 ? actionsCount / maxActionsPerThread : actionsCount / maxActionsPerThread + 1;
            result[0] = resultThreads;
            result[1] = maxActionsPerThread;
            return result;
        }
    }

}
