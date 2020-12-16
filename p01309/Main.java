package p01309;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    static int[][] grid = new int[16][16];

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            int i, j;
            String line;

            while (true) {
                for (i = 0; i < 16; i++) {
                    line = sc.nextLine();
                    for (j = 0; j < 16; j++) {
                        if (line.charAt(j) == '-')
                            grid[i][j] = 0;
                        else
                            grid[i][j] = line.charAt(j) - 64;
                    }
                }

                solve(0, 0);

                // Output
                for (i = 0; i<16; i++) {
                    for (j = 0; j < 16; j++)
                        System.out.print((char) (grid[i][j] + 64));
                    
                    System.out.println();
                }
                
                if (!sc.hasNext())
                    break;
                System.out.println();
                sc.nextLine();
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    static boolean solve(int row, int col) {
        if (row == 16) {
            return true;
        }

        if (grid[row][col] != 0) {
            if (col + 1 > 15)
                return solve(row + 1, 0);
            else
                return solve(row, col + 1);

        } else {
            for (int number = 1; number < 17; number++) {
                if (checkRow(number, row) && checkColumn(number, col) && checkGrid(number, row, col)) {
                    grid[row][col] = number;

                    if (col + 1 > 15) {
                        if (solve(row + 1, 0))
                            return true;
                        else
                            continue;

                    } else {
                        if (solve(row, col + 1))
                            return true;
                        else
                            continue;
                    }
                }
            }

            grid[row][col] = 0;
            return false;
        }
    }

    static boolean checkRow(int value, int row) {
    int[] copy = grid[row].clone();
    Arrays.sort(copy);
        for (int i = 15; i >= 0; i--) {
            if (copy[i] == 0) return true;
            if (copy[i] == value) return false;
        }

        return true;
    }

    static boolean checkColumn(int value, int col) {
        for (int i = 0; i < 16; i++) {
            if (grid[i][col] == value) {
                return false;
            }
        }

        return true;
    }

    static boolean checkGrid(int value, int row, int col) {
        int x = row - row % 4;
        int y = col - col % 4;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[x + i][y + j] == value)
                    return false;
            }
        }

        return true;
    }
}