package comquintonjcinemasnob.github.cinemasnob;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by Maxwell on 2/6/2016.
 * Class that handles adding users to the system
 */
public class UserManager  implements AuthenticationManagement, UserManagement{

    //Map of users in the system
    private static Map<String, User> users = new HashMap<>();
    /**
     * Finds the user by username
     * @param id Username
     * @return User being searched for
     */
    public User findUserById(String id) {
        return users.get(id);
    }

    /**
     * Adds a new user to the database
     * @param name Username
     * @param pass Password
     * @param email Email
     */
    public void addUser(String name, String pass, String email) {
        User user = new User(name, pass, email);
        users.put(name, user);
    }

    /**
     * Checks if user entered a valid username and password
     * @param name Username
     * @param pass Password
     * @return True/False whether or not username/password was valid
     */
    public boolean handleLoginRequests(String name, String pass) {
        User u = findUserById(name);
        if (u == null) {
            return false;
        }
        return u.checkPassword(pass);
    }

}
