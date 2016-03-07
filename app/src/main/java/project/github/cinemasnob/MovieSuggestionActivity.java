package project.github.cinemasnob;

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

/**
 * Movie suggestions
 */
public class MovieSuggestionActivity extends AppCompatActivity {

    private RatingOpenHelper ratingdb;
    private ListView sortedMovieList;
    private HashMap<String, Integer> movieIds = new HashMap<String, Integer>();

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
                    movieIds.put(help.getTitle(), help.getId());
                }

                // Populate the ListView
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        MovieSuggestionActivity.this,
                        android.R.layout.simple_list_item_1,
                        titles);
                sortedMovieList.setAdapter(arrayAdapter);
            }
        });

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
