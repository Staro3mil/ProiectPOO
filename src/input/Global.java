package input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

   public void recommend(){
       HashMap<String, Integer> likes = new HashMap<>();
       likes.put("Action", 0);
       likes.put("Comedy", 0);
       likes.put("Crime", 0);
       likes.put("Thriller", 0);
       for (Movie movie : currentUser.getLikedMovies()) {
            for (String movieGenre : movie.getGenres()) {
                likes.put(movieGenre, likes.get(movieGenre) + 1);
            }
       }
       Integer max = 0;
       for (Map.Entry<String, Integer> element : likes.entrySet()) {
           if ( element.getValue() > max) {
               max = element.getValue();
           }
       }
       if (max == 0) {
           Notification notification = new Notification("No recommendation", "Recommendation");
           ArrayList<Notification> notif = currentUser.getNotifications();
           notif.add(notification);
           currentUser.setNotifications(notif);
           currentUser.displayRecommendation();
           return;
       }
       for (Map.Entry<String, Integer> element : likes.entrySet()) {
           if ( element.getValue() == max) {
               genreRecommended(element.getKey());
               return;
           }
       }





   }

   public void genreRecommended(String genre) {
       currentUser.resetMovies();
       for (Movie likedMovie : currentUser.getLikedMovies()) {
           for (int i = 0; i < currentMovies.size(); i++) {
               Movie movie = currentMovies.get(i);
               if (likedMovie.getName().equals(movie.getName())) {
                   currentMovies.remove(i);
               }
           }
       }
       String movieName = currentMovies.get(0).getName();
       int maxLikes = 0;
       for (Movie movie : currentMovies) {
           for (String movieGenre : movie.getGenres()) {
               if (movieGenre.equals(genre) && movie.getNumLikes() > maxLikes) {
                   movieName = movie.getName();
               }
           }
       }
       if (movieName.isEmpty()) {
           Notification notification = new Notification("No recommendation", "Recommendation");
           ArrayList<Notification> notif = currentUser.getNotifications();
           notif.add(notification);
           currentUser.setNotifications(notif);
           currentUser.displayRecommendation();
       } else {
           Notification notification = new Notification(movieName, "Recommendation");
           ArrayList<Notification> notif = currentUser.getNotifications();
           notif.add(notification);
           currentUser.setNotifications(notif);
           currentUser.displayRecommendation();
       }
   }

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
    public void removeData (final String deletedMovie) {
        for ( Movie movie : movies) {
            //goes through all movies
            if (movie.getName().equals(deletedMovie)) {
                movies.remove(movie);
                //goes through all the users
                for (User user : users) {
                    //checks the purchased movies first
                    for (Movie purchasedMovie : user.getPurchasedMovies()) {
                        if (purchasedMovie.getName().equals(deletedMovie)) {
                            //refunds the movie
                            if (user.getCredentials().getAccountType().equals("premium")) {
                                user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() + 1);
                            } else {
                                user.setTokensCount(user.getTokensCount() + 2);
                            }
                            //adds the notification
                            Notification notif = new Notification(deletedMovie, "DELETE");
                            ArrayList<Notification> notifications = new ArrayList<>();
                            notifications = user.getNotifications();
                            notifications.add(notif);
                            user.setNotifications(notifications);
                            ArrayList<Movie> movies1 = user.getPurchasedMovies();
                            movies1.remove(purchasedMovie);
                            user.setPurchasedMovies(movies1);
                            //checks the watched movies
                            for (Movie watched : user.getWatchedMovies()) {
                                if (watched.getName().equals(deletedMovie)) {
                                     movies1 = user.getWatchedMovies();
                                    movies1.remove(watched);
                                    user.setWatchedMovies(movies1);
                                    break;
                                }
                            }
                            //checks liked movies
                            for (Movie liked : user.getLikedMovies()) {
                                if (liked.getName().equals(deletedMovie)) {
                                    movies1 = user.getLikedMovies();
                                    movies1.remove(liked);
                                    user.setLikedMovies(movies1);
                                    break;
                                }
                            }
                            //checks rated movies
                            for (Movie rated : user.getRatedMovies()) {
                                if (rated.getName().equals(deletedMovie)) {
                                    movies1 = user.getRatedMovies();
                                    movies1.remove(rated);
                                    user.setRatedMovies(movies1);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
                //if the movie exists it stops looking through the rest of the movies
                return;
            }
        }
        //if there was no movie found with that name returns an error
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


