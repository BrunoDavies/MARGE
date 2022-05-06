import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MainRggTool {
    private static int userGraphSize;
    private static String userJSONFilePath;
    private static int userNumberOfGraphs;
    public static void main(String[] args) throws NoSuchAlgorithmException, ParseException, IOException {
        getUserInput();
        InputParser currentInput = new InputParser(userJSONFilePath);

        generateUserGraphs(currentInput, userGraphSize, userNumberOfGraphs);
    }

    private static void generateUserGraphs(InputParser currentInput, int userGraphSize, int userNumberOfGraphs) throws NoSuchAlgorithmException {
        ArrayList<Hypergraph> allHypergraphs = new ArrayList<>();

        for (int i = 0; i < userNumberOfGraphs; i++) {
            GraphGeneration userGeneration = new GraphGeneration(currentInput.getUserHRG(), userGraphSize);
            userGeneration.preProcessingPhase();
            userGeneration.generationPhase();
            for (Production p: userGeneration.getProductionExecutionOrder()) {
                System.out.print(p.getProductionId() + " ");
            }
            allHypergraphs.add(userGeneration.getGeneratedHypergraph());
        }
        writeGeneratedGraph(allHypergraphs);
    }

    private static void writeGeneratedGraph(ArrayList<Hypergraph> allHypergraphs) {
        File results = new File("results.json");
        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(results, allHypergraphs);

//            FileWriter fileWriter = new FileWriter("results", true);
//            PrintWriter printWriter = new PrintWriter(fileWriter);
//            Iterator<Hypergraph> iterator = allHypergraphs.iterator();
//            while (iterator.hasNext()) {
//                Hypergraph hypergraph = iterator.next();
//                if (!iterator.hasNext()) {
//                    printWriter.print(production.getProductionId() + "");
//                } else {
//                    printWriter.print(production.getProductionId() + ", ");
//
//                }
//            }
//            printWriter.close();
//            System.out.println("File has been written");

        }catch(Exception e){
            System.out.println("Could not create file");
        }
    }

    private static void getUserInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter path to JSON file with the Hypergraph Grammar: ");
        userJSONFilePath = sc.nextLine();

        System.out.println("Enter size of wanted graph: ");
        userGraphSize = sc.nextInt();

        System.out.println("Number of graphs to be generated with those specifications: ");
        userNumberOfGraphs = sc.nextInt();
    }
}
