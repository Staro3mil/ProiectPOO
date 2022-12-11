package input;

public final class Filter {
    private Movie contains;
    private Sort sort;

    public Movie getContains() {
        return contains;
    }

    public void setContains(final Movie contains) {
        this.contains = contains;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(final Sort sort) {
        this.sort = sort;
    }
}
