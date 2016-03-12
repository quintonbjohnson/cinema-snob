package project.github.cinemasnob.model;

/**
 * Represent a rating object
 */
public class Rating {

    private String username;
    private String movieTitle;
    private float rating;
    private int id;

    public Rating(String username, String movieTitle, float rating, int id) {
        this.username = username;
        this.movieTitle = movieTitle;
        this.rating = rating;
        this.id = id;
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
    public float getRating() {
        return rating;
    }

    /**
     * Get id
     * @return Movie's id
     */
    public int getId() {
        return id;
    }
}
