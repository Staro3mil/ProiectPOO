package input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public final class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private int rating;
    private int numRatings;

    /** Converts the Movie object to an ObjectNode and returns it */
    public ObjectNode toNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("name", name);
        node.put("year", year);
        node.put("duration", duration);
        ArrayNode genreArray = mapper.createArrayNode();
        for (String genre : genres) {
            genreArray.add(genre);
        }
        node.set("genres", genreArray);
        ArrayNode actorsArray = mapper.createArrayNode();
        for (String actor : actors) {
            actorsArray.add(actor);
        }
        node.set("actors", actorsArray);
        ArrayNode countriesArray = mapper.createArrayNode();
        for (String country : countriesBanned) {
            countriesArray.add(country);
        }
        node.set("countriesBanned", countriesArray);
        node.put("numLikes", numLikes);
        node.put("rating", rating);
        node.put("numRatings", numRatings);
        return node;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }
}
