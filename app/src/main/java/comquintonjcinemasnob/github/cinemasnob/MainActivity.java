package comquintonjcinemasnob.github.cinemasnob;

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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //Button loginButton = (Button)findViewById(R.id.submit_login);;
    Map<String, String> users = new HashMap<String, String>();
    /*String name = "User";
    String pass = "pass";
    users.put(name, pass);*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


     //  = (EditText)findViewById(R.id.edittext);

    public void login() {
        String username = ((EditText)findViewById(R.id.login_username_entry)).getText().toString();
        String password = ((EditText)findViewById(R.id.login_password_entry)).getText().toString();
        //Intent intent = new Intent(this, LayoutTwoActivity.class);
        //startActivity(intent);
    }

}