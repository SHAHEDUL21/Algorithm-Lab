
//Write a program to find the path from source to destination using DFS
import java.util.*;

public class DFS_PathFinder {
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

        // Input source and destination
        System.out.print("Enter source vertex: ");
        int source = sc.nextInt();

        System.out.print("Enter destination vertex: ");
        int destination = sc.nextInt();

        boolean[] visited = new boolean[vertices];
        List<Integer> path = new ArrayList<>();

        if (dfsFindPath(graph, source, destination, visited, path)) {
            System.out.println("Path from " + source + " to " + destination + ": " + path);
        } else {
            System.out.println("No path exists between " + source + " and " + destination);
        }

        sc.close();
    }

    static boolean dfsFindPath(List<List<Integer>> graph, int current, int destination,
                                boolean[] visited, List<Integer> path) {
        visited[current] = true;
        path.add(current);

        if (current == destination) {
            return true; // Path found
        }

        for (int neighbor : graph.get(current)) {
            if (!visited[neighbor]) {
                if (dfsFindPath(graph, neighbor, destination, visited, path)) {
                    return true;
                }
            }
        }

        // Backtrack
        path.remove(path.size() - 1);
        return false;
    }
}

//Sample Input
// Enter number of vertices: 6
// Enter number of edges: 6
// Enter edges (u v):
// 0 1
// 0 2
// 1 3
// 2 4
// 4 5
// 3 5
// Enter source vertex: 0
// Enter destination vertex: 5