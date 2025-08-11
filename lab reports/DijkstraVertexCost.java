import java.util.*;

public class DijkstraVertexCost {

    static class Edge {
        int to, weight;
        Edge(int t, int w) {
            to = t;
            weight = w;
        }
    }

    static void dijkstra(List<List<Edge>> graph, int[] vertexCost, int src) {
        int V = graph.size();
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = vertexCost[src];

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{src, dist[src]});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int d = curr[1];

            if (d > dist[u]) continue;

            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int newDist = dist[u] + edge.weight + vertexCost[v];
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.add(new int[]{v, newDist});
                }
            }
        }

        System.out.println("Vertex\tDistance from Source");
        for (int i = 0; i < V; i++) {
            System.out.println(i + "\t" + dist[i]);
        }
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }

        // Define vertex costs
        int[] vertexCost = {2, 4, 3, 6, 1};

        // Add edges (undirected example)
        graph.get(0).add(new Edge(1, 5));
        graph.get(1).add(new Edge(0, 5));

        graph.get(0).add(new Edge(2, 2));
        graph.get(2).add(new Edge(0, 2));

        graph.get(1).add(new Edge(2, 1));
        graph.get(2).add(new Edge(1, 1));

        graph.get(1).add(new Edge(3, 3));
        graph.get(3).add(new Edge(1, 3));

        graph.get(2).add(new Edge(4, 4));
        graph.get(4).add(new Edge(2, 4));

        graph.get(3).add(new Edge(4, 2));
        graph.get(4).add(new Edge(3, 2));

        int source = 0;
        dijkstra(graph, vertexCost, source);
    }
}
