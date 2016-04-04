package project.github.cinemasnob.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of movies returned from the REST call.
 */
public class MovieList {

    /**
     * List of movies.
     */
    private final List<String> movieList;

    /**
     * Constructor for MovieList.
     */
    public MovieList() {
        movieList = new ArrayList<>();
    }

    /**
     * Add movie to movie list.
     * @param t the movie title
     */
    public void addTitle(String t) {
        movieList.add(t);
    }

    /**
     * Get titles of all the movies in the list.
     * @return arrayList of the titles of all the movies
     */
    public List<String> getTitleList() {
//        final List<String> titles = new ArrayList<>();
//        for (int i = 0; i < movieList.size(); i++) {
//            titles.add(movieList.get(i).getTitle());
//        }
//        return titles;
        return movieList;
    }
}
