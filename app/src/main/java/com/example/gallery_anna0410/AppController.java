package com.example.gallery_anna0410;

/**
 * Initialize all the volley core object
 * Library volley controls the processing and caching of network requests
 *
 * Volley automatically compiles all network requests.
 * Volley will take over all the network requests of your application to execute them to retrieve the response or image from the websites.
 * Volley provides transparent disk caching and in-memory caching.
 * Volley provides a powerful API for canceling a request. You can cancel a single request or set multiple requests to cancel.
 * Volley provides powerful change capabilities.
 * Volley provides debugging and tracing tools.
 **/

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
}
