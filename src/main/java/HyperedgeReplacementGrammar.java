import java.util.ArrayList;
import java.util.Map;

public class HyperedgeReplacementGrammar {
    private ArrayList<String> nonTerminalLabels = new ArrayList<>();
    private ArrayList<String> terminalLabels = new ArrayList<>();
    //TODO Production: two separate entities with mapping between
    private ArrayList<String> productionLHS = new ArrayList<>();
    private Hypergraph productionRHS = new Hypergraph();
    private Map<String, Hypergraph> productions;
    private String startingSymbol; //Defined from non-terminal labels
    //TODO markFunctions: marking of labels and such. Need to see concrete example, not mathematical.


    public HyperedgeReplacementGrammar(ArrayList<String> nonTerminalLabels, ArrayList<String> terminalLabels, String startingSymbol) {
        this.nonTerminalLabels = nonTerminalLabels;
        this.terminalLabels = terminalLabels;
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
}
