package comquintonjcinemasnob.github.cinemasnob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.security.KeyStoreParameter;

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
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    KEY_USERNAME + " TEXT, " +
                    KEY_PASSWORD + " TEXT, " +
                    KEY_EMAIL + " TEXT)";

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

    /*
     * Gets user from database used in login
     * @param dbhelp database helper
     * @param name username to get user
     * @return the user
     */
    public User getUser(UserOpenHelper dbhelp, String name) {
        SQLiteDatabase db = dbhelp.getReadableDatabase();
        String[] projection = {
                KEY_USERNAME,
                KEY_PASSWORD,
                KEY_EMAIL
        };
        String sortOrder = KEY_USERNAME + " DESC";

        //Cursor for SQL Database
        Cursor c = db.query(USER_TABLE_NAME, new String[] {
                KEY_USERNAME, KEY_PASSWORD, KEY_EMAIL},
                KEY_USERNAME + "=?",
                new String[] { name },
                null, null, null, null);

        if (!(c.moveToFirst())) {
            return null;
        }
        String username = c.getString(c.getColumnIndexOrThrow(KEY_USERNAME));
        String password = c.getString(c.getColumnIndexOrThrow(KEY_PASSWORD));
        String useremail = c.getString(c.getColumnIndexOrThrow(KEY_EMAIL));
        return new User(username, password, useremail);
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
