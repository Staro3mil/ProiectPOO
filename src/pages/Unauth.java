package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class Unauth extends Page {
    private ArrayNode output;

    public void ChangePage(String nextPage) {
        if(nextPage.equals("login")) {
            super.setCurrentPage("Login");
        } else if (nextPage.equals("register")) {
            super.setCurrentPage("Register");
        } else {
            super.Error(output);
        }

    }

    public Unauth(ArrayNode output) {
        this.output = output;
    }
}
