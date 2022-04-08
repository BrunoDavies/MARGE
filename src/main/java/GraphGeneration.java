import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GraphGeneration {
    private HyperedgeReplacementGrammar inputHRG;
    private int sizeOfGeneratedGraph;
    private int[][] nonTerminalMatrix;
    private int[][] productionMatrix;
    private ArrayList<Production> productionExecutionOrder = new ArrayList<>();

    public GraphGeneration(HyperedgeReplacementGrammar inputHRG, int sizeOfGeneratedGraph) {
        this.inputHRG = inputHRG;
        this.sizeOfGeneratedGraph = sizeOfGeneratedGraph;
        this.nonTerminalMatrix = new int[inputHRG.getNonTerminalLabels().size()][sizeOfGeneratedGraph];
        this.nonTerminalMatrix = new int[inputHRG.getAllProductions().size()][sizeOfGeneratedGraph];
    }

    public void preProccessingPhase() {
        //Step 1: init all values in matrices to 0 - already done when creating graphs
        nonTerminalMatrix = new int[inputHRG.getNonTerminalLabels().size()][sizeOfGeneratedGraph];
        productionMatrix = new int[inputHRG.getAllProductions().size()][sizeOfGeneratedGraph];

        //Step 2: Sort out all terminal or single node possibilities
        ArrayList<String> inputHRGTerminalLabels = inputHRG.getTerminalLabels();
        for (Production production : inputHRG.getAllProductions()) {
            if (inputHRGTerminalLabels.contains(production.getRightHandSideOfProduction().get(0)) &&
                    production.getRightHandSideOfProduction().size() == 1) {
                productionMatrix[production.getProductionId() - 1][production.getNumberOfInternalNodes()] = 1;
            }

            //TODO figure out what is meant by lamda in research paper
            //if ()
        }

        //Step 3: Fill in main entries
        for (int graphSize = 1; graphSize <= sizeOfGeneratedGraph; graphSize++) {
            for (String a : inputHRG.getNonTerminalLabels()) {
                for (Production production : inputHRG.getAllProductions()) {
                    if (a == production.getLeftHandSideOfProduction()) {
                        nonTerminalMatrix[inputHRG.getNonTerminalLabels().indexOf(a)][graphSize - 1] +=
                                productionMatrix[production.getProductionId() - 1][graphSize - 1];
                    }
                }
            }
            for (Production production : inputHRG.getNonTerminalProductions()) {
                for (int k = 1; k <= graphSize; k++) {
                    if (graphSize + production.getNumberOfInternalNodes() <= sizeOfGeneratedGraph - 1) {
                        productionMatrix[production.getProductionId() - 1][graphSize + production.getNumberOfInternalNodes()] +=
                                nonTerminalMatrix[inputHRG.getNonTerminalLabels()
                                        .indexOf(production.getRightHandSideOfProduction().get(0))][k - 1] *
                                        nonTerminalMatrix[inputHRG.getNonTerminalLabels()
                                                .indexOf(production.getRightHandSideOfProduction().get(1))][(graphSize - k)];
                    }
                }
            }
        }
    }

    public void generationPhase() {
        int graphLength = sizeOfGeneratedGraph - inputHRG.getProductionWithLHS(inputHRG.getStartingSymbol()).size();

        if (nonTerminalMatrix[inputHRG.getNonTerminalLabels().indexOf(inputHRG.getStartingSymbol())][graphLength] == 0){
            throw new IllegalArgumentException("There are no possible derivations");
        }

        productionExecutionOrder = deriveHypergraph(inputHRG.getStartingSymbol(), graphLength, productionExecutionOrder);
    }

    private ArrayList<Production> deriveHypergraph(String symbol, int graphLength,
                                                   ArrayList<Production> productionExecutionOrder) {
        Production randomProduction = generationPhaseRandomProduction(symbol, graphLength);

        if (!inputHRG.getNonTerminalProductions().contains(randomProduction)){
            productionExecutionOrder.add(randomProduction);
        }
        
        else {
            //Find a better name
            productionExecutionOrder.add(randomProduction);
            int newGraphLength = graphLength - randomProduction.getNumberOfInternalNodes();
            int randomNonTerminalVariation = generationPhaseRandomNonTerminal(randomProduction, newGraphLength, graphLength);
            //"B" Label
            productionExecutionOrder = deriveHypergraph(randomProduction.getRightHandSideOfProduction().get(0),
                    randomNonTerminalVariation, productionExecutionOrder);

            //"C" Label
            productionExecutionOrder = deriveHypergraph(randomProduction.getRightHandSideOfProduction().get(1),
                    newGraphLength - randomNonTerminalVariation, productionExecutionOrder);
        }
        
        return productionExecutionOrder;
    }

    private int generationPhaseRandomNonTerminal(Production randomProduction, int newGraphLength, int graphLength) {
        ArrayList<Integer> allPossibleKValues = new ArrayList<>();
        for (int k = 1; k < newGraphLength; k++) {
            int currentPosibility = nonTerminalMatrix[inputHRG.getNonTerminalLabels().indexOf(
                                        randomProduction.getRightHandSideOfProduction().get(0))][k-1] *
                                    nonTerminalMatrix[inputHRG.getNonTerminalLabels().indexOf(
                                            randomProduction.getRightHandSideOfProduction().get(1))][newGraphLength - k - 1];

            if (currentPosibility != 0) {
                for (int i = 0; i < currentPosibility; i++) {
                    allPossibleKValues.add(k);
                }
            }
        }
        Random rand = new Random();
        return allPossibleKValues.get(rand.nextInt(allPossibleKValues.size()));


    }

    private Production generationPhaseRandomProduction(String symbol, int graphLength) {
        List<Integer> listOfProductionId = new ArrayList<>();

        for (Production p : inputHRG.getProductionWithLHS(symbol)) {
            for (int i = 0; i < productionMatrix[p.getProductionId() -1][graphLength - 1]; i++) {
                listOfProductionId.add(p.getProductionId());
            }
        }
        Random rand = new Random();
        int randomProductionId = listOfProductionId.get(rand.nextInt(listOfProductionId.size()));

        return inputHRG.getAllProductions().get(randomProductionId - 1);
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

    public ArrayList<Production> getProductionExecutionOrder() {
        return productionExecutionOrder;
    }

    public void setProductionExecutionOrder(ArrayList<Production> productionExecutionOrder) {
        this.productionExecutionOrder = productionExecutionOrder;
    }

    //For the gen phase: collect all the production identities in order of execution - see paper for example - and
    // execute that separately (maybe, could be too big when doing graphs of size > 100,000


}
