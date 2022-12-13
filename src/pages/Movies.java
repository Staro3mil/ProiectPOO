package pages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.*;

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
    }
    /** Filters the movies*/
    public void filterMovie(final Action action) {
        if (currentMovies.isEmpty()) {
            currentUser.showUserMovies();
            return;
        }
        Filter filter = action.getFilters();
        Sort sort = filter.getSort();
        String duration = sort.getDuration();
        String rating = sort.getRating();
        currentMovies.sort(Comparator.comparingInt(Movie::getRating));
        if (rating.equals("decreasing")) {
            Collections.sort(currentMovies, Collections.reverseOrder());
        }
        currentMovies.sort(Comparator.comparingInt(Movie::getDuration));
        if (duration.equals("decreasing")) {
            Collections.sort(currentMovies, Collections.reverseOrder());
        }
//        Movie contains = action.getFilters().getContains();
//        for(Movie movie : currentMovies) {
//            if (!java.util.Objects.equals(contains, movie)) {
//                currentMovies.remove(movie);
//            }
//        }


    }

    public void seeDetails(Action action){
        String movieName = action.getMovie();
        for(Movie movie : currentMovies) {
            if (movieName.equals(movie.getName())) {
                return;
            }
        }
        User useraux = new User();
        useraux.setCredentials(currentUser.getCredentials());
        currentUser = null;
        error();
        currentUser = useraux;
    }

    @Override
    public void onPage(final Action action) {
        String feature = action.getFeature();
        if (action.getPage().equals("see details")) {
            seeDetails(action);
            return;
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
