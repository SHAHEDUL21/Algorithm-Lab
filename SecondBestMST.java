//Write a Program in java to find the Second Best Minimum Spanning Tree using Kruskal 

import java.util.*;

class Edge implements Comparable<Edge> {
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

public class SecondBestMST {
    static int[] parent;

    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static boolean union(int a, int b) {
        int pa = find(a);
        int pb = find(b);
        if (pa == pb) return false;
        parent[pa] = pb;
        return true;
    }

    static int kruskal(List<Edge> edges, int n, Set<Edge> bannedEdge, List<Edge> mstEdges) {
        Collections.sort(edges);
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int cost = 0, count = 0;
        mstEdges.clear();

        for (Edge e : edges) {
            if (bannedEdge.contains(e)) continue;
            if (union(e.u, e.v)) {
                cost += e.w;
                mstEdges.add(e);
                count++;
                if (count == n - 1) break;
            }
        }
        return (count == n - 1) ? cost : Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input graph
        System.out.print("Enter number of vertices: ");
        int n = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int m = sc.nextInt();

        List<Edge> edges = new ArrayList<>();
        System.out.println("Enter edges (u v w):");
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            edges.add(new Edge(u, v, w));
        }

        // Step 1: Find MST
        List<Edge> mstEdges = new ArrayList<>();
        int mstCost = kruskal(edges, n, Collections.emptySet(), mstEdges);

        // Step 2: Find second best MST
        int secondBest = Integer.MAX_VALUE;

        for (Edge removed : mstEdges) {
            Set<Edge> banned = new HashSet<>();
            banned.add(removed);
            List<Edge> tempMST = new ArrayList<>();
            int cost = kruskal(edges, n, banned, tempMST);
            if (cost > mstCost && cost < secondBest) {
                secondBest = cost;
            }
        }

        System.out.println("Minimum Spanning Tree cost: " + mstCost);
        if (secondBest == Integer.MAX_VALUE) {
            System.out.println("No Second Best MST exists.");
        } else {
            System.out.println("Second Best MST cost: " + secondBest);
        }

        sc.close();
    }
}


//Sample Input
// Enter number of vertices: 4
// Enter number of edges: 5
// Enter edges (u v w):
// 0 1 1
// 0 2 2
// 1 2 3
// 1 3 4
// 2 3 5