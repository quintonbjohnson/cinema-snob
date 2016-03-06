package project.github.cinemasnob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MovieSuggestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_suggestion);

        Button majorButton = (Button)findViewById(R.id.major_button);
        Button yearButton = (Button)findViewById(R.id.year_button);
        Button overallButton = (Button)findViewById(R.id.overall_button);

    }

}
