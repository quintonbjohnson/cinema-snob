package project.github.cinemasnob.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import project.github.cinemasnob.R;
import project.github.cinemasnob.controller.UserOpenHelper;


/**
 * Admin home screen for seeing a list of Users.
 */
public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final UserOpenHelper userDB = new UserOpenHelper(this);

        List<String> listOfNames = userDB.getUserList(userDB);

        ListView userList = (ListView) findViewById(R.id.user_list);

        // Populate the ListView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                AdminActivity.this,
                android.R.layout.simple_list_item_1,
                listOfNames);
        userList.setAdapter(arrayAdapter);

        // When User taps on a movie item, send him or her
        // to the MovieItemActivity
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Selected item
                String username = ((TextView) view).getText().toString();
                // Launching new Activity on selecting single List Item
                Intent goToUserItem = new Intent(getApplicationContext(),
                        UserItemActivity.class);
                // Sending data to new activity
                goToUserItem.putExtra("User", username);
                startActivity(goToUserItem);
            }
        });

        Button logout = (Button) findViewById(R.id.admin_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(AdminActivity.this,
                        LoginScreenActivity.class);
                startActivity(logout);
                finish();
            }
        });
    }
}
