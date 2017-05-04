package tj.com.mytest.application;

import android.app.Application;

/**
 * Created by Jun on 17/5/4.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static MyApplication getInstance() {
        return mApplication;
    }
}
