package pages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;
import input.Movie;
import static input.Global.currentMovies;
import static input.Global.currentUser;
import static input.Global.output;
import static input.Global.currPage;
import static input.Global.movies;

public final class Movies implements Page {
    /** Searches for a movie in the list of current movies
     * and removes the ones that do not start with the given String*/
    public void searchMovie(final Action action) {
        String search = action.getStartsWith();
        for (Movie movie : movies) {
            String movieName = movie.getName();
            if (!movieName.startsWith(search)) {
                currentMovies.remove(movie);
            }
        }
        currentUser.showUserMovies();
    }
    /** Filters the movies*/
    public void filterMovie(final Action action) {
        if (currentMovies.isEmpty()) {
            currentUser.showUserMovies();
        }
    }
    @Override
    public void onPage(final Action action) {
        String feature = action.getFeature();
        switch (feature) {
            case "search":
                searchMovie(action);
                break;
            case "filter":
                filterMovie(action);
                break;
            default:
                error();
                break;
        }
    }

    @Override
    public void changePage(final String nextPage) {
        switch (nextPage) {
            case "homepage":
                currPage = "movies";
                break;
            case "see details":
                currPage = "upgrades";
                break;
            case "logout":
                currPage = "unauth";
                break;
            default:
                error();
                break;
        }
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
        if (currentUser == null) {
            errorOut.set("currentUser", null);
        } else {
            ObjectNode credentials = currentUser.getCredentials().toNode();
            errorOut.set("currentUser", credentials);
        }
        output.add(errorOut);
    }
}
