package project.github.cinemasnob.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import project.github.cinemasnob.R;
import project.github.cinemasnob.controller.RequestController;
import project.github.cinemasnob.model.Movie;
import project.github.cinemasnob.model.MovieList;

/*
 * Home Screen
 */
public class HomeScreenActivity extends AppCompatActivity {

    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private static final int MOVIE_PAGE_LIMIT = 10;
    private ListView movieList;
    private HashMap<String, Integer> movieIds = new HashMap<>();
    private EditText searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Creating a listerner for clicking the listview
        movieList = (ListView)findViewById(R.id.movieList);
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                String title = ((TextView) view).getText().toString();
                int movieID = movieIds.get(title);
                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), MovieItemActivity.class);
                // sending data to new activity
                i.putExtra("ID", movieID);
                startActivity(i);
                searchBox.setText("");
            }
        });

        // Logout button sends back to login screen
        Button logout = (Button)findViewById(R.id.admin_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(HomeScreenActivity.this,
                        LoginScreenActivity.class);
                startActivity(logout);
                finish();
            }
        });

        // Profile button sends to profile
        Button profile = (Button)findViewById(R.id.profile_button);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(HomeScreenActivity.this,
                        UserProfileActivity.class);
                startActivity(goToProfile);
            }
        });

        // Suggestion button sends to profile
        Button suggestion = (Button)findViewById(R.id.suggestion_button);

        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSuggestion = new Intent(HomeScreenActivity.this,
                        MovieSuggestionActivity.class);
                startActivity(goToSuggestion);
            }
        });

        // RecentMovies button sends to recent movies
        Button recentMovies = (Button)findViewById(R.id.recentMovies_button);

        recentMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList = (ListView)findViewById(R.id.movieList);
                final MovieList listOfMovies = new MovieList();
                String url = "";
                try {
                    url = "http://api.rottentomatoes.com/api/public/v1.0/" +
                            "lists/movies/in_theaters.json?page_limit=15&page=1&country=us&apikey="
                            + API_KEY;
                } catch (Exception e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //create new movieList
                        //MovieList listOfMovies = new MovieList();
                        try {
                            JSONArray movies = response.getJSONArray("movies");
                            //one single movie
                            JSONObject movie;
                            //Test block to see how many movies
                            // there are that are returned in the JSON object
                            for (int i = 0; i < movies.length(); i++) {
                                movie = movies.getJSONObject(i);
                                //create a new movie
                                Movie newMovie = new Movie(movie.getString("title"),
                                        movie.getString("year"),
                                        movie.getString("mpaa_rating"), movie.getString("id"));
                                listOfMovies.addMovie(newMovie);
                                movieIds.put(movie.getString("title"), Integer.parseInt(movie.getString("id")));
                                //will print out all the titles of the movies
                                // that were returned from the REST call search
                                Log.d("Movie Object: ", listOfMovies.getTitleList().get(i));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                                    HomeScreenActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    listOfMovies.getTitleList());
                            movieList.setAdapter(arrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", "There was a volley error.");
                            }
                        });
                RequestController.getInstance().addToRequestQueue(jsonObjReq);
            }
        });

        // RecentDVDs button sends to displays DVDs
        Button recentDVD = (Button)findViewById(R.id.recentDVD_button);

        recentDVD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MovieList listOfMovies = new MovieList();
                String url = "";
                try {
                    url = "http://api.rottentomatoes.com/api/public/v1.0/" +
                            "lists/dvds/new_releases.json?page_limit=15&page=1&country=us&apikey="
                            + API_KEY;
                } catch (Exception e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Create new movieList
                        // MovieList listOfMovies = new MovieList();
                        try {
                            JSONArray movies = response.getJSONArray("movies");
                            // One single movie
                            JSONObject movie;
                            // Iterating through JSON objects
                            for (int i = 0; i < movies.length(); i++) {
                                movie = movies.getJSONObject(i);
                                // Create a new movie
                                Movie newMovie = new Movie(movie.getString("title"),
                                        movie.getString("year"),
                                        movie.getString("mpaa_rating"), movie.getString("id"));
                                listOfMovies.addMovie(newMovie);
                                movieIds.put(movie.getString("title"), Integer.parseInt(movie.getString("id")));

                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(HomeScreenActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    listOfMovies.getTitleList());
                            movieList.setAdapter(arrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", "We done goofed");
                            }
                        });
                RequestController.getInstance().addToRequestQueue(jsonObjReq);
            }
        });

        // Search for Movies
        movieList = (ListView) findViewById(R.id.movieList);
        searchBox = (EditText) findViewById(R.id.searchText);

        // Display the movies related to the search text in the list view
        Button searchForMovies = (Button)findViewById(R.id.searchMovies);
        searchForMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String movieSearchString = searchBox.getText().toString();
                final MovieList listOfMovies = new MovieList();
                String url = "";
                try {
                    url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q="
                            + URLEncoder.encode(movieSearchString,"UTF-8")
                            + "&page_limit="
                            + MOVIE_PAGE_LIMIT
                            + "&page=1&apikey="
                            + API_KEY;
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Create new movieList
                        // MovieList listOfMovies = new MovieList();
                        try {
                            JSONArray movies = response.getJSONArray("movies");
                            // One single movie
                            JSONObject movie;
                            // Test block to see how many movies there are
                            // that are returned in the JSON object
                            for (int i = 0; i < movies.length(); i++) {
                                movie = movies.getJSONObject(i);
                                // Create a new movie
                                Movie newMovie = new Movie(movie.getString("title"),
                                        movie.getString("year"), movie.getString("mpaa_rating"), movie.getString("id"));
                                listOfMovies.addMovie(newMovie);
                                movieIds.put(movie.getString("title"), Integer.parseInt(movie.getString("id")));
                                // Will print out all the titles of the movies
                                // that were returned from the REST call search
                                Log.d("Movie Object: ", listOfMovies.getTitleList().get(i));
                            }
                            ArrayAdapter<String> arrayAdapter =
                                    new ArrayAdapter<>(HomeScreenActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            listOfMovies.getTitleList());
                            movieList.setAdapter(arrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", "There was a Volley error.");
                            }
                        });
                RequestController.getInstance().addToRequestQueue(jsonObjReq);
            }
        });
    }
}
