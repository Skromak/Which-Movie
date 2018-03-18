package com.project.skromak.whichmovie;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by SamMcK on 18/03/2018.
 */

public class ClientPlayer extends Activity {
    public static Handler UIHandler;

    static
    {
        UIHandler = new Handler(Looper.getMainLooper());
    }
    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }
}
