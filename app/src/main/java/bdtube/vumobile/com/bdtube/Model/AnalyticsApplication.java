package bdtube.vumobile.com.bdtube.Model;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import bdtube.vumobile.com.bdtube.R;

/**
 * Created by toukirul on 15/3/2018.
 */

public class AnalyticsApplication extends Application{

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        sAnalytics = GoogleAnalytics.getInstance(this);
        sAnalytics.setLocalDispatchPeriod(1800);

        sTracker = sAnalytics.newTracker("UA-115743678-1"); // Replace with actual tracker id
        sTracker.enableExceptionReporting(true);
        sTracker.enableAdvertisingIdCollection(true);
        sTracker.enableAutoActivityTracking(true);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }

}
