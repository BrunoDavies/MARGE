import java.util.ArrayList;

public class HyperedgeReplacementGrammar {
    private ArrayList<String> nonTerminalLabels = new ArrayList<>();
    private ArrayList<String> terminalLabels = new ArrayList<>();
    //TODO Production: lhs (label) and rhs (hypergraph) so maybe tuple of the two. Find concrete example.
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
