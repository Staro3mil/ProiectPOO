import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import input.*;
import pages.*;
import static input.Global.*;


public class Main {
    /** Does the thing */
    public static void main(final String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Input input = mapper.readValue(new File(args[0]), Input.class);
        output = mapper.createArrayNode();
        ArrayList<Action> actionList = input.getActions();
        users = input.getUsers();
        movies = input.getMovies();
        Map<String, Page> pageHandler = new HashMap<>();
        pageHandler.put("login", new Login());
        pageHandler.put("unauth", new Unauth());
        pageHandler.put("register", new Register());
        pageHandler.put("auth", new Auth());

        for (int i = 0; i < actionList.size(); i++) {
            Action currAction = actionList.get(i);
            String type = currAction.getType();
            Page currentPage = pageHandler.get(currPage);
            if (type.equals("change page")) {
                String nextPage = currAction.getPage();
                currentPage.changePage(nextPage);
            } else {
                currentPage.onPage(currAction);
            }


        }




        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
        objectWriter.writeValue(new File("output.txt"), output);
    }
}
