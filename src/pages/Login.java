package pages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;
import input.Credentials;
import input.User;

import java.util.ArrayList;

import static input.Global.currentUser;
import static input.Global.output;
import static input.Global.currPage;
import static input.Global.users;
import static input.Global.pages;




public final class Login implements Page {
    /** Checks if the user exists in the list of already existing users and then logs them in */
    public void loginUser(final Credentials loginDetails) {
        for (User user : users) {
            Credentials credential = user.getCredentials();
            String existingName = credential.getName();
            String existingPassword = credential.getPassword();
            String loginName = loginDetails.getName();
            String loginPassword = loginDetails.getPassword();
            //Checks if the name and password match an existing user
            if (existingName.equals(loginName) && loginPassword.equals(existingPassword)) {
                //Changes the current user, displays them and
                // changes the current page to the Authenticated Homepage
                currentUser = user;
                currentUser.showUserMovies();
                currPage = "auth";
                pages = new ArrayList<>();
                return;
            }
        }
        error();
    }
    @Override
    public void onPage(final Action action) {
        loginUser(action.getCredentials());
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
        output.add(errorOut);
        currPage = "unauth";
    }

}
