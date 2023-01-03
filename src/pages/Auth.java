package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

import static input.Global.currentMovies;
import static input.Global.currentUser;
import static input.Global.output;
import static input.Global.currPage;
import static input.Global.pages;
//import static input.Global.errors;

public final class Auth implements Page {

    @Override
    public void changePage(final String nextPage) {
        switch (nextPage) {
            case "movies":
                pages.add(currPage);
                currPage = "movies";
                currentUser.resetMovies();
                currentUser.showUserMovies();
                break;
            case "upgrades":
                pages.add(currPage);
                currPage = "upgrades";
                break;
            case "logout":
                currPage = "unauth";
                currentMovies = new ArrayList<>();
                currentUser = null;
                pages = new ArrayList<>();
                break;
            default:
                error();
                break;
        }
    }
    @Override
    public void onPage(final Action action) {
        error();
    }

    @Override
    public void error() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorOut = mapper.createObjectNode();
       errorOut.put("error", "Error");
   //     errorOut.put("error", "Error Auth");
        ArrayNode movieArray = mapper.createArrayNode();
        errorOut.set("currentMoviesList", movieArray);
        errorOut.set("currentUser", null);
//        errors++;
//        errorOut.put("number", errors);
        output.add(errorOut);

    }

    public Auth() {

    }
}
