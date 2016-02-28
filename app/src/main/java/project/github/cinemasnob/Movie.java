package project.github.cinemasnob;

/**
 * Created by Maxwell on 2/21/2016.
 * Represents a single movie returned from a REST call
 * this class represents a single movie object
 */
public class Movie {

    private String title;
    private String year;
    private String mpaa_rating;
    private String movieID;

    public Movie(String title, String year, String mpaa_rating, String movieID) {
        this.title = title;
        this.year = year;
        this.mpaa_rating = mpaa_rating;
        this.movieID = movieID;
    }

    /**
     * Get movie title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get movie year
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * Get rating
     * @return mpaa_rating
     */
    public String getMpaa_rating() {
        return mpaa_rating;
    }

    /**
     * Get movie id Number
     * return movieId
     */
    public String getID() {
        return movieID;
    }
}
