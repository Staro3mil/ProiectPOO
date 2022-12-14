package input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import static input.Global.currentMovies;
import static input.Global.currentUser;
import static input.Global.output;
import static input.Global.movies;

public final class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = 15;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();

    /** Displays the current user along with all their current movies without adding any movies*/
    public void showUserMovies() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorOut = mapper.createObjectNode();
        errorOut.set("error", null);
        ArrayNode movieArray = mapper.createArrayNode();
        if (currentMovies != null) {
            for (Movie movie : currentMovies) {
                movieArray.add(movie.toNode());
            }
        }
        errorOut.set("currentMoviesList", movieArray);
        if (currentUser == null) {
            String nothing = null;
            errorOut.put("currentUser", nothing);
        } else {
            ObjectNode cred = currentUser.toNode();
            errorOut.set("currentUser", cred);
        }
        output.add(errorOut);
    }
    /** Resets the movies of the user and adds all the ones that aren't banned*/
    public void resetMovies() {
        currentMovies = new ArrayList<>();
        if (currentUser != null) {
            for (Movie movie : movies) {
                String country = currentUser.getCredentials().getCountry();
                for (String countryBan : movie.getCountriesBanned()) {
                    if (!countryBan.equals(country)) {
                        currentMovies.add(movie);
                    }
                }
            }
        }
    }


    /** Converts the User object to an ObjectNode and returns it */
    public ObjectNode toNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outUser = mapper.createObjectNode();
        outUser.set("credentials", credentials.toNode());
        outUser.put("tokensCount", tokensCount);
        outUser.put("numFreePremiumMovies", numFreePremiumMovies);
        ArrayNode movies = mapper.createArrayNode();

        if (purchasedMovies.isEmpty()) {
            outUser.set("purchasedMovies", movies);
        } else {
            for (Movie movie : purchasedMovies) {
                movies.add(movie.toNode());
            }
            outUser.set("purchasedMovies", movies);
            movies = mapper.createArrayNode();
        }
        if (watchedMovies.isEmpty()) {
            outUser.set("watchedMovies", movies);
        } else {
            for (Movie movie : watchedMovies) {
                movies.add(movie.toNode());
            }
            outUser.set("watchedMovies", movies);
            movies = mapper.createArrayNode();
        }
        if (likedMovies.isEmpty()) {
            outUser.set("likedMovies", movies);
        } else {
            for (Movie movie : likedMovies) {
                movies.add(movie.toNode());
            }
            outUser.set("likedMovies", movies);
            movies = mapper.createArrayNode();
        }
        if (ratedMovies.isEmpty()) {
            outUser.set("ratedMovies", movies);
        } else {
            for (Movie movie : ratedMovies) {
                movies.add(movie.toNode());
            }
            outUser.set("ratedMovies", movies);
            movies = mapper.createArrayNode();
        }
        return outUser;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
}
