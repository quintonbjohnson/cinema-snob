package project.github.cinemasnob.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.github.cinemasnob.R;
import project.github.cinemasnob.controller.RatingOpenHelper;
import project.github.cinemasnob.controller.UserOpenHelper;
import project.github.cinemasnob.model.MovieHelper;
import project.github.cinemasnob.model.User;

/**
 * Movie suggestions
 */
public class MovieSuggestionActivity extends AppCompatActivity {

    /**
     * opens ratings database
     */
    private RatingOpenHelper ratingDB;
    /**
     * opens user database
     */
    private UserOpenHelper userDB;
    /**
     * list of movies in order put into a ListView
     */
    private ListView sortedMovieList;
    /**
     * map of movie titles and id numbers
     */
    private final Map<String, Integer> movieIds = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestion);
        Context context = this;
        this.sortedMovieList = (ListView) findViewById(R.id.sorted_movies);

        // Instantiate databases
        ratingDB = new RatingOpenHelper(context);
        userDB = new UserOpenHelper(context);

        // Sort overall movies
        Button overallButton = (Button) findViewById(R.id.overall_button);

        overallButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Comparator<MovieHelper> myComparator = new Comparator<MovieHelper>() {
                    @Override
                    public int compare(MovieHelper lhs, MovieHelper rhs) {
                        return (int) ((int) lhs.getRating() - rhs.getRating());
                    }
                };
                List<MovieHelper> movies = ratingDB.averageOverall(ratingDB); // Average out movies in the database
                Collections.sort(movies, myComparator); // Sort movies from highest to lowest rating
                Collections.reverse(movies);
                ArrayList<String> titles = new ArrayList<>();
                for (MovieHelper help : movies) {
                    titles.add(help.getTitle());
                    movieIds.put(help.getTitle(), help.getId());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( // Populate the ListView
                        MovieSuggestionActivity.this,
                        android.R.layout.simple_list_item_1, titles);
                sortedMovieList.setAdapter(arrayAdapter);
            }
        });

        // Sort overall ratings by major
        Button majorButton = (Button) findViewById(R.id.major_button);

        majorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Comparator<MovieHelper> myComparator = new Comparator<MovieHelper>() {
                    @Override
                    public int compare(MovieHelper lhs, MovieHelper rhs) {
                        return (int) ((int) lhs.getRating() - rhs.getRating());
                    }
                };
                // Average out movies in the database
                List<MovieHelper> movies = ratingDB.averageMajor(ratingDB,
                        userDB,
                        User.getCurrentUser().getMajor());
                // Sort movies from highest to lowest rating
                Collections.sort(movies, myComparator);
                Collections.reverse(movies);
                ArrayList<String> titles = new ArrayList<>();
                for (MovieHelper help : movies) {
                    titles.add(help.getTitle());
                    movieIds.put(help.getTitle(), help.getId());
                }
                // Populate the ListView
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        MovieSuggestionActivity.this,
                        android.R.layout.simple_list_item_1,
                        titles);
                sortedMovieList.setAdapter(arrayAdapter);
            }
        });


        // When User taps on a movie item, send him or her
        // to the MovieItemActivity
        sortedMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Selected item
                String title = ((TextView) view).getText().toString();
                int movieID = movieIds.get(title);
                // Launching new Activity on selecting single List Item
                Intent goToMovieItem = new Intent(getApplicationContext(),
                        MovieItemActivity.class);
                // Sending data to new activity
                goToMovieItem.putExtra("ID", movieID);
                startActivity(goToMovieItem);
            }
        });
    }
}
