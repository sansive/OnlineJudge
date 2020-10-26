/**
 * 861 - Little Bishops
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */
package p861;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main {
        private static int sum;

        public static void main(String[] args) {
                try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
                        int n, k;

                        // Get test cases
                        while (true) {
                                // Get number of squares per line
                                n = sc.nextInt();
                                // Get number of bishops
                                k = sc.nextInt();

                                // End of input condition
                                if (n == 0 && k == 0) break;

                                // Output
                                System.out.println(bishops_placements(n, k));
                        }

                        sc.close();
                        System.exit(0);

                } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                }
        }

        /**
         * It calculates the total number of ways one can put the given number of
         * bishops on a chessboard of the given size so that no two of them lie in
         * attacking positions.
         * 
         * @param n: number of squares per line
         * @param k: number of bishops
         * 
         * @return number of ways
         */
        private static int bishops_placements(int n, int k) {
                if(n==1 && k==1) return 1;
                if(n*2-2 < k) return 0;
                
                // For a more eficient program, the chessboard is divided
                // in white and black diagonals
                boolean[] white_diagonals;
                boolean[] black_diagonals;
                if(n%2 == 0){
                        white_diagonals = new boolean[n-1];
                        black_diagonals = new boolean[n];

                } else {
                        white_diagonals = new boolean[n];
                        black_diagonals = new boolean[n-1];
                }

                // Consider all possible numbers of bishops placed on black diagonals
                // with corresponding numbers of bishops on white diagonals
                int counter = 0;
                int a;
                for(int i=0; i<k+1; i++){
                        // Number of ways that the bishops are placed on black diagonals
                        sum = 0;
                        numberWays(white_diagonals, black_diagonals, i, 1);
                        a = sum;

                        // Number of ways that the rest of the bishops are placed on white diagonals
                        sum = 0;
                        numberWays(white_diagonals, black_diagonals, k-i, 0);

                        counter = counter + (a*sum);
                }
                return counter;
        }

        
        /**
         * It calculates the number of ways to place the given number of bishops
         * on diagonals with the same color.
         * 
         * @param white_diagonals: it represents the white squares on the chessboard
         * @param black_diagonals; it represents the black squares on the chessboard
         * @param bishops: number of bishops to be placed
         * @param start: it refers to the diagonal color
         */
        private static void numberWays(boolean[] white_diagonals, boolean[] black_diagonals, int bishops, int start) {
                if(bishops == 0){
                        sum++;

                } else {
                        int middle_black = (black_diagonals.length-1)/2;
                        int middle_white = (white_diagonals.length)/2;
                        int diagonals = white_diagonals.length + black_diagonals.length;
                        int index;

                        while (start < diagonals-bishops+1) {
                                index = start;
                                if(index > diagonals/2){
                                        index = (diagonals-index-1);
                                }
                                index = index/2;
        
                                if(start%2 == 1){
                                        // Black diagonal
                                        // Consider all possible squares in which the bishop can be placed
                                        for(int j = middle_black-index; j<middle_black+index+2; j++){
                                                // If the square is free
                                                if(!black_diagonals[j]){
                                                         // Place the bishop
                                                        black_diagonals[j] = true;
                                                        // Search placements for the rest of the bishops
                                                        numberWays(white_diagonals, black_diagonals, bishops-1, start+2);
                                                        black_diagonals[j] = false;
                                                }
                                        }
                                }

                                if(start%2 == 0){
                                        // White diagonal
                                        // Consider all possible squares in which the bishop can be placed
                                        for(int j = middle_white-index; j<middle_white+index+1; j++){
                                                // If the square is free
                                                if(!white_diagonals[j]){
                                                        // Place the bishop
                                                        white_diagonals[j] = true;
                                                        // Search placements for the rest of the bishops
                                                        numberWays(white_diagonals, black_diagonals, bishops-1, start+2);
                                                        white_diagonals[j] = false;
                                                }
                                        }
                                }

                                start = start + 2;
                        }
                }
        }
}