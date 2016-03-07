package project.github.cinemasnob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Context;

import java.util.ArrayList;

/**
 * Movie suggestions
 */
public class MovieSuggestionActivity extends AppCompatActivity {

    private RatingOpenHelper ratingdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestion);
        Context context = this;

        ratingdb = new RatingOpenHelper(context);
        Button overallButton = (Button)findViewById(R.id.overall_button);

        overallButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Average out movies in the database
                ArrayList<MovieHelper> movies = ratingdb.averageOverall(ratingdb);
            }
        });
    }
}
