package project.github.cinemasnob;


import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Brandon on 2/20/2016.
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

    public static synchronized RequestController getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (rq == null) {
            rq = Volley.newRequestQueue(getApplicationContext());
        }
        return rq;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.getTag();
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (rq != null) {
            rq.cancelAll(tag);
        }
    }
}
