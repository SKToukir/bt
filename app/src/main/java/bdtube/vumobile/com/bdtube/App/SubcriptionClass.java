package bdtube.vumobile.com.bdtube.App;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bdtube.vumobile.com.bdtube.Api.Config;
import bdtube.vumobile.com.bdtube.R;
import bdtube.vumobile.com.bdtube.SplashActivity;
import bdtube.vumobile.com.bdtube.VideoPreviewActivity;

/**
 * Created by toukirul on 2/10/2017.
 */

public class SubcriptionClass {

    public static String ContentCategoryCode = "", ContentCode = "", ContentTitle = "", ContentType = "", physicalFileName = "", artist = "", ContentZedCode = "",
            totalLike = "", totalView = "", imgUrl = "", relatedContentUrl = "", relatedCatCode = "", duration = "", info = "", genre = "", isHD = "";

    public static String AppsVersion = "";
    public static String webVersion = "";

    private Context mCOntext;

    public SubcriptionClass(Context context) {
        this.mCOntext = context;
    }

    public void checkSubscription() {
        ChargingJsonArrayRequestVersion(mCOntext, "http://www.vumobile.biz/bdtube/test.php");
    }

    public void ChargingJsonArrayRequestVersion(final Context context, String url) {

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG", response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject homeURL = (JSONObject) response
                                        .get(i);
                                String version = homeURL.getString("vesion");

                                webVersion = version;
                                Log.d("version", version);

                                getPackage(version);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());

