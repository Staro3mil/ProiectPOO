package pages;

import input.Action;

public interface Page {
    /** Method for what is on the page*/
     void onPage(Action action);
    /** Method for changing to a new page*/
     void changePage(String nextPage);
    /** Method for displaying an error*/
     void error();






}
