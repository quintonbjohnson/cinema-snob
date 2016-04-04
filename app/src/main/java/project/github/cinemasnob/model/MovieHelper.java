package project.github.cinemasnob.model;

/**
 * Movie helper for averaging ratings.
 */
public class MovieHelper {

    /**
     * ID of the Movie.
     */
    private final int pId;

    /**
     * Rating of the Movie.
     */
    private float pRating;

    /**
     * Number of times same Movie has been rated.
     */
    private int pCount;

    /**
     * Title of the Movie.
     */
    private final String pTitle;

    /**
     * The MovieHelper constructor.
     * @param movieID the movie id
     * @param rating the movie rating
     * @param count the number of the same movies
     * @param title the movie title
     */
    public MovieHelper(int movieID, float rating, int count, String title) {
        this.pRating = rating;
        this.pId = movieID;
        this.pTitle = title;
        this.pCount = count;
    }

    /**
     * Get the Movie ID.
     * @return the ID
     */
    public int getId() {
        return pId;
    }

    /**
     * Get the Movie rating.
     * @return the rating
     */
    public float getRating() {
        return pRating;
    }

    /**
     * Get the Movie count.
     * @return the count
     */
    public int getCount() {
        return pCount;
    }

    /**
     * Get the Movie title.
     *
     * @return the title
     */
    public String getTitle() {
        return pTitle;
    }

    /**
     * Increment count.
     *
     * @param extra the amount to add
     */
    public void setCount(int extra) {
        pCount = pCount + extra;
    }

    /**
     * Add to Rating.
     *
     * @param rating the amount to add
     */
    public void addRating(float rating) {
        pRating = pRating + rating;
    }

    /**
     * Set the Rating.
     *
     * @param rating the rating to set to
     */
    public void setRating(float rating) {
        pRating = rating;
    }
}


