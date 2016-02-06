package comquintonjcinemasnob.github.cinemasnob;

/**
 * Created by Maxwell on 2/6/2016.
 * Represents a single user
 */
public class User {
    String userName;
    String password;

    public User(String n, String p) {
        userName = n;
        password = p;
    }

    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }
}
