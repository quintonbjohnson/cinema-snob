package CS2340.github.cinemasnob;

/**
 * Represents a single user
 */
public class User {

    private String userName;
    private String password;
    private static User currentUser;

    /**
     * Instantiates the User object
     * @param n Username
     * @param p Password
     * @param e Email
     */
    public User(String n, String p, String e ) {
        userName = n;
        password = p;
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

    /**
     * Gets the currentUser
     * @return the currentUser
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the currentUser
     * @param user theCurrentUser
     */
    static void setCurrentUser(User user) {
        currentUser = user;
    }
}
