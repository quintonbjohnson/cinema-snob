package comquintonjcinemasnob.github.cinemasnob;

/**
 * Created by Maxwell on 2/6/2016.
 * Includes functions for managing users on the system
 */
public interface UserManagement {
    void addUser(String name, String pass);
    User findUserById(String id);
}

