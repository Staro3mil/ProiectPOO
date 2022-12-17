package comparator;
import java.util.Comparator;
import input.Movie;


public final class MovieComparator implements Comparator<Movie> {
    //variables can be either "blank", "decreasing" or "increasing"
    private String rating;
    private String duration;

    public MovieComparator(final String duration, final String rating) {
        this.rating = rating;
        this.duration = duration;
    }
    //Compares two variables of type int
    private int compareInt(final int x, final int y) {
        if (x > y) {
            return 1;
        }
        if (x < y) {
            return -1;
        }
        return 0;
    }
    //Compares two variables of type double
    private int compareDouble(final double x, final double y) {
        if (x > y) {
            return 1;
        }
        if (x < y) {
            return -1;
        }
        return 0;
    }
    //Overrides the compare method as to sort by duration and rating
    @Override
    public int compare(final Movie o1, final Movie o2) {
        //If there is no duration then it sorts by rating and
        // if the rating is in decreasing order then it swaps the values of o1 and o2
        if (duration.equals("blank")) {
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
        //Same thing as before except now rating is blank
        if (rating.equals("blank")) {
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
        //If both duration and rating are not blank it starts by sorting by duration then
        // if the duration is equal it sorts by rating
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
