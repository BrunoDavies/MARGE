import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphGeneration {
    private HyperedgeReplacementGrammar inputHRG;
    private int sizeOfGeneratedGraph;
    private HashMap<String, ArrayList<Integer>> nonTerminalMatrix;
    private HashMap<List<Object>, ArrayList<Integer>> productionMatrix;   //maybe make Production a class and then have that as key?

    public GraphGeneration(HyperedgeReplacementGrammar inputHRG, int sizeOfGeneratedGraph,
                           HashMap<String, ArrayList<Integer>> nonTerminalMatrix, HashMap<List<Object>, ArrayList<Integer>> productionMatrix) {
        this.inputHRG = inputHRG;
        this.sizeOfGeneratedGraph = sizeOfGeneratedGraph;
        this.nonTerminalMatrix = nonTerminalMatrix;
        this.productionMatrix = productionMatrix;

    }

    public HashMap<String, ArrayList<Integer>> preProccessingPhaseNonTerminal(HyperedgeReplacementGrammar inputHRG,
                                                                              int sizeOfGeneratedGraph) {

        return null;
    }

    public HashMap<List<Object>, ArrayList<Integer>> preProccessingPhaseProduction(HyperedgeReplacementGrammar inputHRG,
                                                                                   int sizeOfGeneratedGraph) {

        return null;
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

    public HashMap<String, ArrayList<Integer>> getNonTerminalMatrix() {
        return nonTerminalMatrix;
    }

    public void setNonTerminalMatrix(HashMap<String, ArrayList<Integer>> nonTerminalMatrix) {
        this.nonTerminalMatrix = nonTerminalMatrix;
    }

    public HashMap<List<Object>, ArrayList<Integer>> getProductionMatrix() {
        return productionMatrix;
    }

    public void setProductionMatrix(HashMap<List<Object>, ArrayList<Integer>> productionMatrix) {
        this.productionMatrix = productionMatrix;
    }

    //For the gen phase: collect all the production identities in order of execution - see paper for example - and
    // execute that separately (maybe, could be too big when doing graphs of size > 100,000


}
