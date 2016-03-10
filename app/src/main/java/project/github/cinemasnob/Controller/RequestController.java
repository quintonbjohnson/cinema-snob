package project.github.cinemasnob.Controller;


import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Controls request queue
 */
public class RequestController extends Application {
    public static final String TAG = RequestController.class.getSimpleName();
    private RequestQueue rq;
    private static RequestController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * Gets the current instance of the application
     * @return RequestController of the current instance
     */
    public static synchronized RequestController getInstance() {
        return instance;
    }

    /**
     * Get method that returns the request queue
     * @return request queue for calling the API and returning JSON object
     */
    public RequestQueue getRequestQueue() {
        if (rq == null) {
            rq = Volley.newRequestQueue(getApplicationContext());
        }
        return rq;
    }

    /**
     * Adds a request to the request queue
     * @param req request to be added to queue
     * @param tag tag name for adding
     * @param <T> any type of request
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Adds a request to the request queue
     * @param req request to be added to queue
     * @param <T> any type of request
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.getTag();
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests currently in the request queue
     * @param tag the tag specified will have all requests under this tag removed
     */
    public void cancelPendingRequests(Object tag) {
        if (rq != null) {
            rq.cancelAll(tag);
        }
    }
}
