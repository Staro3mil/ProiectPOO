import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;


public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Input input = mapper.readValue(new File(args[0]), Input.class);
        ArrayNode output = mapper.createArrayNode();


        ArrayList<Action> actionList = input.getActions();

        for (int i = 0; i < actionList.size(); i++) {
            Action currAction = actionList.get(i);
            String type = currAction.getType();
            String page = currAction.getPage();

            if (type.equals("change page")) {

            }

        }




        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
        objectWriter.writeValue(new File("output.txt"), output);
    }
}
