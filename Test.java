import java.io.IOException;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Graph graph = new Graph();
        try {
            graph.createFromFile("Graph.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(graph.getNodes());
        System.out.println(graph.getEdges());
        Dijkstra dijkstra = new Dijkstra(graph);
        System.out.println(Arrays.deepToString(dijkstra.getDistanceMatrix(graph)));

    }
}