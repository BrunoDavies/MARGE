import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.SecureRandom;

public class GraphGeneration {
    private HyperedgeReplacementGrammar inputHRG;
    private int sizeOfGeneratedGraph;
    private int[][] nonTerminalMatrix;
    private int[][] productionMatrix;
    private ArrayList<Production> productionExecutionOrder = new ArrayList<>();
    private Hypergraph generatedHypergraph;

    public GraphGeneration(HyperedgeReplacementGrammar inputHRG, int sizeOfGeneratedGraph) {
        this.inputHRG = inputHRG;
        this.sizeOfGeneratedGraph = sizeOfGeneratedGraph;
        this.nonTerminalMatrix = new int[inputHRG.getNonTerminalLabels().size()][sizeOfGeneratedGraph];
        this.productionMatrix = new int[inputHRG.getAllProductions().size()][sizeOfGeneratedGraph];
    }

    public void preProcessingPhase() {
        //Step 2: Sort out all terminal or single node possibilities
        for (Production production : inputHRG.getAllProductions()) {
            if (inputHRG.getTerminalLabels().contains(production.getRightHandSideOfProduction().get(0)) &&
                    production.getRightHandSideOfProduction().size() == 1) {
                productionMatrix[production.getProductionId() - 1][production.getNumberOfInternalNodes()] = 1;
            } else if (production.getRightHandSideOfProduction().isEmpty()) {
                productionMatrix[production.getProductionId()][production.getNumberOfInternalNodes() - 1] = 1;
            }
        }

        //Step 3: Fill in main entries
        for (int graphSize = 1; graphSize <= sizeOfGeneratedGraph; graphSize++) {
            for (String a : inputHRG.getNonTerminalLabels()) {
                for (Production production : inputHRG.getAllProductions()) {
                    if (Objects.equals(a, production.getLeftHandSideOfProduction())) {
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

    public void generationPhase() throws NoSuchAlgorithmException {
        int graphLength = sizeOfGeneratedGraph - inputHRG.getProductionWithLHS(inputHRG.getStartingSymbol()).size();

        if (nonTerminalMatrix[inputHRG.getNonTerminalLabels().indexOf(inputHRG.getStartingSymbol())][graphLength] == 0){
            throw new IllegalArgumentException("There are no possible derivations");
        }

        productionExecutionOrder = deriveHypergraph(inputHRG.getStartingSymbol(), graphLength, productionExecutionOrder);
    }

    private Hypergraph applyDerivationOrder(ArrayList<Production> productionExecutionOrder) {
        //Place holder
        return productionExecutionOrder.get(1).getRightHandSideHypergraph();
    }

    private ArrayList<Production> deriveHypergraph(String symbol, int graphLength, ArrayList<Production> productionExecutionOrder) throws NoSuchAlgorithmException {
        Production randomProduction = generationPhaseRandomProduction(symbol, graphLength);

        if (!inputHRG.getNonTerminalProductions().contains(randomProduction))
        {
            productionExecutionOrder.add(randomProduction);
        } else
        {
            productionExecutionOrder.add(randomProduction);
            int newGraphLength = graphLength - randomProduction.getNumberOfInternalNodes();
            int randomNonTerminalVariation = randomRHSProductionSplit(randomProduction, newGraphLength);

            //"B" Label
            productionExecutionOrder = deriveHypergraph(randomProduction.getRightHandSideOfProduction().get(0),
                    randomNonTerminalVariation, productionExecutionOrder);

            //"C" Label
            productionExecutionOrder = deriveHypergraph(randomProduction.getRightHandSideOfProduction().get(1),
                    newGraphLength - randomNonTerminalVariation, productionExecutionOrder);
        }
        return productionExecutionOrder;
    }

    private Production generationPhaseRandomProduction(String symbol, int graphLength) throws NoSuchAlgorithmException {
        List<Integer> listOfProductionId = new ArrayList<>();

        for (Production p : inputHRG.getProductionWithLHS(symbol)) {
            for (int i = 0; i < productionMatrix[p.getProductionId() - 1][graphLength - 1]; i++) {
                listOfProductionId.add(p.getProductionId());
            }
        }
        SecureRandom rand = SecureRandom.getInstance("NativePRNG");
        int randomProductionId = listOfProductionId.get(rand.nextInt(listOfProductionId.size()));

        return inputHRG.getAllProductions().get(randomProductionId - 1);
    }

    private int randomRHSProductionSplit(Production randomProduction, int newGraphLength) throws NoSuchAlgorithmException {
        ArrayList<Integer> allPossibleKValues = new ArrayList<>();
        for (int k = 1; k < newGraphLength; k++) {
            int currentPossibility = nonTerminalMatrix[inputHRG.getNonTerminalLabels().indexOf(
                                        randomProduction.getRightHandSideOfProduction().get(0))][k-1] *
                                    nonTerminalMatrix[inputHRG.getNonTerminalLabels().indexOf(
                                            randomProduction.getRightHandSideOfProduction().get(1))][newGraphLength - k - 1];

            if (currentPossibility != 0) {
                for (int i = 0; i < currentPossibility; i++) {
                    allPossibleKValues.add(k);
                }
            }
        }
        SecureRandom rand = SecureRandom.getInstance("NativePRNG");
        return allPossibleKValues.get(rand.nextInt(allPossibleKValues.size()));
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

    public Hypergraph getGeneratedHypergraph() {
        generatedHypergraph = applyDerivationOrder(productionExecutionOrder);
        return generatedHypergraph;
    }

    public void setGeneratedHypergraph(Hypergraph generatedHypergraph) {
        this.generatedHypergraph = generatedHypergraph;
    }
}
