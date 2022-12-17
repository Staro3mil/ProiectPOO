package pages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;
import input.Credentials;
import input.User;


import static input.Global.output;
import static input.Global.users;
import static input.Global.currPage;
import static input.Global.currentUser;
//import static input.Global.errors;



public final class Register implements Page {
    /** Checks if the user already exists in the list of already existing users
     * and then adds them to the list */
    public void registerUser(final Credentials registerDetails) {
        //Adds the new user to the list of global users and displays them in the output
        User newUser = new User();
        newUser.setCredentials(registerDetails);
        users.add(newUser);
        currentUser = newUser;
        currentUser.showUserMovies();
        currPage = "auth";

    }
    @Override
    public void onPage(final Action action) {
        String newName = action.getCredentials().getName();
        //Checks if a user with the same name already exists
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
        errorOut.set("currentMoviesList", movieArray);
        errorOut.set("currentUser", null);
//        errors++;
//        errorOut.put("number", errors);
        output.add(errorOut);
        currPage = "unauth";
    }
}
