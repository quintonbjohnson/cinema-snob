package project.github.cinemasnob.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import project.github.cinemasnob.R;
import project.github.cinemasnob.controller.UserOpenHelper;
import project.github.cinemasnob.model.User;

/*
 * Class for the main activity
 */
public class LoginScreenActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        context = this;

        // Login
        Button submitLogin = (Button) findViewById(R.id.submit_login);
        submitLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onLoginButtonClicked(view);
            }
        });

        // Register
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

        UserOpenHelper userdb = new UserOpenHelper(context);
        User currentUser = userdb.getUser(userdb, usernameBox.getText().toString());
        // Check if User exists
        if (currentUser != null) {
            if (passwordBox.getText().toString().equals(currentUser.getPassword())) {
                if (currentUser.getUserName().equals("ADMIN")
                        && currentUser.getPassword().equals("2340")) {
                    // Admin case; go to AdminScreen
                    Intent goToAdminScreen = new Intent(this, AdminActivity.class);
                    User.setCurrentUser(currentUser);
                    startActivity(goToAdminScreen);
                    finish();
                } else if (currentUser.getBanStatus()) {
                    CharSequence failedLogin = "You have been banned. " +
                            "Please contact Cinema Snob administration.";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast fail = Toast.makeText(context, failedLogin, duration);
                    fail.show();
                } else {
                    Intent goToHomeScreen = new Intent(this, HomeScreenActivity.class);
                    User.setCurrentUser(currentUser);
                    startActivity(goToHomeScreen);
                    finish();
                }
            } else {
                CharSequence failedLogin = "Incorrect Username or Password, try again.";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast fail = Toast.makeText(context, failedLogin, duration);
                fail.show();
            }
        } else {
            CharSequence failedLogin = "User doesn't exist.";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast fail = Toast.makeText(context, failedLogin, duration);
            fail.show();
        }
    }

    /**
     * Called when 'Register' button is pressed, send user to RegistrationActivity
     * @param v Current view
     */
    public void onNewUserButtonClicked(View v) {
        Intent goToCreateProfile = new Intent(this, RegistrationActivity.class);
        startActivity(goToCreateProfile);
        finish();
    }
}