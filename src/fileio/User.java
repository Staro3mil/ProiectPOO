package fileio;

import java.util.ArrayList;

public class User {
    private ArrayList<Credentials> credentials;

    public ArrayList<Credentials> getCredentials() {
        return credentials;
    }

    public void setCredentials(ArrayList<Credentials> credentials) {
        this.credentials = credentials;
    }
}
