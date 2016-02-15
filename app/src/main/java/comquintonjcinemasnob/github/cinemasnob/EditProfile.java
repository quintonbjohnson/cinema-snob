package comquintonjcinemasnob.github.cinemasnob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    /*
     * On Creation of the activity
     * @param savedInstanceState the saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Bundle retrieveCurrentUser = getIntent().getExtras();
        String[] changes = {majorData, interestsData, retrieveCurrentUser.getString("USER_NAME")};

        Bundle textChangesBundle = new Bundle();
        // Sending updated profile info
        textChangesBundle.putStringArray("PROFILE_CHANGES", changes);
        Intent goToProfile = new Intent(this, UserProfile.class);
        goToProfile.putExtras(textChangesBundle);
        startActivity(goToProfile);
        finish();
    }
}
