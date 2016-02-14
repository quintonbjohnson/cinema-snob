package comquintonjcinemasnob.github.cinemasnob;

/**
 * Created by Maxwell on 2/6/2016.
 * Represents a single user
 */
public class User {
    String userName;
    String password;
    String email;

    /**
     * Instantiates the User object
     * @param n Username
     * @param p Password
     * @param e Email
     */
    public User(String n, String p, String e ) {
        userName = n;
        password = p;
        email = e;
    }

    /**
     * Checks if password provided matches actual password
     * @param pass Password provided by user
     * @return True or false if password matches or does not match
     */
    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }
}
