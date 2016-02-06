package comquintonjcinemasnob.github.cinemasnob;

/**
 * Created by Maxwell on 2/6/2016.
 * Provide methods for determine if a user can login
 */
public interface AuthenticationManagement {
    boolean handleLoginRequests(String name, String pass);
}
