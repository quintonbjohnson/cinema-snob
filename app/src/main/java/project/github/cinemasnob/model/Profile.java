package project.github.cinemasnob.model;

/**
 * Represents a single Profile
 */
public class Profile {

    private final String interests;
    private final String major;
    private final String userName;

    /**
     * Instantiates the Profile object
     * @param n userName
     * @param m major
     * @param i interests
     */
    public Profile(String n, String m, String i) {
        userName = n;
        major = m;
        interests = i;
    }

    /**
     * Getter method for major
     * @return User's major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Getter method for interests
     * @return User's interests
     */
    public String getInterests() {
        return interests;
    }

    /**
     * getter method for username
     * @return User's username
     */
    public String getUsername() {
        return userName;
    }
}
