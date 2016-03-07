package project.github.cinemasnob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Movie suggestions
 */
public class MovieSuggestionActivity extends AppCompatActivity {

    private RatingOpenHelper ratingdb;
    private ListView sortedMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestion);
        Context context = this;
        sortedMovieList = (ListView)findViewById(R.id.sorted_movies);

        ratingdb = new RatingOpenHelper(context);
        Button overallButton = (Button)findViewById(R.id.overall_button);

        overallButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Comparator<MovieHelper> myComparator = new Comparator<MovieHelper>() {
                    @Override
                    public int compare(MovieHelper lhs, MovieHelper rhs) {
                        return (int) ((int) lhs.getRating() - rhs.getRating());
                    }
                };
                // Average out movies in the database
                ArrayList<MovieHelper> movies = ratingdb.averageOverall(ratingdb);
                // Sort movies from highest to lowest rating
                Collections.sort(movies, myComparator);
                Collections.reverse(movies);
                ArrayList<String> titles = new ArrayList<String>();
                for (MovieHelper help : movies) {
                    titles.add(help.getTitle());
                }

                // Populate the ListView
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        MovieSuggestionActivity.this,
                        android.R.layout.simple_list_item_1,
                        titles);
                sortedMovieList.setAdapter(arrayAdapter);
            }
        });
    }
}
