package project.github.cinemasnob.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.github.cinemasnob.model.MovieHelper;
import project.github.cinemasnob.model.Rating;
import project.github.cinemasnob.model.User;

/**
 * Class for the ProfileOpenHelper SQLite database.
 */
public class RatingOpenHelper extends SQLiteOpenHelper {

    /**
     * The version of the database.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * The name of the database.
     */
    private static final String DATABASE_NAME = "Movies";

    /**
     * The table name.
     */
    private static final String MOVIE_TABLE_NAME = "Rated";

    /**
     * The key for the Title of a Movie.
     */
    private static final String KEY_TITLE = "Title";

    /**
     * The Username associated with a Rating.
     */
    private static final String KEY_USERNAME = "Username";

    /**
     * The Rating value.
     */
    private static final String KEY_RATING = "Rating";

    /**
     * The movie ID associated with a Rating.
     */
    private static final String KEY_ID = "Id";

    /**
     * Creation of the table.
     */
    private static final String TABLE_CREATE =
            "CREATE TABLE " + MOVIE_TABLE_NAME + " ("
                    + KEY_TITLE + " TEXT, "
                    + KEY_USERNAME + " TEXT, "
                    + KEY_RATING + " TEXT,"
                    + KEY_ID + " TEXT)";

    /**
     * The constructor.
     * @param context the context of the activity
     */
    public RatingOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
    }

    /**
     * Put the profile in the database.
     * @param dbHelp the database
     * @param name the username of the User
     * @param title the major of the User
     * @param rating the interests of the User
     * @param movieID the id of the Movie
     * @return whether or not the rating was added
     */
    public boolean putRating(RatingOpenHelper dbHelp, String name,
                          String title, float rating, int movieID) {
        SQLiteDatabase database = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (name == null || title == null || movieID == 0) {
            return false;
        }
        values.put(KEY_TITLE, title);
        values.put(KEY_USERNAME, name);
        values.put(KEY_RATING, Float.toString(rating));
        values.put(KEY_ID, movieID);
        database.insert(
                MOVIE_TABLE_NAME,
                null,
                values);
        return true;
    }

    /**
     * Get profile based on given info.
     * @param dbHelp the database
     * @param name the userName
     * @param title the title of the Movie
     * @param movieID the ID of the Movie
     * @return the Profile
     */
    public Rating getRating(RatingOpenHelper dbHelp, String name,
                            String title, String movieID) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();

        String whereClause = KEY_TITLE + "=?" + " AND "
                + KEY_USERNAME + "=?" + " AND "
                + KEY_ID  + "=?";
        String[] whereArgs = new String[]{title, name, movieID};

        //Cursor for SQL Database
        Cursor cursor = database.query(MOVIE_TABLE_NAME,
                new String[] {KEY_TITLE, KEY_USERNAME, KEY_RATING, KEY_ID},
                whereClause,
                whereArgs,
                null, null, null, null);

        if (!(cursor.moveToFirst())) {
            return null;
        }

        String movieTitle =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
        String username =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERNAME));
        String rating =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_RATING));
        int movieId =
                cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
        cursor.close();
        return new Rating(username,
                movieTitle,
                Float.parseFloat(rating),
                movieId);
    }

    /**
     * Update a profile interests based on given info.
     * @param dbHelp the database
     * @param rating the interests
     * @param title the title of the Movie
     * @param name the name of the User
     * @param movieID the ID of the Movie
     */
    public void updateRating(RatingOpenHelper dbHelp, String rating,
                             String title, String name, String movieID) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RATING, rating);
        String whereClause = KEY_TITLE + "=?" + " AND "
                + KEY_USERNAME + "=?" + " AND "
                + KEY_ID + "=?";
        String[] whereArgs = new String[]{title, name, movieID};
        database.update(MOVIE_TABLE_NAME, values, whereClause, whereArgs);
    }

    /**
     * Goes through the database and averages the ratings of movies.
     * @param dbHelp the database
     * @return an ArrayList of MovieHelpers that hold the movie ids with the
     * average rating
     */
    public List<MovieHelper> averageOverall(RatingOpenHelper dbHelp) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT Title, Username, "
                + "Rating, Id FROM Rated ", null);
        Map<Integer, MovieHelper>  movieHash = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                String rating = cursor.getString(2);
                String title = cursor.getString(0);
                String stringID = cursor.getString(3);
                int movieID = Integer.parseInt(stringID);
                // If the movie hash map doesn't have the ID already, add it
                if (!(movieHash.containsKey(movieID))) {
                    MovieHelper movieHelp =
                            new MovieHelper(Integer.parseInt(stringID),
                                    Float.parseFloat(rating), 0, title);
                    movieHash.put(Integer.parseInt(stringID), movieHelp);
                } else if (movieHash.containsKey(movieID)) {
                    // If it does have the ID already, add count and add rating
                    movieHash.get(movieID).addRating(Float.parseFloat(rating));
                    movieHash.get(movieID).setCount(1);
                }
            } while (cursor.moveToNext());
        }
        List<MovieHelper> movies = new ArrayList<>();
        // Averaging out ratings
        for (MovieHelper value : movieHash.values()) {
            int totalMovies = value.getCount();
            value.setRating(value.getRating() / totalMovies);
            movies.add(value);
        }
        cursor.close();
        database.close();
        return movies;
    }

    /**
     * Goes through the database and averages the ratings of movies by major.
     * @param dbHelp the database
     * @param userHelp the User database
     * @param major the major to average
     * @return an ArrayList of MovieHelpers that hold the movie ids with the
     * average rating by major
     */
    public List<MovieHelper> averageMajor(RatingOpenHelper dbHelp,
                                               UserOpenHelper userHelp,
                                               String major) {
        SQLiteDatabase ratingDB = dbHelp.getReadableDatabase();
        Cursor cursor = ratingDB.rawQuery("SELECT Title, Username, "
                + "Rating, Id FROM Rated ", null);
        Map<Integer, MovieHelper> movieHash = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                String username = cursor.getString(1);
                String rating = cursor.getString(2);
                String stringID = cursor.getString(3);
                User currentUser = userHelp.getUser(userHelp, username);
                int movieID = Integer.parseInt(stringID);
                // If the movie hash map doesn't have the ID already, add it
                if (!(movieHash.containsKey(movieID))
                        && currentUser.getMajor().equalsIgnoreCase(major)) {
                    MovieHelper movieHelp = new MovieHelper(
                            Integer.parseInt(stringID),
                            Float.parseFloat(rating), 0,
                            title);
                    movieHash.put(Integer.parseInt(stringID), movieHelp);
                } else if (movieHash.containsKey(movieID)
                        && currentUser.getMajor().equalsIgnoreCase(major)) {
                    movieHash.get(movieID).addRating(Float.parseFloat(rating));
                    movieHash.get(movieID).setCount(1);
                }
            } while (cursor.moveToNext());
        }
        List<MovieHelper> movies = new ArrayList<>();
        // Averaging out ratings
        for (MovieHelper value : movieHash.values()) {
            int totalMovies = value.getCount();
            value.setRating(value.getRating() / totalMovies);
            movies.add(value);
        }
        cursor.close();
        ratingDB.close();
        return movies;
    }


    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion, int newVersion) {
        // Overridden method
    }
}

