package p00120;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class Main {
    static ArrayList<Integer> stack;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            String line, flips;
            int index, i, j;
            
            while (sc.hasNextInt()) {
                stack = new ArrayList<Integer>();
                flips = "";                

                // Fill the stack with the pancakes
                line = sc.nextLine();
                Scanner sc_line = new Scanner(line);                
                while (sc_line.hasNextInt())
                    stack.add(sc_line.nextInt());
                sc_line.close();
                // Reverse the stack so the top of it is in the higest
                // position on the ArrayList
                Collections.reverse(stack);

                for (i=0; i<stack.size()-1 && !isSorted(); i++) {
                    // Check if the pancake is well positioned
                    index = i;
                    for (j = i+1; j<stack.size(); j++)
                        if (stack.get(j)>stack.get(index))
                            index = j;

                    // If it is not
                    if (index != i) {
                        // Flip the selected pancake onto the top
                        if (index != stack.size()-1) {
                            Collections.reverse(stack.subList(index, stack.size()));
                            flips = flips + (index+1) + " ";
                        }

                        // Flip the selected pancake from top to position i
                        if (!isSorted()) {
                            Collections.reverse(stack.subList(i, stack.size()));
                            flips = flips + (i+1) + " ";
                        }
                    }
                }

                // Output
                System.out.println(line);
                System.out.println(flips + "0");
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * It returns wether the stack is sorted or not
     * 
     * @return  True: If it is
     *          False: If not
     */
    private static boolean isSorted() {
        for (int i=0; i<stack.size()-1; i++)
            if (stack.get(i)<stack.get(i+1)) return false;

        return true;
    }
}