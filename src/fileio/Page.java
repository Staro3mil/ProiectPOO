package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Page {

    String current;

    ArrayList<Movie> movies;

    User user = null;

    String v = null;

    ObjectMapper mapper = new ObjectMapper();

    public Page(String current) {
        this.current = current;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ObjectNode ChangePage(String nextPage) {
        if (current == "unauth" && !nextPage.matches("login|regiser")){
            ObjectNode out = mapper.createObjectNode();
            out.put("Error", "error");
            ArrayNode movieArray = mapper.createArrayNode();
            for(Movie movie : movies) {
                movieArray.add(movie.toNode());
            }
            out.set("currentMoviesList", movieArray);
            if ( user == null) {
                out.put("currentUser", v);
            } else {
                out.set("currentUser", user.getCredentials().toNode());
            }
        }
    }
}
