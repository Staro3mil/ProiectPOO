package fileio;

public class Filter {
    private Movie contains;
    private Sort sort;

    public Movie getContains() {
        return contains;
    }

    public void setContains(Movie contains) {
        this.contains = contains;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
