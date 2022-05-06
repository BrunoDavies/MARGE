import java.util.ArrayList;

public class Production {
    private String leftHandSideOfProduction;
    private int productionId;
    private ArrayList<String> rightHandSideOfProduction;
    private Hypergraph rightHandSideHypergraph;
    private int numberOfInternalNodes;

    public Production(String leftHandSideOfProduction, int productionId, ArrayList<String> rightHandSideOfProduction,
                      Hypergraph productionHypergraph, int numberOfRegNodes) {
        this.leftHandSideOfProduction = leftHandSideOfProduction;
        this.productionId = productionId;
        this.rightHandSideOfProduction = rightHandSideOfProduction;
        this.rightHandSideHypergraph = productionHypergraph;
        this.numberOfInternalNodes = numberOfRegNodes;
    }

    public Production() {
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

    public Hypergraph getRightHandSideHypergraph() {
        return rightHandSideHypergraph;
    }

    public void setRightHandSideHypergraph(Hypergraph rightHandSideHypergraph) {
        this.rightHandSideHypergraph = rightHandSideHypergraph;
    }

    public int getNumberOfInternalNodes() {
        return numberOfInternalNodes;
    }

    public void setNumberOfInternalNodes(int numberOfInternalNodes) {
        this.numberOfInternalNodes = numberOfInternalNodes;
    }
}
