package pages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Movie;
import input.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static input.Global.currentMovies;
import static input.Global.currentUser;
import static input.Global.output;
import static input.Global.currPage;
import static input.Global.movies;
import static input.Global.pages;
//import static input.Global.errors;


public final class Details implements Page  {
    /** Purchases the movie and adds it to the user's list */
    public void purchaseMovie(final Action action) {
        ArrayList<Movie> movies = currentUser.getPurchasedMovies();
        Movie purchasedMovie = currentMovies.get(0);
        //Checks if the user already bought the movie
        for (Movie existingMovies : currentUser.getPurchasedMovies()) {
            if (existingMovies.getName().equals(purchasedMovie.getName())) {
                error();
                return;
            }
        }
        //Checks if the user is premium
        if (currentUser.getCredentials().getAccountType().equals("premium")) {
            //Checks if the user has free movies to use
            if (currentUser.getNumFreePremiumMovies() <= 0) {
                //Checks if the user has enough tokens to buy a movie
                // in case he has no more fee movies
                if (currentUser.getTokensCount() < 2) {
                    error();
                    return;
                }
                movies.add(purchasedMovie);
                currentUser.setTokensCount(currentUser.getTokensCount() - 2);
                currentUser.setPurchasedMovies(movies);
                currentUser.showUserMovies();
                return;
            }
            movies.add(purchasedMovie);
            currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
            currentUser.setPurchasedMovies(movies);
            currentUser.showUserMovies();
        } else {
            //If a user is not premium then it checks for token balance and
            // adds the movie to the list of purchased movies if the user has at least 2 tokens
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
        String movieName = currentMovies.get(0).getName();
        ArrayList<Movie> likedMovies = currentUser.getLikedMovies();
        Movie likedMovie;
        //Checks if the user already liked the movie
        for (Movie existingMovies : currentUser.getLikedMovies()) {
            if (existingMovies.getName().equals(movieName)) {
                error();
                return;
            }
        }
        //Looks in the list of watched movies for the movie that the user wants to like
        //if there is no movie it will return an error
        for (Movie movie : currentUser.getWatchedMovies()) {
            if (movie.getName().equals(movieName)) {
                likedMovie = movie;
                likedMovies.add(likedMovie);
                currentUser.setLikedMovies(likedMovies);
                //Looks for the movie that was liked in the list of total movies
                // and adds +1 to the like count
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
        String movieName = currentMovies.get(0).getName();
        ArrayList<Movie> movies = currentUser.getWatchedMovies();
        Movie watchedMovie;
        //Checks if the user already watched the movie
        for (Movie existingMovies : currentUser.getWatchedMovies()) {
            if (existingMovies.getName().equals(movieName)) {
                currentUser.showUserMovies();
                return;
            }
        }
        //Looks in the list of purchased movies for the movie that the user wants to watch
        //if there is no movie it will return an error
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
        //Checks if the rating is valid
        if (action.getRate() > 5 || action.getRate() < 0) {
            error();
            return;
        }

        String movieName = currentMovies.get(0).getName();
        ArrayList<Movie> movies = currentUser.getRatedMovies();
        Movie ratedMovie;
        //Checks if the user already rated the movie
        for (Movie existingMovies : currentUser.getRatedMovies()) {
            if (existingMovies.getName().equals(movieName)) {
                rate(existingMovies.getName(), action.getRate());
                currentUser.showUserMovies();
                return;
            }
        }
        //Looks in the list of watched movies for the movie that the user wants to rate
        //if there is no movie it will return an error
        for (Movie movie : currentUser.getWatchedMovies()) {
            if (movie.getName().equals(movieName)) {
                ratedMovie = movie;
                movies.add(ratedMovie);
                currentUser.setRatedMovies(movies);
                rate(movieName, action.getRate());
                currentUser.showUserMovies();
                return;
            }
        }
        error();
    }
    /** Adjusts the rating of a movie */
    public void rate(final String movie, final double rate) {
        //Looks for the movie in the global list of movies
        for (Movie searchMovie : movies) {
            String movieName = searchMovie.getName();
            //Checks if the name of the movie matches the one we want to rate
            if (movieName.equals(movie)) {
                //Gets the array of already existing ratings
                HashMap<String, Double> ratings = searchMovie.getRatings();
                //Adds the new rating
                ratings.put(currentUser.getCredentials().getName(), rate);
                Double sum = 0.0;
                //Calculates the average of all the ratings in the array
                for (Map.Entry<String, Double> rates : ratings.entrySet()) {
                    sum += rates.getValue();
                }
                sum = sum / ratings.size();
                //Sets the new array of ratings, the new rating of the movie
                // and the new number of ratings
                searchMovie.setRating(sum);
                searchMovie.setRatings(ratings);
                searchMovie.setNumRatings(ratings.size());
                return;
            }
        }
    }
    /** Subscribes to a genre **/
    public void subscribeGenre(final Action action) {
        for (String genre: currentMovies.get(0).getGenres()) {
            if (genre.equals(action.getSubscribedGenre())) {
                ArrayList<String> subscriptions = currentUser.getSubscriptions();
                subscriptions.add(genre);
                currentUser.setSubscriptions(subscriptions);
                return;
            }
        }
        error();
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
            case "subscribe":
                subscribeGenre(action);
            default:
                error();
                break;
        }
    }

    @Override
    public void changePage(final String nextPage) {
        switch (nextPage) {
            case "auth":
                pages.add(currPage);
                currPage = "auth";
                break;
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
                pages.add(currPage);
                currPage = "unauth";
                currentMovies = new ArrayList<>();
                currentUser = null;
                pages = new ArrayList<>();
                break;
            case "back":
                int n = pages.size() - 1;
                String page = pages.get(n);
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
        //errorOut.put("error", "Error Details");
        errorOut.put("error", "Error");
        ArrayNode movieArray = mapper.createArrayNode();
        errorOut.set("currentMoviesList", movieArray);
        errorOut.set("currentUser", null);
//        errors++;
//        errorOut.put("number", errors);
        output.add(errorOut);
    }
}

