package project.github.cinemasnob.model;

/**
 * Movie helper for averaging ratings
 */
public class MovieHelper {

    private final int id;
    private float rating;
    private int count;
    private final String title;

    /**
     *
     * @param id the movie id
     * @param rating the movie rating
     * @param count the number of the same movies
     * @param title the movie title
     */
    public MovieHelper(int id, float rating, int count, String title) {
        this.rating = rating;
        this.id = id;
        this.title = title;
        this.count = count;
    }

    /**
     * Get the movie ID
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get the movie rating
     *
     * @return the rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Get the movie count
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Get the movie title
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Increment count
     *
     * @param extra the amount to add
     */
    public void setCount(int extra) {
        count = this.count + extra;
    }

    /**
     * Add to rating
     *
     * @param rating the amount to add
     */
    public void addRating(float rating) {
        this.rating = this.rating + rating;
    }

    /**
     * Set the rating
     *
     * @param rating the rating to set to
     */
    public void setRating(float rating) {
        this.rating = rating;
    }
}


