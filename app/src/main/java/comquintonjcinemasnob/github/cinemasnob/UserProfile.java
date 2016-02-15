package comquintonjcinemasnob.github.cinemasnob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/*
 * User profile
 */
public class UserProfile extends AppCompatActivity {

    /*public UserProfile(String name) {
        username = name;
    }*/

    /*
     * On creation of the UserProfile
     * @param savedInstanceState the saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        TextView nameView = (TextView)findViewById(R.id.usernameText);
        Bundle retrieveCurrentUser = getIntent().getExtras();
        nameView.setText(retrieveCurrentUser.getString("USER_NAME"));

        TextView major = (TextView)findViewById(R.id.MajorText);
        TextView interests = (TextView)findViewById(R.id.InterestsText);
    }
}
