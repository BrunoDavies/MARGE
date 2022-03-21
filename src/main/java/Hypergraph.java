import java.util.ArrayList;
import java.util.HashMap;

public class Hypergraph {
    //TODO look into using frobenius normal form for node and vertex representation. Might limit manipulation

    private ArrayList<Integer> nodes;
    private ArrayList<Integer> edges;
    private ArrayList<String> labels;
    private HashMap<Integer, Integer> attributes;   //edge -> node
    private HashMap<String, Integer> labelEdgeMapping;  //label -> edge
    private ArrayList<Integer> externalNodes;

    public Hypergraph(ArrayList<Integer> nodes, ArrayList<Integer> edges, ArrayList<String> labels, HashMap<Integer, Integer> attributes, HashMap<String, Integer> labelEdgeMapping, ArrayList<Integer> externalNodes) {
        this.nodes = nodes;
        this.edges = edges;
        this.labels = labels;
        this.attributes = attributes;
        this.labelEdgeMapping = labelEdgeMapping;
        this.externalNodes = externalNodes;
    }

    public ArrayList<Integer> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Integer> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Integer> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Integer> edges) {
        this.edges = edges;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public HashMap<Integer, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<Integer, Integer> attributes) {
        this.attributes = attributes;
    }

    public HashMap<String, Integer> getLabelEdgeMapping() {
        return labelEdgeMapping;
    }

    public void setLabelEdgeMapping(HashMap<String, Integer> labelEdgeMapping) {
        this.labelEdgeMapping = labelEdgeMapping;
    }

    public ArrayList<Integer> getExternalNodes() {
        return externalNodes;
    }

    public void setExternalNodes(ArrayList<Integer> externalNodes) {
        this.externalNodes = externalNodes;
    }
}
