package src;
import java.io.*;
import java.util.*;

public class Graph {
    private final List<Node> nodes;
    private final List<Edge> edges;
    Random rand = new Random();
    public Graph(){
        this.nodes = new ArrayList<Node>();
        this.edges = new ArrayList<Edge>();
    }
    public Graph(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    private void addNode(int nodeId, String nodeName) {
        Node node = new Node(nodeId, nodeName);
        nodes.add(node);
    }

    private void addEdge(int laneId, int sourceLocNo, int destLocNo, int duration) {
        Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void createFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        String[] tabsTString;
        int val = 1;
        int ed = 1;
        line = br.readLine();
        tabsTString = line.split(",");

        while ((line = br.readLine()) != null) {
            tabsTString = line.split(",");
            if (tabsTString.length == 2) {
                addNode(val++, tabsTString[1]);
            } else if (tabsTString.length == 3) {
                addEdge(ed++, Integer.parseInt(tabsTString[0]) - 1,
                        Integer.parseInt(tabsTString[1]) - 1, Integer.parseInt(tabsTString[2]));
                addEdge(ed++, Integer.parseInt(tabsTString[1]) - 1,
                        Integer.parseInt(tabsTString[0]) - 1, Integer.parseInt(tabsTString[2]));
            }
        }
        br.close();
    }
    public void createRandom(){
        List<String> name = new ArrayList<>(List.of("A","B","C","D","E","F","G","H","I","J"));
        int j = 0;
        int nbVertex = rand.nextInt(10)+1;
        int nbEdge = rand.nextInt((nbVertex*(nbVertex-1))/2)+1;
        for (int i = 0; i < nbVertex; i++) {
            String nodeName = name.get(rand.nextInt(name.size()));
            addNode(i,nodeName);
            name.remove(nodeName);
        }
        while( j < nbEdge){
            int edgeFrom = nodes.get(rand.nextInt(nbVertex)).getId();
            int edgeTo = nodes.get(rand.nextInt(nbVertex)).getId();
            if(edgeFrom != edgeTo){
                addEdge(j++,edgeFrom,edgeTo,rand.nextInt(1000)+1);
            }
        }
    }

    public static int cycle(Graph graph){
        int longueur = 0;
        int dist = Integer.MAX_VALUE;
        Node v = graph.nodes.get(0);
        ArrayList<Node> visite = new ArrayList<>(graph.nodes);
        for(int i=0; i < graph.nodes.size(); i++){
            for(Edge e : graph.edges) {
                if (e.getSource() == v && visite.contains(e.getSource())) {
                    if (dist > e.getWeight() && e.getWeight() != 0) {
                        dist = e.getWeight();
                        v = e.getDestination();
                        visite.remove(e.getSource());
                        System.out.println(e.getDestination());
                    }
                }
            }
            /*System.out.println(longueur);*/
            longueur += dist;
        }
        return longueur;
    }

    public static int cycledGraphOptimalDistance(Graph graph){
        int dist;
        int dist2 = Integer.MAX_VALUE;
        int longueur = 0 ;
        Dijkstra dijkstra = new Dijkstra(graph);
        int chemin[][] = dijkstra.getDistanceMatrix(graph);
        for(int i = 0; i < chemin.length; i++){
            for (int j = 0; j < chemin[i].length; j++){
                dist = chemin[i][j];
                if(dist2>dist || dist != 0){
                    dist2 = dist;
                }
            }
            longueur += dist2;
        }
        return longueur;
    }
}
