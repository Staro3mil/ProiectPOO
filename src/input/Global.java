package input;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

public class Global {
    public static String currPage = "unauth";
    public static ArrayList<User> users;
    public static User currentUser = null;
    public static ArrayList<Movie> currentMovies;
    public static ArrayList<Movie> movies;
    public static ArrayNode output;
}
