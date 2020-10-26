/**
 * 1112 - Mice and Maze
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */
package p1112;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.PriorityQueue;

class Main {
    static int[] weights;
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            int test_cases = sc.nextInt();
            int N, E, T, M;
            ArrayList<ArrayList<IntegerPair>> adjacents;

            while (test_cases > 0) {
                // Get number of cells (vertices)
                N = sc.nextInt();
                weights = new int[N];
                adjacents = new  ArrayList<ArrayList<IntegerPair>>();
                for (int i = 0; i < N; i++)
                    adjacents.add(new ArrayList<IntegerPair>());                

                // Get exit cell
                E = sc.nextInt() - 1;

                // Get time units
                T = sc.nextInt();

                // Get connections (edges) and their weight
                M = sc.nextInt();
                int v,u,w;
                for (int i = 0; i < M; i++) {
                    v = sc.nextInt() - 1;
                    u  = sc.nextInt() - 1;
                    w = sc.nextInt();
                    adjacents.get(u).add(new IntegerPair(v, w));
                }                              
                
                // Get minimun weights to the exit cell
                dijkstra(E, adjacents);

                // Count how many weights are less than T
                int counter = 0;
                for (int i = 0; i < N; i++)
                    if (weights[i] <= T) counter++;

                // Output
                System.out.println(counter);
                if (test_cases != 1) System.out.println();

                test_cases --;
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * It calculates the minimum weights between all the cells and the exit cell
     * following dijkstra's algorithm.
     * 
     * @param E: inicial vertex (exit cell)
     * @param adjacents: graph's edges
     */
    private static void dijkstra(int E, ArrayList<ArrayList<IntegerPair>> adjacents) {
        PriorityQueue<IntegerPair> vertices = new PriorityQueue<IntegerPair>();
        // Initial values
        for (int i=0; i<weights.length; i++) {
            if (i == E) weights[i] = 0;
            else weights[i] = 10000000;
        }
        vertices.add(new IntegerPair(E, 0));

        while (true) {
            // Choose the vertex with the lowest weight
            IntegerPair selectedVertex = vertices.poll();
            // If vertices is empty it has finished
            if (selectedVertex == null) break;

            int w = selectedVertex.weight;
            int v = selectedVertex.vertex;

            // The adjacent vertices' weight is updated if the sum of the current
            // weight plus the new weight is strictly smaller. 
            for (int i=0; i<adjacents.get(v).size(); i++) {
                IntegerPair adjacent = adjacents.get(v).get(i);
                int vertex = adjacent.vertex;
                int weight = adjacent.weight;
                if (w + weight < weights[vertex]) {
                    weights[vertex] = w + weight;
                    vertices.add(new IntegerPair(vertex, weights[vertex]));
                }

            }
           
        }
    }
}

class IntegerPair implements Comparable<IntegerPair> {
    int vertex, weight;

    IntegerPair(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public int compareTo(IntegerPair otherVertex) {
        if (this.weight != otherVertex.weight)
            return this.weight - otherVertex.weight;
        return this.vertex - otherVertex.vertex;
    }
}