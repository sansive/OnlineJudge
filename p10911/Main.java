/**
 * 10911 - Forming Quiz Teams
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */
package p10911;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.Hashtable;

class Main {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
			int cases = 1;
			int N;

			while (true) {
				// Number of students
                N = sc.nextInt() * 2;
                
				// End condition
				if (N == 0) break;

				// Dinamic Programming to optimize the algorithm
                Hashtable<String, Double> dp = new Hashtable<String, Double>();

                // Array with all the coordinates
                Coord[] coords = new Coord[N];
				for (int i = 0; i < N; i++) {
					// It is not necessary to know the names
                    sc.next();                    
                    coords[i] = new Coord(sc.nextInt(), sc.nextInt());
				}

				// Output
				System.out.println(String.format("Case %d: %.2f", cases, getAnswer(coords, dp)));

				cases++;
			}

			sc.close();
			System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Recursive method
	 * 
	 * @param coords: array with the students' coordinates
	 * @param dp: Hashtable to implement dinamic programming
	 * @return minimum summation of the distances
	 */
	private static double getAnswer(Coord[] coords, Hashtable<String, Double> dp) {
		// Look if we have save the result before
		String key = toString(coords);
		if (dp.get(key) != null) return dp.get(key);

		// If we have only two coordinates left, calculate the
		// distance and stop the recursive calls
		if (coords.length == 2) {
			dp.put(key, getDistance(coords[0], coords[1]));
			return dp.get(key);
		}

		// Go over the array looking for the right partner
		double answer = Integer.MAX_VALUE;
		for (int i = 1; i < coords.length; i++) {
			Coord[] subcoords = getSubcoords(coords, i);
			double distance = getDistance(coords[0], coords[i]) + getAnswer(subcoords, dp);

			if (distance < answer) answer = distance;
		}

		// Save the result to implement dinamic programming
		dp.put(key, answer);

		return dp.get(key);
	}


	/**
	 * @param coords: array with the students' coordinates 
	 * @return representative string of the given coordinates
	 */
	static String toString(Coord[] coords) {
		String cadena  = "";
		for (Coord coord : coords)
			cadena += "(" + coord.getX() + "," + coord.getY() + ")-";

		return cadena;
	}


	/**
	 * @param coords: array with coordinates
	 * @param n: index to remove
	 * @return: array coords without the index 0 and n
	 */
	static Coord[] getSubcoords(Coord[] coords, int n) {
		Coord[] answer = new Coord[coords.length - 2];
		boolean found = false;

		for (int i = 1; i < coords.length; i++)
			if (i == n) found = true;
			else {
				if (!found) answer[i - 1] = coords[i];
				if (found) answer[i - 2] = coords[i];
			}

		return answer;
	}

	/**
	 * @param coord1: first coordinate
	 * @param coord2: second coordinate
	 * @return distance between the given coordinates
	 */
	static double getDistance(Coord coord1, Coord coord2) {
        int x = coord1.getX() - coord2.getX();
        int y = coord1.getY() - coord2.getY();

		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	// Class for the coordinates
	static class Coord {
		private Integer x, y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}
}