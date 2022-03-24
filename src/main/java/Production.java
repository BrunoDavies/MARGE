import java.util.ArrayList;

public class Production {
    private String leftHandSideOfProduction;
    private int productionId;
    private ArrayList<String> rightHandSideOfProduction;
    private Hypergraph productionHypergraph;
    private int numberOfRegNodes;

    public Production(String leftHandSideOfProduction, int productionId, ArrayList<String> rightHandSideOfProduction,
                      Hypergraph productionHypergraph, int numberOfRegNodes) {
        this.leftHandSideOfProduction = leftHandSideOfProduction;
        this.productionId = productionId;
        this.rightHandSideOfProduction = rightHandSideOfProduction;
        this.productionHypergraph = productionHypergraph;
        this.numberOfRegNodes = numberOfRegNodes;
    }

    public String getLeftHandSideOfProduction() {
        return leftHandSideOfProduction;
    }

    public void setLeftHandSideOfProduction(String leftHandSideOfProduction) {
        this.leftHandSideOfProduction = leftHandSideOfProduction;
    }

    public int getProductionId() {
        return productionId;
    }

    public void setProductionId(int productionId) {
        this.productionId = productionId;
    }

    public ArrayList<String> getRightHandSideOfProduction() {
        return rightHandSideOfProduction;
    }

    public void setRightHandSideOfProduction(ArrayList<String> rightHandSideOfProduction) {
        this.rightHandSideOfProduction = rightHandSideOfProduction;
    }

    public Hypergraph getProductionHypergraph() {
        return productionHypergraph;
    }

    public void setProductionHypergraph(Hypergraph productionHypergraph) {
        this.productionHypergraph = productionHypergraph;
    }

    public int getNumberOfRegNodes() {
        return numberOfRegNodes;
    }

    public void setNumberOfRegNodes(int numberOfRegNodes) {
        this.numberOfRegNodes = numberOfRegNodes;
    }
}
