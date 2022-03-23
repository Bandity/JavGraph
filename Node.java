public class Node {
    private int id;
    private String name;

    public Node(){
        this(0,null);
    }

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (id == 0) {
            if (other.id != 0)
                return false;
        } else if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}