import java.util.ArrayList;
import java.util.List;

public class HyperedgeReplacementGrammar {
    private ArrayList<String> nonTerminalLabels = new ArrayList<>();    //Labels that identify the non-terminal hyperedges
    private ArrayList<String> terminalLabels = new ArrayList<>();   //Labels that identify the terminal hyperedges

    //Stack for productions.
    // ArrayList<String> productionLHS = new ArrayList<>();
    // Hypergraph productionRHS = new Hypergraph();
    private List<Production> allProductions; //Add a arraylist with lhs, production id, rhs, and indicator of terminal production
    //Individual production format: {String, String, Hypergraph, Int}

    private String startingSymbol; //Defined from non-terminal labels

    //Not really needed? Maybe needed in GraphGeneration or not at all.
    //TODO markFunctions: marking of labels to be replaced. Need to see concrete example, not mathematical.

    public HyperedgeReplacementGrammar(ArrayList<String> nonTerminalLabels, ArrayList<String> terminalLabels, List<Production> allProductions, String startingSymbol) {
        this.nonTerminalLabels = nonTerminalLabels;
        this.terminalLabels = terminalLabels;
        this.allProductions = allProductions;
        this.startingSymbol = startingSymbol;

        //TODO if even one production is not valid throw error to user (need to re-submit a valid HRG).
        boolean thisValidHRG = validHRG();
    }

    //TODO write test
    private boolean validHRG() {
        boolean nonTerminalLabelFinite = Double.isFinite(nonTerminalLabels.size());
        boolean terminalLabelFinite = Double.isFinite(terminalLabels.size());
        //Intersection of nonTer and Ter needs to be empty
        boolean intersectionOfNonTerminalAndTerminal = checkIntersectionIsEmpty(nonTerminalLabels, terminalLabels);
        //Check if list of productions is finite?
        boolean startIsInNonTerminal = nonTerminalLabels.contains(startingSymbol);
        boolean allProductionsValid = allProductionValid(allProductions);

        //maybe a cleaner way to do this
        if (nonTerminalLabelFinite && terminalLabelFinite && intersectionOfNonTerminalAndTerminal && startIsInNonTerminal
                && allProductionsValid)
        {
            return true;
        } else {
            return false;
        }
    }

    private boolean allProductionValid(List<Production> allProductions) {
        for (Production singleProduction : allProductions) {
            if (!singleProductionValid(singleProduction))
            {
                return false;
            }
        }
        return true;
    }

    private boolean singleProductionValid(Production singleProduction) {
        //Case 1: Er = {e1, e2} where lab(e1), lab(e2) are in the set of NonTerminalLabels && mark(e1) =/= mark(e2)

//        if ()

        //Case 2: Er = {e1} where lab(e1} in set of TerminalLabels and mark(e1) = alpha (isolated node I think?)
        //Case 3: Er = Empty set, |Vr| > |extR| - meaning we consider all terminal productions
        //Case 4: A=S, p is the empty production and for each q in P, for each e in rhs(q), lab(2) =/= S. This says that
        // the empty production is only allowed if there is no other production having the starting symbol in its rhs.

        return false;
    }

    //TODO write test
    private boolean checkIntersectionIsEmpty(ArrayList<String> nonTerminalLabels, ArrayList<String> terminalLabels) {
        List<String> intersectionElements = new ArrayList<String>();

        for (String t : nonTerminalLabels) {
            if(terminalLabels.contains(t)) {
                intersectionElements.add(t);
            }
        }

        if(intersectionElements.isEmpty()){
            return true;
        }
        else {
            return false;
        }
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

    public List<Production> getAllProductions() {
        return allProductions;
    }

    public void setAllProductions(List<Production> allProductions) {
        this.allProductions = allProductions;
    }

}
