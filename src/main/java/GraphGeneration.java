import java.util.HashMap;
import java.util.List;

public class GraphGeneration {
    private HyperedgeReplacementGrammar inputHRG;
    private int sizeOfGeneratedGraph;
    private HashMap<String, Integer> nonTerminalMatrix;
    private HashMap<List<Object>, Integer> productionMatrix;   //maybe make Production a class and then have that as key?

    public GraphGeneration(HyperedgeReplacementGrammar inputHRG, int sizeOfGeneratedGraph, HashMap<String, Integer> nonTerminalMatrix, HashMap<List<Object>, Integer> productionMatrix) {
        this.inputHRG = inputHRG;
        this.sizeOfGeneratedGraph = sizeOfGeneratedGraph;
        this.nonTerminalMatrix = nonTerminalMatrix;
        this.productionMatrix = productionMatrix;
    }

    public HyperedgeReplacementGrammar getInputHRG() {
        return inputHRG;
    }

    public void setInputHRG(HyperedgeReplacementGrammar inputHRG) {
        this.inputHRG = inputHRG;
    }

    public int getSizeOfGeneratedGraph() {
        return sizeOfGeneratedGraph;
    }

    public void setSizeOfGeneratedGraph(int sizeOfGeneratedGraph) {
        this.sizeOfGeneratedGraph = sizeOfGeneratedGraph;
    }

    public HashMap<String, Integer> getNonTerminalMatrix() {
        return nonTerminalMatrix;
    }

    public void setNonTerminalMatrix(HashMap<String, Integer> nonTerminalMatrix) {
        this.nonTerminalMatrix = nonTerminalMatrix;
    }

    public HashMap<List<Object>, Integer> getProductionMatrix() {
        return productionMatrix;
    }

    public void setProductionMatrix(HashMap<List<Object>, Integer> productionMatrix) {
        this.productionMatrix = productionMatrix;
    }

    //For the gen phase: collect all the production identities in order of execution - see paper for example - and
    // execute that separately (maybe, could be too big when doing graphs of size > 100,000


}
