package bdtube.vumobile.com.bdtube.category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bdtube.vumobile.com.bdtube.Adapter.BanglaCinemaAdapter;
import bdtube.vumobile.com.bdtube.Adapter.BanglaSongAdapter;
import bdtube.vumobile.com.bdtube.Api.Config;
import bdtube.vumobile.com.bdtube.App.FAQDialog;
import bdtube.vumobile.com.bdtube.App.HelpDialog;
import bdtube.vumobile.com.bdtube.App.RecyclerTouchListener;
import bdtube.vumobile.com.bdtube.App.SubcriptionClass;
import bdtube.vumobile.com.bdtube.FaqActivity;
import bdtube.vumobile.com.bdtube.Model.BanglaCinemaSong;
import bdtube.vumobile.com.bdtube.Model.BanglaSong;
import bdtube.vumobile.com.bdtube.R;
import bdtube.vumobile.com.bdtube.SplashActivity;
import bdtube.vumobile.com.bdtube.VideoPreviewActivity;

public class BanglaMusicCat extends AppCompatActivity implements View.OnClickListener {


    private Intent i;

    private BanglaCinemaSong banglaCinemaSong;
    private BanglaSong banglaSong;

    private List<BanglaCinemaSong> banglaCinemaSongList = new ArrayList<>();
    private List<BanglaSong> banglaSongList = new ArrayList<>();

    private RecyclerView.Adapter adapterBanglaSong, adapterBanglaCinemaSong;

    private RecyclerView recycler_view_bangla_gan, recycler_view_bd_cinema_gan;

    private Toolbar toolbar;

    private ImageView btnBuddyLink, btnBdTubeLink, btnBanglaBeatsLink, btnAmarStickerLink, btnRateMeLink,
            btnHome, btnHelpSame, btnHelp;

    private TextView bdCinemaGanVideoMore, banglaGanVideoMore;

    private ProgressDialog progressDialog = null;

