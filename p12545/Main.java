/**
 * 12545 - Bits Equalizer
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */
package p12545;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main {
	public static void main (String[] args) {
		try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4*1024))) {
			String S, T;
			int C, i, j, change0, change1, changeI, changeI_1, moves;
			
			// Get number of test cases
			C = sc.nextInt();

			// For each test case
  			for (i = 1; i <= C; i++) {			
				S = sc.next();
				T = sc.next();
				change0 = change1 = changeI = changeI_1 = 0;

				// Go over the strings looking for the differences
				for (j = 0; j < S.length(); j++) {
					if (S.charAt(j) == '0' && T.charAt(j) == '1') change0++;
					else if (S.charAt(j) == '1' && T.charAt(j) == '0') change1++;
					else if (S.charAt(j) == '?' && T.charAt(j) == '0') changeI++;
					else if (S.charAt(j) == '?' && T.charAt(j) == '1') {
						changeI_1++;
						changeI++;
					}
				}
			
				// As we can´t change 1's into 0's
				if (change1 > change0 + changeI_1) moves = -1;
				else moves = Math.max(change0, change1) + changeI;

				// Output
				System.out.println("Case " + i + ": " + moves);
			}

			sc.close();
        	System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}