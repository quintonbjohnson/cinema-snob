package comquintonjcinemasnob.github.cinemasnob;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class EditUserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nameView = (TextView)findViewById(R.id.usernameText);
        Bundle retrieveCurrentUser = getIntent().getExtras();
        nameView.setText(retrieveCurrentUser.getString("USER_NAME"));
    }

}
