import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HyperedgeReplacementGrammar {
    private ArrayList<String> nonTerminalLabels;
    private ArrayList<String> terminalLabels;
    private ArrayList<Production> allProductions;
    private String startingSymbol;
    private ArrayList<Production> nonTerminalProductions = new ArrayList<>();

    public HyperedgeReplacementGrammar(ArrayList<Production> allProductions) {
        this.allProductions = allProductions;
        this.nonTerminalLabels = getAllNonTerminalEdges();
        this.terminalLabels = getAllTerminalEdges();
        this.startingSymbol = allProductions.get(0).getLeftHandSideOfProduction();

        if (!validHRG())
        {
            throw new IllegalArgumentException("Invalid Grammar Input");
        }

        collectNonTerminalProductions();
    }

    public HyperedgeReplacementGrammar(ArrayList<String> nonTerminalLabels, ArrayList<String> terminalLabels,
                                       ArrayList<Production> allProductions, String startingSymbol) {
        this.allProductions = allProductions;
        this.nonTerminalLabels = nonTerminalLabels;
        this.terminalLabels = terminalLabels;
        this.startingSymbol = startingSymbol;

        if (!validHRG())
        {
            throw new IllegalArgumentException("Invalid Grammar Input");
        }

        collectNonTerminalProductions();
    }

    private void collectNonTerminalProductions() {
        for (Production production : allProductions) {
            if (!Collections.disjoint(production.getRightHandSideOfProduction(), nonTerminalLabels)) {
                this.nonTerminalProductions.add(production);
            }
        }
    }

    private boolean validHRG() {
        boolean nonTerminalLabelFinite = Double.isFinite(nonTerminalLabels.size());
        boolean terminalLabelFinite = Double.isFinite(terminalLabels.size());
        //Intersection of nonTer and Ter needs to be empty
        boolean intersectionOfNonTerminalAndTerminal = checkIntersectionIsEmpty(nonTerminalLabels, terminalLabels);
        //Check if list of productions is finite?
        boolean startIsInNonTerminal = nonTerminalLabels.contains(startingSymbol);
        boolean allProductionsValid = allProductionValid(allProductions);

        return (nonTerminalLabelFinite && terminalLabelFinite && intersectionOfNonTerminalAndTerminal && startIsInNonTerminal
                && allProductionsValid);
    }

    public ArrayList<Production> getProductionWithLHS(String label) {
        ArrayList<Production> productionsWithLHS = new ArrayList<>();
        for (Production production : allProductions) {
            if (production.getLeftHandSideOfProduction().equals(label)) {
                productionsWithLHS.add(production);
            }
        }
        return productionsWithLHS;
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
        //Case 3: Er = Empty set, |Vr| > |extR| - meaning we consider all terminal productions
        if (singleProduction.getRightHandSideOfProduction().isEmpty())
        {
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
            return true;
        }

        //Case 1: Er = {e1, e2} where lab(e1), lab(e2) are in the set of NonTerminalLabels && mark(e1) =/= mark(e2)
        if (nonTerminalLabels.containsAll(singleProduction.getRightHandSideOfProduction()) &&
                singleProduction.getRightHandSideOfProduction().get(0) != singleProduction.getRightHandSideOfProduction().get(1)) {
            return true;
        }

        return false;
    }

    private boolean checkIntersectionIsEmpty(ArrayList<String> nonTerminalLabels, ArrayList<String> terminalLabels) {
        List<String> intersectionElements = new ArrayList<>();

        for (String t : nonTerminalLabels) {
            if(terminalLabels.contains(t)) {
                intersectionElements.add(t);
            }
        }

        return intersectionElements.isEmpty();
    }

    private ArrayList<String> getAllNonTerminalEdges() {
        ArrayList<String> allNonTerminalEdges = new ArrayList<>();

        for (Production p : allProductions){
            if (!allNonTerminalEdges.contains(p.getLeftHandSideOfProduction())){
                allNonTerminalEdges.add(p.getLeftHandSideOfProduction());
            }
            for (String edge : p.getRightHandSideHypergraph().allNonTerminalEdges()) {
                if (!allNonTerminalEdges.contains(edge)){
                    allNonTerminalEdges.add(edge);
                }
            }
        }
        return allNonTerminalEdges;
    }

    private ArrayList<String> getAllTerminalEdges() {
        ArrayList<String> allTerminalEdged = new ArrayList<>();

        for (Production p : allProductions){
            for (String edge : p.getRightHandSideHypergraph().allTerminalEdges()) {
                if (!allTerminalEdged.contains(edge)){
                    allTerminalEdged.add(edge);
                }
            }
        }
        return allTerminalEdged;
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
