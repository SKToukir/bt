package bdtube.vumobile.com.bdtube.Model;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by toukirul on 27/3/2018.
 */

public class SharedPref {

    public static final String MY_PREFS_NAME = "my_pref";
    public static final String KEY = "accept_terms";
    private Context mContext;

    public SharedPref(Context context) {
        this.mContext = context;
    }

    public void acceptTermsAndCond(boolean acceptOrNot) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(KEY, acceptOrNot);
        editor.apply();
    }

    public boolean checkIsAcceptTermsOrNot() {
        SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        boolean isAccept = prefs.getBoolean(KEY, false);

        return isAccept;
    }
}
