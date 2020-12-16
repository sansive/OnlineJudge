package p00410;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Locale;

class Main {
    public static void main(final String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            // Counter of sets
            int n_set = 0;

            // Variables declaration
            int C, S, sum, smallest, bigest;
            double IMBALANCE, AM, CM;

            // For each set
            while (sc.hasNext()) {
                n_set++;

                // Get number of chambers
                C = sc.nextInt();
                // Get number of specimens
                S = sc.nextInt();

                // Initialize the total mass in the set
                sum = 0;
                // Masses of the specimens in the set
                int[] masses = new int[2*C];
                for (int i = 0; i < S; i++) {
                    masses[i] = sc.nextInt();
                    sum = sum + masses[i];
                }
                // Since we can have 2 specimens per chamber maximum
                for (int i=S; i<2*C; i++) {
                    masses[i] = 0;
                }
                Arrays.sort(masses);           

                // First line of output
                System.out.println("Set #" + n_set);
                IMBALANCE = 0;
                // Calculate the Average Mass of the chambers                
                AM = (double)sum / C;

                // Greedy algorithm: pair up the smallest with the bigest
                for (int i = 0; i < C; i++) {
                    String chamber = " " + i + ":";

                    smallest = masses[i];
                    bigest = masses[2*C - 1 - i];

                    if (smallest != 0) {
                        chamber = chamber + " " + smallest;
                        if (bigest != 0)    chamber = chamber + " " + bigest;

                    } else {
                        if (bigest != 0)    chamber = chamber + " " + bigest; 
                    }

                    // Output of chamber
                    System.out.println(chamber);

                    // Expression
                    CM = smallest + bigest;
                    IMBALANCE = IMBALANCE + Math.abs(CM - AM);
                }

                // Two last lines of output
                System.out.println("IMBALANCE = " + String.format(Locale.US, "%.5f", IMBALANCE));
                System.out.println();
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}