package project.github.cinemasnob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Activity for recent DVDs
 */
public class RecentDVDActivity extends AppCompatActivity {

    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private ListView movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_dvd);

        movieList = (ListView)findViewById(R.id.movieList);
        final String movieSearchString = "Recent Movies";
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
                    JSONObject movie = null;
                    // Iterating through JSON objects
                    for (int i = 0; i < movies.length(); i++) {
                        movie = movies.getJSONObject(i);
                        // Create a new movie
                        Movie newMovie = new Movie(movie.getString("title"),
                                movie.getString("year"),
                                movie.getString("mpaa_rating"));
                        listOfMovies.addMovie(newMovie);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RecentDVDActivity.this,
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
                        Log.d("Ohfuck.jpg", "We done goofed");
                    }
                });
        RequestController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
