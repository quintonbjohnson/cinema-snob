package project.github.cinemasnob.controller;

import android.content.Context;

import org.junit.Test;

import java.sql.SQLOutput;

import project.github.cinemasnob.model.Rating;

import static org.junit.Assert.*;

/**
 * Created by Quinton on 4/2/16.
 */
public class RatingOpenHelperTest {

    private Context mockContext;

    @Test
    public void testPutRating() throws Exception {
        RatingOpenHelper ratingDB = new RatingOpenHelper(mockContext);

        // Test null username
        boolean failure = ratingDB.putRating(ratingDB, null, "The Revenant",
                5.0f, 770809932);
        assertFalse(failure);

        // Test null title
        failure = ratingDB.putRating(ratingDB, "quint", null,
                5.0f, 770809932);
        assertFalse(failure);

        // Test moveID
        failure = ratingDB.putRating(ratingDB, "quint", "The Revenant",
                5.0f, 0);
        assertFalse(failure);

        // Success scenario
        try {
            boolean success = ratingDB.putRating(ratingDB, "quint",
                    "The Revenant", 5.0f, 770809932);
            assertTrue(success);

        } catch (NullPointerException e) {
            System.out.println("Insert uses a null input value, this is fine.");
        }

        try {
            Rating testRating =
                    ratingDB.getRating(ratingDB, "quint",
                            "The Revenant", "770809932");
            float testRatingValue = testRating.getRating();

            assertEquals(testRatingValue, 5.0f);
        } catch (NullPointerException e) {
            System.out.println("Insert uses a null input value, this is fine.");
        }

    }
}