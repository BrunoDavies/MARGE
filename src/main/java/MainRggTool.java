import com.fasterxml.jackson.core.JsonProcessingException;
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
    public static void main(String[] args) throws NoSuchAlgorithmException, ParseException, JsonProcessingException {

        InputParser currentInput = new InputParser("exampleGrammar.json");

        GraphGeneration userGeneration = new GraphGeneration(currentInput.getUserHRG(), 7);

        userGeneration.preProcessingPhase();
        userGeneration.generationPhase();

        System.out.println("yo");
    }
}
