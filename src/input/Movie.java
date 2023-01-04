package input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.HashMap;

import static input.Global.movies;

public final class Movie {
    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres = new ArrayList<>();
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;
    private ArrayList<String> genre = new ArrayList<>();
    //Each movie has an array of ratings from previous users
    // which is used to calculate the rating
    private HashMap<String, Double> ratings = new HashMap<>();

    /** Converts the Movie object to an ObjectNode and returns it */
    public ObjectNode toNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("name", name);
        node.put("year", year);
        node.put("duration", duration);
        ArrayNode genreArray = mapper.createArrayNode();
        //Checks to see if the genre array is empty,
        // if it is then it uses the genres array instead
        //if not then it takes the first element of the genre array
        if (genre.isEmpty()) {
            for (String genreMultiple : genres) {
                genreArray.add(genreMultiple);
            }
            node.set("genres", genreArray);
        } else {
            node.put("genre", genre.get(0));
        }

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
        //Takes the number of likes, rating and number of ratings
        // from the global list of movies instead of the current list of movies
        for (Movie movie : movies) {
            if (name.equals(movie.getName())) {
                numLikes = movie.getNumLikes();
                rating = movie.getRating();
                numRatings = movie.getNumRatings();
                break;
            }
        }

        node.put("numLikes", numLikes);
        node.put("rating", rating);
        node.put("numRatings", numRatings);
        return node;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(final String year) {
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

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public HashMap<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(HashMap<String, Double> ratings) {
        this.ratings = ratings;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(final ArrayList<String> genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(final Object o) {
        Movie comparator = (Movie) o;
        boolean matchActor = false;
        boolean matchGenre = false;
        boolean matchGenres = false;
        if (comparator.getActors() != null) {
            for (String actor : this.getActors()) {
                if (actor.equals(comparator.getActors().get(0))) {
                    matchActor = true;
                }
            }
        } else {
            matchActor = true;
        }
        if (!comparator.getGenre().isEmpty()) {
            for (String genreSingle : this.getGenres()) {
                if (genreSingle.equals(comparator.getGenre().get(0))) {
                    matchGenre = true;
                }
            }
        } else {
            matchGenre = true;
        }
        if (!comparator.getGenres().isEmpty()) {
            for (String genreMultiple : this.getGenres()) {
                if (genreMultiple.equals(comparator.getGenres().get(0))) {
                    matchGenres = true;
                }
            }
        } else {
            matchGenres = true;
        }


        return (matchActor && matchGenre && matchGenres);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
