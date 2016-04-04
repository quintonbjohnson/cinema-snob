package project.github.cinemasnob.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
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
import project.github.cinemasnob.controller.RatingOpenHelper;
import project.github.cinemasnob.controller.RequestController;
import project.github.cinemasnob.model.Rating;
import project.github.cinemasnob.model.User;

public class MovieItemActivity extends AppCompatActivity {

    /**
     * The title of the Movie.
     */
    private String title = "";

    /**
     * The actors of the Movie.
     */
    private String actors = "";

    /**
     * The genre of the Movie.
     */
    private String genre = "";

    /**
     * The Context of the current Activity.
     */
    private Context context;

    /**
     * The rating database.
     */
    private RatingOpenHelper ratingDB;

    /**
     * The current User.
     */
    private User currentUser;

    /**
     * The movieID of the Movie.
     */
    private int movieID;

    /**
     * The API_KEY for the Rotten Tomatoes API
     */
    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);
        context = this;
        currentUser = User.getCurrentUser();

        final TextView titleText = (TextView) findViewById(R.id.title_text);
        final TextView actorText = (TextView) findViewById(R.id.actorsText);
        final TextView genreText = (TextView) findViewById(R.id.genreText);

        Intent i = getIntent();

        // Getting attached intent data from first element
        movieID = i.getIntExtra("ID", 0);
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
                    //getting movie data
                    title = response.getString("title");
                    titleText.setText(title);
                    //Getting actors
                    JSONArray actorsArray =
                            response.getJSONArray("abridged_cast");
                    for (int i = 0; i < actorsArray.length(); i++) {
                        actors = actors
                                + actorsArray.getJSONObject(i)
                                .getString("name");
                        if (i != actorsArray.length() - 1) {
                            actors = actors + ", ";
                        }
                    }
                    actorText.setText(actors);

                    JSONArray genres = response.getJSONArray("genres");
                    for (int i = 0; i < genres.length(); i++) {
                        genre = genre + genres.getString(i);
                        if (i != genres.length() - 1) {
                            genre = genre + ", ";
                        }
                    }
                    genreText.setText(genre);

                    ratingDB = new RatingOpenHelper(context);
                    RatingBar movieRating =
                            (RatingBar) findViewById(R.id.ratingBar2);
                    Rating currentRating =
                            ratingDB.getRating(ratingDB,
                                    currentUser.getUserName(),
                            title, Integer.toString(movieID));
                    if (currentRating != null) {
                        movieRating.setRating(currentRating.getRating());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
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


        RatingBar movieRating = (RatingBar) findViewById(R.id.ratingBar2);
        movieRating.
                setOnRatingBarChangeListener(
                        new RatingBar.OnRatingBarChangeListener() {

            // Called when the user swipes the RatingBar
            @Override
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser) {
                onRatingBarChanged(rating);
            }
        });
    }

    /**
     * Runs when the rating bar is changed.
     * @param rating the rating of the bar
     */
    private void onRatingBarChanged(float rating) {
        Rating currentRating = ratingDB.getRating(ratingDB,
                currentUser.getUserName(), title,
                Integer.toString(movieID));
        if ((currentRating) == null) {
            ratingDB.putRating(ratingDB,
                    currentUser.getUserName(),
                    title, rating, movieID);
        } else {
            ratingDB.updateRating(ratingDB, Float.toString(rating), title,
                    currentUser.getUserName(), Integer.toString(movieID));
        }
    }
}
