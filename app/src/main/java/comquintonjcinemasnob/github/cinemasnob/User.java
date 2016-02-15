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
     * Getter method for password
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter method for username
     * @return User's username
     */
    public String getUserName() {
        return userName;
    }

}
