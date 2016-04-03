package project.github.cinemasnob.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;

import project.github.cinemasnob.R;
import project.github.cinemasnob.controller.RatingOpenHelper;
import project.github.cinemasnob.controller.RequestController;
import project.github.cinemasnob.model.Rating;
import project.github.cinemasnob.model.User;

public class MovieItemActivity extends AppCompatActivity {

    private String title = "";
    private String actors = "";
    private String genre = "";
    private Context context;
    private RatingOpenHelper ratingdb;
    private User currentUser;
    private int movieID;


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
        //final TextView ratingText = (TextView) findViewById(R.id.rtRatingText);
        //final TextView mpaaRatingText = (TextView) findViewById(R.id.mpaaRating);
        //final TextView synopsisText = (TextView) findViewById(R.id.synopsisText);
        //final ImageView profileView = (ImageView) findViewById(R.id.profilePicture);

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
                    JSONArray actorsArray = response.getJSONArray("abridged_cast");
                    //actors = actorsArray.getJSONObject(0).getString("name") + ", ";
                    for (int i = 0; i < actorsArray.length(); i++) {
                        actors = actors + actorsArray.getJSONObject(i).getString("name");
                        if (i != actorsArray.length() - 1)
                            actors = actors + ", ";
                    }
                    actorText.setText(actors);

                    JSONArray genres = response.getJSONArray("genres");
                    for (int i = 0; i < genres.length(); i++) {
                        genre = genre + genres.getString(i);
                        if (i != genres.length() - 1)
                            genre = genre + ", ";
                    }
                    genreText.setText(genre);

                    //criticScore = movie.getString("critics_score");
//                    synopsis = response.getString("synopsis");
                    //titleText.setText(synopsis);
                    //rating = movie.getString("rating");
                    //titleText.setText(rating);
                    //mpaaRatingText.setText(response.getString("mpaa_rating"));

                    //Bitmap myBitmap = BitmapFactory.decodeFile(movie.getJSONObject("posters").getString("profile"));
                    //profileView.setImageBitmap(myBitmap);
                    //profileView.setImageIcon();
                    ratingdb = new RatingOpenHelper(context);
                    RatingBar movieRating = (RatingBar) findViewById(R.id.ratingBar2);
                    Rating currentRating = ratingdb.getRating(ratingdb, currentUser.getUserName(),
                            title, Integer.toString(movieID));
                    if (currentRating != null) {
                        movieRating.setRating(currentRating.getRating());
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
                        Log.d("Error", "JSON error");
                    }
                });
        RequestController.getInstance().addToRequestQueue(jsonObjReq);

        // Displaying all the movie data from JSON object


        RatingBar movieRating = (RatingBar) findViewById(R.id.ratingBar2);
        movieRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            // Called when the user swipes the RatingBar
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                onRatingBarChanged(rating);
            }
        });
    }

    public void onRatingBarChanged(float rating) {
        Rating currentRating = ratingdb.getRating(ratingdb,
                currentUser.getUserName(), title,
                Integer.toString(movieID));
        if ((currentRating) == null) {
            ratingdb.putRating(ratingdb,
                    currentUser.getUserName(),
                    title, rating, movieID);
        } else {
            ratingdb.updateRating(ratingdb, Float.toString(rating), title,
                    currentUser.getUserName(), Integer.toString(movieID));
        }
    }
}
