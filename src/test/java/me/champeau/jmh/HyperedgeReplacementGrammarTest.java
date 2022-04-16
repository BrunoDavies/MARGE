package me.champeau.jmh;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HyperedgeReplacementGrammarTest {

    private ArrayList<String> testNonTerminalLabels = new ArrayList<>(Arrays.asList("S", "C", "D"));
    private ArrayList<String> testTerminalLabels = new ArrayList<>(Arrays.asList("c", "d"));
    private ArrayList<String> ahhh = new ArrayList<>(Arrays.asList("c"));
    private ArrayList<String> p1CharList = new ArrayList<>(Arrays.asList("C", "D"));
    private Production productionP1 = new Production("S", 1, p1CharList,
            null, 1);
    private Production productionP2 = new Production("S", 2, p1CharList,
            null, 0);

    private ArrayList<Production> testAllProductions = new ArrayList<>(Arrays.asList(productionP1));
    private String testStartingSymbol = "S";

    private ArrayList<String> terminalRHS = new ArrayList<>(Arrays.asList("E", "F"));
    private Production terminalProduction = new Production("C", 3, terminalRHS,
            null, 0);

    HyperedgeReplacementGrammar baseHRG = new HyperedgeReplacementGrammar(testNonTerminalLabels, testTerminalLabels,
            testAllProductions, testStartingSymbol);

    HyperedgeReplacementGrammar testHRG;

    @BeforeEach
    void setUp() {
        System.out.println("Setting up before test...");
        testHRG = baseHRG;
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tearing down after test...");
        testHRG = null;
    }

    @Test
    void getNonTerminalLabels() {
        assertEquals(testNonTerminalLabels, testHRG.getNonTerminalLabels());
    }

    @Test
    void getTerminalLabels() {
        assertEquals(testTerminalLabels, testHRG.getTerminalLabels());
    }

    @Test
    void getStartingSymbol() {
        assertEquals(testStartingSymbol, testHRG.getStartingSymbol());
    }

    @Test
    void getAllProductions() {
        assertEquals(testAllProductions, testHRG.getAllProductions());
    }

    //TODO parameterize these tests

    @Test
    void collectNonTerminalProductionsBaseHRG() {
        assertEquals(testAllProductions, testHRG.getNonTerminalProductions());
    }

    @Test
    void multipleNonTerminalProductionsAreAddedToNTList(){
        ArrayList<String> terminalRHS = new ArrayList<>(Arrays.asList("c"));
        Production terminalProduction = new Production("C", 3, terminalRHS, null, 0);
        ArrayList<Production> onlyTerminalProductions = new ArrayList<>();
        onlyTerminalProductions.add(productionP1);
        onlyTerminalProductions.add(productionP2);
        onlyTerminalProductions.add(terminalProduction);

        HyperedgeReplacementGrammar testHRG = new HyperedgeReplacementGrammar(testNonTerminalLabels, testTerminalLabels,
                onlyTerminalProductions,
                testStartingSymbol);

        assertEquals(productionP1, testHRG.getNonTerminalProductions().get(0));
        assertEquals(productionP2, testHRG.getNonTerminalProductions().get(1));
    }

    @Test
    void invalidHRGInput(){
        ArrayList<Production> invalidProductionList = new ArrayList<>(Arrays.asList(terminalProduction));
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            HyperedgeReplacementGrammar invalidHRG = new HyperedgeReplacementGrammar(testNonTerminalLabels,
                    testTerminalLabels, invalidProductionList, "S");
        });
    }

    @Test
    void emptyProductionIsValid(){
        ArrayList<String> emptyRHS= new ArrayList<>();
        Production emptyProduction = new Production("F", 1, emptyRHS,
                 null, 0);
        ArrayList<Production> invalidProductionList = new ArrayList<>(Arrays.asList(emptyProduction));
        HyperedgeReplacementGrammar emptyProductionHRG = new HyperedgeReplacementGrammar(testNonTerminalLabels,
                testTerminalLabels, invalidProductionList, testStartingSymbol);

        assertTrue(emptyProductionHRG.getAllProductions().get(0).getRightHandSideOfProduction().isEmpty());
    }

    //TODO Finish this off
    @Test
    void production() {

    }

}