import java.util.*;

public class CycleDetectionBFS {
    static class Node {
        int vertex, parent;
        Node(int v, int p) {
            vertex = v;
            parent = p;
        }
    }

    public static boolean isCyclicBFS(List<List<Integer>> adj, int V) {
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (bfsCheck(adj, i, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean bfsCheck(List<List<Integer>> adj, int start, boolean[] visited) {
        Queue<Node> q = new LinkedList<>();
        visited[start] = true;
        q.add(new Node(start, -1));

        while (!q.isEmpty()) {
            Node node = q.poll();
            for (int neighbor : adj.get(node.vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    q.add(new Node(neighbor, node.vertex));
                } else if (neighbor != node.parent) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(1);
        adj.get(1).add(0);
        adj.get(1).add(2);
        adj.get(2).add(1);
        adj.get(2).add(3);
        adj.get(3).add(2);
        adj.get(3).add(4);
        adj.get(4).add(3);
        adj.get(4).add(1); // This edge creates a cycle
        adj.get(1).add(4);

        if (isCyclicBFS(adj, V))
            System.out.println("Cycle detected in the graph.");
        else
            System.out.println("No cycle detected in the graph.");
    }
}
