package pages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;
import input.Filter;
import input.Movie;
import input.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        currentUser.resetMovies();
    }
    /** Filters the movies*/
    public void filterMovie(final Action action) {
        if (currentMovies.isEmpty()) {
            currentUser.showUserMovies();
            return;
        }
        Filter filter = action.getFilters();
        Sort sort = filter.getSort();
        String duration;
        String rating;
        if (sort.getDuration() == null) {
             duration = null;
        } else {
             duration = sort.getDuration();
        }
        if (sort.getRating() == null) {
             rating = null;
        } else {
             rating = sort.getRating();
        }

        Collections.sort(currentMovies, new MovieComparator(duration, rating));

        if (action.getFilters().getContains() != null) {
            Movie contains = action.getFilters().getContains();
            for (Movie movie : currentMovies) {
                if (!movie.equals(contains)) {
                    currentMovies.remove(movie);
                }
            }
        }

        currentUser.showUserMovies();
        currentUser.resetMovies();

    }
    /** Displays details about the specified movie
     * and allows for actions to be made for said movie */
    public void seeDetails(final Action action) {
        String movieName = action.getMovie();
        for (Movie movie : currentMovies) {
            if (movieName.equals(movie.getName())) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode errorOut = mapper.createObjectNode();
                errorOut.set("error", null);
                ArrayNode movies = mapper.createArrayNode();
                movies.add(movie.toNode());
                errorOut.set("currentMoviesList", movies);
                errorOut.set("currentUser", currentUser.toNode());
                output.add(errorOut);
                currPage = "see details";
 //resets the current movies to nothing and adds the movie that was specified in the input
                currentMovies = new ArrayList<>();
                currentMovies.add(movie);
                return;
            }
        }
        error();
    }


    @Override
    public void onPage(final Action action) {
        String feature = action.getFeature();
        if (action.getPage() != null) {
            if (action.getPage().equals("see details")) {
                seeDetails(action);
                return;
            }
        }
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
            case "logout":
                currPage = "unauth";
                currentMovies = new ArrayList<>();
                currentUser = null;
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
     //   errorOut.put("error", "Movies Error");
        errorOut.put("error", "Error");
        ArrayNode movieArray = mapper.createArrayNode();
        errorOut.set("currentMoviesList", movieArray);
        errorOut.set("currentUser", null);
        output.add(errorOut);
    }
}
