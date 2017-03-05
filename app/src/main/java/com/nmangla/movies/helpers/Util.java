package com.nmangla.movies.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

public class Util {

    public static final String TAG = "movies";

    // These parameters can be modified to vary service call results
    public static final String API_KEY = "86f69170a69368450382c9092476d828";
    public static final String PRIMARY_RELEASE_DATE = "2016-12-31";
    public static final String SORT_BY = "release_date.desc";

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";
    private static final String IMAGE_SIZE_SMALL = "/w500";
    private static final String IMAGE_SIZE_BIG = "/w780";

    // Check internet connectivity
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    // Show snack bar
    public static void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    // Get small size image for movie listing
    public static String getSmallImage(String imagePath) {
        return IMAGE_BASE_URL + IMAGE_SIZE_SMALL + imagePath;
    }

    // Get big size image for particular movie description
    public static String getBigImage(String imagePath) {
        return IMAGE_BASE_URL + IMAGE_SIZE_BIG + imagePath;
    }
}
