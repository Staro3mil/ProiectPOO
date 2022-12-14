package pages;

import input.Action;

import static input.Global.currPage;
import static input.Global.currentUser;
public final class Upgrades implements Page {
    /** Buys tokens and substracts the amount from the current User's balance*/
    public void buyToken(final Action action) {
        int amount = action.getCount();
        String string = currentUser.getCredentials().getBalance();
        int balance = Integer.parseInt(string);
        balance -= amount;
        int tokenCount = currentUser.getTokensCount();
        currentUser.setTokensCount(tokenCount + amount);
        string = Integer.toString(balance);
        currentUser.getCredentials().setBalance(string);
    }
    /**Makes the current user a premium account*/
    public void buyAccount(final Action action) {
        int amount = 10;
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

    }
}
