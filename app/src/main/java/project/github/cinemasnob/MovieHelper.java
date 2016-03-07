package project.github.cinemasnob;

/**
 * Movie helper for averaging ratings
 */
public class MovieHelper {

    private int id;
    private float rating;
    private int count;

    public MovieHelper(int id, float rating, int count) {
        this.rating = rating;
        this.id = id;
        this.count = 1;

    }

    /**
     * Get the movie ID
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get the movie rating
     * @return the rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Get the movie count
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Increment count
     * @param extra the amount to add
     */
    public void setCount(int extra) {
        count = this.count + extra;
    }

    /**
     * Add to rating
     * @param rating the amount to add
     */
    public void addRating(float rating) {
        this.rating = this.rating + rating;
    }

    /**
     * Set the rating
     * @param rating the rating to set to
     */
    public void setRating(float rating) {
        this.rating = rating;
    }
}