    private TextView txtFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangla_music_cat);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.jgj);
        getSupportActionBar().setTitle("");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        initUI();

        initRecyclerBanglaSong();
        parseBanglaSong();

        initRecyclerBanglaCinemaSong();
        parseBanglaCinemaSOng();

        recycler_view_bangla_gan.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_bangla_gan, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                BanglaSong newVideoClass = banglaSongList.get(position);
                SubcriptionClass.ContentCategoryCode = newVideoClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = newVideoClass.getContentCode();
                SubcriptionClass.ContentTitle = newVideoClass.getContentTitle();
                SubcriptionClass.ContentType = newVideoClass.getContentType();
                SubcriptionClass.physicalFileName = newVideoClass.getVideoURL();
                SubcriptionClass.artist = newVideoClass.getArtist();
                SubcriptionClass.ContentZedCode = newVideoClass.getContentZedCode();
                SubcriptionClass.totalLike = newVideoClass.getTotalLike();
                SubcriptionClass.totalView = newVideoClass.getTotalView();
                SubcriptionClass.imgUrl = newVideoClass.getImageUrl();
                SubcriptionClass.info = newVideoClass.getInfo();
                SubcriptionClass.genre = newVideoClass.getGenre();
                SubcriptionClass.duration = newVideoClass.getDuration();
                SubcriptionClass.isHD = newVideoClass.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "5DCA7C64-F342-434A-A934-750F37D74AEC";
                new SubcriptionClass(BanglaMusicCat.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_bd_cinema_gan.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_bd_cinema_gan, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                BanglaCinemaSong newVideoClass = banglaCinemaSongList.get(position);
                SubcriptionClass.ContentCategoryCode = newVideoClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = newVideoClass.getContentCode();
                SubcriptionClass.ContentTitle = newVideoClass.getContentTitle();
                SubcriptionClass.ContentType = newVideoClass.getContentType();
                SubcriptionClass.physicalFileName = newVideoClass.getVideoURL();
                SubcriptionClass.artist = newVideoClass.getArtist();
                SubcriptionClass.ContentZedCode = newVideoClass.getContentZedCode();
                SubcriptionClass.totalLike = newVideoClass.getTotalLike();
                SubcriptionClass.totalView = newVideoClass.getTotalView();
                SubcriptionClass.imgUrl = newVideoClass.getImageUrl();
                SubcriptionClass.info = newVideoClass.getInfo();
                SubcriptionClass.genre = newVideoClass.getGenre();
                SubcriptionClass.duration = newVideoClass.getDuration();
                SubcriptionClass.isHD = newVideoClass.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "BCN";
                new SubcriptionClass(BanglaMusicCat.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void parseBanglaCinemaSOng() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "BCN");
            js.put("PageTotal", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, Config.URL_NEW_VIDEO, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("JSON response", response.toString());
                hideDialog();
                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        banglaCinemaSong = new BanglaCinemaSong();
                        String TimeStamp = homeURL.getString("TimeStamp");
                        String ContentCode = homeURL.getString("ContentCode");
                        String ContentCategoryCode = homeURL.getString("ContentCategoryCode");
                        String ContentTitle = homeURL.getString("ContentTitle");
                        String PreviewURL = homeURL.getString("BigPreview");
                        String VideoURL = homeURL.getString("PhysicalFileName");
                        String ContentType = homeURL.getString("ContentType");
                        String Value = homeURL.getString("Value");
                        String Artist = homeURL.getString("Artist");
                        String ContentZedCode = homeURL.getString("ContentZedCode");
                        String totalLike = homeURL.getString("totalLike");
                        String totalView = homeURL.getString("totalView");
                        String imgUrl = homeURL.getString("imageUrl");
                        String info = homeURL.getString(Config.INFO);
                        String duration = homeURL.getString(Config.DURATION);
                        String genre = homeURL.getString(Config.GENRE);
                        String isHD = homeURL.getString(Config.isHd);

                        banglaCinemaSong.setContentCategoryCode(ContentCategoryCode);
                        banglaCinemaSong.setContentCode(ContentCode);
                        banglaCinemaSong.setTimeStamp(TimeStamp);
                        banglaCinemaSong.setContentTitle(ContentTitle);
                        banglaCinemaSong.setPreviewURL(PreviewURL);
                        banglaCinemaSong.setVideoURL(VideoURL);
                        banglaCinemaSong.setContentType(ContentType);
                        banglaCinemaSong.setValue(Value);
                        banglaCinemaSong.setArtist(Artist);
                        banglaCinemaSong.setContentZedCode(ContentZedCode);
                        banglaCinemaSong.setTotalLike(totalLike);
                        banglaCinemaSong.setTotalView(totalView);
                        banglaCinemaSong.setImageUrl(imgUrl);
                        banglaCinemaSong.setDuration(duration);
                        banglaCinemaSong.setInfo(info);
                        banglaCinemaSong.setGenre(genre);
                        banglaCinemaSong.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        banglaCinemaSongList.add(banglaCinemaSong);
                    }

                    recycler_view_bd_cinema_gan.setAdapter(adapterBanglaCinemaSong);
                    adapterBanglaCinemaSong.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "o" + error.getMessage());
            }
        });

        //StringRequest request = new StringRequest()

        RequestQueue requestQueue = Volley.newRequestQueue(BanglaMusicCat.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerBanglaCinemaSong() {
        adapterBanglaCinemaSong = new BanglaCinemaAdapter(BanglaMusicCat.this, banglaCinemaSongList);
        recycler_view_bd_cinema_gan = (RecyclerView) findViewById(R.id.recycler_view_bd_cinema_gan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_bd_cinema_gan.setLayoutManager(mLayoutManager);
        recycler_view_bd_cinema_gan.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseBanglaSong() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "5DCA7C64-F342-434A-A934-750F37D74AEC");
            js.put("PageTotal", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, Config.URL_NEW_VIDEO, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("JSON response", response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        banglaSong = new BanglaSong();
                        String TimeStamp = homeURL.getString("TimeStamp");
                        String ContentCode = homeURL.getString("ContentCode");
                        String ContentCategoryCode = homeURL.getString("ContentCategoryCode");
                        String ContentTitle = homeURL.getString("ContentTitle");
                        String PreviewURL = homeURL.getString("BigPreview");
                        String VideoURL = homeURL.getString("PhysicalFileName");
                        String ContentType = homeURL.getString("ContentType");
                        String Value = homeURL.getString("Value");
                        String Artist = homeURL.getString("Artist");
                        String ContentZedCode = homeURL.getString("ContentZedCode");
                        String totalLike = homeURL.getString("totalLike");
                        String totalView = homeURL.getString("totalView");
                        String imgUrl = homeURL.getString("imageUrl");
                        String info = homeURL.getString(Config.INFO);
                        String duration = homeURL.getString(Config.DURATION);
                        String genre = homeURL.getString(Config.GENRE);
                        String isHD = homeURL.getString(Config.isHd);

                        banglaSong.setContentCategoryCode(ContentCategoryCode);
                        banglaSong.setContentCode(ContentCode);
                        banglaSong.setTimeStamp(TimeStamp);
                        banglaSong.setContentTitle(ContentTitle);
                        banglaSong.setPreviewURL(PreviewURL);
                        banglaSong.setVideoURL(VideoURL);
                        banglaSong.setContentType(ContentType);
                        banglaSong.setValue(Value);
                        banglaSong.setArtist(Artist);
                        banglaSong.setContentZedCode(ContentZedCode);
                        banglaSong.setTotalLike(totalLike);
                        banglaSong.setTotalView(totalView);
                        banglaSong.setImageUrl(imgUrl);
                        banglaSong.setInfo(info);
                        banglaSong.setDuration(duration);
                        banglaSong.setGenre(genre);
                        banglaSong.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        banglaSongList.add(banglaSong);
                    }

                    recycler_view_bangla_gan.setAdapter(adapterBanglaSong);
                    adapterBanglaSong.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "o" + error.getMessage());
            }
        });

        //StringRequest request = new StringRequest()

        RequestQueue requestQueue = Volley.newRequestQueue(BanglaMusicCat.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerBanglaSong() {
        adapterBanglaSong = new BanglaSongAdapter(BanglaMusicCat.this, banglaSongList);
        recycler_view_bangla_gan = (RecyclerView) findViewById(R.id.recycler_view_bangla_gan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_bangla_gan.setLayoutManager(mLayoutManager);
        recycler_view_bangla_gan.setItemAnimator(new DefaultItemAnimator());
    }

    private void initUI() {

        txtFooter = (TextView) findViewById(R.id.txtFooter);

        txtFooter.setText("BDTube © "+String.valueOf(SplashActivity.year)+".All Rights Reserved.");

        progressDialog = new ProgressDialog(BanglaMusicCat.this);
        progressDialog.setMessage("অপেক্ষা করুন ...");
        progressDialog.show();

        btnHome = (ImageView) findViewById(R.id.btnHome);
        btnHelpSame = (ImageView) findViewById(R.id.btnHelpSame);
        btnHelp = (ImageView) findViewById(R.id.btnHelp);

        bdCinemaGanVideoMore = (TextView) findViewById(R.id.bdCinemaGanVideoMore);
        banglaGanVideoMore = (TextView) findViewById(R.id.banglaGanVideoMore);

        bdCinemaGanVideoMore.setOnClickListener(this);
        banglaGanVideoMore.setOnClickListener(this);

        btnBuddyLink = (ImageView) findViewById(R.id.btnBuddyLink);
        btnBdTubeLink = (ImageView) findViewById(R.id.btnBdTubeLink);
        btnBanglaBeatsLink = (ImageView) findViewById(R.id.btnBanglaBeatsLink);
        btnAmarStickerLink = (ImageView) findViewById(R.id.btnAmarStickerLink);
        btnRateMeLink = (ImageView) findViewById(R.id.btnRateMeLink);

        btnHome.setOnClickListener(this);
        btnHelpSame.setOnClickListener(this);
        btnHelp.setOnClickListener(this);

        btnBuddyLink.setOnClickListener(this);
        btnBdTubeLink.setOnClickListener(this);
        btnBanglaBeatsLink.setOnClickListener(this);
        btnAmarStickerLink.setOnClickListener(this);
        btnRateMeLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.banglaGanVideoMore:
                NewVideoActivity.catTitle = "বাংলা গান";
                NewVideoActivity.catCode = "5DCA7C64-F342-434A-A934-750F37D74AEC";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(BanglaMusicCat.this, NewVideoActivity.class));
                break;
            case R.id.bdCinemaGanVideoMore:
                NewVideoActivity.catTitle = "বাংলা সিনেমার গান";
                NewVideoActivity.catCode = "BCN";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(BanglaMusicCat.this, NewVideoActivity.class));
                break;
            case R.id.btnBuddyLink:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy&hl=en"));
                startActivity(i);
                break;
            case R.id.btnBdTubeLink:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=bdtube.vumobile.com.bdtube&hl=en"));
                startActivity(i);
                break;
            case R.id.btnBanglaBeatsLink:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.vumobile.banglabeats.global&hl=en"));
                startActivity(i);
                break;
            case R.id.btnAmarStickerLink:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.vumobile.amarsticker&hl=en"));
                startActivity(i);
                break;
            case R.id.btnRateMeLink:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=rate.vumobile.com.rateme&hl=en"));
                startActivity(i);
                break;

            case R.id.btnHome:
                finish();
                break;

            case R.id.btnHelpSame:
                HelpDialog helpDialog = new HelpDialog();
                helpDialog.Help(BanglaMusicCat.this);
                break;
            // faq
            case R.id.btnHelp:

                FAQDialog faqDialog = new FAQDialog();
                //faqDialog.FUCK(MainActivity.this);

                Log.d("MSISDNsss",SplashActivity.MSISDN);
                if (SplashActivity.MSISDN.startsWith("88018")){
                    Log.d("MSISDNsss","Robi");
                    FaqActivity.operator_type = "1";
                    startActivity(new Intent(BanglaMusicCat.this, FaqActivity.class));
                    //    faqDialog.FUCK(MainActivity.this, "1");
                }else if (SplashActivity.MSISDN.startsWith("88019")){
                    Log.d("MSISDNsss","BL");
                    FaqActivity.operator_type = "2";
                    startActivity(new Intent(BanglaMusicCat.this, FaqActivity.class));
                    //faqDialog.FUCK(MainActivity.this, "2");
                }else if (SplashActivity.MSISDN.startsWith("88015")){
                    Log.d("MSISDNsss","TT");
                    FaqActivity.operator_type = "3";
                    startActivity(new Intent(BanglaMusicCat.this, FaqActivity.class));
                    //faqDialog.FUCK(MainActivity.this, "3");
                }else if (SplashActivity.MSISDN.startsWith("88016")){
                    Log.d("MSISDNsss","Airtel");
                    FaqActivity.operator_type = "4";
                    startActivity(new Intent(BanglaMusicCat.this, FaqActivity.class));
                    //faqDialog.FUCK(MainActivity.this, "4");
                }else {
                    Log.d("MSISDNsss","wifi");
                    FaqActivity.operator_type = "wifi";
                    startActivity(new Intent(BanglaMusicCat.this, FaqActivity.class));
//                    faqDialog.FUCK(MainActivity.this, "wifi");
                }

                break;
        }
    }

    private void hideDialog(){

        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
