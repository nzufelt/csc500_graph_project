import java.util.ArrayList;

//imports for graphics
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;

public class BasicGraph {
    private ArrayList<BasicNode> nodes;

    public BasicGraph() {
        nodes = new ArrayList<BasicNode>();
    }

    public BasicNode addNode(String title) {
        nodes.add(new BasicNode(title));
        return nodes.get(nodes.size() - 1);
    }

    public void connect(BasicNode a, BasicNode b) {
        a.connectTo(b);
    }

    // Connect the nodes located at index i and j
    public void connect(int i, int j) {
        nodes.get(i).connectTo(nodes.get(j));
    }

    public void propagate(BasicNode startingNode) {
        startingNode.affect();
        ArrayList<BasicNode> nextNodes = startingNode.getNeighbors();
        ArrayList<BasicNode> seenNodes = new ArrayList<BasicNode>();
        seenNodes.add(startingNode);

        while (nextNodes.size() != 0) {
            BasicNode next = nextNodes.remove(0);
            if (seenNodes.contains(next)) {
                continue;
            } else {
                seenNodes.add(next);
                if (next.affect()) {
                    nextNodes.addAll(next.getNeighbors());
                }
                System.out.println("Seeing " + next);
            }
        }
    }

    public String toString() {
        String output = "BasicGraph with nodes:\n";
        for (BasicNode node : nodes) {
            output += node.toString() + "\n";
        }

        return output;
    }

    // create a random BasicGraph
    // param `size`: number of nodes in the graph
    // param `weight`: a double between 0 and 1, determines roughly the percentage
    //                 of connections that exist between nodes
    //                 (0.0 -> empty graph, 1.0 -> complete graph)
    public static BasicGraph generateRandomGraph(int size, double weight) {
        if (0.0 > weight || 1.0 < weight) {
            throw new IllegalArgumentException("Bad weight (must be between 0 and 1): " + weight);
        }

        BasicGraph output = new BasicGraph();

        for (int i = 0; i < size; i++) {
            output.addNode("Node #" + i);
        }

        // add connections randomly
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (Math.random() < weight) {
                    output.connect(i, j);
                }
            }
        }

        return output;
    }
    
    //Drawing code
    private BufferedImage drawer = null;
    private JFrame frame; 
    public static final int width = 512;  //window size
    public static final int height = 512;
    public static final double radius = 200; //radius for circle of nodes
    public static final int nodeRadius = 8;
    
    //draws a point on the screen
    public void draw(boolean labelNodes){
        if (drawer == null)
        {  //drawing for the first time
            drawer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            frame = new JFrame("Graph");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel labelImage = new JLabel(new ImageIcon(drawer));
            frame.getContentPane().add(labelImage);
            frame.pack();
            frame.setVisible(false);
        }
        Graphics2D g = drawer.createGraphics();
        
        //draw the edges
        for (int i = 0; i < nodes.size(); i++)
        {
            for (int j = i; j < nodes.size(); j++)
            {
                if (nodes.get(i).getNeighbors().contains(nodes.get(j)))
                {
                    g.drawLine(getX(i), getY(i), getX(j), getY(j));
                }
            }
        }   
        //now draw nodes ontop
        for (int i = 0; i < nodes.size(); i++)
        {
            g.setColor(nodes.get(i).getColor());
            g.fillOval(getX(i) - nodeRadius / 2, getY(i) - nodeRadius / 2, nodeRadius, nodeRadius);
            if (labelNodes)
            {
                g.drawString("" + i, getX(i) + 9, getY(i) + 5);
            }
        }
        frame.setVisible(true);
    }
    
    private int getX(int index) 
    {
        double angle = (double) index / nodes.size() * 2 * Math.PI;
        double x = Math.cos(angle) * radius + width / 2;
        return (int) x + nodeRadius / 2;
    }
    
    private int getY(int index)
    {
        double angle = (double) index / nodes.size() * 2 * Math.PI;
        double y = Math.sin(angle) * radius + width / 2;
        return (int) y + nodeRadius / 2;
    }

    public static void main(String[] args) {
        BasicGraph graph = generateRandomGraph(40, 0.05);
        System.out.println(graph);
        System.out.println();
        graph.propagate(graph.nodes.get(0));
        graph.draw(false);
    }
}
