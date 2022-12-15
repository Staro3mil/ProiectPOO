package pages;
import java.util.Comparator;
import input.Movie;


public class MovieComparator implements Comparator<Movie> {
    private int compareInt(int x, int y) {
        if (x > y) {
            return -1;
        }
        if (x < y) {
            return 0;
        }
        return 1;
    }
    private int compareDouble(double x, double y) {
        if (x > y) {
            return -1;
        }
        if (x < y) {
            return 0;
        }
        return 1;
    }
    @Override
    public int compare(Movie o1, Movie o2) {
        int duration1 = o1.getDuration();
        int duration2 = o2.getDuration();
        int value1 = compareInt(duration1, duration2);
        if (value1 == 0) {
            double rating1 = o1.getRating();
            double rating2 = o2.getRating();
            int value2 = compareDouble(rating1, rating2);
            if (value2 == 0) {
                return value1;
            } else {
                return value2;
            }
        }
        return value1;
    }
}
