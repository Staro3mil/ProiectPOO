package pages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;
import input.Credentials;
import input.User;
import input.Movie;
import static input.Global.currentMovies;
import static input.Global.currentUser;
import static input.Global.output;
import static input.Global.currPage;
import static input.Global.users;
public final class Register implements Page {
    /** Checks if the user already exists in the list of already existing users
     * and then adds them to the list */
    public void registerUser(final Credentials registerDetails) {
        User newUser = new User();
        newUser.setCredentials(registerDetails);
        users.add(newUser);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outUser = mapper.createObjectNode();
        Integer nothing = null;
        outUser.put("error", nothing);
        ArrayNode movieArray = mapper.createArrayNode();
        if (currentMovies != null) {
            for (Movie movie : currentMovies) {
                movieArray.add(movie.toNode());
            }
        }
        outUser.set("currentMoviesList", movieArray);
        outUser.set("currentUser", newUser.toNode());
        output.add(outUser);
        currPage = "auth";
    }
    @Override
    public void onPage(final Action action) {
        String newName = action.getCredentials().getName();
        for (User existingUser: users) {
            String name = existingUser.getCredentials().getName();
            if (newName.equals(name)) {
                error();
                return;
            }
        }
        registerUser(action.getCredentials());
    }

    @Override
    public void changePage(final String nextPage) {

    }

    @Override
    public void error() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorOut = mapper.createObjectNode();
        errorOut.put("error", "Error");
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
            ObjectNode credentials = currentUser.getCredentials().toNode();
            errorOut.set("currentUser", credentials);
        }
        output.add(errorOut);
        currPage = "unauth";
    }
}
