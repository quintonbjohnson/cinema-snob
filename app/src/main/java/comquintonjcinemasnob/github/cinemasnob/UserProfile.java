package comquintonjcinemasnob.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

        Button editProfile = (Button)findViewById(R.id.EditProfile);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle retrieveCurrentUser = getIntent().getExtras();
                Intent goToEditProfile = new Intent(UserProfile.this, EditProfile.class);
                goToEditProfile.putExtras(retrieveCurrentUser);
                startActivity(goToEditProfile);
            }
        });


        TextView major = (TextView)findViewById(R.id.MajorText);
        TextView interests = (TextView)findViewById(R.id.InterestsText);
        TextView nameView = (TextView)findViewById(R.id.usernameText);

        if (getIntent().getExtras().containsKey("USER_NAME")) {
            Bundle retrieveCurrentUser = getIntent().getExtras();
            nameView.setText(retrieveCurrentUser.getString("USER_NAME"));
        } else if (getIntent().getExtras().containsKey("PROFILE_CHANGES")) {
            Bundle retrieveProfileChanges = getIntent().getExtras();
            major.setText(retrieveProfileChanges.getStringArray("PROFILE_CHANGES")[0]);
            interests.setText(retrieveProfileChanges.getStringArray("PROFILE_CHANGES")[1]);
        }






    }
}
