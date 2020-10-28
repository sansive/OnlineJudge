/**
 * 10099 - The Tourist Guide
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */
package p10099;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.PriorityQueue;

class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4 * 1024))) {
            int scenario_number = 0;
            int N, R, A, B, passengerLimit, start, end, tourists, max, trips;
            Vertex[] vertices;

            while (true) {
                scenario_number++;

                N = sc.nextInt();
                R = sc.nextInt();

                // End of input condition
                if (N == 0 && R == 0)
                    break;

                // Create an array with the graph's vertices (cities)
                vertices = new Vertex[N];
                for (int i = 0; i < N; i++)
                    vertices[i] = new Vertex();

                // Add edges (roads) to the vertices
                for (int i = 0; i < R; i++) {
                    A = sc.nextInt() - 1;
                    B = sc.nextInt() - 1;
                    passengerLimit = sc.nextInt();
                    vertices[A].addEdge(new Edge(A, B, passengerLimit));
                    vertices[B].addEdge(new Edge(B, A, passengerLimit));
                }

                start = sc.nextInt() - 1;
                end = sc.nextInt() - 1;
                tourists = sc.nextInt();

                // Calculate the necessary number of trips
                trips = 0;
                if (start != end) {
                    max = maxTourists(vertices, start, end);
                    if (max > 1 && tourists != 0) {
                        while (tourists > 0) {
                            // The guide must be included on the passengers
                            tourists = tourists - max + 1;
                            trips++;
                        }
                    }
                }

                // Output
                System.out.println("Scenario #" + scenario_number);
                System.out.println("Minimum Number of Trips = " + trips);
                System.out.println();
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Calculate the maximun number of passengers that can 
     * go on a trip.
     * 
     * @param vertices: cities
     * @param start: departure city
     * @param end: arrival city
     * @return max
     */
    private static int maxTourists(Vertex[] vertices, int start, int end) {
        int max = 0;
        Edge e;
        int vertex;
        ArrayList<Edge> newEdges = vertices[start].getEdges();
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>();

        vertices[start].visit();
        for (Edge edge: newEdges) {
            edge.use();
            queue.add(edge);
        }

        while (true) {
            // Get edge with the higher passenger limit
            e = queue.poll();
            vertex = e.getA();

            if (vertices[vertex].isVisited())
                vertex = e.getB();
            
            if (!vertices[vertex].isVisited())
                vertices[vertex].setCount(e.getPassengerLimit());

            if (vertex == end) {
                max = vertices[vertex].getCount();
                break;
            }
            
            vertices[vertex].visit();
            newEdges = vertices[vertex].getEdges();
            for (Edge edge: newEdges) {
                if (!edge.isUsed()) {
                    // Change edge's passenger limit if it is
                    // higher than the vertex's count
                    edge.changeLimit(vertices[vertex].getCount());
                    edge.use();
                    queue.add(edge);
                }
            }
        }

        return max;
    }
}

class Vertex {
    boolean visited;
    int count;
    ArrayList<Edge> edges;

    Vertex() {
        visited = false;
        count = 0;
        edges = new ArrayList<Edge>();
    }

    int getCount() {
        return count;
    }

    ArrayList<Edge> getEdges() {
        return edges;
    }

    void setCount(int count) {
        this.count = count;
    }    

    void addEdge(Edge e) {
        edges.add(e);
    }

    void visit() {
        visited = true;
    }

    boolean isVisited() {
        return visited;
    }    
}

class Edge implements Comparable<Edge> {
    int A, B;
    Integer passengerLimit;
    boolean used;

    Edge(int A, int B, int passengerLimit) {
        this.A = A;
        this.B = B;
        this.passengerLimit = passengerLimit;
        used = false;
    }

    int getA() {
        return A;
    }

    int getB() {
        return B;
    }

    int getPassengerLimit() {
        return passengerLimit;
    }

    void use() {
        used = true;
    }

    boolean isUsed() {
        return used;
    }

    void changeLimit(int c) {
        this.passengerLimit = Math.min(passengerLimit, c);
    }

    @Override
    public int compareTo(Edge e) {
        return e.passengerLimit.compareTo(passengerLimit);
    }
}