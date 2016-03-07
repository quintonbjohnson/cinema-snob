package project.github.cinemasnob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by erikmaday on 3/6/16.
 */
public class MovieListAdapter extends ArrayAdapter<Movie> {

    public MovieListAdapter(Context context, int textViewResourceId,ArrayList<Movie> movies) {
        super(context, textViewResourceId, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_row_layout, parent, false);
        }
        // Lookup view for data population
        TextView movieName = (TextView) convertView.findViewById(R.id.movieName);
        TextView movieRating = (TextView) convertView.findViewById(R.id.movieRating);
        TextView movieYear = (TextView) convertView.findViewById(R.id.movieYear);
        // Populate the data into the template view using the data object
        movieName.setText(movie.getTitle());
        movieRating.setText(movie.getMpaa_rating());
        movieYear.setText(movie.getYear());

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}