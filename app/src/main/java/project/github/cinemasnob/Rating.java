package project.github.cinemasnob;

/**
 * Represent a rating object
 */
public class Rating {

    private String username;
    private String movieTitle;
    private String rating;

    public Rating(String username, String movieTitle, String rating) {
        this.username = username;
        this.movieTitle = movieTitle;
        this.rating = rating;
    }

    /**
     * Get username associated with rating
     * @return rating
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get movie title
     * @return movieTitle
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Get rating
     * @return rating
     */
    public String getRating() {
        return rating;
    }
}