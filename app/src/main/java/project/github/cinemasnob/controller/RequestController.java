package project.github.cinemasnob.controller;


import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Controls request queue.
 */
public class RequestController extends Application {

    /**
     * TAG of the request.
     */
    private static final String TAG = RequestController.class.getSimpleName();

    /**
     * The RequestController.
     */
    private static RequestController instance;

    /**
     * Queue made for requests.
     */
    private static RequestQueue requestQueue;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * Gets the current instance of the application.
     * @return RequestController of the current instance
     */
    public static synchronized RequestController getInstance() {
        return instance;
    }

    /**
     * Get method that returns the request queue.
     * @return request queue for calling the API and returning JSON object
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds a request to the request queue.
     * @param req request to be added to queue
     * @param tag tag name for adding
     * @param <T> any type of request
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TAG);
        this.getRequestQueue().add(req);
    }

    /**
     * Adds a request to the request queue.
     * @param req request to be added to queue
     * @param <T> any type of request
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.getTag();
        this.getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests currently in the request queue.
     * @param tag the tag specified will have all requests removed with the tag
     */
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
