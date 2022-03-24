import java.util.*;

public class Dijkstra {

    private List<Node> listOfNodes;
    private List<Edge> listOfEdges;
    private List<Node> settledNodes;
    private List<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;

    public Dijkstra(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.listOfNodes = new ArrayList<Node>(graph.getNodes());
        this.listOfEdges = new ArrayList<Edge>(graph.getEdges());
    }

    public void execute(Node source) {
        settledNodes = new ArrayList<Node>(); // Nodes verifiés
        unSettledNodes = new ArrayList<Node>(); // Nodes non verifiés
        distance = new HashMap<Node, Integer>(); // Distance entre la source e Node dans l'HashMap
        predecessors = new HashMap<Node, Node>(); // prédécesseurs
        distance.put(source, 0); // Source jusqu'à la source il y a une destination 0
        unSettledNodes.add(source); // Mettre la source comme non traiter
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private Node getMinimum(List<Node> nodes) {
        Node minimum = null;
        for (Node node : nodes) {
            if (minimum == null) {
                minimum = node;
            } else {
                if (getShortestDistance(node) < getShortestDistance(minimum)) {
                    minimum = node;
                }
            }
        }
        return minimum;
    }

    private int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) { // return une distance colosale
            return Integer.MAX_VALUE;
        } else {
            return d; // return la valeur associer dans l'HashMap distance
        }
    }

    private void findMinimalDistances(Node node) {
        List<Node> neighbors = getNeighbors(node);
        for (Node target : neighbors) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target)); // Mettre la distance entre
                                                                                             // la source et la
                                                                                             // destination
                predecessors.put(target, node); // Mettre la source prédécesseur de destination
                unSettledNodes.add(target); // Maittre le node target comme non traiter dans le set
            }
        }
    }

    public int getDistance(Node node, Node target) {
        for (Edge edge : listOfEdges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Ça ne devais pas arriver, graph non connexe");
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : listOfEdges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private boolean isSettled(Node vertex) {
        return settledNodes.contains(vertex);
    }

    public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = target;
        // vérifie ce le chemin existe
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Maitre la collection dans le bonne ordre
        Collections.reverse(path);
        return path;
    }

    public int[][] getDistanceMatrix() {
        int nodeLength = listOfNodes.size();
        int[][] distanceMatrix = new int[nodeLength][nodeLength];
        LinkedList<Node> nodes = new LinkedList<Node>();

        for (int i = 0; i < nodeLength; i++) {
            this.execute(listOfNodes.get(i));

            for (int j = 0; j < nodeLength; j++) {
                nodes = getPath(listOfNodes.get(j));

                if (nodes == null) {
                    distanceMatrix[i][j] = 0;
                } else {
                    int calculate = 0;

                    for (int k = 0; k < nodes.size(); k++) {
                        if (k + 1 < nodes.size()) {
                            calculate += getDistance(nodes.get(k), nodes.get(k + 1));
                        }
                    }

                    distanceMatrix[i][j] = calculate;
                    calculate = 0;
                }
            }
        }
        return distanceMatrix;
    }
}