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

import bdtube.vumobile.com.bdtube.Adapter.BanglaMovieAdapter;
import bdtube.vumobile.com.bdtube.Adapter.BanglaMusicAdapter;
import bdtube.vumobile.com.bdtube.Adapter.BanglaNatokAdapter;
import bdtube.vumobile.com.bdtube.Adapter.BanglaTelefilmAdapter;
import bdtube.vumobile.com.bdtube.Api.Config;
import bdtube.vumobile.com.bdtube.App.FAQDialog;
import bdtube.vumobile.com.bdtube.App.HelpDialog;
import bdtube.vumobile.com.bdtube.App.RecyclerTouchListener;
import bdtube.vumobile.com.bdtube.App.SubcriptionClass;
import bdtube.vumobile.com.bdtube.FaqActivity;
import bdtube.vumobile.com.bdtube.Model.BanglaMovie;
import bdtube.vumobile.com.bdtube.Model.BanglaMusicVideo;
import bdtube.vumobile.com.bdtube.Model.BanglaNatok;
import bdtube.vumobile.com.bdtube.Model.BanglaTelifilm;
import bdtube.vumobile.com.bdtube.R;
import bdtube.vumobile.com.bdtube.SplashActivity;
import bdtube.vumobile.com.bdtube.VideoPreviewActivity;

public class HDVideoCat extends AppCompatActivity implements View.OnClickListener {

    private Intent i;

    private BanglaMusicVideo banglaMusicVideo;
    private BanglaNatok banglaNatok;
    private BanglaMovie banglaMovie;
    private BanglaTelifilm banglaTelifilm;

    private List<BanglaMusicVideo> musicVideoClassList = new ArrayList<>();
    private List<BanglaNatok> banglaNatokList = new ArrayList<>();
    private List<BanglaMovie> banglaMovieList = new ArrayList<>();
    private List<BanglaTelifilm> banglaTelifilmList = new ArrayList<>();

    private RecyclerView.Adapter adapterBDMusic, adapterBDNatok, adapterBDMovie, adapterBDTelefilm;

    private RecyclerView recycler_view_bdmusic_video, recycler_view_banglaNatok, recycler_view_bangla_movie, recycler_view_banglaTeleifilm;

    private TextView bdMusicVideoMore, banglaNatokMore, banglaMovieMore, banglaTeleifilmMore;

    private ImageView btnBuddyLink, btnBdTubeLink, btnBanglaBeatsLink, btnAmarStickerLink, btnRateMeLink,
            btnHome, btnHelpSame, btnHelp;

    private Toolbar toolbar;

    private ProgressDialog progressDialog = null;

