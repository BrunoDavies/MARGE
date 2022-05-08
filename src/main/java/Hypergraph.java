import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public Hypergraph() {
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

        for (int i = 0; i < edges.size(); i++) {
            if (terminalStatus.get(i) == 1) {
                terminalEdges.add(edges.get(i));
            }
        }
        return terminalEdges;
    }
    public ArrayList<String> allNonTerminalEdges() {
        ArrayList<String> nonTerminalEdges = new ArrayList<>();

        for (int i = 0; i < edges.size(); i++) {
            if (terminalStatus.get(i) == 0) {
                nonTerminalEdges.add(edges.get(i));
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

    public void removeEdge(Production replacement, boolean isNonTerminal) {
        terminalStatus.remove(edges.indexOf(replacement.getLeftHandSideOfProduction()));
        edges.remove(replacement.getLeftHandSideOfProduction());
        internalStatus = new ArrayList<>();
        attachments.entrySet().removeIf(entry -> entry.getKey().equals(entry.getKey().toUpperCase()));

    }

    public void addEdge(Production production) {
        for (String edge : production.getRightHandSideHypergraph().edges){
            edges.add(edge);
        }

        for (int status : production.getRightHandSideHypergraph().terminalStatus) {
            terminalStatus.add(status);
        }


        for (int i : nodes) {
            internalStatus.add(1);
        }

        for (Map.Entry<String, ArrayList<Integer>> k : production.getRightHandSideHypergraph().attachments.entrySet()) {
            if (attachments.containsKey(k.getKey())){
                if (k.getKey().length() < 2) {
                    attachments.put(k.getKey() + "2", k.getValue());
                } else {
                    attachments.put(k.getKey() + Integer.parseInt(k.getKey().indent(1)) + 1, k.getValue());
                }

            }
            attachments.put(k.getKey(), k.getValue());
        }
    }
}
