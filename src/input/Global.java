package input;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class Global {
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
}
