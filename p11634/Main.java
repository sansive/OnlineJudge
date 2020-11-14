/**
 * 11634 - Generate random numbers
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */
package p11634;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.Arrays;

class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            // Variables declaration
            int a, counter;
            boolean[] numbers = new boolean[10000];

            // For each test case
            while (true) {
                // Get a0
                a = sc.nextInt();

                // End condition
                if (a == 0)
                    break;

                // Algorithm's initialization
                Arrays.fill(numbers, false);
                counter = 0;
                // While we are getting different numbers
                while (!numbers[a]) {
                    // Mark number
                    numbers[a] = true;
                    // Calculate next number
                    a = (a * a) / 100;
                    a = a % 10000;
                    // Increase counter
                    counter++;
                }

                // Output
                System.out.println(counter);
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
