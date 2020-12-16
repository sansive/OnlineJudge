package p00146;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main {
    static String line;

    public static void main (String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4*1024))) {            
            // Get lines
            while (true) {
                line= sc.nextLine();

                // End condition
                if (line.equals("#")) break;

                // Output
                char arrayLine[] = line.toCharArray();
                if (nextPermutation(arrayLine)) System.out.println(line);
                else System.out.println("No Successor");
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    /**
     * It builds a successor word if te given word has it.
     * 
     * @param   arrayLine: word to find a succesor
     * @return  true    If it has a successor
     *          false   If not
     */
    static boolean nextPermutation(char arrayLine[]) {
        for (int i = arrayLine.length - 2; i>=0; i--) {
            // Check if the letters are in order
            if (arrayLine[i] < arrayLine[i+1]) {
                // Change all the letter behind the select one
                for (int j = arrayLine.length - 1; ; j--) {
                    if (arrayLine[j]>arrayLine[i]) {
                        char a = arrayLine[i];
                        arrayLine[i] = arrayLine[j];
                        arrayLine[j] = a;

                        i++;
                        j = arrayLine.length - 1;
                        while (i<j) {
                            a = arrayLine[i];
                            arrayLine[i] = arrayLine[j];
                            arrayLine[j] = a;

                            i++;
                            j--;
                        }

                        // Update the line
                        line = "";
                        for (int k=0; k<arrayLine.length; k++) {
                            line = line + arrayLine[k];
                        }
                        return true;
                    }
                }
            }
        }       
        
        return false;
    }
}