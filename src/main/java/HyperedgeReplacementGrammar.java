import java.util.ArrayList;
import java.util.List;

public class HyperedgeReplacementGrammar {
    private ArrayList<String> nonTerminalLabels = new ArrayList<>();    //Labels that identify the non-terminal hyperedges
    private ArrayList<String> terminalLabels = new ArrayList<>();   //Labels that identify the terminal hyperedges

    //Stack for productions.
//    private ArrayList<String> productionLHS = new ArrayList<>();
//    private Hypergraph productionRHS = new Hypergraph();
    private List<List<Object>> allProductions = new ArrayList<List<Object>>(); //Add a arraylist with lhs, production number, rhs, and internal node(?)

    private String startingSymbol; //Defined from non-terminal labels
    //TODO markFunctions: marking of labels and such. Need to see concrete example, not mathematical.

    public HyperedgeReplacementGrammar(ArrayList<String> nonTerminalLabels, ArrayList<String> terminalLabels, List<List<Object>> allProductions, String startingSymbol) {
        this.nonTerminalLabels = nonTerminalLabels;
        this.terminalLabels = terminalLabels;
        this.allProductions = allProductions;
        this.startingSymbol = startingSymbol;
    }

    public ArrayList<String> getNonTerminalLabels() {
        return nonTerminalLabels;
    }

    public void setNonTerminalLabels(ArrayList<String> nonTerminalLabels) {
        this.nonTerminalLabels = nonTerminalLabels;
    }

    public ArrayList<String> getTerminalLabels() {
        return terminalLabels;
    }

    public void setTerminalLabels(ArrayList<String> terminalLabels) {
        this.terminalLabels = terminalLabels;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public void setStartingSymbol(String startingSymbol) {
        this.startingSymbol = startingSymbol;
    }

    public List<List<Object>> getAllProductions() {
        return allProductions;
    }

    public void setAllProductions(List<List<Object>> allProductions) {
        this.allProductions = allProductions;
    }

}
