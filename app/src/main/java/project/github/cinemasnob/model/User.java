package project.github.cinemasnob.model;

/**
 * Represents a single user
 */
public class User {

    /**
     * username
     */
    private String pUserName;

    /**
     * password
     */
    private String pPassword;

    /**
     * email
     */
    private String pEmail;

    /**
     * major
     */
    private String pMajor;

    /**
     * ban status
     */
    private boolean pIsBanned;

    /**
     * current user
     */
    private static User pCurrentUser;

    /**
     * No-args constructor for User
     */
    public User() {

    }

    /**
     * Constructor for user class
     * @param name the name
     * @param password the password
     * @param email the email
     * @param major the major
     * @param isBanned the ban status
     */
    public User(String name, String password, String email, String major, boolean isBanned) {
        pUserName = name;
        pPassword = password;
        pEmail = email;
        pMajor = major;
        pIsBanned = isBanned;
    }

    /**
     * Getter method for password
     * @return User's password
     */
    public String getPassword() {
        return pPassword;
    }

    /**
     * Getter method for username
     * @return User's username
     */
    public String getUserName() {
        return pUserName;
    }

    /**
     * Getter method for email
     * @return User's email
     */
    public String getEmail() {
        return pEmail;
    }

    /**
     * Getter method for major
     * @return User's major
     */
    public String getMajor() {
        return pMajor;
    }

    /**
     * Getter method for checking if User is banned
     * @return if the User is banned
     */
    public boolean getBanStatus() {
        return pIsBanned;
    }

    /**
     * Gets the currentUser
     * @return the currentUser
     */
    public static User getCurrentUser() {
        return pCurrentUser;
    }

    /**
     * Sets the currentUser
     * @param user theCurrentUser
     */
    public static void setCurrentUser(User user) {
        pCurrentUser = user;
    }
}
