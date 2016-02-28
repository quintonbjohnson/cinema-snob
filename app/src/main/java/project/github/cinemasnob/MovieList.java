package project.github.cinemasnob;

import java.util.ArrayList;

/**
 * Created by Maxwell on 2/21/2016.
 * Represents a list of movies returned from the REST call
 */
public class MovieList {
    private ArrayList<Movie> movieList;

    public MovieList() {
        movieList = new ArrayList<Movie>();
    }

    /**
     * Add movie to movie list
     * @param m
     */
    public void addMovie(Movie m) {
        movieList.add(m);
    }

    /**
     * get titles of all the movies in the list
     * @return arrayList of the titles of all the movies
     */
    public ArrayList<String> getTitleList() {
        ArrayList<String> titles = new ArrayList<String>();
        for (int i = 0; i < movieList.size(); i++) {
            titles.add(movieList.get(i).getTitle() + " [" + movieList.get(i).getID() + "]");
        }
        return titles;
    }
}
