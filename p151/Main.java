/**
 * 151 - Power Crisis
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */

package p151;

import java.io.BufferedInputStream;
import java.util.Scanner;

class Main {

	public static void main(final String[] args) {
		try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4*1024))) {
			int N;

			// Get inputs
			while(true) {
				// Get number of regions
				N = sc.nextInt();
				
				// End condition
				if (N==0) break;
				else {
					// Output
					System.out.println(smallest(N));
				}
			}

			sc.close();
        	System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}


	/**
	 * It calculates the smallest number m that will ensure that region 13
	 * is the last region working.
	 * 
	 * @param N: number of regions
	 * 
	 * @return The smallest number m
	 */
	static int smallest(int N) {
		boolean regions[];
		int m = 1;
		int index;
		int counter;

		while(true) {
			index = 0;
			counter = N;
			regions = new boolean[N];

			while (true) {
				regions[index] = true;
				counter--;

				if (counter==0) break;

				// The pointer is moved m positions to the ringht,
				// skiping the ones that are already true
				for (int i=0; i<m; i++) {
					index++;
					if (index==N) index=0;
					if (regions[index]) i--;
				}

				while(true) {
					if (!regions[index]) break;
					index++;
					if (index==N) index=0;
				}
			}

			// If index points the 13th region --> end
			if (index==12) break;
			m++;
		}

		return m;
	}
}