package input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;

import static input.Global.currentUser;
import static input.Global.currentMovies;
import static input.Global.movies;
import static input.Global.output;
//import static input.Global.errors;

public final class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = 15;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();
    private ArrayList<Notification> notifications = new ArrayList<>();
    private ArrayList<String> subscriptions = new ArrayList<>();

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
//        errors++;
//        errorOut.put("number", errors);
        output.add(errorOut);
    }
    /** Displays the current user along with all their current movies without adding any movies*/
    public void displayReccomendation() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorOut = mapper.createObjectNode();
        errorOut.set("error", null);
        errorOut.set("currentMoviesList", null);
        if (currentUser == null) {
            String nothing = null;
            errorOut.put("currentUser", nothing);
        } else {
            ObjectNode cred = currentUser.toNode();
            errorOut.set("currentUser", cred);
        }
//        errors++;
//        errorOut.put("number", errors);
        output.add(errorOut);
    }
    /** Resets the movies of the user and adds all the ones that aren't banned*/
    public void resetMovies() {
        currentMovies = new ArrayList<>();
        if (currentUser != null) {
            for (Movie movie : movies) {
                String country = currentUser.getCredentials().getCountry();
                Boolean banned = false;
                for (String countryBan : movie.getCountriesBanned()) {
                    if (countryBan.equals(country)) {
                        banned = true;
                    }
                }
                if (!banned) {
                    currentMovies.add(movie);
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
        ArrayNode pMovies = mapper.createArrayNode();
        ArrayNode lMovies = mapper.createArrayNode();
        ArrayNode wMovies = mapper.createArrayNode();
        ArrayNode rMovies = mapper.createArrayNode();
        ArrayNode notifs = mapper.createArrayNode();
        //Goes through each array of Purchased Movies, Liked Movies etc.
        //in order to convert each movie into a node then add it to the arrayNode
        //respective to each kind of movie array
        if (purchasedMovies.isEmpty()) {
            outUser.set("purchasedMovies", pMovies);
        } else {
            for (Movie movie : purchasedMovies) {
                pMovies.add(movie.toNode());
            }
            outUser.set("purchasedMovies", pMovies);
        }
        if (watchedMovies.isEmpty()) {
            outUser.set("watchedMovies", wMovies);
        } else {
            for (Movie movie : watchedMovies) {
                wMovies.add(movie.toNode());
            }
            outUser.set("watchedMovies", wMovies);
        }
        if (likedMovies.isEmpty()) {
            outUser.set("likedMovies", lMovies);
        } else {
            for (Movie movie : likedMovies) {
                lMovies.add(movie.toNode());
            }
            outUser.set("likedMovies", lMovies);
        }
        if (ratedMovies.isEmpty()) {
            outUser.set("ratedMovies", rMovies);
        } else {
            for (Movie movie : ratedMovies) {
                rMovies.add(movie.toNode());
            }
            outUser.set("ratedMovies", rMovies);
        }
        if (notifications.isEmpty()) {
            outUser.set("notifications", notifs);
        } else {
            for (Notification notification : notifications) {
                notifs.add(notification.toNode());
            }
            outUser.set("notifications", notifs);
        }
        return outUser;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(final ArrayList<String> subscriptions) {
        this.subscriptions = subscriptions;
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
