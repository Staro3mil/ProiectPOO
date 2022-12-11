package input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public final class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = 15;
    private ArrayList<Movie> purchasedMovies = null;
    private ArrayList<Movie> watchedMovies = null;;
    private ArrayList<Movie> likedMovies = null;;
    private ArrayList<Movie> ratedMovies = null;;
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
