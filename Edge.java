public class Edge  {
    private int id;
    private Node source;
    private Node destination;
    private int weight;

    public Edge(){
        this(0,null,null,0);
    }
    
    public Edge(int id, Node source, Node destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }
    public Node getDestination() {
        return destination;
    }

    public Node getSource() {
        return source;
    }
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }


}