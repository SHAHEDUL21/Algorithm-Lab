//Prove that, if there exists negative edge, Dijkstra’s shortest path algorithm may fail to find the shortest path. Please add Bellman Ford Algorithm

import java.util.*;

public class BellmanFord {
    static class Edge {
        int u, v;
        long w;
        Edge(int u, int v, long w) {
            this.u = u; this.v = v; this.w = w;
        }
    }

    // Returns true if there is no negative cycle reachable from source.
    // If false, a negative cycle exists (reachable from the source).
    static boolean bellmanFord(int n, List<Edge> edges, int src, long[] dist, int[] parent) {
        final long INF = Long.MAX_VALUE / 4;
        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        // Relax edges n-1 times
        for (int i = 1; i <= n - 1; i++) {
            boolean updated = false;
            for (Edge e : edges) {
                if (dist[e.u] != INF && dist[e.u] + e.w < dist[e.v]) {
                    dist[e.v] = dist[e.u] + e.w;
                    parent[e.v] = e.u;
                    updated = true;
                }
            }
            // optional early stop
            if (!updated) break;
        }

        // Check for negative-weight cycles
        for (Edge e : edges) {
            if (dist[e.u] != INF && dist[e.u] + e.w < dist[e.v]) {
                return false; // negative cycle detected
            }
        }
        return true;
    }

    // Reconstruct path from src to dest using parent[], returns list of nodes or empty if unreachable.
    static List<Integer> reconstructPath(int src, int dest, int[] parent) {
        List<Integer> path = new ArrayList<>();
        if (parent[dest] == -1 && src != dest) {
            // could still be reachable if src==dest
            if (src == dest) path.add(src);
            return path; // unreachable except when src == dest
        }
        int cur = dest;
        while (cur != -1) {
            path.add(cur);
            if (cur == src) break;
            cur = parent[cur];
        }
        Collections.reverse(path);
        if (path.get(0) != src) return new ArrayList<>(); // not reachable
        return path;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int n = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int m = sc.nextInt();

        List<Edge> edges = new ArrayList<>();
        System.out.println("Enter edges (u v w) -- 0-based node indices:");
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            long w = sc.nextLong();
            edges.add(new Edge(u, v, w));
        }

        System.out.print("Enter source vertex: ");
        int src = sc.nextInt();

        long[] dist = new long[n];
        int[] parent = new int[n];

        boolean ok = bellmanFord(n, edges, src, dist, parent);

        if (!ok) {
            System.out.println("Graph contains a negative-weight cycle reachable from source.");
        } else {
            System.out.println("No negative-weight cycle detected (reachable from source).");
            System.out.println("Shortest distances from source " + src + ":");
            for (int i = 0; i < n; i++) {
                if (dist[i] == Long.MAX_VALUE / 4) {
                    System.out.println("Node " + i + " : unreachable");
                } else {
                    System.out.println("Node " + i + " : " + dist[i]);
                }
            }

            // Optionally ask for a destination to print path(s)
            System.out.print("Enter destination vertex to print path (or -1 to skip): ");
            int dest = sc.nextInt();
            if (dest != -1) {
                List<Integer> path = reconstructPath(src, dest, parent);
                if (path.isEmpty()) {
                    System.out.println("No path from " + src + " to " + dest + " (or unreachable).");
                } else {
                    System.out.println("Path from " + src + " to " + dest + " : " + path);
                }
            }
        }

        sc.close();
    }
}


//Sample Input
// Enter number of vertices: 3
// Enter number of edges: 3
// Enter edges (u v w) -- 0-based node indices:
// 0 1 1
// 1 2 -3
// 0 2 4
// Enter source vertex: 0
// Enter destination vertex to print path (or -1 to skip): 2