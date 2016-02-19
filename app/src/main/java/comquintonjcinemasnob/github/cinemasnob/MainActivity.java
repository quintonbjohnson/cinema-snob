package comquintonjcinemasnob.github.cinemasnob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Class for the main activity
 */
public class MainActivity extends AppCompatActivity {

    UserOpenHelper userdb;
    Context context;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        userdb = new UserOpenHelper(context);
        currentUser = userdb.getUser(userdb, usernameBox.getText().toString());
        // Check if User exists
        if (currentUser != null) {
            if (passwordBox.getText().toString().equals(currentUser.getPassword())) {
                Intent goToHomeScreen = new Intent(this, HomeScreen.class);
                Bundle currentUserBundle = new Bundle();
                currentUserBundle.putString("USER_NAME", currentUser.getUserName());
                goToHomeScreen.putExtras(currentUserBundle);
                startActivity(goToHomeScreen);
                finish();
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
     * Called when 'Register' button is pressed, send user to CreateProfile activity
     * @param v Current view
     */
    public void onNewUserButtonClicked(View v) {
        Intent goToCreateProfile = new Intent(this, CreateProfile.class);
        startActivity(goToCreateProfile);
        finish();
    }

    /**
     * Gets the current logged in User
     * @return User
     */
    public User getCurrentUser() {
        return currentUser;
    }
}