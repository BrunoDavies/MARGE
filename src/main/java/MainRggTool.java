import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class MainRggTool {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray userInput = (JSONArray) parser.parse(new FileReader("exampleGrammar.json"));

            for (Object rawInput : userInput){
                JSONObject singleProduction = (JSONObject) rawInput;

                System.out.println(singleProduction.get("leftHandSide"));
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
