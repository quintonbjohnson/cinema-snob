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

    /*
     * On creation of the UserProfile
     * @param savedInstanceState the saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Button editProfileButton = (Button) findViewById(R.id.EditProfile);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle retrieveCurrentUser = getIntent().getExtras();
                Intent goToEditProfile = new Intent(UserProfile.this, EditProfile.class);
                goToEditProfile.putExtras(retrieveCurrentUser);
                startActivity(goToEditProfile);
                finish();
            }
        });

        TextView major = (TextView)findViewById(R.id.majorText);
        TextView interests = (TextView)findViewById(R.id.interestsText);
        TextView nameView = (TextView)findViewById(R.id.usernameText);

        if (getIntent().getExtras().containsKey("USER_NAME")) {
            Bundle retrieveCurrentUser = getIntent().getExtras();
            nameView.setText(retrieveCurrentUser.getString("USER_NAME"));
        } else if (getIntent().getExtras().containsKey("PROFILE_CHANGES")) {
            Bundle retrieveProfileChanges = getIntent().getExtras();
            String[] arrayChanges = retrieveProfileChanges.getStringArray("PROFILE_CHANGES");
            if (arrayChanges != null) {
                major.setText(arrayChanges[0]);
                interests.setText(arrayChanges[1]);
                nameView.setText(arrayChanges[2]);
            }
        }
    }
}
