//Write a program to find the level of each node using BFS.

import java.util.*;

public class BFS_NodeLevels {
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

        // Input starting node
        System.out.print("Enter starting vertex: ");
        int start = sc.nextInt();

        // Perform BFS to find levels
        int[] levels = bfsLevels(graph, start);

        // Display levels
        System.out.println("Node Levels:");
        for (int i = 0; i < levels.length; i++) {
            System.out.println("Node " + i + " -> Level " + levels[i]);
        }

        sc.close();
    }

    static int[] bfsLevels(List<List<Integer>> graph, int start) {
        int[] level = new int[graph.size()];
        Arrays.fill(level, -1); // -1 means unvisited
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        level[start] = 0; // Starting node is level 0
        queue.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    level[neighbor] = level[node] + 1;
                    queue.add(neighbor);
                }
            }
        }
        return level;
    }
}


//sample input
// Enter number of vertices: 6
// Enter number of edges: 5
// Enter edges (u v):
// 0 1
// 0 2
// 1 3
// 2 4
// 4 5
// Enter starting vertex: 0