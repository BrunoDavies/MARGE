import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HyperedgeReplacementGrammar {
    private ArrayList<String> nonTerminalLabels = new ArrayList<>();    //Labels that identify the non-terminal hyperedges
    private ArrayList<String> terminalLabels = new ArrayList<>();   //Labels that identify the terminal hyperedges

    //Stack for productions.
    // ArrayList<String> productionLHS = new ArrayList<>();
    // Hypergraph productionRHS = new Hypergraph();
    private ArrayList<Production> allProductions; //Add a arraylist with lhs, production id, rhs, and indicator of terminal production
    //Individual production format: {String, String, Hypergraph, Int}

    private String startingSymbol; //Defined from non-terminal labels

    private ArrayList<Production> nonTerminalProductions = new ArrayList<>();

    //TODO Check if there are possible duplicate production ids? if no then this works
    //Just use index as production id.
//    private HashMap<Integer, Production> mapBetweenProductionIdAndData = new HashMap<Integer, Production>();

    public HyperedgeReplacementGrammar(ArrayList<String> nonTerminalLabels, ArrayList<String> terminalLabels,
                                       ArrayList<Production> allProductions, String startingSymbol) {
        this.nonTerminalLabels = nonTerminalLabels;
        this.terminalLabels = terminalLabels;
        this.allProductions = allProductions;
        this.startingSymbol = startingSymbol;

        if (!validHRG())
        {
            throw new IllegalArgumentException("Invalid Grammar Input");
        }

        collectNonTerminalProductions();
    }

    //TODO need to test
    private void collectNonTerminalProductions() {
        for (Production production : allProductions) {
            if (!Collections.disjoint(production.getRightHandSideOfProduction(), nonTerminalLabels)) {
                this.nonTerminalProductions.add(production);
            }
        }
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

    //TODO write test
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
        //Case 3: Er = Empty set, |Vr| > |extR| - meaning we consider all terminal productions
        if (singleProduction.getRightHandSideOfProduction().isEmpty())
        {
            //Might not need to check |Vr| > |extR|
            return true;
        }


        //Case 2: Er = {e1} where lab(e1} in set of TerminalLabels and mark(e1) = alpha (isolated node I think?)
        if (singleProduction.getRightHandSideOfProduction().size() == 1 &&
                terminalLabels.containsAll(singleProduction.getRightHandSideOfProduction()))
        {
            return true;
        }

        //Case 4: A=S, p is the empty production and for each q in P, for each e in rhs(q), lab(2) =/= S. This says that
        // the empty production is only allowed if there is no other production having the starting symbol in its rhs.
        if (singleProduction.getRightHandSideOfProduction().indexOf(0) == 'S')
        {
            //This is done horribly... fix later
            return true;
        }

        //Case 1: Er = {e1, e2} where lab(e1), lab(e2) are in the set of NonTerminalLabels && mark(e1) =/= mark(e2)
        if (singleProduction.getRightHandSideOfProduction().size() > 1 &&
                nonTerminalLabels.containsAll(singleProduction.getRightHandSideOfProduction()) &&
                singleProduction.getRightHandSideOfProduction().get(0) !=
                        singleProduction.getRightHandSideOfProduction().get(1)) {
            //This might not work - not sure if they are in wrong order (two edges) + really sloppy && statement
            return true;
        }

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

    public ArrayList<Production> getAllProductions() {
        return allProductions;
    }

    public void setAllProductions(ArrayList<Production> allProductions) {
        this.allProductions = allProductions;
    }

    public ArrayList<Production> getNonTerminalProductions() {
        return nonTerminalProductions;
    }

    public void setNonTerminalProductions(ArrayList<Production> nonTerminalProductions) {
        this.nonTerminalProductions = nonTerminalProductions;
    }
}
