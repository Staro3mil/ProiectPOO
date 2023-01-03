package input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public final class Global {
    //current page the user is on
    public static String currPage = "unauth";
    //global list of users
    public static ArrayList<User> users;
    //the current user that is logged in
    public static User currentUser = null;
    //the list of currently available movies
    public static ArrayList<Movie> currentMovies;
    //the list of global movies
    public static ArrayList<Movie> movies;
    //the output which will be written into the JSON file at the end
    public static ArrayNode output;
    //public static int errors = 0;
    public static ArrayList<String> pages = new ArrayList<>();
    /** Adds a movie to the global database **/
    public void addData(final Movie movie) {
        for (Movie existingMovie : movies) {
            if (existingMovie.getName().equals(movie.getName())) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode errorOut = mapper.createObjectNode();
                //errorOut.put("error", "Error Details");
                errorOut.put("error", "Error");
                ArrayNode movieArray = mapper.createArrayNode();
                errorOut.set("currentMoviesList", movieArray);
                errorOut.set("currentUser", null);
                //errors++;
                //errorOut.put("number", errors);
                output.add(errorOut);
                return;
            }
        }
        movies.add(movie);
        for (User user : users) {
            Boolean banned = false;
            String userCountry = user.getCredentials().getCountry();
            for (String country : movie.getCountriesBanned()) {
                if (country.equals(userCountry)) {
                    banned = true;
                    break;
                }
            }
            if (banned) {
                continue;
            }
            for (String genre : movie.getGenres()) {
                for (String userGenre : user.getSubscriptions()) {
                    if (userGenre.equals(genre)) {
                        ArrayList<Notification> notif = user.getNotifications();
                        Notification notifSingle = new Notification(movie.getName(), "ADD");
                        notif.add(notifSingle);
                        user.setNotifications(notif);
                        break;
                    }
                }
            }
        }
    }
}


