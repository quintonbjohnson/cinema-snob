package project.github.cinemasnob.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import project.github.cinemasnob.model.User;

/**
 * Class for the UserOpenHelper SQLite database.
 */
public class UserOpenHelper extends SQLiteOpenHelper {

    /**
     * Version of the database.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * The name of the database.
     */
    private static final String DATABASE_NAME = "Users";

    /**
     * The name of the table created.
     */
    private static final String USER_TABLE_NAME = "Registered";

    /**
     * The Username associated with the User.
     */
    private static final String KEY_USERNAME = "Username";

    /**
     * The Password associated with the User.
     */
    private static final String KEY_PASSWORD = "Password";

    /**
     * The Email associated with the User.
     */
    private static final String KEY_EMAIL = "Email";

    /**
     * The Major associated with the User.
     */
    private static final String KEY_MAJOR = "Major";

    /**
     * The banStatus of the User.
     */
    private static final String KEY_BANNED = "Banned";

    /**
     * Creation of the table.
     */
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " ("
                    + KEY_USERNAME + " TEXT, "
                    + KEY_PASSWORD + " TEXT, "
                    + KEY_EMAIL + " TEXT, "
                    + KEY_MAJOR + " TEXT, "
                    + KEY_BANNED + " TEXT)";

    /**
     * Constructor.
     * @param context context used to create database.
     */
    public UserOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(USER_TABLE_CREATE);
    }

    /**
     * Add a User with the given info to the database.
     * @param dbHelp the database
     * @param name the username of the User
     * @param pass the password for the User
     * @param email the email of the User
     * @param major the major of the User
     * @param ban the ban status of the User
     */
    public void putUser(UserOpenHelper dbHelp, String name,
                        String pass, String email,
                        String major, boolean ban) {
        SQLiteDatabase database = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_PASSWORD, pass);
        values.put(KEY_EMAIL, email);
        values.put(KEY_MAJOR, major);
        values.put(KEY_BANNED, Boolean.toString(ban));
        database.insert(
                USER_TABLE_NAME,
                null,
                values);
    }

    /**
     * Updates the major of a User with the given username.
     * @param dbHelp the database
     * @param name the username of the User to update
     * @param major the major to update with
     */
    public void updateMajor(UserOpenHelper dbHelp,
                            String name, String major) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAJOR, major);
        String whereClause = KEY_USERNAME + "=?";
        String[] whereArgs = new String[]{name};
        database.update(USER_TABLE_NAME, values, whereClause, whereArgs);
    }

    /**
     * Get the User with the given username from the database.
     * @param dbHelp the database
     * @param name the username
     * @return the User
     */
    public User getUser(UserOpenHelper dbHelp, String name) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();

        //Cursor for SQL Database
        Cursor cursor = database.query(USER_TABLE_NAME,
                new String[] {KEY_USERNAME, KEY_PASSWORD,
                    KEY_EMAIL, KEY_MAJOR, KEY_BANNED, },
                KEY_USERNAME + "=?",
                new String[] {name},
                null, null, null, null);

        if (!(cursor.moveToFirst())) {
            return null;
        }
        String username =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERNAME));
        String password =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD));
        String userEmail =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL));
        String major =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_MAJOR));
        String banned =
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_BANNED));
        Boolean isBanned = Boolean.parseBoolean(banned);
        cursor.close();
        return new User(username, password, userEmail, major, isBanned);
    }

    /**
     * Gets the list of names from the database.
     * @param dbHelp the database
     * @return the list of names
     */
    public List<String> getUserList(UserOpenHelper dbHelp) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT Username "
                + "FROM Registered", null);
        List<String> userList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(0);
                if (!"ADMIN".equals(username)) {
                    userList.add(username);
                }
            } while (cursor.moveToNext());
        }
        Collections.sort(userList, new Comparator<String>() {
            @Override
            public int compare(String string1, String string2) {
                return string1.compareToIgnoreCase(string2);
            }
        });
        cursor.close();
        database.close();
        return userList;
    }

    /**
     * Update the database to denote that the
     * User with the given username is banned.
     * @param dbHelp the database
     * @param name the username of the User to ban
     * @param ban the ban status to set to.
     */
    public void setBanStatusOfUser(UserOpenHelper dbHelp,
                                   String name, boolean ban) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BANNED, Boolean.toString(ban));
        String whereClause = KEY_USERNAME + "=?";
        String[] whereArgs = new String[]{name};
        database.update(USER_TABLE_NAME, values, whereClause, whereArgs);
    }

    /*
     * On upgrade, not used.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion, int newVersion) {
        // Overridden method
    }
}
