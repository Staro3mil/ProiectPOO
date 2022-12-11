package pages;

import input.Action;

import static input.Global.currPage;
public final class Unauth implements Page {
    @Override
    public void changePage(final String nextPage) {
        switch (nextPage) {
            case "login":
                currPage = "login";
                break;
            case "register":
                currPage = "register";
                break;
            default:
                error();
                break;
        }
    }
    @Override
    public void onPage(final Action action) {

    }

    @Override
    public void error() {

    }

    public Unauth() {

    }
}
