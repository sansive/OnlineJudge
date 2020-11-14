package p1309;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static ArrayList<ArrayList<ArrayList<Integer>>> grid  = new ArrayList<ArrayList<ArrayList<Integer>>>();
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            int row, col, number;
            String line;

            while (true) {
                for (row = 0; row < 16; row++) {
                    grid.add(new ArrayList<ArrayList<Integer>>());
                    line = sc.nextLine();
                    for (col = 0; col < 16; col++) {
                        grid.get(row).add(new ArrayList<Integer>());
                        if (line.charAt(col) != '-')
                           grid.get(row).get(col).add(line.charAt(col) - 64);
                    }
                }

                for (row = 0; row < 16; row++) {
                    for (col = 0; col < 16; col++) {
                        if (grid.get(row).get(col).isEmpty()) {
                            for (number= 1; number<17; number++) {
                                if (checkRow(number, row) && checkColumn(number, col) && checkGrid(number, row, col)) {
                                    grid.get(row).get(col).add(number);
                                }
                            }
                        }
                    }
                }

                System.out.println(solve());

                // Output
                for (row = 0; row < 16; row++) {
                    for (col = 0; col < 16; col++)
                        System.out.print((char) (grid.get(row).get(col).get(0) + 64));
                    
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

    static boolean solve() {
        int number, i, j;
        while (!isFilled()) {
            number = 17;
            i = 0;
            j = 0;
            for (int row = 0; row < 16; row++) {
                for (int col = 0; col < 16; col++) {
                    if (grid.get(row).get(col).size() > 1 && grid.get(row).get(col).size() < number) {
                        number = grid.get(row).get(col).size();
                        i = row;
                        j = col;                                
                    }                        
                }
            }
            
            for (Integer x : grid.get(i).get(j)) {
                ArrayList<ArrayList<ArrayList<Integer>>> gridCopy = new ArrayList<ArrayList<ArrayList<Integer>>>(grid);
                ArrayList<Integer> temp = new ArrayList<Integer> (x);
                grid.get(i).set(j, temp);
                if (solve())
                    return true;

                grid = gridCopy;
            }

            return false;     
        }

        return true;
    }

    static boolean isFilled() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (grid.get(i).get(j).size() != 1) {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean checkRow(int value, int row) {
        for (int i = 0; i < 16; i++) {
            if (!grid.get(row).get(i).isEmpty() && grid.get(row).get(i).get(0) == value)
                return false;
        }

        return true;
    }

    static boolean checkColumn(int value, int col) {
        for (int i = 0; i < 16; i++) {
            if (!grid.get(i).get(col).isEmpty() && grid.get(i).get(col).get(0) == value)
                return false;
        }

        return true;
    }

    static boolean checkGrid(int value, int row, int col) {
        int x = row - row % 4;
        int y = col - col % 4;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!grid.get(x + i).get(y + j).isEmpty() && grid.get(x + i).get(y + j).get(0)== value)
                    return false;
            }
        }

        return true;
    }
}