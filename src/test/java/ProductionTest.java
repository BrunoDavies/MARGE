import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductionTest {
    //Hypergraph
    private ArrayList<Integer> nodes = new ArrayList<>(Arrays.asList(1, 2, 3));
    private ArrayList<Integer> internalStatus = new ArrayList<>(Arrays.asList(0, 0, 1));
    private ArrayList<String> edges = new ArrayList<>(Arrays.asList("A", "B", "C"));
    private ArrayList<Integer> terminalStatus = new ArrayList<>(Arrays.asList(1, 1, 0));

    private Hypergraph testHypergraph = new Hypergraph(nodes, internalStatus, edges, terminalStatus, null);

    //Production
    private String leftHandSideOfProduction = "S";
    private int productionId = 1;
    private ArrayList<String> rightHandSideOfProduction =  new ArrayList<>(Arrays.asList("C", "D"));
    private Hypergraph rightHandSideHypergraph = testHypergraph;
    private int numberOfInternalNodes = 1;
    private Production baseProduction = new Production(leftHandSideOfProduction, productionId, rightHandSideOfProduction,
                                                            rightHandSideHypergraph, numberOfInternalNodes);

    private Production testProduction;

    @BeforeEach
    void setUp() {
        System.out.println("Setting up before test...");
        testProduction = baseProduction;
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tearing down after test...");
        testProduction = null;
    }

    @Test
    void basicProductionPasses() {
        assertEquals(baseProduction.getProductionId(), testProduction.getProductionId());
    }
}