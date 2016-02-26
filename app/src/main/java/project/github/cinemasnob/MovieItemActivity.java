package project.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import project.github.cinemasnob.R;

public class MovieItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);

        TextView txtProduct = (TextView) findViewById(R.id.title_text);

        Intent i = getIntent();
        // getting attached intent data
        String title = i.getStringExtra("title");
        // displaying selected product name
        txtProduct.setText(title);
    }

}
