package com.androidcodechallenge.tmdbexplorer.utilities;

import android.util.Log;

import timber.log.Timber;

/**
 * Purpose of this class is used to show log in Huawei mobiles
 * Created by devcrew on 22/03/2018.
 */

public class HuaweiTree extends Timber.DebugTree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG)
            priority = Log.INFO;
        super.log(priority, tag, message, t);
    }
}
