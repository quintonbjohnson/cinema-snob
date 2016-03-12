package project.github.cinemasnob.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import project.github.cinemasnob.model.Profile;
import project.github.cinemasnob.model.User;

/**
 * Class for the ProfileOpenHelper SQLite database.
 */
public class ProfileOpenHelper extends SQLiteOpenHelper {

    /**
     * The version of the database.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * The name of the database.
     */
    private static final String DATABASE_NAME = "Profile";

    /**
     * The name of the Table created.
     */
    private static final String TABLE_NAME = "Registered";

    /**
     * The key for a Username.
     */
    private static final String KEY_USERNAME = "Username";

    /**
     * The Interests of the User.
     */
    private static final String KEY_INTERESTS = "Interests";

    /**
     * The Major of the User.
     */
    private static final String KEY_MAJOR = "Major";

    /**
     * Creation of the table.
     */
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY_USERNAME + " TEXT, "
                    + KEY_MAJOR + " TEXT, "
                    + KEY_INTERESTS + " TEXT)";

    /**
     * The constructor.
     * @param context the context of the activity
     */
    public ProfileOpenHelper(Context context) {
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
     * @param major the major of the User
     * @param interests the interests of the User
     */
    public void putProfile(ProfileOpenHelper dbHelp, String name,
                           String major, String interests) {
        SQLiteDatabase database = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_MAJOR, major);
        values.put(KEY_INTERESTS, interests);
        database.insert(
                TABLE_NAME,
                null,
                values);
    }

    /**
     * Get profile based on given info.
     * @param dbHelp the database
     * @param name the userName
     * @return the Profile
     */
    public Profile getProfile(ProfileOpenHelper dbHelp, String name) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();

        //Cursor for SQL Database
        Cursor cursor = database.query(TABLE_NAME,
                new String[] {KEY_USERNAME, KEY_MAJOR, KEY_INTERESTS},
                KEY_USERNAME + "=?",
                new String[] {name},
                null, null, null, null);

        if (!(cursor.moveToFirst())) {
            return null;
        }
        String username =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERNAME));
        String major =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_MAJOR));
        String interests =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_INTERESTS));
        cursor.close();
        return new Profile(username, major, interests);
    }

    /**
     * Update a profile major based on given info.
     * @param dbHelp the database
     * @param major the major
     */
    public void updateMajor(ProfileOpenHelper dbHelp, String major) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAJOR, major);
        String selection = KEY_USERNAME + " LIKE ?";
        String[] selectionArgs = {User.getCurrentUser().getUserName()};

        database.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    /**
     * Update a profile interests based on given info.
     * @param dbHelp the database
     * @param interests the interests
     */
    public void updateInterests(ProfileOpenHelper dbHelp, String interests) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_INTERESTS, interests);
        String selection = KEY_USERNAME + " LIKE ?";
        String[] selectionArgs = {User.getCurrentUser().getUserName()};

        database.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion, int newVersion) {
        // Overridden method
    }
}

