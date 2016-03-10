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
 * Class for the ProfileOpenHelper SQLite database
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
     * The constructor
     * @param context the context of the activity
     */
    public RatingOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MOVIE_TABLE_CREATE);
    }

    /**
     * Put the profile in the database
     * @param dbhelp the database
     * @param name the username of the User
     * @param title the major of the User
     * @param rating the interests of the User
     */
    public void putRating(RatingOpenHelper dbhelp, String name, String title, float rating, int id) {
        SQLiteDatabase db = dbhelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_USERNAME, name);
        values.put(KEY_RATING, Float.toString(rating));
        values.put(KEY_ID, id);
        long newRowID = db.insert(
                MOVIE_TABLE_NAME,
                null,
                values);
    }

    /**
     * Get profile based on given info
     * @param dbhelp the database
     * @param name the userName
     * @param title the title of the movie
     * @return the Profile
     */
    public Rating getRating(RatingOpenHelper dbhelp, String name, String title) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        String[] projection = {
                KEY_TITLE,
                KEY_USERNAME,
                KEY_RATING,
                KEY_ID
        };
        String whereClause = KEY_TITLE + "=?" + " AND " + KEY_USERNAME + "=?";
        String[] whereArgs = new String[]{title, name};

        //Cursor for SQL Database
        Cursor c = db.query(MOVIE_TABLE_NAME, new String[] {
                        KEY_TITLE, KEY_USERNAME, KEY_RATING, KEY_ID},
                whereClause,
                whereArgs,
                null, null, null, null);

        if (!(c.moveToFirst())) {
            return null;
        }

        String movieTitle = c.getString(c.getColumnIndexOrThrow(KEY_TITLE));
        String username = c.getString(c.getColumnIndexOrThrow(KEY_USERNAME));
        String rating = c.getString(c.getColumnIndexOrThrow(KEY_RATING));
        int movieId = c.getInt(c.getColumnIndexOrThrow(KEY_ID));
        return new Rating(username, movieTitle, Float.parseFloat(rating), movieId);
    }

    /**
     * Update a profile interests based on given info
     * @param dbhelp the database
     * @param rating the interests
     */
    public void updateRating(RatingOpenHelper dbhelp, String rating, String title, String name, int id) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RATING, rating);
        String whereClause = KEY_TITLE + "=?" + " AND " + KEY_USERNAME + "=?";
        String[] whereArgs = new String[]{title, name};
        db.update(MOVIE_TABLE_NAME, values, whereClause, whereArgs);
    }

    /**
     * Goes through the database and averages the ratings of movies
     * @param dbhelp the database
     * @return an ArrayList of MovieHelpers that hold the movie ids with the
     * average rating
     */
    public ArrayList<MovieHelper> averageOverall(RatingOpenHelper dbhelp) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Title, Username, Rating, Id FROM Rated ", null);
        HashMap<Integer, MovieHelper>  movieHash = new HashMap<>();
        if (c.moveToFirst()) {
            do {
                String rating = c.getString(2);
                String title = c.getString(0);
                String id = c.getString(3);
                int movieID = Integer.parseInt(id);
                // If the movie hash map doesn't have the ID already, add it
                if (!(movieHash.containsKey(movieID))) {
                    MovieHelper movieHelp = new MovieHelper(Integer.parseInt(id), Float.parseFloat(rating), 0, title);
                    movieHash.put(Integer.parseInt(id), movieHelp);
                } else if (movieHash.containsKey(movieID)) {
                    // If it does have the ID already, add count and add rating
                    movieHash.get(movieID).addRating(Float.parseFloat(rating));
                    movieHash.get(movieID).setCount(1);
                }
            } while (c.moveToNext());
        }
        ArrayList<MovieHelper> movies = new ArrayList<>();
        // Averaging out ratings
        for (MovieHelper value : movieHash.values()) {
            int totalMovies = value.getCount();
            value.setRating(value.getRating() / totalMovies);
            movies.add(value);
        }
        c.close();
        db.close();
        return movies;
    }

    /**
     * Goes through the database and averages the ratings of movies by major
     * @param dbhelp the database
     * @return an ArrayList of MovieHelpers that hold the movie ids with the
     * average rating by major
     */
    public ArrayList<MovieHelper> averageMajor(RatingOpenHelper dbhelp,
                                               UserOpenHelper userhelp,
                                               String major) {
        SQLiteDatabase ratingdb = dbhelp.getReadableDatabase();
        Cursor c = ratingdb.rawQuery("SELECT Title, Username, Rating, Id FROM Rated ", null);
        HashMap<Integer, MovieHelper>  movieHash = new HashMap<>();
        if (c.moveToFirst()) {
            do {

                String title = c.getString(0);
                String username = c.getString(1);
                String rating = c.getString(2);
                String id = c.getString(3);
                User currentUser = userhelp.getUser(userhelp, username);
                int movieID = Integer.parseInt(id);
                // If the movie hash map doesn't have the ID already, add it
                if (!(movieHash.containsKey(movieID))) {
                    System.out.println("Current user: " + currentUser.getMajor());
                    System.out.println("Other user: " + major);
                    System.out.println(currentUser.getMajor().toLowerCase()
                            .equals(major.toLowerCase()));
                    if (currentUser.getMajor().toLowerCase()
                            .equals(major.toLowerCase())) {
                        MovieHelper movieHelp = new MovieHelper(
                                Integer.parseInt(id),
                                Float.parseFloat(rating),
                                0,
                                title);
                        movieHash.put(Integer.parseInt(id), movieHelp);
                    }
                } else if (movieHash.containsKey(movieID)) {
                    // If it does have the ID already, add count and add rating
                    if (currentUser.getMajor().toLowerCase()
                            .equals(major.toLowerCase())) {
                        movieHash.get(movieID).addRating(Float.parseFloat(rating));
                        movieHash.get(movieID).setCount(1);
                    }
                }
            } while (c.moveToNext());
        }
        ArrayList<MovieHelper> movies = new ArrayList<>();
        // Averaging out ratings
        for (MovieHelper value : movieHash.values()) {
            int totalMovies = value.getCount();
            value.setRating(value.getRating() / totalMovies);
            movies.add(value);
        }
        c.close();
        ratingdb.close();
        return movies;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

