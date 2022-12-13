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
    private ArrayList<Movie> purchasedMovies = null;
    private ArrayList<Movie> watchedMovies = null;;
    private ArrayList<Movie> likedMovies = null;;
    private ArrayList<Movie> ratedMovies = null;;
    /** Adds the list of movies to the current user and displays them*/
    public void showUser() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorOut = mapper.createObjectNode();
        errorOut.set("error", null);
        ArrayNode movieArray = mapper.createArrayNode();
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
    /** Displays the current user along with all their current movies without adding any*/
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
    public void resetMovies(){
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

        if (purchasedMovies == null) {
            outUser.set("purchasedMovies", movies);
        }
        if (watchedMovies == null) {
            outUser.set("watchedMovies", movies);
        }
        if (likedMovies == null) {
            outUser.set("likedMovies", movies);
        }
        if (ratedMovies == null) {
            outUser.set("ratedMovies", movies);
        }
        return outUser;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }
}
