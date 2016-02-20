package project.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/*
 * Home Screen
 */
public class HomeScreenActivity extends AppCompatActivity {

    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private static final int MOVIE_PAGE_LIMIT = 10;
    private ListView movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Logout button sends back to login screen
        Button logout = (Button)findViewById(R.id.logout_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(HomeScreenActivity.this, LoginScreenActivity.class);
                startActivity(logout);
                finish();
            }
        });

        // Profile button sends to profile
        Button profile = (Button)findViewById(R.id.profile_button);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(HomeScreenActivity.this, UserProfileActivity.class);
                startActivity(goToProfile);
            }
        });

        // RecentMovies button sends to recent movies
        Button recentMovies = (Button)findViewById(R.id.recentMovies_button);

        recentMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRecentMovie = new Intent(HomeScreenActivity.this, RecentMoviesActivity.class);
                startActivity(goToRecentMovie);
            }
        });

        // RecentDVDs button sends to recent DVDs
        Button recentDVD = (Button)findViewById(R.id.recentDVD_button);

        recentDVD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRecentDVD= new Intent(HomeScreenActivity.this, RecentDVDActivity.class);
                startActivity(goToRecentDVD);
            }
        });

        // Search for Movies
        movieList = (ListView) findViewById(R.id.movieList);
        EditText searchBox = (EditText) findViewById(R.id.searchText);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO: Make appropriate URLConnection / JSON / API calls here
                // Request task; access API and make a URLConnection request
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }
}
