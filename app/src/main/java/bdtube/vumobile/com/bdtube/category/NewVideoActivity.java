package bdtube.vumobile.com.bdtube.category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bdtube.vumobile.com.bdtube.Adapter.NewVideoAdapter;
import bdtube.vumobile.com.bdtube.Api.Config;
import bdtube.vumobile.com.bdtube.App.RecyclerTouchListener;
import bdtube.vumobile.com.bdtube.App.SubcriptionClass;
import bdtube.vumobile.com.bdtube.Model.NewVideo;
import bdtube.vumobile.com.bdtube.R;
import bdtube.vumobile.com.bdtube.SplashActivity;
import bdtube.vumobile.com.bdtube.VideoPreviewActivity;

public class NewVideoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressDialog progressDialog = null;

    public static boolean isFav = false;

    int it = 1;

    private int c;

    private TextView txtCatTitle;

    public static String catCode="", url="", catTitle = "", isHd = "";

    private String msisdn;

    private Intent i;

    private NewVideo newVideo;

    private List<NewVideo> newVideoList = new ArrayList<>();

    private RecyclerView.Adapter adapter;

    private RecyclerView recycler_view_new_video_cat;

    private Toolbar toolbar;

    private ProgressDialog pDialog;

    private SwipeRefreshLayout swipeRefreshLayout;

    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_video);

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

        initRecycler();


        recycler_view_new_video_cat.addOnItemTouchListener(new RecyclerTouchListener(this, recycler_view_new_video_cat, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                NewVideo newVideoClass = newVideoList.get(position);
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
                SubcriptionClass.duration = newVideoClass.getDuration();
                SubcriptionClass.info = newVideoClass.getInfo();
                SubcriptionClass.genre = newVideoClass.getGenre();
                SubcriptionClass.isHD = newVideoClass.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = catCode;
                new SubcriptionClass(NewVideoActivity.this).checkSubscription();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(true);

                if (!isFav){
                    Log.d("CallRefresh","parseData");
                    parseData(1);
                }else {
                    catCode = "NV";
                    Log.d("CallRefresh","parseFav");
                    parseFavourate(1,SplashActivity.MSISDN);
                }

            }
        });

        recycler_view_new_video_cat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy>0){
                    c = mLayoutManager.findLastVisibleItemPosition()+1;
                    if (c==newVideoList.size() && !swipeRefreshLayout.isRefreshing()){
                        if (!isFav){
                            Log.d("CallRefresh","parseData on not fav"+ String.valueOf(isFav));
                            it = it+1;
                            parseData(it);
                        }else {
                            catCode = "NV";
                            Log.d("CallRefresh","parseFav on");
                            it = it+1;
                            parseFavourate(it,SplashActivity.MSISDN);
                        }
                    }
                }
            }
        });
    }

    private void parseData(final int items) {

        swipeRefreshLayout.setRefreshing(true);

        adapter.notifyDataSetChanged();

        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", catCode);
            js.put("PageTotal",items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("JSONresponse", url+items);
                hideDialog();
                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        newVideo = new NewVideo();
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
                        String isHd = homeURL.getString(Config.isHd);

                        newVideo.setContentCategoryCode(ContentCategoryCode);
                        newVideo.setContentCode(ContentCode);
                        newVideo.setTimeStamp(TimeStamp);
                        newVideo.setContentTitle(ContentTitle);
                        newVideo.setPreviewURL(PreviewURL);
                        newVideo.setVideoURL(VideoURL);
                        newVideo.setContentType(ContentType);
                        newVideo.setValue(Value);
                        newVideo.setArtist(Artist);
                        newVideo.setContentZedCode(ContentZedCode);
                        newVideo.setTotalLike(totalLike);
                        newVideo.setTotalView(totalView);
                        newVideo.setImageUrl(imgUrl);
                        newVideo.setInfo(info);
                        newVideo.setDuration(duration);
                        newVideo.setGenre(genre);
                        newVideo.setIsHd(isHd);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        newVideoList.add(newVideo);
                    }


                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "o"+error.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //StringRequest request = new StringRequest()

        RequestQueue requestQueue = Volley.newRequestQueue(NewVideoActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void parseFavourate(final int items,String msisdn){

        isFav = false;

        Log.d("JSONresponse", String.valueOf(items));
        Log.d("JSONresponse", msisdn);
        swipeRefreshLayout.setRefreshing(true);

        adapter.notifyDataSetChanged();

        JSONObject js = new JSONObject();
        try {
            js.put("MSISDN", msisdn);
            js.put("PageTotal",2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.URL_FAVOURATE,requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("JSONresponse", response.toString());
                Log.d("JSONresponse", url+items);
                hideDialog();
                try {
                    JSONArray array = response.getJSONArray("result");

                    for (int i = 0; i < array.length(); i++){

                        JSONObject homeURL = (JSONObject) array
                                .get(i);
                        newVideo = new NewVideo();
                        String TimeStamp = homeURL.getString("TimeStamp");
                        String ContentCode = homeURL.getString("ContentCode");
                        String ContentCategoryCode = homeURL.getString("ContentCategoryCode");
                        String ContentTitle = homeURL.getString("ContentTitle");
                        String PreviewURL = homeURL.getString("BigPreview");
                        String VideoURL = homeURL.getString("physicalfilename");
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
                        String isHd = homeURL.getString(Config.isHd);

                        newVideo.setContentCategoryCode(ContentCategoryCode);
                        newVideo.setContentCode(ContentCode);
                        newVideo.setTimeStamp(TimeStamp);
                        newVideo.setContentTitle(ContentTitle);
                        newVideo.setPreviewURL(PreviewURL);
                        newVideo.setVideoURL(VideoURL);
                        newVideo.setContentType(ContentType);
                        newVideo.setValue(Value);
                        newVideo.setArtist(Artist);
                        newVideo.setContentZedCode(ContentZedCode);
                        newVideo.setTotalLike(totalLike);
                        newVideo.setTotalView(totalView);
                        newVideo.setImageUrl(imgUrl);
                        newVideo.setInfo(info);
                        newVideo.setDuration(duration);
                        newVideo.setGenre(genre);
                        newVideo.setIsHd(isHd);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        newVideoList.add(newVideo);

                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //StringRequest request = new StringRequest()

        RequestQueue requestQueue = Volley.newRequestQueue(NewVideoActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecycler() {
        adapter = new NewVideoAdapter(NewVideoActivity.this,newVideoList);
        recycler_view_new_video_cat = (RecyclerView) findViewById(R.id.recycler_view_new_video_cat);
        recycler_view_new_video_cat.setAdapter(adapter);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recycler_view_new_video_cat.setLayoutManager(mLayoutManager);
        recycler_view_new_video_cat.setItemAnimator(new DefaultItemAnimator());
    }

    private void initUI() {

        progressDialog = new ProgressDialog(NewVideoActivity.this);
        progressDialog.setMessage("অপেক্ষা করুন ...");
        progressDialog.show();

        msisdn = SplashActivity.MSISDN;

        txtCatTitle = (TextView) findViewById(R.id.txtCatTitle);

        txtCatTitle.setText(catTitle);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_cat_wise);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);


//        if (!isFav){
//
//            parseData(1);
//        }else {
//            parseFavourate(1,SplashActivity.MSISDN);
//        }
    }

    private void hideDialog(){

        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
