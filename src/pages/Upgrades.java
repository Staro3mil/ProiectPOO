package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;

import static input.Global.currentUser;
import static input.Global.currPage;
import static input.Global.output;

public final class Upgrades implements Page {
    /** Buys tokens and substracts the amount from the current User's balance*/
    public void buyToken(final Action action) {
        int amount = action.getCount();
        String string = currentUser.getCredentials().getBalance();
        //Converts the user's balance from string to integer
        int balance = Integer.parseInt(string);
        //Checks if the user has enough balance to buy the tokens
        if (balance < amount) {
            error();
            return;
        }
        balance -= amount;
        int tokenCount = currentUser.getTokensCount();
        currentUser.setTokensCount(tokenCount + amount);
        string = Integer.toString(balance);
        currentUser.getCredentials().setBalance(string);
    }
    /**Makes the current user a premium account*/
    public void buyAccount(final Action action) {
        int amount = 10;
        if (currentUser.getTokensCount() < amount) {
            error();
            return;
        }
        int tokens = currentUser.getTokensCount();
        currentUser.setTokensCount(tokens - amount);
        currentUser.getCredentials().setAccountType("premium");
    }

    @Override
    public void onPage(final Action action) {
        String feature = action.getFeature();
        switch (feature) {
            case "buy tokens":
                buyToken(action);
                break;
            case "buy premium account":
                buyAccount(action);
                break;
            default:
                error();
                break;
        }
    }

    @Override
    public void changePage(final String nextPage) {
        switch (nextPage) {
            case "movies":
                currPage = "movies";
                currentUser.resetMovies();
                currentUser.showUserMovies();
                break;
            case "logout":
                currPage = "unauth";
                currentUser = null;
                break;
            default:
                error();
                break;
        }
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
    }
}
