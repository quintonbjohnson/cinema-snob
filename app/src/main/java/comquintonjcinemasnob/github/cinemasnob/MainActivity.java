package comquintonjcinemasnob.github.cinemasnob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManagement manager = new UserManager();
        //hard coded for M3
        manager.addUser("User", "pass");

        Button submitLogin = (Button) findViewById(R.id.submit_login);
        submitLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onLoginButtonClicked(view);
            }
        });
    }

    public void onLoginButtonClicked(View v) {
        EditText usernameBox = (EditText)findViewById(R.id.login_username_entry);
        EditText passwordBox = (EditText)findViewById(R.id.login_password_entry);
        AuthenticationManagement af = new UserManager();
        CharSequence failedLogin;
        if (af.handleLoginRequests(usernameBox.getText().toString(), passwordBox.getText().toString())) {
            Intent goToHomeScreen = new Intent(this, HomeScreen.class);
            startActivity(goToHomeScreen);
        } else {
            failedLogin = "Incorrect Username or Password, try again.";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast fail = Toast.makeText(context, failedLogin, duration);
            fail.show();
        }
    }

}