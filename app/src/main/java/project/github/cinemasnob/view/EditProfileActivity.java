package project.github.cinemasnob.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import project.github.cinemasnob.R;
import project.github.cinemasnob.controller.ProfileOpenHelper;

/**
 * Activity to EditProfile
 */
public class EditProfileActivity extends AppCompatActivity {

    private Context context;
    private EditText interestsBox;
    private Spinner sItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        String[] spinnerArray = {"Computer Science",
                "Industrial Design", "Engineering", "Business"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.edit_major_spinner);
        sItems.setAdapter(adapter);

        interestsBox = (EditText)findViewById(R.id.interestsText);

        Button saveChangesButton = (Button) findViewById(R.id.SaveChanges);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    /**
     * Method called when 'Login' button is clicked,
     * handles login request and does appropriate
     * response
     */
    private void onSaveButtonClicked() {


        String majorData = sItems.getSelectedItem().toString();
        String interestsData = interestsBox.getText().toString();

        // Get currentUser and update database info
        ProfileOpenHelper profileDB = new ProfileOpenHelper(context);
        profileDB.updateMajor(profileDB, majorData);
        profileDB.updateInterests(profileDB, interestsData);

        // Go back to ProfileActivity
        Intent goToProfile = new Intent(this, UserProfileActivity.class);
        startActivity(goToProfile);
        finish();
    }
}
