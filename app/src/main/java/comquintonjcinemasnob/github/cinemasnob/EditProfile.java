package comquintonjcinemasnob.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        EditText majorBox = (EditText)findViewById(R.id.majorText);
        EditText interestsBox = (EditText)findViewById(R.id.interestsText);

        String majorData = majorBox.getText().toString();
        String interestsData = interestsBox.getText().toString();
        String[] changes = {majorData, interestsData};

        Bundle textChangesBundle = new Bundle();
        // Sending updated profile info
        textChangesBundle.putStringArray("PROFILE_CHANGES", changes);

        Intent goToProfile = new Intent(this, UserProfile.class);
        goToProfile.putExtras(textChangesBundle);
        startActivity(goToProfile);
        finish();
    }
}
