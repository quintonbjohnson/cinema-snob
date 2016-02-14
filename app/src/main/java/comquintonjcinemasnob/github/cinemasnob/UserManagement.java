package comquintonjcinemasnob.github.cinemasnob;

/**
 * Created by Maxwell on 2/6/2016.
 * Includes functions for managing users on the system
 */
public interface UserManagement {
    /**
     * Adds a new user to the database
     * @param name Username
     * @param pass Password
     * @param email Email
     */
    void addUser(String name, String pass, String email);

    /**
     * Finds the user by username
     * @param id Username
     * @return User being searched for
     */
    User findUserById(String id);
}

