package project.github.cinemasnob.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

import project.github.cinemasnob.Model.MovieHelper;
import project.github.cinemasnob.Model.Rating;
import project.github.cinemasnob.Model.User;

/**
 * Class for the ProfileOpenHelper SQLite database.
 */
public class RatingOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Movies";
    private static final String MOVIE_TABLE_NAME = "Rated";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_RATING = "Rating";
    private static final String KEY_ID = "Id";
    private static final String MOVIE_TABLE_CREATE =
            "CREATE TABLE " + MOVIE_TABLE_NAME + " (" +
                    KEY_TITLE + " TEXT, " +
                    KEY_USERNAME + " TEXT, " +
                    KEY_RATING + " TEXT," +
                    KEY_ID + " TEXT)";

    /*
     * The constructor.
     * @param context the context of the activity
     */
    public RatingOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(MOVIE_TABLE_CREATE);
    }

    /**
     * Put the profile in the database.
     * @param dbHelp the database
     * @param name the username of the User
     * @param title the major of the User
     * @param rating the interests of the User
     */
    public void putRating(RatingOpenHelper dbHelp, String name,
                          String title, float rating, int id) {
        SQLiteDatabase database = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_USERNAME, name);
        values.put(KEY_RATING, Float.toString(rating));
        values.put(KEY_ID, id);
        database.insert(
                MOVIE_TABLE_NAME,
                null,
                values);
    }

    /**
     * Get profile based on given info.
     * @param dbHelp the database
     * @param name the userName
     * @param title the title of the movie
     * @return the Profile
     */
    public Rating getRating(RatingOpenHelper dbHelp, String name,
                            String title, String id) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();

        String whereClause = KEY_TITLE + "=?" + " AND "
                + KEY_USERNAME + "=?" + " AND "
                + KEY_ID  + "=?";
        String[] whereArgs = new String[]{title, name, id};

        //Cursor for SQL Database
        Cursor cursor = database.query(MOVIE_TABLE_NAME, new String[] {
                        KEY_TITLE, KEY_USERNAME, KEY_RATING, KEY_ID},
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
    public ArrayList<MovieHelper> averageOverall(RatingOpenHelper dbHelp) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT Title, Username, "
                + "Rating, Id FROM Rated ", null);
        HashMap<Integer, MovieHelper>  movieHash = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                String rating = cursor.getString(2);
                String title = cursor.getString(0);
                String id = cursor.getString(3);
                int movieID = Integer.parseInt(id);
                // If the movie hash map doesn't have the ID already, add it
                if (!(movieHash.containsKey(movieID))) {
                    MovieHelper movieHelp =
                            new MovieHelper(Integer.parseInt(id),
                                    Float.parseFloat(rating), 0, title);
                    movieHash.put(Integer.parseInt(id), movieHelp);
                } else if (movieHash.containsKey(movieID)) {
                    // If it does have the ID already, add count and add rating
                    movieHash.get(movieID).addRating(Float.parseFloat(rating));
                    movieHash.get(movieID).setCount(1);
                }
            } while (cursor.moveToNext());
        }
        ArrayList<MovieHelper> movies = new ArrayList<>();
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
     * @return an ArrayList of MovieHelpers that hold the movie ids with the
     * average rating by major
     */
    public ArrayList<MovieHelper> averageMajor(RatingOpenHelper dbHelp,
                                               UserOpenHelper userHelp,
                                               String major) {
        SQLiteDatabase ratingDB = dbHelp.getReadableDatabase();
        Cursor cursor = ratingDB.rawQuery("SELECT Title, Username, "
                + "Rating, Id FROM Rated ", null);
        HashMap<Integer, MovieHelper>  movieHash = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {

                String title = cursor.getString(0);
                String username = cursor.getString(1);
                String rating = cursor.getString(2);
                String id = cursor.getString(3);
                User currentUser = userHelp.getUser(userHelp, username);
                int movieID = Integer.parseInt(id);
                // If the movie hash map doesn't have the ID already, add it
                if (!(movieHash.containsKey(movieID))) {
                    if (currentUser.getMajor().equalsIgnoreCase(major)) {
                        MovieHelper movieHelp = new MovieHelper(
                                Integer.parseInt(id),
                                Float.parseFloat(rating),
                                0,
                                title);
                        movieHash.put(Integer.parseInt(id), movieHelp);
                    }
                } else if (movieHash.containsKey(movieID)) {
                    // If it does have the ID already, add count and add rating
                    if (currentUser.getMajor().equalsIgnoreCase(major)) {
                        movieHash.get(movieID).addRating(Float.parseFloat(rating));
                        movieHash.get(movieID).setCount(1);
                    }
                }
            } while (cursor.moveToNext());
        }
        ArrayList<MovieHelper> movies = new ArrayList<>();
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Overridden method
    }
}

