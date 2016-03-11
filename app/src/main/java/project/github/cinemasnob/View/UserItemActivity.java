package project.github.cinemasnob.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import project.github.cinemasnob.Controller.UserOpenHelper;
import project.github.cinemasnob.Model.User;
import project.github.cinemasnob.R;


/**
 * User info page for Admins
 */
public class UserItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item);

        TextView usernameText = (TextView)findViewById(R.id.username_text);
        TextView emailText = (TextView)findViewById(R.id.email_text);
        TextView majorText = (TextView)findViewById(R.id.major_text);
        final TextView banStatus = (TextView)findViewById(R.id.ban_status);

        Intent i = getIntent();

        // Get extras and find User in database
        final UserOpenHelper userdb = new UserOpenHelper(this);
        final String username = i.getStringExtra("User");
        final User thisItemUser = userdb.getUser(userdb, username);

        // Set TextViews with appropriate User info
        usernameText.setText(thisItemUser.getUserName());
        emailText.setText(thisItemUser.getEmail());
        majorText.setText(thisItemUser.getMajor());
        String stringBan = "Ban Status: " + Boolean.toString(thisItemUser.getBanStatus());
        banStatus.setText(stringBan);

        Button banButton = (Button)findViewById(R.id.ban_button);

        banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userdb.setBanStatusOfUser(userdb, username, true);
                String stringBan = "Ban Status: " + Boolean.toString(thisItemUser.getBanStatus());
                banStatus.setText(stringBan);
                CharSequence banSuccessful = "User has been banned.";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast fail = Toast.makeText(context, banSuccessful, duration);
                fail.show();

            }
        });

        Button unlockButton = (Button)findViewById(R.id.unlock_button);

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userdb.setBanStatusOfUser(userdb, username, false);
                String stringBan = "Ban Status: " + Boolean.toString(thisItemUser.getBanStatus());
                banStatus.setText(stringBan);
                CharSequence unlockSuccessful = "User has been unlocked.";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast fail = Toast.makeText(context, unlockSuccessful, duration);
                fail.show();
            }
        });
    }
}