                //hideProgressDialog();
            }
        });

        // Adding request to request queue RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        RequestQueue requestQueue = Volley.newRequestQueue(mCOntext);
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(req);
    }

    public void getPackage(String UpdateString) {


        try {
            PackageInfo pinfo;
            pinfo = mCOntext.getPackageManager().getPackageInfo(mCOntext.getPackageName(), 0);
            // Google play problem versionName can be define publicly line 858

            String versionName = "";
            versionName = pinfo.versionName;

            AppsVersion = versionName;
            Log.d("versionName package", versionName + " version web " + UpdateString);

            if (!versionName.equalsIgnoreCase(UpdateString)) {

                Update();

            } else {
                if (SplashActivity.MSISDN.equals("wifi")) {
                    goVideoPlayerPage();
                } else {
                    String msisdn = SplashActivity.MSISDN;
                    checkSub(Config.URL_CHECK_SUB, msisdn);
                }
            }
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public void Update() {


        final Dialog updateDialog = new Dialog(mCOntext, android.R.style.Theme_Translucent_NoTitleBar);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setContentView(R.layout.update_dialog_activity);

        TextView update_text = (TextView) updateDialog.findViewById(R.id.update_text);
        update_text.setText("Critical version update available! \n" +
                "Update NOW to continue using.");
        Button update_app = (Button) updateDialog.findViewById(R.id.update_app);
        ImageView img = (ImageView) updateDialog.findViewById(R.id.imageView1);

        update_app.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                updateDialog.dismiss();

                /**
                 * if this button is clicked, close current
                 * activity
                 */

                final String appPackageName = mCOntext.getPackageName();


                try {
                    mCOntext.startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id="
                                    + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    mCOntext.startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + appPackageName)));
                }


                //String url = "http://203.76.126.210/shaboxbuddy/All_AppUpdateLog.php?Email=" + new UserInfo().userEmail(mCOntext) + "&MNO=" + SplashActivity.MSISDN + "&AppName=bdtube&AppVersion=" + AppsVersion + "";
                String url = "https://wap.shabox.mobi/shaboxbuddy/All_AppUpdateLog.php?Email=" + new UserInfo().userEmail(mCOntext) + "&MNO=" + SplashActivity.MSISDN + "&AppName=bdtube&AppVersion=" + AppsVersion + "";
                WebView webView = new WebView(mCOntext);
                webView.loadUrl(url);
                Log.d("UpdateLog", url + appPackageName);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                updateDialog.dismiss();
            }

        });

        updateDialog.show();
    }

    private void checkSub(String urlCheckSub, final String msisdn) {
        JsonArrayRequest req = new JsonArrayRequest(urlCheckSub + msisdn,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG", response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object

                            JSONObject homeURL = (JSONObject) response
                                    .get(0);

                            String status = homeURL.getString("SubStatus");

                            doAction(status, msisdn);

                            Log.d("JSONdataSubbbbbbbbbb", status + "    MSISDN: +");


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        //  hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());

                //hideProgressDialog();
            }
        });

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(mCOntext);
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(req);
    }

    private void doAction(String status, String msisdn) {
        boolean isSubscriptionOp = subscriptionOperator(msisdn);
        Log.d("isSubscriptionOp", String.valueOf(isSubscriptionOp));

        if (isSubscriptionOp) {
            if (status.equalsIgnoreCase("No")) {
                if (msisdn.startsWith("88019")) {
                    subscribeUserForBLink(msisdn);
                } else if (msisdn.startsWith("88018") || msisdn.startsWith("88016")) {

                    showSubDialog(mCOntext, msisdn);
                }
            } else {
                goVideoPlayerPage();
            }
        } else {
            goVideoPlayerPage();
        }
    }

    private boolean subscriptionOperator(String msisdn) {
        if (msisdn.startsWith("88019") || msisdn.startsWith("88015") || msisdn.startsWith("88018") || msisdn.startsWith("88016")) {
            return true;
        } else {
            return false;
        }
    }

    private void showSubDialog(Context applicationContext, final String msisdn) {
        final Dialog dialogMNO = new Dialog(applicationContext,
                R.style.MyDialog);
        dialogMNO.setContentView(R.layout.robi_charging_dialog1);
        dialogMNO.setCancelable(true);
        final ImageButton btnYes = (ImageButton) dialogMNO.findViewById(R.id.btnYes);
        final ImageButton btnCancel = (ImageButton) dialogMNO.findViewById(R.id.btnCancelSub);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMNO.dismiss();
                dialogConfirm(mCOntext, msisdn);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMNO.dismiss();
            }
        });

        // use this condition to solve crash.Beacuse sometimes app can not show dialog
        if (!((Activity) applicationContext).isFinishing()) {
            //show dialog
            dialogMNO.show();
        }
    }

    private void dialogConfirm(final Context context, final String msisdn) {

        final Dialog dialogConfirm = new Dialog(context,
                R.style.MyDialog);
        dialogConfirm.setContentView(R.layout.subscrib_dialog);
        dialogConfirm.setCancelable(true);
        final Button btnOk = (Button) dialogConfirm.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirm.dismiss();

                // this method is for other operator
                subscribeUser(msisdn, context);

            }
        });

        dialogConfirm.show();
    }

    private void subscribeUserForBLink(String msisdn) {
        Log.d("Banglalink", "in subscribeUserForBLink() method line-331");

        final String subsUrl = "http://wap.shabox.mobi/subscriptionapi/api/Charge/SubscriptionUrl?MSISDN=" + msisdn + "&type=BDTUBE";
        final Dialog dialogMNO = new Dialog(mCOntext,
                R.style.MyDialog);
        dialogMNO.setContentView(R.layout.subscription_dialog_bl);
        dialogMNO.setCancelable(false);

        setUserLogSubscription();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, subsUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String viewSubUrl = response.getString("Urls");
                    Log.d("Responsees", viewSubUrl);
                    if (!viewSubUrl.isEmpty() && viewSubUrl != null) {
                        final WebView webView = (WebView) dialogMNO.findViewById(R.id.webviewSubcription);
                        webView.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                Log.d("working", url);

                                if (url != null) {
                                    Log.d("URL: ", view.getUrl());
                                    // for robi
                                    if (url.contains("confirm")) {
                                        Log.d("working", "1");
                                        dialogMNO.dismiss();
                                        //TODO
                                     // for bangla link
                                    }else if (url.contains("http://bdtube.mobi/?msisdn=")) {
                                        Log.d("working2", "2");
                                        dialogMNO.dismiss();

                                    }
                                }

                            }
                        });
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.loadUrl(viewSubUrl);
                        Log.d("WEBURL", webView.getUrl());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mCOntext);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);

        dialogMNO.show();

    }

    private void setUserLogSubscription() {

        Log.d("UserInfos", SplashActivity.DEVICE_ID+"\n"
                + SplashActivity.MANUFACTURE+"\n"
                + SplashActivity.DEVICE_NAME+"\n"
                + SplashActivity.DEVICE_MODEL+"\n"
                + SplashActivity.MSISDN);


        Map<String, String> params = new HashMap();
        params.put("MSISDN", SplashActivity.MSISDN);
        params.put("DeviceModel", SplashActivity.DEVICE_MODEL);
        params.put("DeviceName", SplashActivity.DEVICE_NAME);
        params.put("DeviceManufacture", SplashActivity.MANUFACTURE);
        params.put("Type", new UserInfo().userEmail(mCOntext));
        params.put("AppName", "BdTube");

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, "https://wap.shabox.mobi/bdnewapi/DataOther/Log", parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                Log.d("SuccessLog",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.d("SuccessLog",error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(mCOntext);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(jsonRequest);
    }



    private void subscribeUser(final String msisdn, final Context context) {

        // String URLSubscription = "http://wap.shabox.mobi/BDTubeAPI/RegAPI.aspx?type=DoSubs&msisdn=" + msisdn + "";
        String URLSubscription = "http://wap.shabox.mobi/robiBDtubeDigital/api/BDTubeDigitalSubscribe";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLSubscription,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("FromServer", response);

                        try {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            if (result.equals("Subscribed")) {
                                goVideoPlayerPage();
                                setUserLogSubscription();
                            } else {
                                Toast.makeText(context, "Subscription failed!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("FromServer", "" + error.getMessage());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                /*
                *  request flag = 1 means it is a chat request
                *  request flag = 2 means it is a video request
                * */

                Map<String, String> params = new HashMap<String, String>();
                params.put("MSISDN", msisdn);
                params.put("Amount", "2");
                params.put("HS_MOD", "gts");
                params.put("HS_MANUFAC", "gts");

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(mCOntext);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    private void goVideoPlayerPage() {
        VideoPreviewActivity.ContentType = ContentType;
        VideoPreviewActivity.physicalFileName = physicalFileName;
        VideoPreviewActivity.relatedCatCode = relatedCatCode;
        VideoPreviewActivity.ContentTitle = ContentTitle;
        VideoPreviewActivity.relatedContentUrl = relatedContentUrl;
        VideoPreviewActivity.imgUrl = imgUrl;
        VideoPreviewActivity.ContentCode = ContentCode;
        VideoPreviewActivity.totalLike = totalLike;
        VideoPreviewActivity.totalView = totalView;
        VideoPreviewActivity.duration = duration;
        VideoPreviewActivity.info = info;
        VideoPreviewActivity.genre = genre;
        VideoPreviewActivity.isHD = isHD;
        Intent i = new Intent(mCOntext, VideoPreviewActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCOntext.startActivity(i);
    }

    public void SuccessLog() {
        DisplayMetrics dms_ = new DisplayMetrics();
        String HS_DIM_ = dms_.widthPixels + "x" + dms_.heightPixels;
        UserInfo userInfo = new UserInfo();
        String HS_MOD_ = userInfo.deviceModel(mCOntext);
        String MANU_FAC = userInfo.deviceMANUFACTURER(mCOntext);
        String SuccessLog = "http://wap.shabox.mobi/BDTubeAPI/AccessSuccess.aspx?type=Success&msisdn=wifi&HS_MOD=" + HS_MOD_ + "&HS_MANUFAC=" + MANU_FAC + "&HS_DIM=" + HS_DIM_ + "&sMasterCat=free&content_code=" + ContentCode + "&ContentTitle=" + ContentTitle + "&sContentType=" + ContentType + "&ZedID=" + ContentZedCode + "&CategoryCode=" + ContentCategoryCode + "&email=" + userInfo.userEmail(mCOntext) + "";
        WebView webView = new WebView(mCOntext);
        webView.loadUrl(SuccessLog);
    }

}
