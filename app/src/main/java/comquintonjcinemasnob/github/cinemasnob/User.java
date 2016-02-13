package comquintonjcinemasnob.github.cinemasnob;

/**
 * Created by Maxwell on 2/6/2016.
 * Represents a single user
 */
public class User {
    String userName;
    String password;
    String email;

    public User(String n, String p, String e ) {
        userName = n;
        password = p;
        email = e;
    }

    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }
}
