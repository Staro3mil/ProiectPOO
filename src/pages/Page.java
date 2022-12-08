package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Credentials;
import fileio.Movie;
import fileio.User;

import java.util.ArrayList;

public class Page {
    private String currentPage;
    private ArrayList<Credentials> users;
    private Credentials currentUser;
    private ArrayList<Movie> userMovies;
    public Page() {

    }

    public void OnPage() {

    }

    public void Error(ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode out = mapper.createObjectNode();

        out.put("error", "Error" );
        ArrayNode movieList = mapper.createArrayNode();
        for(Movie movie:userMovies) {
            movieList.add(movie.toNode());
        }
        if (currentUser == null) {
            String nothing = null;
            out.put("currentUser", nothing);
        } else {
            out.set("currentUser", currentUser.toNode());
        }


    }

    public Page(String currentPage, ArrayList<Credentials> users, Credentials currentUser, ArrayList<Movie> userMovies) {
        this.currentPage = currentPage;
        this.users = users;
        this.currentUser = currentUser;
        this.userMovies = userMovies;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<Credentials> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Credentials> users) {
        this.users = users;
    }

    public Credentials getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Credentials currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Movie> getUserMovies() {
        return userMovies;
    }

    public void setUserMovies(ArrayList<Movie> userMovies) {
        this.userMovies = userMovies;
    }
}
