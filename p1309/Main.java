/**
 * 1309 - Sudoku
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */
package p1309;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

class Main {
    private static int grid[][] = new int [16][16];
    private static boolean filas[][] = new boolean [16][16];
    private static boolean columnas[][] = new boolean [16][16];
    private static boolean cuadrantes[][] = new boolean [16][16];

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            int cuadrante, i, j, n;
            String line;
            
            while (true) {
                cuadrante = -1;

                for (i = 0; i < 16; i++) {    
                    Arrays.fill(filas[i], false);
                    Arrays.fill(columnas[i], false);
                    Arrays.fill(cuadrantes[i], false);
                }
                
                for (i = 0; i < 16; i++) {                    
                    line = sc.nextLine();

                    if (i%4 != 0) cuadrante = cuadrante - 3;
                    else cuadrante++;

                    for (j = 0; j < 16; j++) {
                        n = line.charAt(j) - 65;
                        grid[i][j] = n;

                        if (j%4 == 0 && j!= 0) cuadrante++;
                        
                        if (n != -20) {
                            cuadrantes[cuadrante][n] = true;
                            filas[i][n] = true;
                            columnas[j][n] = true;
                        }
                    }
                }

                //solve(0, 0, 0);
                solve2();

                // Output
                for (i = 0; i<16; i++) {
                    line = "";
                    for (j = 0; j < 16; j++) {
                        char a = (char) (grid[i][j] + 65);
                        line = line + a;
                    }
                    System.out.println(line);
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

    /*
    private static boolean solve(int i, int j, int cuadrante) {
        if (i == 16 && j == 0)
            return true;

        if (grid[i][j] != -20) {
            if (j == 15) {
                i++;
                j = 0;
                if (i%4 != 0) cuadrante = cuadrante - 3;
                else cuadrante++;
            } else {
                ++j;
                if (j%4 == 0) cuadrante++;
            }

            if (solve(i, j, cuadrante)) return true;
            return false;
        }

        // Obtener lista de posibles letras
        ArrayList<Integer> posibleLetters = new ArrayList<Integer>();
        for (int t = 0; t < 16; t++) {
            if (!filas[i][t] && !columnas[j][t] && !cuadrantes[cuadrante][t]) {
                posibleLetters.add(t);
            }
        }

        int k, l, m;
        for (int n : posibleLetters) {
            grid[i][j] = n;

            cuadrantes[cuadrante][n] = true;
            filas[i][n] = true;
            columnas[j][n] = true;

            k = i;
            l = j;
            m = cuadrante;
            if (j == 15) {
                k++;
                l = 0;
                if (k%4 != 0) m = m - 3;
                else m++;
            } else {
                ++l;
                if (l%4 == 0) m++;
            }
            if (solve(k, l, m)) return true;

            cuadrantes[cuadrante][n] = false;
            filas[i][n] = false;
            columnas[j][n] = false;
        }

        grid[i][j] = -20;
        return false;
    }*/

    private static void solve2() {
        int i, j, k;
        int cuadrante = -1;
        estado stack[][] = new estado[16][16];
         
        i=0;
        while (i<16) {
        //for (i = 0; i < 16; i++) {
            if (i%4 != 0) cuadrante = cuadrante - 3;
            else cuadrante++;

            j=0;
            while (j<16) {
            //for (j = 0; j < 16; j++) {
                if (stack[i][j] != null) {
                    int n = stack[i][j].nextLetter();
                    grid[i][j] = n;
                    cuadrante = stack[i][j].getCuadrante();
                    cuadrantes[cuadrante][n] = true;
                    filas[i][n] = true;
                    columnas[j][n] = true;
                    j++;
                    continue;
                }

                if (j%4 == 0 && j != 0) cuadrante++;
                if (grid[i][j] != -20) {
                    j++;
                    continue;
                }
                stack[i][j] = new estado(i, j, cuadrante);

                for (k = 0; k < 16; k++) {
                    if (!filas[i][k] && !columnas[j][k] && !cuadrantes[cuadrante][k]) {
                        stack[i][j].addLetter(k);
                    }
                }

                if (!stack[i][j].hasPosibleLetters()) {
                    stack[i][j] = null;
                    while(true) {
                        if (j==0) {
                            i--;
                            j = 15;
    
                        } else {
                            j--;
                        }

                        if (stack[i][j] == null) {
                            if (j==0) {
                                i--;
                                j = 15;
        
                            } else {
                                j--;
                            }
                            continue;
                        }
                        else if (stack[i][j].hasMoreLetters()) {
                            break;

                        } else {
                            grid[i][j] = -20;
                            int n = stack[i][j].getLetter();
                            cuadrantes[cuadrante][n] = false;
                            filas[i][n] = false;
                            columnas[j][n] = false;
                            stack[i][j] = null;
                        }
                    }

                } else {
                    int n = stack[i][j].getLetter();
                    grid[i][j] = n;
                    cuadrantes[cuadrante][n] = true;
                    filas[i][n] = true;
                    columnas[j][n] = true;
                    j++;
                }               
            }
            i++;
        }
    }

}

class estado {
    int i, j, cuadrante;
    ArrayList<Integer> posibleLetters;

    estado(int i, int j, int cuadrante) {
        this.i = i;
        this.j = j;
        this.cuadrante = cuadrante;
        posibleLetters = new ArrayList<Integer>();
    }

    public int getLetter() {
        return posibleLetters.get(0);
    }

    public int getCuadrante() {
        return cuadrante;
    }

    public void addLetter(int k) {
        posibleLetters.add(k);
    }

    public int nextLetter() {
        posibleLetters.remove(0);
        return posibleLetters.get(0);
    }

    public boolean hasPosibleLetters() {
        if (posibleLetters.isEmpty()) return false;
        return true;
    }

    public boolean hasMoreLetters() {
        if (posibleLetters.size() > 1) return true;
        else return false;
    }
}