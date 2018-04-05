package bdtube.vumobile.com.bdtube.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;
import android.webkit.WebView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IT-10 on 9/14/2017.
 */

public class PostbackOperation {

    // campaign download tracking

    /**
     * NID: Random value (I used date with millisecond)
     * CID: 355958A040A555DE3
     * GID: Unique value // device id
     * Sample url: http://rtb.adplay-mobile.com/postback?nid=123&campaign_id=355958A040A555DE3&gid=123456789
     */
    static String nId, campaignId, android_id, campaignUrl;

    private static String SHARED_PREF_KEY = "firstTimeOpen";

    public static void makePostbackOperation(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("CampaignSp", Context.MODE_PRIVATE);
        if (sharedPreferences.contains(SHARED_PREF_KEY)) {
            Log.d("ttt", "makePostbackOperation: " + "already done");
        } else {
            sharedPreferences.edit().putBoolean(SHARED_PREF_KEY, true).apply();
            // nid
            nId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            // campaign id
            campaignId = "530059BA4799375D2";
            // android id (gid)
            android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            campaignUrl = "http://rtb.adplay-mobile.com/postback?nid=" + nId + "&campaign_id=" + campaignId + "&gid=" + android_id + "";
            Log.d("ttt", "makePostbackOperation: " + campaignUrl);
            // webView to hit url
            WebView webView = new WebView(context);
            webView.loadUrl(campaignUrl);
        }
    }


}
