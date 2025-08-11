//Write a Program in java to find the Second Best Minimum Spanning Tree using Prim’s Algorithm.

import java.util.*;

class PrimEdge {
    int u, v, w;
    PrimEdge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

public class SecondBestMST_Prim {
    static int n;
    static List<List<PrimEdge>> adj;
    static boolean[] inMST;
    static int[][] maxEdgeInPath;

    static int primMST(List<PrimEdge> mstEdges) {
        inMST = new boolean[n];
        int[] key = new int[n];
        int[] parent = new int[n];

        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        key[0] = 0; // Start from vertex 0
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{0, 0});

        int totalCost = 0;

        while (!pq.isEmpty()) {
            int u = pq.poll()[0];
            if (inMST[u]) continue;
            inMST[u] = true;

            if (parent[u] != -1) {
                mstEdges.add(new PrimEdge(parent[u], u, key[u]));
                totalCost += key[u];
            }

            for (PrimEdge e : adj.get(u)) {
                if (!inMST[e.v] && e.w < key[e.v]) {
                    key[e.v] = e.w;
                    parent[e.v] = u;
                    pq.add(new int[]{e.v, e.w});
                }
            }
        }

        return totalCost;
    }

    static int findSecondBestMST(List<PrimEdge> mstEdges) {
        // Build MST adjacency matrix to find max edge in path
        int[][] mstAdj = new int[n][n];
        for (int[] row : mstAdj) Arrays.fill(row, -1);
        for (PrimEdge e : mstEdges) {
            mstAdj[e.u][e.v] = e.w;
            mstAdj[e.v][e.u] = e.w;
        }

        // Precompute max edge in path for all pairs (Floyd-Warshall style)
        maxEdgeInPath = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(maxEdgeInPath[i], -1);
            bfsMaxEdge(i, mstAdj);
        }

        int bestCost = Integer.MAX_VALUE;
        int mstCost = mstEdges.stream().mapToInt(e -> e.w).sum();

        // Check all edges not in MST
        for (int u = 0; u < n; u++) {
            for (PrimEdge e : adj.get(u)) {
                if (mstAdj[e.u][e.v] == e.w || mstAdj[e.v][e.u] == e.w) continue; // MST edge
                int newCost = mstCost - maxEdgeInPath[e.u][e.v] + e.w;
                if (newCost > mstCost && newCost < bestCost) {
                    bestCost = newCost;
                }
            }
        }

        return bestCost;
    }

    static void bfsMaxEdge(int start, int[][] mstAdj) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];
        q.add(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v = 0; v < n; v++) {
                if (mstAdj[u][v] != -1 && !visited[v]) {
                    visited[v] = true;
                    q.add(v);
                    maxEdgeInPath[start][v] = Math.max(maxEdgeInPath[start][u], mstAdj[u][v]);
                    maxEdgeInPath[v][start] = maxEdgeInPath[start][v];
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        n = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int m = sc.nextInt();

        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        System.out.println("Enter edges (u v w):");
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            adj.get(u).add(new PrimEdge(u, v, w));
            adj.get(v).add(new PrimEdge(v, u, w));
        }

        List<PrimEdge> mstEdges = new ArrayList<>();
        int mstCost = primMST(mstEdges);
        int secondBest = findSecondBestMST(mstEdges);

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