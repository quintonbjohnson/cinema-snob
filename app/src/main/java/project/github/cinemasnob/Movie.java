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
    private String rating;
    private String username;

    public Movie(String title, String year, String mpaa_rating) {
        this.title = title;
        this.year = year;
        this.mpaa_rating = mpaa_rating;
    }

    public Movie(int which, String username, String title, String rating) {
        this.title = title;
        this.rating = rating;
        this.username = username;
    }

    /**
     * get movie title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * get movie year
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * get rating
     * @return mpaa_rating
     */
    public String getMpaa_rating() {
        return mpaa_rating;
    }

    /**
     * get movie rating
     * @return rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * get username
     * @return username
     */
    public String getUsername() {
        return username;
    }
}
