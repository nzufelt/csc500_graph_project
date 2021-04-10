import java.util.ArrayList;
import java.awt.Color;

public class BasicNode {
    private ArrayList<BasicNode> neighbors;
    private String title;
    private NodeEffect effect;

    public BasicNode(String title) {
        neighbors = new ArrayList<BasicNode>();
        this.title = title;
        effect = new NodeEffect(); // todo: deal with how to add these
    }

    public void connectTo(BasicNode other) {
        if (!neighbors.contains(other)) {
            neighbors.add(other);
        }

        if (!other.neighbors.contains(this)) {
            other.neighbors.add(this);
        }
    }

    public Color getColor()
    {
        return Color.red;
    }
    
    public boolean affect() {
        return effect.affect();
    }

    public ArrayList<BasicNode> getNeighbors() {
        return new ArrayList(neighbors);  //return a (shallow) copy of the array list instead?
    }

    public String toString() {
        String output = title + " (" + (effect.getAffected() ? "" : "not")  + " affected), connected to: ";
        for (BasicNode other : neighbors) {
            output += other.title + ", ";
        }

        // remove the last ", "
        return output.substring(0, output.length() - 2);
    }
}
