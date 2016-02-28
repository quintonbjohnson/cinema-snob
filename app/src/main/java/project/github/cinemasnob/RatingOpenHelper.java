package project.github.cinemasnob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class for the ProfileOpenHelper SQLite database
 */
public class RatingOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Movies";
    private static final String MOVIE_TABLE_NAME = "Rated";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_RATING = "Rating";
    private static final String MOVIE_TABLE_CREATE =
            "CREATE TABLE " + MOVIE_TABLE_NAME + " (" +
                    KEY_USERNAME + " TEXT, " +
                    KEY_TITLE + " TEXT, " +
                    KEY_RATING + " TEXT)";

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
    public void putRating(RatingOpenHelper dbhelp, String name, String title, float rating) {
        SQLiteDatabase db = dbhelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_TITLE, title);
        values.put(KEY_RATING, Float.toString(rating));
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
                KEY_USERNAME,
                KEY_TITLE,
                KEY_RATING
        };
        String sortOrder = KEY_USERNAME + " DESC";

        //Cursor for SQL Database
        Cursor c = db.query(MOVIE_TABLE_NAME, new String[] {
                        KEY_USERNAME, KEY_TITLE, KEY_RATING},
                KEY_USERNAME + "=?",
                new String[] { name },
                null, null, null, null);

        if (!(c.moveToFirst())) {
            return null;
        }
        String username = c.getString(c.getColumnIndexOrThrow(KEY_USERNAME));
        String movieTitle = c.getString(c.getColumnIndexOrThrow(KEY_TITLE));
        String rating = c.getString(c.getColumnIndexOrThrow(KEY_RATING));
        return new Rating(username, movieTitle, Float.parseFloat(rating));
    }

    /**
     * Update a profile major based on given info
     * @param dbhelp the database
     * @param title the major
     */
    public void updateTitle(RatingOpenHelper dbhelp, String title) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        String selection = KEY_USERNAME + " LIKE ?";
        String[] selectionArgs = {User.getCurrentUser().getUserName()};

        int count = db.update(
                MOVIE_TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    /**
     * Update a profile interests based on given info
     * @param dbhelp the database
     * @param rating the interests
     */
    public void updateRating(RatingOpenHelper dbhelp, String rating) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RATING, rating);
        String selection = KEY_USERNAME + " LIKE ?";
        String[] selectionArgs = {User.getCurrentUser().getUserName()};

        int count = db.update(
                MOVIE_TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

