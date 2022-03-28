import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphGeneration {
    private HyperedgeReplacementGrammar inputHRG;
    private int sizeOfGeneratedGraph;
    private int[][] nonTerminalMatrix;
    private int[][] productionMatrix;

    public GraphGeneration(HyperedgeReplacementGrammar inputHRG, int sizeOfGeneratedGraph) {
        this.inputHRG = inputHRG;
        this.sizeOfGeneratedGraph = sizeOfGeneratedGraph;
        this.nonTerminalMatrix = new int[inputHRG.getNonTerminalLabels().size()][sizeOfGeneratedGraph];
        this.nonTerminalMatrix = new int[inputHRG.getAllProductions().size()][sizeOfGeneratedGraph];
    }

    public void preProccessingPhase(int[][] nonTerminalMatrix, int[][] productionMatrix) {
        //Step 1: init all values in matrices to 0 - already done when creating graphs

        //Step 2: Sort out all terminal or single node possibilities
        for (Production production : inputHRG.getAllProductions()) {
            if (Character.isLowerCase(production.getRightHandSideOfProduction().get(0)) &&
                    production.getRightHandSideOfProduction().size() == 1) {
                productionMatrix[production.getProductionId()][production.getNumberOfInternalNodes() + 1] = 1;
            }

            //TODO figure out what is meant by lamda in research paper
            //if ()
        }

        //Step 3: Fill in main entries
        for (int l = 0; l <= sizeOfGeneratedGraph; l++) {
            for (String a : inputHRG.getNonTerminalLabels()) {
                for (Production production : inputHRG.getAllProductions()) {
                    nonTerminalMatrix[inputHRG.getNonTerminalLabels().indexOf(a)][l] +=
                            productionMatrix[production.getProductionId()][l];
                }
            }
            for (Production production : inputHRG.getNonTerminalProductions()) {
                for (int k = 0; k < 1; k++) {
                    productionMatrix[production.getProductionId()][l+production.getNumberOfInternalNodes()] +=
                            nonTerminalMatrix[inputHRG.getNonTerminalLabels()
                                    .indexOf(production.getRightHandSideOfProduction().get(0))][k] *
                                    nonTerminalMatrix[inputHRG.getNonTerminalLabels()
                                            .indexOf(production.getRightHandSideOfProduction().get(1))][l-k];
                }
            }
        }

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

    public int[][] getNonTerminalMatrix() {
        return nonTerminalMatrix;
    }

    public void setNonTerminalMatrix(int[][] nonTerminalMatrix) {
        this.nonTerminalMatrix = nonTerminalMatrix;
    }

    public int[][] getProductionMatrix() {
        return productionMatrix;
    }

    public void setProductionMatrix(int[][] productionMatrix) {
        this.productionMatrix = productionMatrix;
    }

    //For the gen phase: collect all the production identities in order of execution - see paper for example - and
    // execute that separately (maybe, could be too big when doing graphs of size > 100,000


}
