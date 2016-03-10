package project.github.cinemasnob.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import project.github.cinemasnob.Model.Profile;
import project.github.cinemasnob.Model.User;

/**
 * Class for the ProfileOpenHelper SQLite database
 */
public class ProfileOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Profile";
    private static final String PROFILE_TABLE_NAME = "Registered";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_INTERESTS = "Interests";
    private static final String KEY_MAJOR = "Major";
    private static final String PROFILE_TABLE_CREATE =
            "CREATE TABLE " + PROFILE_TABLE_NAME + " (" +
                    KEY_USERNAME + " TEXT, " +
                    KEY_MAJOR + " TEXT, " +
                    KEY_INTERESTS + " TEXT)";

    /*
     * The constructor
     * @param context the context of the activity
     */
    public ProfileOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROFILE_TABLE_CREATE);
    }

    /**
     * Put the profile in the database
     * @param dbhelp the database
     * @param name the username of the User
     * @param major the major of the User
     * @param interests the interests of the User
     */
    public void putProfile(ProfileOpenHelper dbhelp, String name, String major, String interests ) {
        SQLiteDatabase db = dbhelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_MAJOR, major);
        values.put(KEY_INTERESTS, interests);
        long newRowID = db.insert(
                PROFILE_TABLE_NAME,
                null,
                values);
    }

    /**
     * Get profile based on given info
     * @param dbhelp the database
     * @param name the userName
     * @return the Profile
     */
    public Profile getProfile(ProfileOpenHelper dbhelp, String name) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        String[] projection = {
                KEY_USERNAME,
                KEY_MAJOR,
                KEY_INTERESTS
        };
        String sortOrder = KEY_USERNAME + " DESC";

        //Cursor for SQL Database
        Cursor c = db.query(PROFILE_TABLE_NAME, new String[] {
                        KEY_USERNAME, KEY_MAJOR, KEY_INTERESTS},
                KEY_USERNAME + "=?",
                new String[] { name },
                null, null, null, null);

        if (!(c.moveToFirst())) {
            return null;
        }
        String username = c.getString(c.getColumnIndexOrThrow(KEY_USERNAME));
        String major = c.getString(c.getColumnIndexOrThrow(KEY_MAJOR));
        String interests = c.getString(c.getColumnIndexOrThrow(KEY_INTERESTS));
        return new Profile(username, major, interests);
    }

    /**
     * Update a profile major based on given info
     * @param dbhelp the database
     * @param major the major
     */
    public void updateMajor(ProfileOpenHelper dbhelp, String major) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAJOR, major);
        String selection = KEY_USERNAME + " LIKE ?";
        String[] selectionArgs = {User.getCurrentUser().getUserName()};

        int count = db.update(
                PROFILE_TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    /**
     * Update a profile interests based on given info
     * @param dbhelp the database
     * @param interests the interests
     */
    public void updateInterests(ProfileOpenHelper dbhelp, String interests) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_INTERESTS, interests);
        String selection = KEY_USERNAME + " LIKE ?";
        String[] selectionArgs = {User.getCurrentUser().getUserName()};

        int count = db.update(
                PROFILE_TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

