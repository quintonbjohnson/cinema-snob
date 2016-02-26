package project.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import project.github.cinemasnob.R;

public class MovieItemActivity extends AppCompatActivity {

    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);

        TextView txtProduct = (TextView) findViewById(R.id.title_text);

        Intent i = getIntent();
        // getting attached intent data
        String title = i.getStringExtra("title");
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
                try {
                    JSONArray movies = response.getJSONArray("movies");
                    //one single movie
                    JSONObject movie = null;
                    //Test block to see how many movies
                    // there are that are returned in the JSON object
                    for (int i = 0; i < movies.length(); i++) {
                        movie = movies.getJSONObject(i);
                        //create a new movie
                        Movie newMovie = new Movie(movie.getString("title"),
                                movie.getString("year"),
                                movie.getString("mpaa_rating"));
                        //will print out all the titles of the movies
                        // that were returned from the REST call search
                    }
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
        // displaying selected product name
        txtProduct.setText(title);
    }

}
