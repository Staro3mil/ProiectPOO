package pages;
import comparator.MovieComparator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;
import input.Filter;
import input.Movie;
import input.Sort;

import java.util.ArrayList;
import java.util.Collections;

import static input.Global.currentMovies;
import static input.Global.currentUser;
import static input.Global.output;
import static input.Global.currPage;
import static input.Global.movies;
import static input.Global.pages;
//import static input.Global.errors;


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
        String duration;
        String rating;
        //checks if the sort property is empty then checks duration and rating as well
        if (sort != null) {
            if (sort.getDuration() == null) {
                duration = "blank";
            } else {
                duration = sort.getDuration();
            }
            if (sort.getRating() == null) {
                rating = "blank";
            } else {
                rating = sort.getRating();
            }
            //Uses the overwritten compare method of the MovieComparator class
            // to sort the current list of Movies
            Collections.sort(currentMovies, new MovieComparator(duration, rating));
        }


        if (action.getFilters().getContains() != null) {
            Movie contains = action.getFilters().getContains();
            ArrayList<Movie> filteredMovies = new ArrayList<>();
            for (Movie movie : currentMovies) {
                if (movie.equals(contains)) {
                    filteredMovies.add(movie);
                }
            }
            currentMovies = filteredMovies;
        }

        currentUser.showUserMovies();
    }
    /** Displays details about the specified movie
     * and allows for actions to be made for said movie */
    public void seeDetails(final Action action) {
        String movieName = action.getMovie();
        for (Movie movie : currentMovies) {
            if (movieName.equals(movie.getName())) {
                pages.add(currPage);
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
                currentUser.resetMovies();
                searchMovie(action);
                break;
            case "filter":
                currentUser.resetMovies();
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
            case "upgrades":
                pages.add(currPage);
                currPage = "upgrades";
                currentUser.resetMovies();
                break;
            case "homepage":
                pages.add(currPage);
                currPage = "auth";
                currentUser.resetMovies();
                break;
            case "logout":
                pages.add(currPage);
                currPage = "unauth";
                currentMovies = new ArrayList<>();
                currentUser = null;
                pages = new ArrayList<>();
                break;
            case "movies":
                pages.add(currPage);
                currentUser.resetMovies();
                currentUser.showUserMovies();
                break;
            case "back":
                int n = pages.size() - 1;
                String page = pages.get(n);
                if (page.equals("auth")) {
                    currPage = "auth";
                    pages.remove(n);
                    break;
                }
                pages.remove(n);
                changePage(page);
                pages.remove(n);
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
