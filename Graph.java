import java.io.*;
import java.util.*;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
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

    private void addEdge(int edgeId, int source, int destination, int weight) {
        Edge edge = new Edge(edgeId, nodes.get(source), nodes.get(destination), weight);
        edges.add(edge);
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
        int id = 1;
        line = br.readLine();
        tabsTString = line.split(",");

        while ((line = br.readLine()) != null) {
            tabsTString = line.split(",");
            if (tabsTString.length == 2) {
                addNode(val++, tabsTString[1]);
            } else if (tabsTString.length == 3) {
                addEdge(id++, Integer.parseInt(tabsTString[0]) - 1,
                        Integer.parseInt(tabsTString[1]) - 1, Integer.parseInt(tabsTString[2]));
                addEdge(id++, Integer.parseInt(tabsTString[1]) - 1,
                        Integer.parseInt(tabsTString[0]) - 1, Integer.parseInt(tabsTString[2]));
            }
        }
        br.close();
    }

    private List<String> nodeNameGenerator(int numOfNodes) {
        List<String> nodesNames = new ArrayList<String>();
        for (int i = 0; i < numOfNodes; i++) {
            if (i + 65 <= 90)
                nodesNames.add(Character.toString((char) i + 65));
            else
                nodesNames.add(i + "");
        }
        return nodesNames;
    }

    private void generateNodes(int numOfNodes) {
        List<String> nodeNames = nodeNameGenerator(numOfNodes);
        for (int i = 0; i < numOfNodes; i++) {
            addNode(i, nodeNames.get(i));
        }
    }

    public void createRandom(int maxNodes) {
        if (maxNodes > 0) {
            Random rand = new Random();
            int numOfNodes = rand.nextInt(maxNodes);
            int numOfEdges = rand.nextInt(((numOfNodes * (numOfNodes - 1)) / 2) + 1);
            generateNodes(numOfNodes);

            int i = 0;
            int edgeFrom = 0, edgeTo = 0;
            while (i < numOfEdges && isConnected()) {
                while (edgeFrom == edgeTo) {
                    edgeFrom = nodes.get(rand.nextInt(numOfNodes)).getId();
                    edgeTo = nodes.get(rand.nextInt(numOfNodes)).getId();
                }
                addEdge(i, edgeFrom, edgeTo, rand.nextInt((int) Integer.MAX_VALUE / 200));
                addEdge(i, edgeTo, edgeFrom, rand.nextInt((int) Integer.MAX_VALUE / 200));

                i++;
            }
        }

    }

    private void DFS(int source, List<Node> adjList, boolean[] visited) {
        Node nodeSource = nodes.get(source);
        visited[source] = true;
        List<Node> neighbors = new ArrayList<Node>();

        // Parcour des voisins
        for (Edge edge : edges) {
            if (edge.getSource().equals(nodeSource) && !visited[edge.getDestination().getId() - 1]) {
                neighbors.add(edge.getDestination());
                for (int i = 0; i < neighbors.size(); i++) {
                    if (!visited[neighbors.get(i).getId() - 1]) {
                        visited[neighbors.get(i).getId() - 1] = true;
                        DFS(neighbors.get(i).getId() - 1, adjList, visited);
                    }
                }
            }
        }
    }
    private Map<Node,Integer> getNeighbors(Node nodeSource){
        Map<Node,Integer> neighbors = new HashMap<Node,Integer>();
        for(Edge edge : edges){
            if(edge.getSource().equals(nodeSource)){
                neighbors.put(edge.getDestination(), edge.getWeight());
            }
        }
        return neighbors;
    }
    public int optimalCycle( int source){
        if(!isConnected()){
            return -1;
        }
        int length = 0;
        Node nodeSource = nodes.get(source-1);
        Map<Node, Integer> distance =  getNeighbors(nodeSource);
        int minDist = Integer.MAX_VALUE;
        int count = 0;
        boolean[] visited = new boolean[nodes.size()];
        visited[source-1] = true;

        while(count != getNodes().size()){
            System.out.println(nodeSource);
            for(Node node : distance.keySet()){
                if(distance.get(node) < minDist && !visited[node.getId()-1]){
                    minDist = distance.get(node) ;
                    nodeSource=node;
                    System.out.println(minDist);
                    System.out.println(getNeighbors(node));
                    System.out.println(node);
                }
                
            }
            System.out.println(minDist);
            distance =  getNeighbors(nodeSource);
            length += minDist;
            visited[nodeSource.getId()-1]=true;
            System.out.println(Arrays.toString(visited));
            int check =0;
            for(int i=0; i<visited.length; i++) {
                if(visited[i]){
                    check++;
                    if(check == visited.length){
                        return length;
                    }
                }
                if(check < visited.length-1){
                    minDist = Integer.MAX_VALUE;
                }
                
            }
            
            count++;
            System.out.println("");
        }
        
        return length;
    }
    public boolean isConnected() {

        int lenNodes = nodes.size();
        List<Node> adjList = nodes;

        // created visited array
        boolean[] visited = new boolean[lenNodes];
        // Commence la premi??re recherche
        DFS(0, adjList, visited);
        System.out.println(Arrays.toString(visited));

        // verifie que tous les noeux ont ??t?? visit??s
        int count = 0;
        for (int i = 0; i < visited.length; i++) {
            if (visited[i])
                count++;
        }
        if (lenNodes == count) {
            System.out.println("Le graphe est connexe");
            return true;
        }

        System.out.println("Le graphe n'est connexe");
        return false;
    }

    public static int cycle(Graph graph) {
        int longueur = 0;
        int dist = Integer.MAX_VALUE;
        Node v = graph.nodes.get(0);
        ArrayList<Node> visite = new ArrayList<>(graph.nodes);
        for (int i = 0; i < graph.nodes.size(); i++) {
            for (Edge e : graph.edges) {
                if (e.getSource() == v && visite.contains(e.getSource())) {
                    if (dist > e.getWeight() && e.getWeight() != 0) {
                        dist = e.getWeight();
                        v = e.getDestination();
                        visite.remove(e.getSource());
                        System.out.println(e.getDestination());
                    }
                }
            }
            /* System.out.println(longueur); */
            longueur += dist;
        }
        return longueur;
    }
/*
    public static int optimalCycle(Graph graph) {
        int distance;
        int tempDistance = Integer.MAX_VALUE;
        int length = 0;
        Dijkstra dijkstra = new Dijkstra(graph);
        int distanceMatrix[][];
        int lenNodes = graph.getNodes().size();
        boolean[] visited = new boolean[lenNodes];
        int count = 0;

        Node source = graph.getNodes().get(0);
        visited[source.getId() - 1] = true;
        while (count < lenNodes) {
            dijkstra.execute(source);
            visited[source.getId() - 1] = true;
            distanceMatrix = dijkstra.getDistanceMatrix(source);
            System.out.println(Arrays.toString(distanceMatrix[0]));
            for (int j = 0; j < distanceMatrix[0].length; j++) {
                distance = distanceMatrix[0][j];
                if (tempDistance > distance && j != graph.getNodes().get(source.getId()-1).getId()-1 && !visited[j]) {
                    tempDistance = distance;
                    source = graph.getNodes().get(j);
                    
                }
                
            }
            System.out.println(source);
            length += tempDistance;
            tempDistance = Integer.MAX_VALUE;
            
            count++;
            System.out.println(Arrays.toString(visited));
        }

        return length;
    }*/

}