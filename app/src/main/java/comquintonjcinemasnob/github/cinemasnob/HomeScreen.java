package comquintonjcinemasnob.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
 * Home Screen
 */
public class HomeScreen extends AppCompatActivity {

    /**
     * On creation of HomeScreen activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Logout button sends back to login screen
        Button logout = (Button)findViewById(R.id.logout_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, MainActivity.class));
                finish();
            }
        });

        // Profile button sends to profile
        Button profile = (Button)findViewById(R.id.profile_button);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, UserProfile.class));
            }
        });


    }


}
