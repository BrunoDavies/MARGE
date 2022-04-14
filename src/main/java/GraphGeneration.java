import java.util.*;

public class GraphGeneration {
    private HyperedgeReplacementGrammar inputHRG;
    private int sizeOfGeneratedGraph;
    private int[][] nonTerminalMatrix;
    private int[][] productionMatrix;
    private ArrayList<Production> productionExecutionOrder = new ArrayList<>();
    private ArrayList<Production> linearModificationAllProductions = new ArrayList<>();
    private HashMap<Integer, String[][]> linearModificationProductionRhsSplit = new HashMap<>();

    public GraphGeneration(HyperedgeReplacementGrammar inputHRG, int sizeOfGeneratedGraph) {
        this.inputHRG = inputHRG;
        this.sizeOfGeneratedGraph = sizeOfGeneratedGraph;
        this.nonTerminalMatrix = new int[inputHRG.getNonTerminalLabels().size()][sizeOfGeneratedGraph];
        this.nonTerminalMatrix = new int[inputHRG.getAllProductions().size()][sizeOfGeneratedGraph];
    }

    public void preProcessingPhase() {
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

    public void linearHRGModification() {
        generateAllPossibleVariations();
        boolean changeOccurredFlag = true;
        while (changeOccurredFlag){
            changeOccurredFlag = secondVariationTrimmed();
        }

    }

    private boolean secondVariationTrimmed() {
        boolean changeOccurredFlag = false;
        ArrayList<Integer> secondVariationTrim = new ArrayList<>();
        for (Map.Entry<Integer, String[][]> element : linearModificationProductionRhsSplit.entrySet()) {
            if(Objects.requireNonNull(element.getValue()).length > 1){
                String leftLabel = element.getValue()[0][1] + element.getValue()[0][0];
                String rightLabel = element.getValue()[1][1] + element.getValue()[1][0];
                if (!(linearModificationProductionRhsSplit.containsKey(indexOfLHS(leftLabel)) && linearModificationProductionRhsSplit.containsKey(indexOfLHS(rightLabel)))) {
                    secondVariationTrim.add(element.getKey());
                    changeOccurredFlag = true;
                }
            }
        }
        for (int i : secondVariationTrim) {
            linearModificationProductionRhsSplit.remove(i);
        }
        return changeOccurredFlag;
    }

    private void generateAllPossibleVariations() {
        for (Production production : inputHRG.getNonTerminalProductions()) {
            for (int j = 1; j <= sizeOfGeneratedGraph; j++) {
                int sizePrime = j - production.getNumberOfInternalNodes();
                for (int k = 1; k < sizePrime; k++) {
                    String newProductionLHS = production.getLeftHandSideOfProduction() + j;
                    String[][] rhsSplit = {{Integer.toString(k), production.getRightHandSideOfProduction().get(0)}, {Integer.toString(sizePrime - k), production.getRightHandSideOfProduction().get(1)}};
                    Production productionJ = new Production(
                            newProductionLHS,
                            production.getProductionId(),
                            production.getRightHandSideOfProduction(),
                            production.getRightHandSideHypergraph(),
                            production.getNumberOfInternalNodes());

                    linearModificationAllProductions.add(productionJ);
                    linearModificationProductionRhsSplit.put(linearModificationAllProductions.indexOf(productionJ),
                            rhsSplit);
                }
            }
        }
        for (Production production : inputHRG.getTerminalProductions()) {
            String newProductionLHS = production.getLeftHandSideOfProduction() + 1;
            String[][] rhsSplit = {{Integer.toString(0), production.getRightHandSideOfProduction().get(0)}};

            Production newProduction = new Production(
                    newProductionLHS,
                    production.getProductionId(),
                    production.getRightHandSideOfProduction(),
                    production.getRightHandSideHypergraph(),
                    production.getNumberOfInternalNodes());

            linearModificationAllProductions.add(newProduction);
            linearModificationProductionRhsSplit.put(linearModificationAllProductions.indexOf(newProduction),
                    rhsSplit);
        }
    }

    public int indexOfLHS (String lhsLabel) {
        Optional<Production> possibleMatch =  linearModificationAllProductions.stream().filter(o -> o.getLeftHandSideOfProduction().equals(lhsLabel)).findFirst();
        if (possibleMatch.isEmpty()){
            return -1;
        }
        return linearModificationAllProductions.indexOf(possibleMatch.get());
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

    public ArrayList<Production> getLinearModificationAllProductions() {
        return linearModificationAllProductions;
    }

    public void setLinearModificationAllProductions(ArrayList<Production> linearModificationAllProductions) {
        this.linearModificationAllProductions = linearModificationAllProductions;
    }

    public HashMap<Integer, String[][]> getLinearModificationProductionRhsSplit() {
        return linearModificationProductionRhsSplit;
    }

    public void setLinearModificationProductionRhsSplit(HashMap<Integer, String[][]> linearModificationProductionRhsSplit) {
        this.linearModificationProductionRhsSplit = linearModificationProductionRhsSplit;
    }

    public String[][] getRHSSplit(int productionIndex) {
        return linearModificationProductionRhsSplit.get(productionIndex);
    }
}
