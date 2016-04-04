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
import android.widget.Toast;

import project.github.cinemasnob.R;
import project.github.cinemasnob.controller.ProfileOpenHelper;
import project.github.cinemasnob.controller.UserOpenHelper;
import project.github.cinemasnob.model.User;

/*
 * RegistrationActivity screen
 */
public class RegistrationActivity extends AppCompatActivity {

    /**
     * the context of the current activity
     */
    private Context context;
    /**
     * the spinner to choose your major
     */
    private Spinner sItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = this;
        Button submitRegister = (Button) findViewById(R.id.submit_register);
        submitRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onRegisterButtonClicked();
            }
        });

        Button cancelRegister = (Button) findViewById(R.id.cancel_register);
        cancelRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onCancelRegisterClicked();
            }
        });

        String[] spinnerArray = {"Computer Science", "Industrial Design", "Engineering", "Business"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.major_spinner);
        sItems.setAdapter(adapter);
    }

    /**
     * Takes place when 'Cancel' button is clicked, sends user to login screen
     */
    private void onCancelRegisterClicked() {
        Intent goToMainActivity = new Intent(this, LoginScreenActivity.class);
        startActivity(goToMainActivity);
        finish();
    }

    /**
     * Takes place when 'Register' button is clicked, add User to database
     * if username and email are not taken
     */
    private void onRegisterButtonClicked() {
        EditText usernameBox = (EditText)findViewById(R.id.register_username);
        EditText passwordBox = (EditText)findViewById(R.id.register_password);
        EditText emailBox = (EditText)findViewById(R.id.register_email);



        UserOpenHelper userDB = new UserOpenHelper(context);
        User checkUser = userDB.getUser(userDB,
                usernameBox.getText().toString());

        CharSequence failedLogin;
        String blank = "";
        if (usernameBox.getText().toString().trim().equals(blank) ||
                passwordBox.getText().toString().trim().equals(blank) ||
                emailBox.getText().toString().trim().equals(blank) ||
                !(emailBox.getText().toString().contains("@"))) {
            // Invalid entries
            failedLogin = "Please enter a valid username, " +
                    "password, email, and major.";
            context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast fail = Toast.makeText(context, failedLogin, duration);
            fail.show();
        } else if (!(checkUser == null)) {
            // Username exists already
            failedLogin = "That username already exists.";
            context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast fail = Toast.makeText(context, failedLogin, duration);
            fail.show();
        } else {
            // RegistrationActivity works
            userDB = new UserOpenHelper(context);
            userDB.putUser(userDB,
                    usernameBox.getText().toString(),
                    passwordBox.getText().toString(),
                    emailBox.getText().toString(),
                    sItems.getSelectedItem().toString(), false);
            ProfileOpenHelper profileDB = new ProfileOpenHelper(context);
            profileDB.putProfile(profileDB,
                    usernameBox.getText().toString(),
                    "", "");
            Toast.makeText(getBaseContext(),
                    "Successfully registered!", Toast.LENGTH_LONG).show();
            Intent goToMainActivity = new Intent(this,
                    LoginScreenActivity.class);
            startActivity(goToMainActivity);
            finish();
            finish();
        }
    }
}
