/**
 * 10010 - Where's Waldorf?
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */

package p10010;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main {
    static int m;
    static int n;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            int cases = sc.nextInt();

            // Get cases
            for (int i = 0; i < cases; i++) {
                // Get the grid of letters
                m = sc.nextInt();
                n = sc.nextInt();
                char grid[][] = new char[n][m];
                for (int j = 0; j < m; j++) {
                    String line = sc.next();
                    for (int l = 0; l < n; l++) {
                        char a = line.charAt(l);
                        grid[l][j] = Character.toLowerCase(a);
                    }
                }

                // Get the words to search for
                int k = sc.nextInt();
                for (int j = 0; j < k; j++) {
                    String word = sc.next();
                    System.out.println(searcher(grid, word.toLowerCase()));
                }
                if (i != cases - 1) System.out.println();
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * It searchs for the given word in the given grid of letters.
     * 
     * @param grid: grid of letters that contains the word
     * @param word: word to search for
     * 
     * @return String with the first letter position in the grid
     */
    static String searcher(char grid[][], String word) {
        // Search for the first letter in all the grid
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (grid[i][j] == word.charAt(0)) {
                    // Search horizontally (right)
                    int k = 0;
                    int l = i;
                    while (k < word.length() && l < n) {
                        if (word.charAt(k) != grid[l][j])
                            break;
                        k++;
                        l++;
                    }
                    if (k == word.length()) return (j + 1) + " " + (i + 1);

                    // Search horizontally (left)
                    k = 0;
                    l = i;
                    while (k < word.length() && l >= 0) {
                        if (word.charAt(k) != grid[l][j])
                            break;
                        k++;
                        l--;
                    }
                    if (k == word.length()) return (j + 1) + " " + (i + 1);

                    // Search vertically (up)
                    k = 0;
                    l = j;
                    while (k < word.length() && l < m) {
                        if (word.charAt(k) != grid[i][l])
                            break;
                        k++;
                        l++;
                    }
                    if (k == word.length()) return (j + 1) + " " + (i + 1);

                    // Search vertically (down)
                    k = 0;
                    l = j;
                    while (k < word.length() && l >= 0) {
                        if (word.charAt(k) != grid[i][l])
                            break;
                        k++;
                        l--;
                    }
                    if (k == word.length()) return (j + 1) + " " + (i + 1);

                    // Search diagonally (up-right)
                    k = 0;
                    l = i;
                    int x = j;
                    while (k < word.length() && l < n && x < m) {
                        if (word.charAt(k) != grid[l][x])
                            break;
                        k++;
                        l++;
                        x++;
                    }
                    if (k == word.length()) return (j + 1) + " " + (i + 1);

                    // Search diagonally (down-left)
                    k = 0;
                    l = i;
                    x = j;
                    while (k < word.length() && l >= 0 && x >= 0) {
                        if (word.charAt(k) != grid[l][x])
                            break;
                        k++;
                        l--;
                        x--;
                    }
                    if (k == word.length()) return (j + 1) + " " + (i + 1);

                    // Search diagonally (down-right)
                    k = 0;
                    l = i;
                    x = j;
                    while (k < word.length() && l < n && x >= 0) {
                        if (word.charAt(k) != grid[l][x])
                            break;
                        k++;
                        l++;
                        x--;
                    }
                    if (k == word.length()) return (j + 1) + " " + (i + 1);

                    // Search diagonally (up-left)
                    k = 0;
                    l = i;
                    x = j;
                    while (k < word.length() && l >= 0 && x < m) {
                        if (word.charAt(k) != grid[l][x])
                            break;
                        k++;
                        l--;
                        x++;
                    }
                    if (k == word.length()) return (j + 1) + " " + (i + 1);
                }
            }
        }

        return "No matches found";
    }
}