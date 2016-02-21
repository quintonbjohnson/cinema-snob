package project.github.cinemasnob;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

/**
 * Created by Maxwell on 2/21/2016.
 * Represents a single movie returned from a REST call
 * this class represents a single movie object
 */
public class Movie {

    private String title;
    private String year;
    private String mpaa_rating;

    public Movie(String title, String year, String mpaa_rating) {
        this.title = title;
        this.year = year;
        this.mpaa_rating = mpaa_rating;
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
}
