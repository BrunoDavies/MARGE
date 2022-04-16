package me.champeau.jmh;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GraphGenerationTest {
    //All production prereqs - based off example in research paper; ordered non-terminal to terminal (and then numerical)
    //P1
    private ArrayList<String> p1RHS = new ArrayList<>(Arrays.asList("C", "D"));
    private Production productionP1 = new Production("S", 1, p1RHS,
            null, 1);

    //P2
    private ArrayList<String> p2RHS = new ArrayList<>(Arrays.asList("C", "D"));
    private Production productionP2 = new Production("S", 2, p2RHS,
            null, 0);

    //P3
    private ArrayList<String> p3RHS = new ArrayList<>(Arrays.asList("C", "D"));
    private Production productionP3 = new Production("C", 3, p3RHS,
            null, 1);

    //P6
    private ArrayList<String> p6RHS = new ArrayList<>(Arrays.asList("D", "C"));
    private Production productionP6 = new Production("D", 6, p6RHS,
            null, 1);

    //P4
    private ArrayList<String> p4RHS = new ArrayList<>(Arrays.asList("c"));
    private Production productionP4 = new Production("C", 4, p4RHS,
            null, 0);

    //P5
    private ArrayList<String> p5RHS = new ArrayList<>(Arrays.asList("d"));
    private Production productionP5 = new Production("C", 5, p5RHS,
            null, 0);

    //P7
    private ArrayList<String> p7RHS = new ArrayList<>(Arrays.asList("d"));
    private Production productionP7 = new Production("D", 7, p7RHS,
            null, 0);


    //HRG prereqs
    private ArrayList<String> testNonTerminalLabels = new ArrayList<>(Arrays.asList("S", "C", "D"));
    private ArrayList<String> testTerminalLabels = new ArrayList<>(Arrays.asList("c", "d"));
    private String startingSymbol = "S";
    private ArrayList<Production> allHRGProdcution = new ArrayList<>(Arrays.asList(productionP1, productionP2,
            productionP3, productionP4, productionP5, productionP6, productionP7));

    HyperedgeReplacementGrammar baseHRG = new HyperedgeReplacementGrammar(testNonTerminalLabels, testTerminalLabels,
            allHRGProdcution, startingSymbol);


    //Graph Generation prereqs
    private int testGraphSize = 7;
    GraphGeneration baseGraphGeneration = new GraphGeneration(baseHRG, testGraphSize);
    GraphGeneration testGraphGeneration;


    //Correct Matrices
    private int[][] testNonTerminalMatrix = new int[][]{ {0, 2, 2, 6, 6, 22, 22}, {2, 0, 2, 0, 6, 0, 22},
                                                            {1, 0, 2, 0, 6, 0, 22} };
    private int[][] testProductionMatrix = new int[][] { {0, 0, 2, 0 , 6, 0, 22}, {0, 2, 0, 6, 0, 22, 0},
                                                            {0, 0, 2, 0 , 6, 0, 22}, {1, 0, 0, 0, 0, 0, 0},
                                                                {1, 0, 0, 0, 0, 0, 0}, {0, 0, 2, 0 , 6, 0, 22},
                                                                    {1, 0, 0, 0, 0, 0, 0}};

    //Correct Production order for generation phase
    private ArrayList<Production> orderOfProductions = new ArrayList<>(Arrays.asList(productionP1, productionP4,
                                                                            productionP6, productionP7, productionP5));


    @BeforeEach
    void setUp() {
        System.out.println("Setting up before test...");
        testGraphGeneration = baseGraphGeneration;
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tearing down after test...");
        baseGraphGeneration = null;
    }

    @Test
    void testAllVariablesAreAssigned() {
        assertEquals(testGraphSize, testGraphGeneration.getSizeOfGeneratedGraph());
    }

    @Test
    void testPreProcessingPhaseNonTerminalMatrix() {
        testGraphGeneration.preProcessingPhase();
        assertArrayEquals(testNonTerminalMatrix, testGraphGeneration.getNonTerminalMatrix());
    }

    @Test
    void testPreProcessingPhaseProductionMatrix() {
        testGraphGeneration.preProcessingPhase();
        assertArrayEquals(testProductionMatrix, testGraphGeneration.getProductionMatrix());
    }

    @Test
    void testGenerationPhase() throws NoSuchAlgorithmException {
        testGraphGeneration.preProcessingPhase();
        testGraphGeneration.generationPhase();
        assertEquals(orderOfProductions, testGraphGeneration.getProductionExecutionOrder());
    }
}