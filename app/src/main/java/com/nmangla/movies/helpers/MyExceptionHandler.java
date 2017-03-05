package com.nmangla.movies.helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nmangla.movies.activities.HomeActivity;

import java.io.PrintWriter;
import java.io.StringWriter;

// Gracefully handle all exceptions
public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Context mContext;

    public MyExceptionHandler(Context context) {
        mContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));

        Log.e(Util.TAG, stackTrace.toString());

        Intent intent = new Intent(mContext, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);

        System.exit(0);
    }
}

