package pages;

import input.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import com.fasterxml.jackson.databind.node.ObjectNode;
import static input.Global.currentMovies;
import static input.Global.currentUser;
import static input.Global.output;
import static input.Global.currPage;

public final class Auth implements Page {

    @Override
    public void changePage(final String nextPage) {
        switch (nextPage) {
            case "movies":
                currPage = "movies";
                currentUser.showUser();
                break;
            case "upgrades":
                currPage = "upgrades";
                break;
            case "logout":
                currPage = "unauth";
                currentUser = null;
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
        ArrayNode movieArray = mapper.createArrayNode();
        if (currentMovies != null) {
            for (Movie movie : currentMovies) {
                movieArray.add(movie.toNode());
            }
        }
        errorOut.set("currentMoviesList", movieArray);

            errorOut.set("currentUser", null);

        output.add(errorOut);

    }

    public Auth() {

    }
}