package p00116;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.Arrays;

class Main {
    static int m, n;
    static int[][] matrix = new int[10][100];
    static int[][] costs = new int[10][100];
    static int[][] minimalPaths = new int[10][100];
    // No minimalPath’s weight will exceed integer values representable using 30
    // bits
    // 2 ^ 30 = 1.073.741.824
    static int MAX = 1073741824;

    public static void main(final String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            // Variables declaration
            int totalCost, row, i, j;
            String path;

            // For each matrix
            while (sc.hasNext()) {
                // Get number of rows
                m = sc.nextInt();
                // Get number of columns
                n = sc.nextInt();

                // Get matrix
                for (i = 0; i < m; i++) {
                    // Initialize costs array
                    Arrays.fill(costs[i], MAX);

                    for (j = 0; j < n; j++)
                        matrix[i][j] = sc.nextInt();
                }

                // Dinamic Programming :
                // Get the first row and the total cost of its path
                // This path will be the smallest
                totalCost = MAX;
                row = 0;
                for (i = 0; i < m; i++) {
                    // Find minimal path for that row
                    dinamicProgramming(i, 0);

                    if (totalCost > costs[i][0]) {
                        row = i;
                        totalCost = costs[i][0];
                    }
                }

                // Build minimal-cost path
                path = "";
                for (j = 0; j < n; j++) {
                    if (j == 0)
                        path = path + (row + 1);
                    else
                        path = path + " " + (row + 1);

                    row = minimalPaths[row][j];
                }

                // Output
                System.out.println(path);
                System.out.println(totalCost);
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Recursive Dinamic Programming approach
     *
     * @param i: row
     * @param j: column
     * @return cost of the path
     */
    public static int dinamicProgramming(int i, int j) {
        if (j == n) {
            // End of path
            return 0;
        }        

        if (costs[i][j] != MAX) {
            // The cost has been calculated before (use of memorization)
            return costs[i][j];
        }
        
        int[] possibleSteps = { i - 1, i, i + 1 };
        // The first and last rows (1 and m) of a matrix are considered adjacen
        if (i == 0) possibleSteps[0] = m - 1;
        if (i == m - 1) possibleSteps[2] = 0;

        for (int k = 0; k < 3; k++) {
            int cost = matrix[i][j] + dinamicProgramming(possibleSteps[k], j + 1);
            
            // Save minimal path and its cost
            if (cost < costs[i][j]) {
                minimalPaths[i][j] = possibleSteps[k];
                costs[i][j] = cost;
            }

            // To make sure the output is the lexicographically smallest.
            if ((cost == costs[i][j] && possibleSteps[k] < minimalPaths[i][j])) {
                minimalPaths[i][j] = possibleSteps[k];
                costs[i][j] = cost;
            }
        }

        return costs[i][j];
    }
}