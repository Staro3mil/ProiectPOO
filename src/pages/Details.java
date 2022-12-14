package pages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Movie;
import input.Action;

import java.util.ArrayList;
import static input.Global.currentMovies;
import static input.Global.currentUser;
import static input.Global.output;
import static input.Global.currPage;
import static input.Global.movies;

public final class Details implements Page  {
    /** Purchases the movie and adds it to the user's list */
    public void purchaseMovie(final Action action) {
        ArrayList<Movie> movies = currentUser.getPurchasedMovies();
        Movie purchasedMovie = currentMovies.get(0);

        if (currentUser.getCredentials().getAccountType().equals("premium")) {
            movies.add(purchasedMovie);
            currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
            currentUser.setPurchasedMovies(movies);
            currentUser.showUserMovies();
        } else {
            if (currentUser.getTokensCount() < 2) {
                error();
                return;
            }
            movies.add(purchasedMovie);
            currentUser.setTokensCount(currentUser.getTokensCount() - 2);
            currentUser.setPurchasedMovies(movies);
            currentUser.showUserMovies();
        }
    }
    /** Likes the movie and adds it to the user's list */
    public void likeMovie(final Action action) {
        String movieName = action.getMovie();
        ArrayList<Movie> likedMovies = currentUser.getLikedMovies();
        Movie likedMovie;
        for (Movie movie : currentUser.getWatchedMovies()) {
            if (movie.getName().equals(movieName)) {
                likedMovie = movie;
                likedMovies.add(likedMovie);
                currentUser.setLikedMovies(likedMovies);
                //Looks for the movie that was liked in the list of total movies
                // and adds one to the like count
                for (Movie liked : movies) {
                    if (liked.getName().equals(movieName)) {
                        liked.setNumLikes(liked.getNumLikes() + 1);
                    }
                }
                currentUser.showUserMovies();
                return;
            }
        }
        error();
    }
    /** Watches the movie and adds it to the user's list */
    public void watchMovie(final Action action) {
        String movieName = action.getMovie();
        ArrayList<Movie> movies = currentUser.getWatchedMovies();
        Movie watchedMovie;
        for (Movie movie : currentUser.getPurchasedMovies()) {
            if (movie.getName().equals(movieName)) {
                watchedMovie = movie;
                movies.add(watchedMovie);
                currentUser.setWatchedMovies(movies);
                currentUser.showUserMovies();
                return;
            }
        }
        error();

    }
    /** Rates the movie and adds it to the user's list */
    public void rateMovie(final Action action) {
        String movieName = action.getMovie();
        ArrayList<Movie> movies = currentUser.getRatedMovies();
        Movie ratedMovie;
        for (Movie movie : currentUser.getWatchedMovies()) {
            if (movie.getName().equals(movieName)) {
                ratedMovie = movie;
                movies.add(ratedMovie);
                currentUser.setRatedMovies(movies);
                rate(movieName, action.getRate());
                return;
            }
        }
        error();
    }
    /** Adjusts the rating of a movie */
    public void rate(final String movie, final double rate) {
        for (Movie searchMovie : movies) {
            String movieName = searchMovie.getName();
            if (movieName.equals(movie)) {
                ArrayList<Double> ratings = searchMovie.getRatings();
                ratings.add(rate);
                Double sum = 0.0;
                for (Double rates : ratings) {
                    sum += rates;
                }
                sum = sum / ratings.size();
                searchMovie.setRating(sum);
                searchMovie.setRatings(ratings);
                searchMovie.setNumRatings(ratings.size());
            }
        }
    }


    @Override
    public void onPage(final Action action) {
        String feature = action.getFeature();
        switch (feature) {
            case "purchase":
                purchaseMovie(action);
                break;
            case "like":
                likeMovie(action);
                break;
            case "watch":
                watchMovie(action);
                break;
            case "rate":
                rateMovie(action);
                break;
            default:
                error();
                break;
        }
    }

    @Override
    public void changePage(final String nextPage) {
        switch (nextPage) {
            case "auth":
                currPage = "auth";
                break;
            case "movies":
                currPage = "movies";
                currentUser.resetMovies();
                currentUser.showUserMovies();
                break;
            case "upgrades":
                currPage = "upgrades";
                break;
            case "logout":
                currPage = "unauth";
                currentMovies = new ArrayList<>();
                currentUser = null;
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
        errorOut.set("currentMoviesList", movieArray);
        errorOut.set("currentUser", null);
        output.add(errorOut);
    }
}

