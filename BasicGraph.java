import java.util.ArrayList;

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

    public static void main(String[] args) {
        BasicGraph graph = generateRandomGraph(7, 0.5);
        System.out.println(graph);
        System.out.println();
        graph.propagate(graph.nodes.get(0));
    }
}
