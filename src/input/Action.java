package input;

public final class Action {
    private String type;
    private String page;
    private String feature;

    private Credentials credentials;

    private String startsWith;

    private Filter filters;

    private int count;

    private Double rate;

    private String movie;

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(final Filter filters) {
        this.filters = filters;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(final Double rate) {
        this.rate = rate;
    }
}
