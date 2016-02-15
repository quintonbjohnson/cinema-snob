package comquintonjcinemasnob.github.cinemasnob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// TODO     Edit profile for current user
/*
 * Class for the main activity
 */
public class MainActivity extends AppCompatActivity {
    public String currentUsername;
    UserOpenHelper userdb;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        Button submitLogin = (Button) findViewById(R.id.submit_login);
        submitLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onLoginButtonClicked(view);
            }
        });
        Button registerNewUser = (Button) findViewById(R.id.register);
        registerNewUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onNewUserButtonClicked(view);
            }
        });
    }

    /**
     * Method called when 'Login' button is clicked, handles login request and does appropriate
     * response
     * @param v Current view
     */
    public void onLoginButtonClicked(View v) {
        EditText usernameBox = (EditText)findViewById(R.id.login_username_entry);
        EditText passwordBox = (EditText)findViewById(R.id.login_password_entry);

        userdb = new UserOpenHelper(context);
        User currentUser = userdb.getUser(userdb, usernameBox.getText().toString());
        if (passwordBox.getText().toString().equals(currentUser.getPassword())) {
                Intent goToHomeScreen = new Intent(this, HomeScreen.class);
                startActivity(goToHomeScreen);
                finish();
        } else {
            CharSequence failedLogin = "Incorrect Username or Password, try again.";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast fail = Toast.makeText(context, failedLogin, duration);
            fail.show();
        }
    }

    /**
     * Called when 'Register' button is pressed, send user to CreateProfile activity
     * @param v Current view
     */
    public void onNewUserButtonClicked(View v) {
        Intent goToCreateProfile = new Intent(this, CreateProfile.class);
        startActivity(goToCreateProfile);
        finish();
    }

}