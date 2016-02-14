package comquintonjcinemasnob.github.cinemasnob;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    /*public UserProfile(String name) {
        username = name;
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView nameView = (TextView)findViewById(R.id.usernameText);
        //nameView.setText(username);

        TextView moviesWatched = (TextView)findViewById(R.id.MoviesWatchedText);
        TextView moviesFavorited = (TextView)findViewById(R.id.FavoriteMoviesText);

    }



}
