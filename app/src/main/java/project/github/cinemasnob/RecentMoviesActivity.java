package project.github.cinemasnob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Activity for recent movies
 */
public class RecentMoviesActivity extends AppCompatActivity {

    public static final String[] movieList = {
            "Titanic",
            "Deadpool",
            "Fight Club"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_movies);

        ListView view = (ListView)findViewById(R.id.movieList);
        //view.setAdapter(new EnhancedListAdapter(this, movieList));

    }
}
