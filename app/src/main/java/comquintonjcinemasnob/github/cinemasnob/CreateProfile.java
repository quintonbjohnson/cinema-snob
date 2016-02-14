package comquintonjcinemasnob.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateProfile extends AppCompatActivity {
    UserManagement manager = new UserManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

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

    public void onCancelRegisterClicked(View view) {
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        startActivity(goToMainActivity);
    }

    public void onRegisterButtonClicked(View view) {
        EditText usernameBox = (EditText)findViewById(R.id.register_username);
        EditText passwordBox = (EditText)findViewById(R.id.register_password);
        EditText emailBox = (EditText)findViewById(R.id.register_email);

        manager.addUser(usernameBox.toString(), passwordBox.toString(), emailBox.toString());
        //User newUser = new User(usernameBox.toString(), passwordBox.toString(), emailBox.toString());


    }

}
