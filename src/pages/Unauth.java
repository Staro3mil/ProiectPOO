package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;

import static input.Global.pages;
import static input.Global.currPage;
import static input.Global.output;

public final class Unauth implements Page {
    @Override
    public void changePage(final String nextPage) {
        switch (nextPage) {
            case "login":
                pages.add(currPage);
                currPage = "login";
                break;
            case "register":
                pages.add(currPage);
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
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errorOut = mapper.createObjectNode();
        errorOut.put("error", "Error");
        ArrayNode movieArray = mapper.createArrayNode();
        errorOut.set("currentMoviesList", movieArray);
        errorOut.set("currentUser", null);
        output.add(errorOut);
    }

    public Unauth() {

    }
}
