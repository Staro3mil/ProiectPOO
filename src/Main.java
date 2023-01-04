import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import input.Action;
import input.Global;
import input.Input;
import pages.Auth;
import pages.Details;
import pages.Login;
import pages.Movies;
import pages.Page;
import pages.Register;
import pages.Unauth;
import pages.Upgrades;
import static input.Global.users;
import static input.Global.output;
import static input.Global.movies;
import static input.Global.currentMovies;
import static input.Global.currentUser;
import static input.Global.currPage;
//import static input.Global.errors;

public class Main {
    /** Does the thing */
    public static void main(final String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Input input = mapper.readValue(new File(args[0]), Input.class);
        Global global = new Global();
        output = mapper.createArrayNode();
        ArrayList<Action> actionList = input.getActions();
        users = input.getUsers();
        movies = input.getMovies();
        currentMovies = new ArrayList<>();
        currentUser = null;
        currPage = "unauth";
        //Creates a new HashMap of pairs of Strings and Page objects
        //Each string is associated with its corresponding Page object
        //Eg. the "login" string points to a Login type object
        Map<String, Page> pageHandler = new HashMap<>();
        //Adds every page and its corresponding string to the HashMap
        pageHandler.put("login", new Login());
        pageHandler.put("unauth", new Unauth());
        pageHandler.put("register", new Register());
        pageHandler.put("auth", new Auth());
        pageHandler.put("movies", new Movies());
        pageHandler.put("upgrades", new Upgrades());
        pageHandler.put("see details", new Details());

        for (int i = 0; i < actionList.size(); i++) {
            Action currAction = actionList.get(i);
            String type = currAction.getType();
            //Checks to see which page we are currently on and
            // looks for the string in the pageHandler HashMap
            //it then grabs the object associated with that string and
            // assigns it to the currentPage variable
            Page currentPage = pageHandler.get(currPage);
            switch (type) {
                case "change page":
                    // if we want to change the page we check first if we are on the movies page and
                    // want to change to "see details". If we are then
                    // we call the onPage method instead of the changePage method
                    String nextPage = currAction.getPage();
                    if (currPage.equals("movies") && nextPage.equals("see details")) {
                        currentPage.onPage(currAction);
                    } else {
                        //Otherwise we check if the user wants to return to a
                        // previous page or change to a new one
                        currentPage.changePage(nextPage);
                    }
                    break;
                case "back":
                    currentPage.changePage(type);
                    break;
                case "on page":
                    currentPage.onPage(currAction);
                    break;
                case "database":
                    if (currAction.getFeature().equals("add")) {
                        global.addData(currAction.getAddedMovie());
                    } else {
                        global.removeData((currAction.getDeletedMovie()));
                    }
                    break;
                default:
                    break;
            }

        }
        global.recommend();



        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
        //Also creates an output.json file to check our output for debugging purposes
        objectWriter.writeValue(new File("output.json"), output);
    }
}
