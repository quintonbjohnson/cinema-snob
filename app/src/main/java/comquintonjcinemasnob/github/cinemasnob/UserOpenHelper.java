package comquintonjcinemasnob.github.cinemasnob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Brandon on 2/13/2016.
 */
public class UserOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users";
    private static final String USER_TABLE_NAME = "Registered";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_EMAIL = "Email";
    // Not 100% sure this is correct syntax
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    KEY_USERNAME + " TEXT, " +
                    KEY_PASSWORD + " TEXT, " +
                    KEY_EMAIL + " TEXT)";

    public UserOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    public void putUser(UserOpenHelper dbhelp, String name, String pass, String email) {
        SQLiteDatabase db = dbhelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name);
        values.put(KEY_PASSWORD, pass);
        values.put(KEY_EMAIL, email);
        long newRowID = db.insert(
                        USER_TABLE_NAME,
                        null,
                        values);
    }



    // dbhelper.getUser(dbhelper, name, pass, email);
    public User getUser(UserOpenHelper dbhelp, String name) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        String[] projection = {
                KEY_USERNAME,
                KEY_PASSWORD,
                KEY_EMAIL
        };
        String sortOrder = KEY_USERNAME + " DESC";

        String selection = KEY_USERNAME;
        String[] selectionArgs = {name};

        Cursor c = db.query(
                USER_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
                );
        if (!(c.moveToFirst())) {
            return null;
        }
        String username = c.getString(c.getColumnIndexOrThrow(KEY_USERNAME));
        String password = c.getString(c.getColumnIndexOrThrow(KEY_PASSWORD));
        String useremail = c.getString(c.getColumnIndexOrThrow(KEY_EMAIL));
        return new User(username, password, useremail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
