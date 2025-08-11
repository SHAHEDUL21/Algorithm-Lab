import java.util.*;

public class DistinctMST {
    static class Edge implements Comparable<Edge> {
        int u, v, w;
        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
        @Override
        public int compareTo(Edge other) {
            return this.w - other.w;
        }
    }

    static int find(int[] parent, int i) {
        if (parent[i] != i)
            parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    static boolean union(int[] parent, int[] rank, int u, int v) {
        int rootU = find(parent, u);
        int rootV = find(parent, v);
        if (rootU == rootV) return false;
        if (rank[rootU] < rank[rootV]) parent[rootU] = rootV;
        else if (rank[rootU] > rank[rootV]) parent[rootV] = rootU;
        else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }
        return true;
    }

    static int mstWeight(List<Edge> edges, int V) {
        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++) parent[i] = i;
        Collections.sort(edges);
        int weight = 0, count = 0;
        for (Edge e : edges) {
            if (union(parent, rank, e.u, e.v)) {
                weight += e.w;
                count++;
                if (count == V - 1) break;
            }
        }
        return weight;
    }

    static int countDistinctMSTs(List<Edge> edges, int V) {
        int baseWeight = mstWeight(edges, V);
        int distinctCount = 0;
        int E = edges.size();
        for (int mask = 0; mask < (1 << E); mask++) {
            List<Edge> subset = new ArrayList<>();
            for (int i = 0; i < E; i++) {
                if ((mask & (1 << i)) != 0) subset.add(edges.get(i));
            }
            if (subset.size() == V - 1) {
                if (isSpanningTree(subset, V) && mstWeight(subset, V) == baseWeight) {
                    distinctCount++;
                }
            }
        }
        return distinctCount;
    }

    static boolean isSpanningTree(List<Edge> subset, int V) {
        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++) parent[i] = i;
        int edgesUsed = 0;
        for (Edge e : subset) {
            if (union(parent, rank, e.u, e.v)) {
                edgesUsed++;
            }
        }
        return edgesUsed == V - 1;
    }

    public static void main(String[] args) {
        int V = 4;
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 1));
        edges.add(new Edge(0, 2, 1));
        edges.add(new Edge(1, 2, 1));
        edges.add(new Edge(1, 3, 2));
        edges.add(new Edge(2, 3, 2));

        int count = countDistinctMSTs(edges, V);
        System.out.println("Number of distinct MSTs: " + count);
    }
}
