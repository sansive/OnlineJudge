package p10038;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.ArrayList;

class Main {
    static int n;

    public static void main (String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4*1024))) {
            // Get inputs
            while (sc.hasNextInt()) {
                // Get line of input
                n = sc.nextInt();
                int numbers[] = new int[n];
                for (int i=0; i<n; i++)
                    numbers[i] = sc.nextInt();
    
                // Output
                if (isJolly (numbers)) System.out.println("Jolly");
                else System.out.println("Not jolly");            
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    /**
     * It checks if a sequence of integers is a jolly jumper or not.
     * 
     * @param   numbers: sequence of integers
     * @return  true    If it is
     *          false   If not
     */
    static boolean isJolly (int numbers[]) {
        ArrayList<Integer> results = new ArrayList<Integer>();

        // Algorithm
        for(int i=0; i<n-1; i++) {
            int result = Math.abs(numbers[i] - numbers[i + 1]);
			if (result == 0) return false;
			if (result >= n) return false;
			if (results.contains(result)) return false;
			results.add(result);
        }

        return true;
    }
}