package project.github.cinemasnob.view;

import android.content.Context;
import android.graphics.Movie;

import junit.framework.TestCase;
import project.github.cinemasnob.controller.RatingOpenHelper;
import project.github.cinemasnob.model.Rating;

/**
 * Created by Jacob on 4/4/2016.
 */
public class MovieItemActivityTest extends TestCase {

    private Context mockContext;

    public void testOnCreate() throws Exception {

    }

    public void testOnRatingBarChanged() {
        RatingOpenHelper ratingDB = new RatingOpenHelper(mockContext);

        Rating currentRating = ratingDB.getRating(ratingDB,
                "quint", "The Revenant",
                Integer.toString(770809932));


        ratingDB.putRating(ratingDB, "quint",
                "The Revenant", 5.0f, 770809932);
    }
}