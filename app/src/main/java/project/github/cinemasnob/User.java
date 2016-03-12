package project.github.cinemasnob;

/**
 * Represents a single user
 */
public class User {

    private String userName;
    private String password;
    private String email;
    private String major;
    private boolean isBanned;
    private static User currentUser;

    /**
     * No-args constructor for User
     */
    public User() {

    }

    /**
     * Instantiates the User object
     * @param name Username
     * @param password Password
     *
     * @param major Major
     */
    public User(String name, String password, String email, String major, boolean isBanned) {
        this.userName = name;
        this.password = password;
        this.email = email;
        this.major = major;
        this.isBanned = isBanned;
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
     * Getter method for email
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter method for major
     * @return User's major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Getter method for checking if User is banned
     * @return if the User is banned
     */
    public boolean getBanStatus() {
        return isBanned;
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
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
