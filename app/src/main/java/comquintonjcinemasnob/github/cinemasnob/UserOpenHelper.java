package comquintonjcinemasnob.github.cinemasnob;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Brandon on 2/13/2016.
 */
public class UserOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users";
    private static final String USER_TABLE_NAME = "Registered Users";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_EMAIL = "Email";
    // Not 100% sure this is correct syntax
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    KEY_USERNAME + " TEXT, " +
                    KEY_PASSWORD + " TEXT, " +
                    KEY_EMAIL + " TEXT);";

    UserOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        Log.d("Database", "Table created");
    }

    public void putUser(UserOpenHelper dbhelp, String name, String pass, String email) {
        SQLiteDatabase db = dbhelp.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_USERNAME, name);
        cv.put(KEY_PASSWORD, pass);
        cv.put(KEY_EMAIL, email);
        db.insert(USER_TABLE_CREATE, null, cv);
        Log.d("Database", "New row inserted");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
