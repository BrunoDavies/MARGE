import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class InputParser {


    private final String filePath;
    private List<Production> productions;
    private HyperedgeReplacementGrammar hrg;

    public InputParser(String filePath) throws ParseException, IOException {
        this.filePath = filePath;
        instantiateHRG();
    }

    public void instantiateHRG() throws ParseException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();

            productions = Arrays.asList(mapper.readValue(Paths.get(filePath).toFile(), Production[].class));


        } catch (Exception e) {
            e.printStackTrace();
        }
        hrg = new HyperedgeReplacementGrammar(new ArrayList<>(productions));
    }

    public String getFilePath() {
        return filePath;
    }

    public HyperedgeReplacementGrammar getUserHRG() {
        return hrg;
    }

}
