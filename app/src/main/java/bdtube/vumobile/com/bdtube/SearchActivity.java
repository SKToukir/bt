package bdtube.vumobile.com.bdtube;

import android.app.ProgressDialog;
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

import bdtube.vumobile.com.bdtube.Adapter.SearchAdapter;
import bdtube.vumobile.com.bdtube.Api.Config;
import bdtube.vumobile.com.bdtube.App.RecyclerTouchListener;
import bdtube.vumobile.com.bdtube.App.SubcriptionClass;
import bdtube.vumobile.com.bdtube.Model.SearchClass;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog pDialog;
    private RecyclerView recycler_view_search;
    private RecyclerView.Adapter adapter;
    private String searchContent;
    private Toolbar toolbar;

    int c;
    private GridLayoutManager mLayoutManager;

    private SearchClass searchClass;
    private List<SearchClass> searchClassList = new ArrayList<>();

    public static String search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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

        parseSearchQuery(1);


        recycler_view_search.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_search, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                SearchClass newVideoClass = searchClassList.get(position);
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
                SubcriptionClass.relatedCatCode = "NV";
                new SubcriptionClass(SearchActivity.this).checkSubscription();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//
//                swipeRefreshLayout.setRefreshing(true);
//                parseSearchQuery(1);
//            }
//        });

//        recycler_view_search.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (dy>0){
//                    c = mLayoutManager.findLastVisibleItemPosition()+1;
//                    if (c==searchClassList.size() && !swipeRefreshLayout.isRefreshing()){
//                        parseSearchQuery(c+1);
//                    }
//                }
//            }
//        });

    }

    private void parseSearchQuery(int items) {

        swipeRefreshLayout.setRefreshing(true);
        searchClassList.clear();
        adapter.notifyDataSetChanged();

        JSONObject js = new JSONObject();
        try {
            js.put("SearchText", search);
            js.put("total", "100");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, Config.URL_SEARCH, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("JSONresponse", response.toString());
                pDialog.dismiss();
                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        searchClass = new SearchClass();
                        String TimeStamp = homeURL.getString("TimeStamp");
                        String ContentCode = homeURL.getString("ContentCode");
                        String ContentCategoryCode = homeURL.getString("ContentCategoryCode");
                        String ContentTitle = homeURL.getString("ContentTitle");
                        String PreviewURL = homeURL.getString("BigPreview");
                        String VideoURL = homeURL.getString("physicalfilename");
                        String ContentType = homeURL.getString("type");
                        String Value = homeURL.getString("Value");
                        String Artist = homeURL.getString("Artist");
                        String ContentZedCode = homeURL.getString("zid");
                        String totalLike = homeURL.getString("totalLike");
                        String totalView = homeURL.getString("totalView");
                        String imgUrl = homeURL.getString("imageUrl");
                        String info = homeURL.getString(Config.INFO);
                        String duration = homeURL.getString(Config.DURATION);
                        String genre = homeURL.getString(Config.GENRE);
                        String isHD = homeURL.getString(Config.isHd);

                        searchClass.setContentCategoryCode(ContentCategoryCode);
                        searchClass.setContentCode(ContentCode);
                        searchClass.setTimeStamp(TimeStamp);
                        searchClass.setContentTitle(ContentTitle);
                        searchClass.setPreviewURL(PreviewURL);
                        searchClass.setVideoURL(VideoURL);
                        searchClass.setContentType(ContentType);
                        searchClass.setValue(Value);
                        searchClass.setArtist(Artist);
                        searchClass.setContentZedCode(ContentZedCode);
                        searchClass.setTotalLike(totalLike);
                        searchClass.setTotalView(totalView);
                        searchClass.setImageUrl(imgUrl);
                        searchClass.setInfo(info);
                        searchClass.setDuration(duration);
                        searchClass.setGenre(genre);
                        searchClass.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        searchClassList.add(searchClass);
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

        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);

    }

    private void initRecycler() {
        adapter = new SearchAdapter(SearchActivity.this,searchClassList);
        recycler_view_search = (RecyclerView) findViewById(R.id.recycler_view_search);
        recycler_view_search.setAdapter(adapter);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recycler_view_search.setLayoutManager(mLayoutManager);
        recycler_view_search.setItemAnimator(new DefaultItemAnimator());
    }

    private void initUI() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);
        pDialog.show();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_cat_wise);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        // parseSearchQuery(1);
    }
}
