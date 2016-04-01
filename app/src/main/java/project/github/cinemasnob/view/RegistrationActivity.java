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
import project.github.cinemasnob.controller.ProfileOpenHelper;
import project.github.cinemasnob.controller.UserOpenHelper;
import project.github.cinemasnob.model.User;

/*
 * RegistrationActivity screen
 */
public class RegistrationActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = this;
        Button submitRegister = (Button) findViewById(R.id.submit_register);
        submitRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onRegisterButtonClicked(view);
            }
        });

        Button cancelRegister = (Button) findViewById(R.id.cancel_register);
        cancelRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onCancelRegisterClicked(view);
            }
        });
    }

    /**
     * Takes place when 'Cancel' button is clicked, sends user to login screen
     * @param view The current view
     */
    public void onCancelRegisterClicked(View view) {
        Intent goToMainActivity = new Intent(this, LoginScreenActivity.class);
        startActivity(goToMainActivity);
        finish();
    }

    /**
     * Takes place when 'Register' button is clicked, add User to database
     * if username and email are not taken
     * @param view The current view
     */
    public void onRegisterButtonClicked(View view) {
        EditText usernameBox = (EditText)findViewById(R.id.register_username);
        EditText passwordBox = (EditText)findViewById(R.id.register_password);
        EditText emailBox = (EditText)findViewById(R.id.register_email);
        EditText majorBox = (EditText)findViewById(R.id.register_major);

        UserOpenHelper userdb = new UserOpenHelper(context);
        User checkUser = userdb.getUser(userdb, usernameBox.getText().toString());

        CharSequence failedLogin;
        if (usernameBox.getText().toString().trim().equals("") ||
                passwordBox.getText().toString().trim().equals("") ||
                emailBox.getText().toString().trim().equals("") ||
                !(emailBox.getText().toString().contains("@")) ||
                majorBox.getText().toString().equals("")) {
            // Invalid entries
            failedLogin = "Please enter a valid username, password, email, and major.";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast fail = Toast.makeText(context, failedLogin, duration);
            fail.show();
        } else if (!(checkUser == null)) {
            // Username exists already
            failedLogin = "That username already exists.";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast fail = Toast.makeText(context, failedLogin, duration);
            fail.show();
        } else {
            // RegistrationActivity works
            userdb = new UserOpenHelper(context);
            userdb.putUser(userdb,
                    usernameBox.getText().toString(),
                    passwordBox.getText().toString(),
                    emailBox.getText().toString(),
                    majorBox.getText().toString(), false);
            ProfileOpenHelper profiledb = new ProfileOpenHelper(context);
            profiledb.putProfile(profiledb, usernameBox.getText().toString(), "", "");
            Toast.makeText(getBaseContext(), "Successfully registered!", Toast.LENGTH_LONG).show();
            Intent goToMainActivity = new Intent(this, LoginScreenActivity.class);
            startActivity(goToMainActivity);
            finish();
            finish();
        }
    }
}