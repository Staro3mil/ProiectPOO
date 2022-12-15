package pages;
import java.util.Comparator;
import input.Movie;


public class MovieComparator implements Comparator<Movie> {
    private String rating;
    private String duration;

    public MovieComparator (String duration, String rating) {
        this.rating = rating;
        this.duration = duration;
    }
    private int compareInt(int x, int y) {
        if (x > y) {
            return 1;
        }
        if (x < y) {
            return -1;
        }
        return 0;
    }
    private int compareDouble(double x, double y) {
        if (x > y) {
            return 1;
        }
        if (x < y) {
            return -1;
        }
        return 0;
    }
    @Override
    public int compare(Movie o1, Movie o2) {
        if (duration.equals(null)) {
            double rating1 = o1.getRating();
            double rating2 = o2.getRating();
            if (rating.equals("decreasing")) {
                double temp = rating1;
                rating1 = rating2;
                rating2 = temp;
            }
            int value2 = compareDouble(rating1, rating2);
            return value2;
        }
        if (rating.equals(null)) {
            int duration1 = o1.getDuration();
            int duration2 = o2.getDuration();
            if (duration.equals("decreasing")) {
                int temp = duration1;
                duration1 = duration2;
                duration2 = temp;
            }
            int value1 = compareInt(duration1, duration2);
            return value1;
        }
        int duration1 = o1.getDuration();
        int duration2 = o2.getDuration();
        if (duration.equals("decreasing")) {
            int temp = duration1;
            duration1 = duration2;
            duration2 = temp;
        }
        int value1 = compareInt(duration1, duration2);
        if (value1 == 0) {
            double rating1 = o1.getRating();
            double rating2 = o2.getRating();
            if (rating.equals("decreasing")) {
                double temp = rating1;
                rating1 = rating2;
                rating2 = temp;
            }
            int value2 = compareDouble(rating1, rating2);
            return value2;
        }
        return value1;
    }

}
