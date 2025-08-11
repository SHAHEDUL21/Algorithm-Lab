//Write a program to perform BSF traversal on a dynamic graph from user


import java.util.*;

public class BFS_DynamicGraph {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of vertices and edges
        System.out.print("Enter number of vertices: ");
        int vertices = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int edges = sc.nextInt();

        // Adjacency list representation
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }

        // Input edges
        System.out.println("Enter edges (u v):");
        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u); // For undirected graph
        }

        // BFS traversal
        System.out.print("Enter starting vertex for BFS: ");
        int start = sc.nextInt();

        bfs(graph, start);

        sc.close();
    }

    static void bfs(List<List<Integer>> graph, int start) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        System.out.print("BFS Traversal: ");
        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }
}

// Sample Input:
// Enter number of vertices: 5
// Enter number of edges: 4
// Enter edges (u v):
// 0 1
// 0 2
// 1 3
// 2 4
// Enter starting vertex for BFS: 0
