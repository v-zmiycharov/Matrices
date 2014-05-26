/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_matrices;

import java.text.DecimalFormat;
import java.util.Random;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import static rsa_matrices.RSA_Matrices.isQuietMode;
import static rsa_matrices.RSA_Matrices.k;
import static rsa_matrices.RSA_Matrices.m;
import static rsa_matrices.RSA_Matrices.n;
import static rsa_matrices.RSA_Matrices.threadsCount;

/**
 *
 * @author valentin.zmiycharov
 */
public final class Helpers {

    public static Options createOptions() {
        Options options = new Options();
        options.addOption("m", true, "Columns of first table");
        options.addOption("n", true, "Rows of first table");
        options.addOption("k", true, "Columns of second table");
        options.addOption("t", true, "Maximum possible tasks");
        options.addOption("q", false, "Maximum possible tasks");
        return options;
    }

    public static boolean assignInputValues(Options options, String[] args) {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException ex) {
            return false;
        }

        //Validate input
        if (!cmd.hasOption("m") || !cmd.hasOption("n")
                || !cmd.hasOption("k") || !cmd.hasOption("t")) {
            return false;
        }

        try {
            m = Integer.parseInt(cmd.getOptionValue("m"));
            n = Integer.parseInt(cmd.getOptionValue("n"));
            k = Integer.parseInt(cmd.getOptionValue("k"));
            threadsCount = Integer.parseInt(cmd.getOptionValue("t"));
            isQuietMode = cmd.hasOption("q");

            if (m <= 0 || n <= 0 || k <= 0 || threadsCount <= 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static void showMessageIfNotQuiet(String message) {
        if (!isQuietMode) {
            System.out.println(message);
        }
    }
    
    public static int[] calculateOptimalThreadsCount(int suggestedMaxCount, int actionsCount, int maxPossible) {
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
    
    public static double[][] generateMatrix(int rows, int columns) {
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

    public static void printMatrix(double[][] matrix, String matrixName) {
        int MAXIMUM_ALLOWED_COUNT = 10;
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

}
