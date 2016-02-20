package CS2340.github.cinemasnob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity to EditProfile
 */
public class EditProfileActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button saveChangesButton = (Button) findViewById(R.id.SaveChanges);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onSaveButtonClicked(view);
            }
        });

    }

    /**
     * Method called when 'Login' button is clicked, handles login request and does appropriate
     * response
     * @param v Current view
     */
    public void onSaveButtonClicked(View v) {
        EditText majorBox = (EditText)findViewById(R.id.majorText);
        EditText interestsBox = (EditText)findViewById(R.id.interestsText);

        String majorData = majorBox.getText().toString();
        String interestsData = interestsBox.getText().toString();

        // Get currentUser and update database info
        User currentUser = User.getCurrentUser();
        ProfileOpenHelper profiledb = new ProfileOpenHelper(context);
        profiledb.updateMajor(profiledb, majorData);
        profiledb.updateInterests(profiledb, interestsData);

        // Go back to ProfileActivity
        Intent goToProfile = new Intent(this, UserProfileActivity.class);
        startActivity(goToProfile);
        finish();
    }
}
