package fileio;

import java.util.ArrayList;

public class Input {
    private ArrayList<Credentials> users;
    private ArrayList<Movie> movies;
    private ArrayList<Action> actions;

    public Input(){

    }

    public ArrayList<Credentials> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Credentials> users) {
        this.users = users;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }
}
