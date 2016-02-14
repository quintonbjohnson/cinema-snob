package comquintonjcinemasnob.github.cinemasnob;

/**
 * Created by Maxwell on 2/6/2016.
 * Provide methods for determine if a user can login
 */
public interface AuthenticationManagement {
    /**
     * Decides whether or not request to login is valid
     * @param name Username
     * @param pass Password
     * @return True/False if request is valid or not
     */
    boolean handleLoginRequests(String name, String pass);
}
