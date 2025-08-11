//Write a program to perform DFS traversal on a dynamic graph from user.

import java.util.*;

public class DFS_DynamicGraph {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of vertices and edges
        System.out.print("Enter number of vertices: ");
        int vertices = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int edges = sc.nextInt();

        // Create adjacency list
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
            graph.get(v).add(u); // Undirected graph
        }

        // Input starting vertex
        System.out.print("Enter starting vertex for DFS: ");
        int start = sc.nextInt();

        boolean[] visited = new boolean[vertices];
        System.out.print("DFS Traversal: ");
        dfs(graph, start, visited);

        sc.close();
    }

    static void dfs(List<List<Integer>> graph, int node, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(graph, neighbor, visited);
            }
        }
    }
}



//Sample Input
// Enter number of vertices: 5
// Enter number of edges: 4
// Enter edges (u v):
// 0 1
// 0 2
// 1 3
// 2 4
// Enter starting vertex for DFS: 0