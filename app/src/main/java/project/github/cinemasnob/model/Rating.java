package project.github.cinemasnob.model;

/**
 * Represent a rating object
 */
public class Rating {

    /**
     * username associated with the rating
     */
    private final String pUsername;

    /**
     * movie title associated with the rating
     */
    private final String pMovieTitle;

    /**
     * rating of the movie
     */
    private final float pRating;

    /**
     * id of the movie
     */
    private final int pId;

    /**
     * Constructor for rating class
     * @param username the username
     * @param movieTitle the movie title
     * @param rating the rating
     * @param id the movie id
     */
    public Rating(String username, String movieTitle, float rating, int id) {
        pUsername = username;
        pMovieTitle = movieTitle;
        pRating = rating;
        pId = id;
    }

    /**
     * Get username associated with rating
     * @return rating
     */
    public String getUsername() {
        return pUsername;
    }

    /**
     * Get movie title
     * @return movieTitle
     */
    public String getMovieTitle() {
        return pMovieTitle;
    }

    /**
     * Get rating
     * @return rating
     */
    public float getRating() {
        return pRating;
    }

    /**
     * Get id
     * @return Movie's id
     */
    public int getId() {
        return pId;
    }
}
