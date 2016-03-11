package project.github.cinemasnob.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import project.github.cinemasnob.Model.MovieHelper;
import project.github.cinemasnob.Model.User;

/**
 * Class for the UserOpenHelper SQLite database
 */
public class UserOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users";
    private static final String USER_TABLE_NAME = "Registered";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_MAJOR = "Major";
    private static final String KEY_BANNED = "Banned";
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    KEY_USERNAME + " TEXT, " +
                    KEY_PASSWORD + " TEXT, " +
                    KEY_EMAIL + " TEXT, " +
                    KEY_MAJOR + " TEXT, " +
                    KEY_BANNED + " TEXT)";

    /*
     * The constructor
     * @param context the context of the activity
     */
    public UserOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    /*
     * Put user in database
     * @param dbhelp the database helper
     * @param name the username
     * @param pass the password
     * @param email the email
     */
    public void putUser(UserOpenHelper dbhelp, String name, String pass, String email,
                        String major, boolean ban) {
        SQLiteDatabase db = dbhelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_PASSWORD, pass);
        values.put(KEY_EMAIL, email);
        values.put(KEY_MAJOR, major);
        values.put(KEY_BANNED, Boolean.toString(ban));
        long newRowID = db.insert(
                        USER_TABLE_NAME,
                        null,
                        values);
    }

    /*
     * Gets user from database used in login
     * @param dbhelp database helper
     * @param name username to get user
     * @return the user
     */
    public User getUser(UserOpenHelper dbhelp, String name) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        String sortOrder = KEY_USERNAME + " DESC";

        //Cursor for SQL Database
        Cursor c = db.query(USER_TABLE_NAME, new String[] {
                KEY_USERNAME, KEY_PASSWORD, KEY_EMAIL, KEY_MAJOR, KEY_BANNED},
                KEY_USERNAME + "=?",
                new String[] { name },
                null, null, null, null);

        if (!(c.moveToFirst())) {
            return null;
        }
        String username = c.getString(c.getColumnIndexOrThrow(KEY_USERNAME));
        String password = c.getString(c.getColumnIndexOrThrow(KEY_PASSWORD));
        String userEmail = c.getString(c.getColumnIndexOrThrow(KEY_EMAIL));
        String major = c.getString(c.getColumnIndexOrThrow(KEY_MAJOR));
        String banned = c.getString(c.getColumnIndexOrThrow(KEY_BANNED));
        Boolean isBanned = Boolean.parseBoolean(banned);
        c.close();
        return new User(username, password, userEmail, major, isBanned);
    }

    /**
     * Gets the list of usernames from the database
     * @param dbhelp the database
     * @return the list of usernames
     */
    public ArrayList<String> getUserList(UserOpenHelper dbhelp) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Username FROM Registered", null);
        ArrayList<String> userList = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                String username = c.getString(0);
                if (!username.equals("ADMIN")) {
                    userList.add(username);
                }
            } while (c.moveToNext());
        }
        Collections.sort(userList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        c.close();
        db.close();
        return userList;
    }

    /**
     * Update the database to denote that the User with the given username is banned
     * @param dbhelp the database
     * @param name the username of the User to ban
     */
    public void setBanStatusOfUser(UserOpenHelper dbhelp, String name, boolean ban) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BANNED, Boolean.toString(ban));
        String whereClause = KEY_USERNAME + "=?";
        String[] whereArgs = new String[]{name};
        db.update(USER_TABLE_NAME, values, whereClause, whereArgs);
    }

    /*
     * On upgrade, not used
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
