//Print the shortest path for Dijkstra’s algorithm

import java.util.*;

public class DijkstraPath {
    static class Edge {
        int v;
        int w;
        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    static void dijkstra(int n, List<List<Edge>> graph, int src) {
        int[] dist = new int[n];
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{src, 0});

        while (!pq.isEmpty()) {
            int u = pq.poll()[0];
            if (visited[u]) continue;
            visited[u] = true;

            for (Edge e : graph.get(u)) {
                if (dist[u] + e.w < dist[e.v]) {
                    dist[e.v] = dist[u] + e.w;
                    parent[e.v] = u;
                    pq.add(new int[]{e.v, dist[e.v]});
                }
            }
        }

        // Print shortest distances and paths
        System.out.println("Shortest paths from source " + src + ":");
        for (int i = 0; i < n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println("Node " + i + " : unreachable");
            } else {
                System.out.print("Node " + i + " : distance = " + dist[i] + ", path = ");
                printPath(i, parent);
                System.out.println();
            }
        }
    }

    static void printPath(int v, int[] parent) {
        List<Integer> path = new ArrayList<>();
        for (int cur = v; cur != -1; cur = parent[cur]) {
            path.add(cur);
        }
        Collections.reverse(path);
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) System.out.print(" -> ");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int n = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int m = sc.nextInt();

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        System.out.println("Enter edges (u v w) for undirected graph:");
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w)); // remove for directed graph
        }

        System.out.print("Enter source vertex: ");
        int src = sc.nextInt();

        dijkstra(n, graph, src);

        sc.close();
    }
}


//Sample Input
// Enter number of vertices: 5
// Enter number of edges: 6
// Enter edges (u v w) for undirected graph:
// 0 1 2
// 0 2 4
// 1 2 1
// 1 3 7
// 2 4 3
// 3 4 1
// Enter source vertex: 0
