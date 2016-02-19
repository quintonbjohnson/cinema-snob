package comquintonjcinemasnob.github.cinemasnob;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;


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
