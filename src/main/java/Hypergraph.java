import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Hypergraph {
    private ArrayList<Integer> nodes;
    private ArrayList<Integer> internalStatus;
    private ArrayList<String> edges;
    private ArrayList<Integer> terminalStatus;
    private HashMap<String, ArrayList<Integer>> attachments = new HashMap<>();

    public Hypergraph(ArrayList<Integer> nodes, ArrayList<Integer> internalStatus, ArrayList<String> edges, ArrayList<Integer> terminalStatus, HashMap<String, ArrayList<Integer>> attachments) {
        this.nodes = nodes;
        this.internalStatus = internalStatus;
        this.edges = edges;
        this.terminalStatus = terminalStatus;
        this.attachments = attachments;
    }

    public ArrayList<Integer> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Integer> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Integer> getInternalStatus() {
        return internalStatus;
    }

    public void setInternalStatus(ArrayList<Integer> internalStatus) {
        this.internalStatus = internalStatus;
    }

    public ArrayList<String> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<String> edges) {
        this.edges = edges;
    }

    public ArrayList<Integer> getTerminalStatus() {
        return terminalStatus;
    }

    public ArrayList<String> allTerminalEdges() {
        ArrayList<String> terminalEdges = new ArrayList<>();

        for (String edge : edges) {
            if (terminalStatus.get(edges.indexOf(edge)) == 0) {
                terminalEdges.add(edge);
            }
        }
        return terminalEdges;
    }
    public ArrayList<String> allNonTerminalEdges() {
        ArrayList<String> nonTerminalEdges = new ArrayList<>();

        for (String edge : edges) {
            if ((Long) terminalStatus.get(edges.indexOf(edge)).intValue() == 1) {
                nonTerminalEdges.add(edge);
            }
        }
        return nonTerminalEdges;
    }

    public void setTerminalStatus(ArrayList<Integer> terminalStatus) {
        this.terminalStatus = terminalStatus;
    }

    public HashMap<String, ArrayList<Integer>> getAttachments() {
        return attachments;
    }

    public void setAttachments(HashMap<String, ArrayList<Integer>> attachments) {
        this.attachments = attachments;
    }
}
