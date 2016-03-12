package project.github.cinemasnob.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import project.github.cinemasnob.model.User;

/**
 * Class for the UserOpenHelper SQLite database.
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

    /**
     * Constructor
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
     * Get the User with the given username from the database.
     * @param dbHelp the database
     * @param name the username
     * @return the User
     */
    public User getUser(UserOpenHelper dbHelp, String name) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();

        //Cursor for SQL Database
        Cursor cursor = database.query(USER_TABLE_NAME, new String[] {
                KEY_USERNAME, KEY_PASSWORD, KEY_EMAIL, KEY_MAJOR, KEY_BANNED},
                KEY_USERNAME + "=?",
                new String[] { name },
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
    public ArrayList<String> getUserList(UserOpenHelper dbHelp) {
        SQLiteDatabase database = dbHelp.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT Username "
                + "FROM Registered", null);
        ArrayList<String> userList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(0);
                if (!username.equals("ADMIN")) {
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
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // Overridden method
    }
}
