import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HyperedgeReplacementGrammarTest {

    private ArrayList<String> testNonTerminalLabels = new ArrayList<>(Arrays.asList("S", "C", "D"));
    private ArrayList<String> testTerminalLabels = new ArrayList<>(Arrays.asList("c", "d"));
    private ArrayList<String> p1CharList = new ArrayList<>(Arrays.asList("C", "D"));
    private Production productionP1 = new Production("S", 1, p1CharList,
            null, 1);

    private List<Production> testAllProductions = new ArrayList<>(Arrays.asList(productionP1));
    private String testStartingSymbol = "S";


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
    void collectNonTerminalProductionsOne() {
        assertEquals(testAllProductions, testHRG.getNonTerminalProductions());
    }
}