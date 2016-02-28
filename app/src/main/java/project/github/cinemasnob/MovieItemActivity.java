package project.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.github.cinemasnob.R;

public class MovieItemActivity extends AppCompatActivity {

    public String title = "";
    public String actors = "";
    public String genre = "";
    public String rating = "";
    public String synopsis = "";
    public String criticScore = "";



    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);

        final TextView titleText = (TextView) findViewById(R.id.title_text);
        final TextView actorText = (TextView) findViewById(R.id.actorsText);
        final TextView genreText = (TextView) findViewById(R.id.genreText);
        final TextView ratingText = (TextView) findViewById(R.id.rtRatingText);
        final TextView synopsisText = (TextView) findViewById(R.id.synopsisText);
        final ImageView profileView = (ImageView) findViewById(R.id.profilePicture);

        Intent i = getIntent();
        // getting attached intent data from first element
        int movieID = i.getIntExtra("ID", 0);
        String url = "";
        try {
            url = "http://api.rottentomatoes.com/api/public/v1.0/movies/"
                    + movieID
                    + ".json?apikey="
                    + API_KEY;
        } catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //one single movie
                    JSONObject movie = response;
                    //getting movie data
                    title = movie.getString("title");
                    titleText.setText(title);
                    //Getting actors
                    JSONArray actorsArray = movie.getJSONArray("abridged_cast");
                    //actors = actorsArray.getJSONObject(0).getString("name") + ", ";
                    for (int i = 0; i < actorsArray.length(); i++) {
                        actors = actors + actorsArray.getJSONObject(i).getString("name");
                        if (i != actorsArray.length() - 1)
                            actors = actors + ", ";
                    }
                    actorText.setText(actors);

                    JSONArray genres = movie.getJSONArray("genres");
                    for (int i = 0; i < genres.length(); i++) {
                        genre = genre + genres.getString(i);
                        if (i != genres.length() - 1)
                            genre = genre + ", ";
                    }
                    genreText.setText(genre);

                    //criticScore = movie.getString("critics_score");
                    synopsis = movie.getString("synopsis");
                    //titleText.setText(synopsis);
                    //rating = movie.getString("rating");
                    //titleText.setText(rating);

                    //profileView.setImageIcon();
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

        // displaying all the movie data from JSON object





    }

}
