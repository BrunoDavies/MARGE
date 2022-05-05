import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InputParser {


    private final String filePath;
    private ArrayList<Production> userProductionInput = new ArrayList<>();
    private HyperedgeReplacementGrammar userHRG;
    private Hypergraph productionHypergraph;

    public InputParser(String filePath) throws ParseException, JsonProcessingException {
        this.filePath = filePath;
        instantiateHRG();
    }

    public void instantiateHRG() throws ParseException, JsonProcessingException {
        JsonNode root = new ObjectMapper().readTree(filePath);
        ObjectMapper mapper = new ObjectMapper();

        for (JsonNode individualElement : root){
            String lhs = individualElement.get("leftHandSide").asText();
            int productionID = individualElement.get("productionID").asInt();
            ArrayList<String> rhs = new ArrayList<>();
            for (JsonNode rhsElement : individualElement.get("rightHandSide")) {
                rhs.add(rhsElement.asText());
            }
            Hypergraph hypergraph = mapper.readValue(individualElement.get("hypergraph").asText(), Hypergraph.class);
            int internalNodes = individualElement.get("internalNodes").asInt();

            Production production = new Production(lhs, productionID, rhs, hypergraph, internalNodes);
            userProductionInput.add(production);
        }
        userHRG = new HyperedgeReplacementGrammar(userProductionInput);

//        JSONParser parser = new JSONParser();
//
//        try {
//            JSONArray userInput = (JSONArray) parser.parse(new FileReader("exampleGrammar.json"));
//
//            for (Object rawInput : userInput){
//                JSONObject jsonProduction = (JSONObject) rawInput;
//                JSONObject jsonHypergraph = (JSONObject) jsonProduction.get("hypergraph");
//
//
//                productionHypergraph = new Hypergraph((ArrayList<Integer>) jsonHypergraph.get("nodes"),
//                        (ArrayList<Integer>) jsonHypergraph.get("internalStatus"),
//                        (ArrayList<String>) jsonHypergraph.get("edges"),
//                        (ArrayList<Integer>) jsonHypergraph.get("terminalStatus"),
//                        (HashMap<String, ArrayList<Integer>>) jsonHypergraph.get("attachments"));
//
//                singleProduction = new Production((String) jsonProduction.get("leftHandSide"),
//                        ((Long) jsonProduction.get("productionID")).intValue(),
//                        (ArrayList<String>) jsonProduction.get("rightHandSide"),
//                        productionHypergraph,
//                        ((Long) jsonProduction.get("internalNodes")).intValue());
//
//                userProductionInput.add(singleProduction);
//            }
//        } catch (IOException e){
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        userHRG = new HyperedgeReplacementGrammar(userProductionInput);
    }

    public String getFilePath() {
        return filePath;
    }

    public HyperedgeReplacementGrammar getUserHRG() {
        return userHRG;
    }

    public void setUserHRG(HyperedgeReplacementGrammar userHRG) {
        this.userHRG = userHRG;
    }
}
