package project.github.cinemasnob.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import project.github.cinemasnob.Model.Profile;
import project.github.cinemasnob.Model.User;

/**
 * Class for the ProfileOpenHelper SQLite database.
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
     * The constructor.
     * @param context the context of the activity
     */
    public ProfileOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(PROFILE_TABLE_CREATE);
    }

    /**
     * Put the profile in the database.
     * @param dbHelp the database
     * @param name the username of the User
     * @param major the major of the User
     * @param interests the interests of the User
     */
    public void putProfile(ProfileOpenHelper dbHelp, String name,
                           String major, String interests ) {
        SQLiteDatabase database = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_MAJOR, major);
        values.put(KEY_INTERESTS, interests);
        database.insert(
                PROFILE_TABLE_NAME,
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
        Cursor cursor = database.query(PROFILE_TABLE_NAME, new String[] {
                        KEY_USERNAME, KEY_MAJOR, KEY_INTERESTS},
                KEY_USERNAME + "=?",
                new String[] { name },
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
                PROFILE_TABLE_NAME,
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
                PROFILE_TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Overridden method
    }
}

