/**
 * 10670 - Work Reduction
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */
package p10670;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.Arrays;

class Main {
    static Agency[] agencies;
    static int M;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            int n_case = sc.nextInt();
            int cont = 1;

            int N, L, i, A, B;
            String line, name;

            // For each test case
            while (cont <= n_case) {
                // Get workload
                N = sc.nextInt();
                // Get target workload
                M = sc.nextInt();
                // Get number of work reduction agencies available
                L = sc.nextInt();

                // Get agency's rates
                sc.nextLine();
                agencies = new Agency[L];
                for (i = 0; i < L; i++) {
                    line = sc.nextLine();
                    Scanner sc_line = new Scanner(line).useDelimiter(":");
                    name = sc_line.next();
                    line = sc_line.next();
                    sc_line.close();
                    Scanner sc_line2 = new Scanner(line).useDelimiter(",");
                    A = Integer.parseInt(sc_line2.next());
                    B = Integer.parseInt(sc_line2.next());
                    sc_line2.close();

                    agencies[i] = new Agency(name, A, B);
                }

                // Calculate the minimum cost for each agency
                for (i = 0; i < L; i++)
                    agencies[i].setMinimumCost(calculateMinCost(N, agencies[i]));

                // Sort the array according to their minimum cost
                Arrays.sort(agencies);

                // Output
                System.out.println("Case " + cont);
                for (i = 0; i < L; i++)
                    System.out.println(agencies[i].getName() + " " + agencies[i].getMinimumCost());

                // Next test case
                cont++;
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * It calculates the minimum cost for the given agency and the given workload
     * 
     * @param N:  workload
     * @param a:  agency
     * @return minimum cost
     */
	static int calculateMinCost(int N, Agency ag) {
        if (N == M) return 0;
        
		int half = N / 2;
		if (half >= M)
			return (Math.min(ag.getB(), ag.getA() * (N - half)) + calculateMinCost(half, ag));
		else
			return (ag.getA() + calculateMinCost(N - 1, ag));
    }
    
    /**
     * Object class to save the agencies' information
     */
	static class Agency implements Comparable<Agency> {
		String name;
		int A, B, minimumCost;

		Agency(String name, int A, int B) {
			this.name = name;
			this.A = A;
            this.B = B;
        }
        
        public String getName() {
            return name;
        }

        public int getA() {
            return A;
        }

        public int getB() {
            return B;
        }

        public int getMinimumCost() {
            return minimumCost;
        }

        public void setMinimumCost(int n) {
            minimumCost = n;
        }

        @Override
        public int compareTo (Agency ag) {
            if (this.minimumCost < ag.getMinimumCost())
                return -1;

            else if (this.minimumCost > ag.getMinimumCost())
                return 1;

            else {
                return this.name.compareTo(ag.getName());
            }
        }
	}
}