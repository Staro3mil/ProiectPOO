package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.Movie;
import fileio.User;

import java.util.ArrayList;

public class Page {
    private String currentPage;
    private ArrayList<User> users;
    private User currentUser;
    public Page() {

    }

    public void OnPage() {

    }

    public Page(String currentPage, ArrayList<User> users, User currentUser) {
        this.currentPage = currentPage;
        this.users = users;
        this.currentUser = currentUser;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
