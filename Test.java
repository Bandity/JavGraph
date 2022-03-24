import java.io.IOException;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Graph graph = new Graph();
        Graph graph2 = new Graph();
        try {
            graph.createFromFile("Graph.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(graph.getNodes());
        System.out.println(graph.getEdges());
        Dijkstra dijkstra = new Dijkstra(graph);
        System.out.println(Arrays.deepToString(dijkstra.getDistanceMatrix()));
        graph.isConnected();
        System.out.println(graph.optimalCycle(1));
        System.out.println(dijkstra.getPath(graph.getNodes().get(0)));
        /*
        graph2.createRandom(100);
        System.out.println(graph2.getNodes());
        graph2.isConnected();
        System.out.println(graph2.optimalCycle(1));
        */
    }
}