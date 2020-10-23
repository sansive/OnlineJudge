/**
 * 100 - The 3n + 1 problem
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */

package p100;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main {

	public static void main (String[] args) {
		try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4*1024))) {
			int a;
			int b;
			int max;
			int from;
			int to;
			
			// Get inputs
			while (sc.hasNextInt()) {
				a = sc.nextInt();
				b = sc.nextInt();
				max = 0;

				// Determine the actual order of the integers (a, b)
				from = Math.min(a, b);
				to = Math.max(a, b);

				// Get cycle length for all numbers between a and b
				// max = maximum cycle length
				for (int i=from; i<=to; i++) {
					max = Math.max(max, cycleLength(i));
				}

				// Output
				System.out.println(a + " " + b + " " + max);
			}

			sc.close();
        	System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}	
	
	/**
	 * It calculates the cycle length for a given integer.
	 * 
	 * @param	a: number
	 * @return	Cycle length
	 */
	static int cycleLength (int a) {
		int counter = 1;
		
		// Algorithm implementation
		while (a!=1) {
			if (a%2 == 0) a = a/2;
			else a = 3*a + 1;
			counter++;
		}
		
		return counter;
	}
}