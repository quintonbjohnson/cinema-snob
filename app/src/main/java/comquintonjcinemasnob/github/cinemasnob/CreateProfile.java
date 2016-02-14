package comquintonjcinemasnob.github.cinemasnob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateProfile extends AppCompatActivity {
    UserManagement manager = new UserManager();
    UserOpenHelper userdb;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
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
     * @param view
     */
    public void onCancelRegisterClicked(View view) {
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        startActivity(goToMainActivity);
        finish();
    }

    /**
     * Takes place when 'Register' button is clicked, adds User to database if username and email are not taken
     * @param view
     */

    public void onRegisterButtonClicked(View view) {
        EditText usernameBox = (EditText)findViewById(R.id.register_username);
        EditText passwordBox = (EditText)findViewById(R.id.register_password);
        EditText emailBox = (EditText)findViewById(R.id.register_email);


        CharSequence failedLogin;
        if (usernameBox.getText().toString().trim().equals("") || passwordBox.getText().toString().trim().equals("") ||
                emailBox.getText().toString().trim().equals("")) {
            failedLogin = "Please enter a valid username, password, and email.";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast fail = Toast.makeText(context, failedLogin, duration);
            fail.show();
        } else {
            userdb = new UserOpenHelper(context);
            userdb.putUser(userdb, usernameBox.getText().toString(), passwordBox.getText().toString(), emailBox.getText().toString());
            Toast.makeText(getBaseContext(), "Successfully registered!", Toast.LENGTH_LONG).show();
//            manager.addUser(usernameBox.getText().toString(), passwordBox.getText().toString(),
//                    emailBox.getText().toString());
            Intent goToMainActivity = new Intent(this, MainActivity.class);
            startActivity(goToMainActivity);
            finish();
            finish();
        }
    }

    public Context getContext(){
        return context;
    }

    public UserOpenHelper getDB() {
        return userdb;
    }
}