    private TextView txtFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdvideo_cat);

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

        initRecyclerBanglaMusic();
        parseBanglaMusic();

        initRecyclerNatok();
        parseBanglaNatok();

        initRecyclerBanglaMovie();
        parseBanglaMovie();

        initRecyclerBanglaTelefilm();
        parseBanglaTelefilm();

        recycler_view_bdmusic_video.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_bdmusic_video, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                BanglaMusicVideo newVideoClass = musicVideoClassList.get(position);
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
                SubcriptionClass.relatedCatCode = "7B21FD2A-2D55-4953-8DF8-115FF38D8E32";
                new SubcriptionClass(HDVideoCat.this).checkSubscription();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_banglaNatok.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_banglaNatok, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                BanglaNatok newVideoClass = banglaNatokList.get(position);
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
                SubcriptionClass.relatedCatCode = "4781C5FB-0F16-4892-877D-F2F73DD4DE92";
                new SubcriptionClass(HDVideoCat.this).checkSubscription();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_bangla_movie.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_bangla_movie, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                BanglaMovie newVideoClass = banglaMovieList.get(position);
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
                SubcriptionClass.relatedCatCode = "B8957F67-501F-4D2A-A580-3D3DFFEB4828";
                new SubcriptionClass(HDVideoCat.this).checkSubscription();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_banglaTeleifilm.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_banglaTeleifilm, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                BanglaTelifilm newVideoClass = banglaTelifilmList.get(position);
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
                SubcriptionClass.relatedCatCode = "E4EF9CCE-E1E7-4C59-84D9-4642492D4F2C";
                new SubcriptionClass(HDVideoCat.this).checkSubscription();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void parseBanglaTelefilm() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "E4EF9CCE-E1E7-4C59-84D9-4642492D4F2C");
            js.put("PageTotal","1");
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
                    for (int i = 0; i < 9; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        banglaTelifilm = new BanglaTelifilm();
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


                        banglaTelifilm.setContentCategoryCode(ContentCategoryCode);
                        banglaTelifilm.setContentCode(ContentCode);
                        banglaTelifilm.setTimeStamp(TimeStamp);
                        banglaTelifilm.setContentTitle(ContentTitle);
                        banglaTelifilm.setPreviewURL(PreviewURL);
                        banglaTelifilm.setVideoURL(VideoURL);
                        banglaTelifilm.setContentType(ContentType);
                        banglaTelifilm.setValue(Value);
                        banglaTelifilm.setArtist(Artist);
                        banglaTelifilm.setContentZedCode(ContentZedCode);
                        banglaTelifilm.setTotalLike(totalLike);
                        banglaTelifilm.setTotalView(totalView);
                        banglaTelifilm.setImageUrl(imgUrl);
                        banglaTelifilm.setDuration(duration);
                        banglaTelifilm.setGenre(genre);
                        banglaTelifilm.setInfo(info);
                        banglaTelifilm.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        banglaTelifilmList.add(banglaTelifilm);
                    }

                    recycler_view_banglaTeleifilm.setAdapter(adapterBDTelefilm);
                    adapterBDTelefilm.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "o"+error.getMessage());
            }
        });

        //StringRequest request = new StringRequest()

        RequestQueue requestQueue = Volley.newRequestQueue(HDVideoCat.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerBanglaTelefilm() {
        adapterBDTelefilm = new BanglaTelefilmAdapter(HDVideoCat.this,banglaTelifilmList);
        recycler_view_banglaTeleifilm = (RecyclerView) findViewById(R.id.recycler_view_banglaTeleifilm);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_banglaTeleifilm.setLayoutManager(mLayoutManager);
        recycler_view_banglaTeleifilm.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseBanglaMovie() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "B8957F67-501F-4D2A-A580-3D3DFFEB4828");
            js.put("PageTotal","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, Config.URL_NEW_VIDEO, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("JSONresponseHD", response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        banglaMovie = new BanglaMovie();
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


                        banglaMovie.setContentCategoryCode(ContentCategoryCode);
                        banglaMovie.setContentCode(ContentCode);
                        banglaMovie.setTimeStamp(TimeStamp);
                        banglaMovie.setContentTitle(ContentTitle);
                        banglaMovie.setPreviewURL(PreviewURL);
                        banglaMovie.setVideoURL(VideoURL);
                        banglaMovie.setContentType(ContentType);
                        banglaMovie.setValue(Value);
                        banglaMovie.setArtist(Artist);
                        banglaMovie.setContentZedCode(ContentZedCode);
                        banglaMovie.setTotalLike(totalLike);
                        banglaMovie.setTotalView(totalView);
                        banglaMovie.setImageUrl(imgUrl);
                        banglaMovie.setDuration(duration);
                        banglaMovie.setGenre(genre);
                        banglaMovie.setInfo(info);
                        banglaMovie.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        banglaMovieList.add(banglaMovie);
                    }

                    recycler_view_bangla_movie.setAdapter(adapterBDMovie);
                    adapterBDMovie.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "o"+error.getMessage());
            }
        });

        //StringRequest request = new StringRequest()

        RequestQueue requestQueue = Volley.newRequestQueue(HDVideoCat.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerBanglaMovie() {
        adapterBDMovie = new BanglaMovieAdapter(HDVideoCat.this,banglaMovieList);
        recycler_view_bangla_movie = (RecyclerView) findViewById(R.id.recycler_view_bangla_movie);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_bangla_movie.setLayoutManager(mLayoutManager);
        recycler_view_bangla_movie.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseBanglaNatok() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "4781C5FB-0F16-4892-877D-F2F73DD4DE92");
            js.put("PageTotal","1");
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
                    for (int i = 0; i < 9; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        banglaNatok = new BanglaNatok();
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

                        banglaNatok.setContentCategoryCode(ContentCategoryCode);
                        banglaNatok.setContentCode(ContentCode);
                        banglaNatok.setTimeStamp(TimeStamp);
                        banglaNatok.setContentTitle(ContentTitle);
                        banglaNatok.setPreviewURL(PreviewURL);
                        banglaNatok.setVideoURL(VideoURL);
                        banglaNatok.setContentType(ContentType);
                        banglaNatok.setValue(Value);
                        banglaNatok.setArtist(Artist);
                        banglaNatok.setContentZedCode(ContentZedCode);
                        banglaNatok.setTotalLike(totalLike);
                        banglaNatok.setTotalView(totalView);
                        banglaNatok.setImageUrl(imgUrl);
                        banglaNatok.setGenre(genre);
                        banglaNatok.setInfo(info);
                        banglaNatok.setDuration(duration);
                        banglaNatok.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        banglaNatokList.add(banglaNatok);
                    }

                    recycler_view_banglaNatok.setAdapter(adapterBDNatok);
                    adapterBDNatok.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "o"+error.getMessage());
            }
        });

        //StringRequest request = new StringRequest()

        RequestQueue requestQueue = Volley.newRequestQueue(HDVideoCat.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerNatok() {
        adapterBDNatok = new BanglaNatokAdapter(HDVideoCat.this,banglaNatokList);
        recycler_view_banglaNatok = (RecyclerView) findViewById(R.id.recycler_view_banglaNatok);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_banglaNatok.setLayoutManager(mLayoutManager);
        recycler_view_banglaNatok.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseBanglaMusic() {

        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "7B21FD2A-2D55-4953-8DF8-115FF38D8E32");
            js.put("PageTotal","1");
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
                    for (int i = 0; i < 9; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        banglaMusicVideo = new BanglaMusicVideo();
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

                        banglaMusicVideo.setContentCategoryCode(ContentCategoryCode);
                        banglaMusicVideo.setContentCode(ContentCode);
                        banglaMusicVideo.setTimeStamp(TimeStamp);
                        banglaMusicVideo.setContentTitle(ContentTitle);
                        banglaMusicVideo.setPreviewURL(PreviewURL);
                        banglaMusicVideo.setVideoURL(VideoURL);
                        banglaMusicVideo.setContentType(ContentType);
                        banglaMusicVideo.setValue(Value);
                        banglaMusicVideo.setArtist(Artist);
                        banglaMusicVideo.setContentZedCode(ContentZedCode);
                        banglaMusicVideo.setTotalLike(totalLike);
                        banglaMusicVideo.setTotalView(totalView);
                        banglaMusicVideo.setImageUrl(imgUrl);
                        banglaMusicVideo.setInfo(info);
                        banglaMusicVideo.setGenre(genre);
                        banglaMusicVideo.setDuration(duration);
                        banglaMusicVideo.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        musicVideoClassList.add(banglaMusicVideo);
                    }

                    recycler_view_bdmusic_video.setAdapter(adapterBDMusic);
                    adapterBDMusic.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "o"+error.getMessage());
            }
        });

        //StringRequest request = new StringRequest()

        RequestQueue requestQueue = Volley.newRequestQueue(HDVideoCat.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);

    }

    private void initRecyclerBanglaMusic() {
        adapterBDMusic = new BanglaMusicAdapter(HDVideoCat.this,musicVideoClassList);
        recycler_view_bdmusic_video = (RecyclerView) findViewById(R.id.recycler_view_bdmusic_video);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_bdmusic_video.setLayoutManager(mLayoutManager);
        recycler_view_bdmusic_video.setItemAnimator(new DefaultItemAnimator());
    }

    private void initUI() {

        txtFooter = (TextView) findViewById(R.id.txtFooter);

        txtFooter.setText("BDTube © "+String.valueOf(SplashActivity.year)+".All Rights Reserved.");

        progressDialog = new ProgressDialog(HDVideoCat.this);
        progressDialog.setMessage("অপেক্ষা করুন ...");
        progressDialog.show();

        bdMusicVideoMore = (TextView) findViewById(R.id.bdMusicVideoMore);
        banglaNatokMore = (TextView) findViewById(R.id.banglaNatokMore);
        banglaMovieMore = (TextView) findViewById(R.id.banglaMovieMore);
        banglaTeleifilmMore = (TextView) findViewById(R.id.banglaTeleifilmMore);

        bdMusicVideoMore.setOnClickListener(this);
        banglaNatokMore.setOnClickListener(this);
        banglaMovieMore.setOnClickListener(this);
        banglaTeleifilmMore.setOnClickListener(this);

        btnHome = (ImageView) findViewById(R.id.btnHome);
        btnHelpSame = (ImageView) findViewById(R.id.btnHelpSame);
        btnHelp = (ImageView) findViewById(R.id.btnHelp);

        btnHome.setOnClickListener(this);
        btnHelpSame.setOnClickListener(this);
        btnHelp.setOnClickListener(this);

        btnBuddyLink = (ImageView) findViewById(R.id.btnBuddyLink);
        btnBdTubeLink = (ImageView) findViewById(R.id.btnBdTubeLink);
        btnBanglaBeatsLink = (ImageView) findViewById(R.id.btnBanglaBeatsLink);
        btnAmarStickerLink = (ImageView) findViewById(R.id.btnAmarStickerLink);
        btnRateMeLink = (ImageView) findViewById(R.id.btnRateMeLink);

        btnBuddyLink.setOnClickListener(this);
        btnBdTubeLink.setOnClickListener(this);
        btnBanglaBeatsLink.setOnClickListener(this);
        btnAmarStickerLink.setOnClickListener(this);
        btnRateMeLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.bdMusicVideoMore:
                NewVideoActivity.catTitle = "বাংলা মিউজিক ভিডিও ";
                NewVideoActivity.catCode = "7B21FD2A-2D55-4953-8DF8-115FF38D8E32";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(HDVideoCat.this, NewVideoActivity.class));
                break;
            case R.id.banglaNatokMore:
                NewVideoActivity.catTitle = "বাংলা নাটক ";
                NewVideoActivity.catCode = "4781C5FB-0F16-4892-877D-F2F73DD4DE92";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(HDVideoCat.this, NewVideoActivity.class));
                break;
            case R.id.banglaMovieMore:
                NewVideoActivity.catTitle = "বাংলা মুভি ";
                NewVideoActivity.catCode = "B8957F67-501F-4D2A-A580-3D3DFFEB4828";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(HDVideoCat.this, NewVideoActivity.class));
                break;
            case R.id.banglaTeleifilmMore:
                NewVideoActivity.catTitle = "বাংলা টেলিফ্লিম ";
                NewVideoActivity.catCode = "E4EF9CCE-E1E7-4C59-84D9-4642492D4F2C";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(HDVideoCat.this, NewVideoActivity.class));
                break;
            case R.id.btnBuddyLink:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy&hl=en"));
                startActivity(i);
                break;
            case R.id.btnBdTubeLink:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.vumobile.clubzed&hl=en"));
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
                helpDialog.Help(HDVideoCat.this);
                break;
            // faq
            case R.id.btnHelp:

                FAQDialog faqDialog = new FAQDialog();
                //faqDialog.FUCK(MainActivity.this);

                Log.d("MSISDNsss",SplashActivity.MSISDN);
                if (SplashActivity.MSISDN.startsWith("88018")){
                    Log.d("MSISDNsss","Robi");
                    FaqActivity.operator_type = "1";
                    startActivity(new Intent(HDVideoCat.this, FaqActivity.class));
                    //    faqDialog.FUCK(MainActivity.this, "1");
                }else if (SplashActivity.MSISDN.startsWith("88019")){
                    Log.d("MSISDNsss","BL");
                    FaqActivity.operator_type = "2";
                    startActivity(new Intent(HDVideoCat.this, FaqActivity.class));
                    //faqDialog.FUCK(MainActivity.this, "2");
                }else if (SplashActivity.MSISDN.startsWith("88015")){
                    Log.d("MSISDNsss","TT");
                    FaqActivity.operator_type = "3";
                    startActivity(new Intent(HDVideoCat.this, FaqActivity.class));
                    //faqDialog.FUCK(MainActivity.this, "3");
                }else if (SplashActivity.MSISDN.startsWith("88016")){
                    Log.d("MSISDNsss","Airtel");
                    FaqActivity.operator_type = "4";
                    startActivity(new Intent(HDVideoCat.this, FaqActivity.class));
                    //faqDialog.FUCK(MainActivity.this, "4");
                }else {
                    Log.d("MSISDNsss","wifi");
                    FaqActivity.operator_type = "wifi";
                    startActivity(new Intent(HDVideoCat.this, FaqActivity.class));
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
