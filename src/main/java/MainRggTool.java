import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainRggTool {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        JSONParser parser = new JSONParser();
        ArrayList<Production> userProductionInput = new ArrayList<>();

        try {
            JSONArray userInput = (JSONArray) parser.parse(new FileReader("exampleGrammar.json"));

            for (Object rawInput : userInput){
                JSONObject jsonProduction = (JSONObject) rawInput;
                JSONObject jsonHypergraph = (JSONObject) jsonProduction.get("hypergraph");


                Hypergraph productionHypergraph = new Hypergraph((ArrayList<Integer>) jsonHypergraph.get("nodes"),
                            (ArrayList<Integer>) jsonHypergraph.get("internalStatus"),
                            (ArrayList<String>) jsonHypergraph.get("edges"),
                            (ArrayList<Integer>) jsonHypergraph.get("terminalStatus"),
                            (HashMap<String, ArrayList<Integer>>) jsonHypergraph.get("attachments"));

                Production singleProduction = new Production((String) jsonProduction.get("leftHandSide"),
                            ((Long) jsonProduction.get("productionID")).intValue(),
                            (ArrayList<String>) jsonProduction.get("rightHandSide"),
                            productionHypergraph,
                            ((Long) jsonProduction.get("internalNodes")).intValue());

                userProductionInput.add(singleProduction);
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("yo2");

        HyperedgeReplacementGrammar userHRG = new HyperedgeReplacementGrammar(userProductionInput);

        GraphGeneration userGeneration = new GraphGeneration(userHRG, 7);

        userGeneration.preProcessingPhase();
        userGeneration.generationPhase();

        System.out.println("yo");
    }
}
