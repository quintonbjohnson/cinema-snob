package comquintonjcinemasnob.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;

/*
 * User profile
 */
public class UserProfileActivity extends AppCompatActivity {

    Context context;
    ProfileOpenHelper profiledb;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Button editProfileButton = (Button) findViewById(R.id.EditProfile);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent goToEditProfile = new Intent(UserProfileActivity.this,
                        EditProfileActivity.class);
                startActivity(goToEditProfile);
                finish();
            }
        });

        TextView nameView = (TextView)findViewById(R.id.usernameText);
        TextView major = (TextView)findViewById(R.id.majorText);
        TextView interests = (TextView)findViewById(R.id.interestsText);

        // Get currentUser and profile
        currentUser = User.getCurrentUser();
        if (currentUser != null) {
            ProfileOpenHelper profiledb = new ProfileOpenHelper(context);
            Profile currentProfile = profiledb.getProfile(profiledb, currentUser.getUserName());

            // Set text fields
            System.out.println("Major:" + currentProfile.getMajor());
            System.out.println(currentProfile.getInterests());
            nameView.setText(currentUser.getUserName());
            major.setText(currentProfile.getMajor());
            interests.setText(currentProfile.getInterests());
        }
    }
}
