package project.github.cinemasnob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
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

public class MovieItemActivity extends AppCompatActivity {

    public String title = "";
    public String actors = "";
    public String genre = "";
    public String synopsis = "";
    private Context context;


    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);
        context = this;

        final TextView titleText = (TextView) findViewById(R.id.title_text);
        final TextView actorText = (TextView) findViewById(R.id.actorsText);
        final TextView genreText = (TextView) findViewById(R.id.genreText);
        final TextView ratingText = (TextView) findViewById(R.id.rtRatingText);
        final TextView synopsisText = (TextView) findViewById(R.id.synopsisText);
        final ImageView profileView = (ImageView) findViewById(R.id.profilePicture);

        Intent i = getIntent();

        // Getting attached intent data from first element
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
                        Log.d("Error", "JSON error");
                    }
                });
        RequestController.getInstance().addToRequestQueue(jsonObjReq);

        // Displaying all the movie data from JSON object

        final RatingOpenHelper ratingdb = new RatingOpenHelper(context);
        RatingBar movieRating = (RatingBar)findViewById(R.id.ratingBar2);
        Rating currentRating = ratingdb.getRating(ratingdb, User.getCurrentUser().getUserName(), title);
        if (!(currentRating == null)) {
            movieRating.setRating(currentRating.getRating());
        }
        movieRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            // Called when the user swipes the RatingBar
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                User currentUser = User.getCurrentUser();
                Rating currentRating = ratingdb.getRating(ratingdb, currentUser.getUserName(), title);
                if ((currentRating) == null) {
                    ratingdb.putRating(ratingdb, currentUser.getUserName(), title, rating);
                } else {
                    ratingdb.updateRating(ratingdb, Float.toString(rating));
                }
            }
        });
    }
}
