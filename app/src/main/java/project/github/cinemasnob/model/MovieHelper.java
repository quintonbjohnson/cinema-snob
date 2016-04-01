package project.github.cinemasnob.model;

/**
 * Movie helper for averaging ratings
 */
public class MovieHelper {

    /**
     * id of the movie
     */
    private final int pId;

    /**
     * rating of the movie
     */
    private float pRating;

    /**
     * number of times same movie has been rated
     */
    private int pCount;

    /**
     * title of the movie
     */
    private final String pTitle;

    /**
     *
     * @param id the movie id
     * @param rating the movie rating
     * @param count the number of the same movies
     * @param title the movie title
     */
    public MovieHelper(int id, float rating, int count, String title) {
        pRating = rating;
        pId = id;
        pTitle = title;
        pCount = count;
    }

    /**
     * Get the movie ID
     *
     * @return the ID
     */
    public int getId() {
        return pId;
    }

    /**
     * Get the movie rating
     *
     * @return the rating
     */
    public float getRating() {
        return pRating;
    }

    /**
     * Get the movie count
     *
     * @return the count
     */
    public int getCount() {
        return pCount;
    }

    /**
     * Get the movie title
     *
     * @return the title
     */
    public String getTitle() {
        return pTitle;
    }

    /**
     * Increment count
     *
     * @param extra the amount to add
     */
    public void setCount(int extra) {
        pCount = pCount + extra;
    }

    /**
     * Add to rating
     *
     * @param rating the amount to add
     */
    public void addRating(float rating) {
        pRating = pRating + rating;
    }

    /**
     * Set the rating
     *
     * @param rating the rating to set to
     */
    public void setRating(float rating) {
        pRating = rating;
    }
}


