package bdtube.vumobile.com.bdtube;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.downloader.Status;
import com.longtailvideo.jwplayer.JWPlayerFragment;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.events.ControlBarVisibilityEvent;
import com.longtailvideo.jwplayer.events.listeners.AdvertisingEvents;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bdtube.vumobile.com.bdtube.Adapter.RelatedItemsAdapter;
import bdtube.vumobile.com.bdtube.Api.Config;
import bdtube.vumobile.com.bdtube.App.DividerItemDecoration;
import bdtube.vumobile.com.bdtube.App.RecyclerTouchListener;
import bdtube.vumobile.com.bdtube.App.SubcriptionClass;
import bdtube.vumobile.com.bdtube.App.UserInfo;
import bdtube.vumobile.com.bdtube.Model.RelatedItemClass;
import bdtube.vumobile.com.bdtube.db.DBHandler;
import bdtube.vumobile.com.bdtube.db.DbModelClass;
import bdtube.vumobile.com.bdtube.notification.PHPRequest;

public class VideoPreviewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private DBHandler dbHandler;
    private DbModelClass dbModelClass;

    private ProgressDialog pDialog;
    File apkStorage;
    private boolean isDonloading = false;

    private String imageDownloadPath = null, videoDownloadPath = null, imageFileName;

    int it = 1;

    private String url;

    JWPlayerFragment fragment;

    private TextView videoTitle;

    private static final String SKIN_URL = "file:///android_asset/custom.css";

    public static boolean isNotific = false;

    public static String notification_vUrl = "";

    public String pushResponseUrl = "https://wap.shabox.mobi/gcmtrack/api/Gcm/PushResponse";
    //public String pushResponseUrl = "http://203.76.126.210/sticker_gcm_server_bdtube/push_response.php";

    PHPRequest phpRequest = new PHPRequest();
    private int c;
    private ImageView btnLike, btnFvrt;
    JWPlayerView playerView;

    public static int song_position;

    String VideoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/Video%20Clips/D800x600/Ami_Jare_by_DJ_Rahat_N_Reshmi.mp4";
    public static String ContentCategoryCode = "", ContentCode = "", ContentTitle = "", ContentType = "", physicalFileName = "", artist = "", ContentZedCode = "",
            totalLike = "", totalView = "", imgUrl = "", relatedContentUrl = "", relatedCatCode = "", duration = "", info = "", genre = "", isHD = "";

    private GridLayoutManager mLayoutManager;

    private List<RelatedItemClass> relatedItemClassList = new ArrayList<>();
    private RelatedItemClass relatedItemClass;

    private RecyclerView recycler_view_related_items;
    private RecyclerView.Adapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private TextView txtTotalLikes, txtTotalViews, txtDuration, txtInfo, txtGenre;

    private ImageButton imgDownloadOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);


        if (!SplashActivity.isOnline(this)) {
            finish();
            startActivity(new Intent(VideoPreviewActivity.this, SplashActivity.class));
        }

        initUI();

        try {

            VideoPreviewActivity.ContentTitle = getIntent().getExtras().getString("ContentTitle");
            VideoPreviewActivity.ContentType = getIntent().getExtras().getString("ContentType");
            VideoPreviewActivity.physicalFileName = getIntent().getExtras().getString("physicalFileName");
            VideoPreviewActivity.relatedCatCode = getIntent().getExtras().getString("relatedCatCode");
            VideoPreviewActivity.relatedContentUrl = getIntent().getExtras().getString("relatedContentUrl");
            VideoPreviewActivity.imgUrl = getIntent().getExtras().getString("imgUrl");
            VideoPreviewActivity.ContentCode = getIntent().getExtras().getString("ContentCode");
            VideoPreviewActivity.totalLike = getIntent().getExtras().getString("totalLike");
            VideoPreviewActivity.totalView = getIntent().getExtras().getString("totalView");
            VideoPreviewActivity.duration = getIntent().getExtras().getString("duration");
            VideoPreviewActivity.info = getIntent().getExtras().getString("info");
            VideoPreviewActivity.genre = getIntent().getExtras().getString("genre");

            String checkString = ContentTitle + "\n" + ContentType + "\n" + physicalFileName + "\n" + relatedCatCode + "\n" + relatedContentUrl + "\n" +
                    imgUrl + "\n" + ContentCode + "\n" + totalLike + "\n" + totalView + "\n" + duration + "\n" + info + "\n" + genre;
            Log.d("lllllllllllllll", checkString);

            txtGenre.setText(genre);
            txtInfo.setText(info);
            txtDuration.setText(duration);
            txtTotalViews.setText(totalView);
            txtTotalLikes.setText(totalLike);
            videoTitle.setText(ContentTitle);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setUpVideoUrl();

        initRecycler();

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                Log.d("CallThisLog", "158");
                parseRelatedItems(1);
            }
        });

        recycler_view_related_items.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) {
                    c = mLayoutManager.findLastVisibleItemPosition() + 1;
                    if (c == relatedItemClassList.size() && !swipeRefreshLayout.isRefreshing()) {
                        it = it + 1;
                        Log.d("CallThisLog", "171");
                        parseRelatedItems(it);
                    }
                }
            }
        });


        recycler_view_related_items.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_related_items, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                RelatedItemClass newVideoClass = relatedItemClassList.get(position);
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
                SubcriptionClass.relatedCatCode = relatedCatCode;
                new SubcriptionClass(VideoPreviewActivity.this).checkSubscription();
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        Log.d("isNotific", String.valueOf(isNotific));

        try {
            String k = getIntent().getExtras().getString("do_action");
            Log.d("isNotific", k);
            if (k.equalsIgnoreCase("play")) {
                new SendLaunchPushResponse().execute();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void showConnectionDialogPreview() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.wireless);

        alert.setTitle("Attention !");
        alert.setMessage("To Use This Application Please Connect To Internet");

        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Intent intent = new Intent(
                        Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                finish();
            }
        });

        alert.show();

    }

    private void parseRelatedItems(final int items) {
        Log.d("catcode", relatedCatCode);
        swipeRefreshLayout.setRefreshing(true);

        adapter.notifyDataSetChanged();

        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", relatedCatCode);
            js.put("PageTotal", items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, relatedContentUrl, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("JSONresponse", relatedCatCode + " " + relatedContentUrl + " " + items);

                try {
                    // Parsing json array response
                    // loop through each json object
                    Log.d("LOGGGGG", "Parse Complete");


                    swipeRefreshLayout.setVisibility(View.VISIBLE);

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        relatedItemClass = new RelatedItemClass();
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

                        relatedItemClass.setContentCategoryCode(ContentCategoryCode);
                        relatedItemClass.setContentCode(ContentCode);
                        relatedItemClass.setTimeStamp(TimeStamp);
                        relatedItemClass.setContentTitle(ContentTitle);
                        relatedItemClass.setPreviewURL(PreviewURL);
                        relatedItemClass.setVideoURL(VideoURL);
                        relatedItemClass.setContentType(ContentType);
                        relatedItemClass.setValue(Value);
                        relatedItemClass.setArtist(Artist);
                        relatedItemClass.setContentZedCode(ContentZedCode);
                        relatedItemClass.setTotalLike(totalLike);
                        relatedItemClass.setTotalView(totalView);
                        relatedItemClass.setImageUrl(imgUrl);
                        relatedItemClass.setInfo(info);
                        relatedItemClass.setGenre(genre);
                        relatedItemClass.setDuration(duration);
                        relatedItemClass.setIsHd(isHD);

                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);


                        relatedItemClassList.add(relatedItemClass);
                    }


                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "o" + error.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //StringRequest request = new StringRequest()

        RequestQueue requestQueue = Volley.newRequestQueue(VideoPreviewActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecycler() {
        adapter = new RelatedItemsAdapter(VideoPreviewActivity.this, relatedItemClassList);
        recycler_view_related_items = (RecyclerView) findViewById(R.id.recycler_view_related_items);
        recycler_view_related_items.setAdapter(adapter);
        recycler_view_related_items.setNestedScrollingEnabled(false);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recycler_view_related_items.setLayoutManager(mLayoutManager);
        recycler_view_related_items.setItemAnimator(new DefaultItemAnimator());
        recycler_view_related_items.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initUI() {

        getWindow().clearFlags(WindowManager
                .LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        videoTitle = (TextView) findViewById(R.id.video_preview_DetailsCategoryTitle);
        btnFvrt = (ImageView) findViewById(R.id.btnFvrt);
        btnLike = (ImageView) findViewById(R.id.btnLike);
        txtTotalLikes = (TextView) findViewById(R.id.txtTotalLikes);
        txtTotalViews = (TextView) findViewById(R.id.txtTotalViews);
        txtDuration = (TextView) findViewById(R.id.txtDuration);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtGenre = (TextView) findViewById(R.id.txtGenre);
        imgDownloadOffline = (ImageButton) findViewById(R.id.imgDownloadOffline);

        imgDownloadOffline.setOnClickListener(this);
        btnFvrt.setOnClickListener(this);
        btnLike.setOnClickListener(this);
        txtTotalViews.setOnClickListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_cat_wise);
        swipeRefreshLayout.setOnRefreshListener(this);

        videoTitle.setText(ContentTitle);
        videoTitle.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_text));

        txtGenre.setText(genre);
        txtInfo.setText(info);
        txtDuration.setText(duration);
        txtTotalViews.setText(totalView);
        txtTotalLikes.setText(totalLike);

        progressdialog = new ProgressDialog(this);

        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);
    }

    public void setUpVideoUrl() {

        if (ContentType.equalsIgnoreCase("FV")) {
            String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/" + physicalFileName + ".mp4";
            url = videoURL.replaceAll(" ", "%20");
            playVideo(url);
        } else {
            String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/Video Clips/D480x320/" + physicalFileName + ".mp4";
            url = videoURL.replaceAll(" ", "%20");
            playVideo(url);
        }


    }

    private void playVideo(String url) {

        Log.d("ContentImage", imgUrl + " url" + url);

        try {

            if (isHD.equals("1")) {
                //playerView = (JWPlayerView) findViewById(R.id.VideoView);
                PlayerConfig playerConfig = new PlayerConfig.Builder()
                        .file(url).controls(true).timeSliderAbove(true).image(imgUrl).logoFile("http://wap.shabox.mobi/clubz/images/hd.png")
                        .autostart(false)
                        .logoPosition(PlayerConfig.LOGO_POSITION_BOTTOM_RIGHT).logoHide(true)
                        .build();
                fragment = JWPlayerFragment.newInstance(playerConfig);
                fragment.setFullscreenOnDeviceRotate(true);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.containers, fragment);
                ft.commit();

            } else {

                Log.d("COntentImage", imgUrl + " url" + url);
                //playerView = (JWPlayerView) findViewById(R.id.VideoView);
                PlayerConfig playerConfig = new PlayerConfig.Builder()
                        .file(url).controls(true).timeSliderAbove(true).image(imgUrl)
                        .autostart(false)
                        .build();
                fragment = JWPlayerFragment.newInstance(playerConfig);
                fragment.setFullscreenOnDeviceRotate(true);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.containers, fragment);
                ft.commit();
//               playerView = (JWPlayerView) findViewById(R.id.VideoView);
//               PlayerConfig playerConfig = new PlayerConfig.Builder()
//                       .file(url).controls(true).skin(Skin.SEVEN)
//                       .timeSliderAbove(true).image(imgUrl)
//                       .build();
//               playerView = new JWPlayerView(VideoPreviewActivity.this, playerConfig);
//               ViewGroup jwPlayerViewContainer = (ViewGroup) findViewById(R.id.containers);
//               jwPlayerViewContainer.addView(playerView);
//               playerView.setMute(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("EXOERROR", "EXOERROR");
        }

    }

    int i = 0;

    @Override
    protected void onResume() {
        super.onResume();

        fragment.onResume();

        fragment.getPlayer().addOnBeforeCompleteListener(new AdvertisingEvents.OnBeforeCompleteListener() {
            @Override
            public void onBeforeComplete() {
                playNextSong();
            }
        });

        fragment.getPlayer().addOnControlBarVisibilityListener(new VideoPlayerEvents.OnControlBarVisibilityListener() {
            @Override
            public void onControlBarVisibilityChanged(ControlBarVisibilityEvent controlBarVisibilityEvent) {

                String state = String.valueOf(fragment.getPlayer().getState());

                if (state.equals("BUFFERING") && i == 0) {
                    i += 1;
                    int view = Integer.valueOf(totalView);
                    view += 1;
                    txtTotalViews.setText(String.valueOf(view));
                    try {
                        postFavView("wifi", "View", ContentCode);
                        //giveView("wifi","View",ContentCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("ViewCount", state + "--first viewcount " + String.valueOf(i));
                } else {
                    Log.d("ViewCount", state + "--already viewcount ");
                }

                Log.d("StatePlayer", state);

            }
        });

//        playerView.onResume();
    }

    private void playNextSong() {
        int track;
        Log.d("SongPosition", String.valueOf(song_position));
        if (song_position < 10) {
            track = song_position += 1;
        } else {
            track = 0;
        }
        Log.d("SongPosition", String.valueOf(track));


        RelatedItemClass newVideoClass = relatedItemClassList.get(track);
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
        SubcriptionClass.relatedCatCode = relatedCatCode;
        new SubcriptionClass(VideoPreviewActivity.this).checkSubscription();
        finish();

    }


    @Override
    public void onRefresh() {
        Log.d("CallThisLog", "165");
        parseRelatedItems(1);
    }

    private void postFavView(String finalMobileNumber, final String like, String contentCode) {

        Log.d("kkkkkkkk", finalMobileNumber + " " + like + " " + contentCode);
        String url = "http://android.vumobile.biz/bdnewapi/Data/LikeFav";
        JSONObject js = new JSONObject();
        try {
            js.put("MSISDN", finalMobileNumber);
            js.put("Type", like);
            js.put("ContentCode", contentCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            String result = response.getString("result");

                            if (result.equals("Success")) {

                                try {
                                    if (like.equals("View")) {
                                        int views = Integer.parseInt(totalView);
                                        views = views + 1;
                                        String Views = String.valueOf(views);
                                        totalView = String.valueOf(views);
                                        txtTotalViews.setText(Views);
                                    } else {
                                        Log.d("SAVEFORFAV", "SAVEFORFAV");
                                        Toast.makeText(getApplicationContext(), "Add as favourite!", Toast.LENGTH_LONG).show();
                                    }
                                } catch (NumberFormatException e) {


                                }
                            } else {
                                //Toast.makeText(getApplicationContext(),"Can not find your mobile number!",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(VideoPreviewActivity.this);
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(jsonObjReq);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnLike:
                int j = Integer.parseInt(totalLike);
                j++;
                totalLike = String.valueOf(j);
                txtTotalLikes.setText(totalLike);
                giveLike(SplashActivity.MSISDN, "Like", ContentCode, j);
                break;
            case R.id.btnFvrt:
                postFavView(SplashActivity.MSISDN, "Fav", ContentCode);
                break;
            case R.id.imgDownloadOffline:
                Toast.makeText(getApplicationContext(),"Download Start!",Toast.LENGTH_LONG).show();
                imgUrl = imgUrl.replace(" ","%20");
                downLoadImage(url,imgUrl);
//                if (url != null && imgUrl != null) {
//                    if (isDonloading == false) {
//                        isDonloading = true;
//                        downLoadImage(url,imgUrl);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Download on process!", Toast.LENGTH_LONG).show();
//                    }
//
//                }
                break;
        }
    }

    private void downLoadImage(final String url, String imgUrl) {

        Picasso.with(this)
                .load(imgUrl)
                .into(new Target() {
                          @Override
                          public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                              try {
                              ContextWrapper cw = new ContextWrapper(getApplicationContext());
                              File directory = cw.getDir("imagediroff", MODE_PRIVATE);

                              if (!directory.exists()) {
                                  directory.mkdir();
                              }

                              imageFileName = physicalFileName+".jpg";
                              File myPath = new File(directory, imageFileName);
                              FileOutputStream fos = null;

                              try {
                                  fos = new FileOutputStream(myPath);
                                  bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                              } catch (Exception e) {
                                  e.printStackTrace();
                              } finally {
                                  try {
                                      fos.close();
                                  } catch (Exception e) {
                                      e.printStackTrace();
                                  }
                              }
                              imageDownloadPath = directory.getPath();
                              Log.d("FilePath", "dd "+myPath.getPath());
                              //new DownloadTask(VideoPreviewActivity.this,url, imageDownloadPath, imageFileName,physicalFileName);
                              downloadVideoFile(url, imageDownloadPath, imageFileName,physicalFileName);
                          }

                          @Override
                          public void onBitmapFailed(Drawable errorDrawable) {
                          }

                          @Override
                          public void onPrepareLoad(Drawable placeHolderDrawable) {
                          }
                      }
                );

    }

    private void downloadVideoFile(String url, final String imageDownloadPath, final String imageFileName, String physicalFileName) {

        pDialog = new ProgressDialog(VideoPreviewActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(false);
        pDialog.show();

        apkStorage = new File(
                Environment.getExternalStorageDirectory() + "/"
                        + "offlinedir");
        //If File is not present create directory
        if (!apkStorage.exists()) {
            apkStorage.mkdir();
            Log.e("Response", "Directory Created.");
        }

        final String videoFileName = physicalFileName+".mp4";
        int downloadId = PRDownloader.download(url, apkStorage.getPath(), videoFileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        Log.d("Response",apkStorage.getPath()+"/"+videoFileName);

                        pDialog.setProgress((int)((progress.currentBytes*100)/progress.totalBytes));

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        videoDownloadPath = apkStorage.getPath()+"/"+videoFileName;
                        Toast.makeText(getApplicationContext(), "Complete!",Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                        saveDownloadContentDataIntoDB(imageFileName, imageDownloadPath, videoFileName, videoDownloadPath);
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });
        Status status = PRDownloader.getStatus(downloadId);
        Log.d("Response",status.toString());

    }

    private void saveDownloadContentDataIntoDB(String imageFileName, String imageDownloadPath, String videoFileName, String videoDownloadPath) {

        dbHandler = new DBHandler(this);
        dbModelClass = new DbModelClass();

        dbModelClass.setVideoTitle(ContentTitle);
        dbModelClass.setTotal_likes(totalLike);
        dbModelClass.setTotal_views(totalView);
        dbModelClass.setGenre(genre);
        dbModelClass.setInfo(info);
        dbModelClass.setDuration(duration);
        dbModelClass.setImageFileName(imageFileName);
        dbModelClass.setVideoFileName(videoFileName);
        dbModelClass.setVideoFilePath(videoDownloadPath);
        dbModelClass.setImageFilePath(imageDownloadPath);

        dbHandler.addItem(dbModelClass);

        setUserLogSubscription();

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
        params.put("Type", new UserInfo().userEmail(this));
        params.put("AppName", "BdTube/download offline video");

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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(jsonRequest);
    }



    private void giveLike(String msisdn, String like, String contentCode, int j) {

        Log.d("kkkkkkkk", msisdn + " " + like + " " + contentCode);
        String url = "http://android.vumobile.biz/bdnewapi/Data/LikeFav";
        JSONObject js = new JSONObject();
        try {
            js.put("MSISDN", msisdn);
            js.put("Type", like);
            js.put("ContentCode", contentCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            String result = response.getString("result");

                            if (result.equals("Success")) {

                                try {
                                    Log.d("Likeresponse", response.toString());
//                                    int likes = Integer.parseInt(totalLike);
//                                    likes = likes+1;
//                                    String Likes = String.valueOf(likes);
//                                    totalLike = String.valueOf(likes);
//                                    Log.d("Likeresponse", String.valueOf(likes));

                                } catch (NumberFormatException e) {


                                }
                            } else {
                                //Toast.makeText(getApplicationContext(),"Can not find your mobile number!",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("response", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(VideoPreviewActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);

    }

    private void giveFav(String msisdn, String fav, String contentCode) {


    }

    private class SendLaunchPushResponse extends AsyncTask<String, String, String> {


        UserInfo userinfo = new UserInfo();
        String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(VideoPreviewActivity.this);
        String HS_MOD_ = userinfo.deviceModel(VideoPreviewActivity.this);
        String user_email = userinfo.userEmail(VideoPreviewActivity.this);

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


            // detect MSISDN when notification launched
            // detect MSISDN when notification launched


        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", user_email));
            params.add(new BasicNameValuePair("action", "launch"));
            params.add(new BasicNameValuePair("handset_name", HS_MANUFAC_));
            params.add(new BasicNameValuePair("handset_model", HS_MOD_));
            params.add(new BasicNameValuePair("msisdn", SplashActivity.MSISDN));
            params.add(new BasicNameValuePair("app", "bt"));

            // getting JSON Object
            // Note that create product url accepts POST method
            phpRequest.makeHttpRequest(pushResponseUrl, "POST", params);
            Log.d("Toukirul", pushResponseUrl + "params: " + params);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }
    }

    @Override
    protected void onPause() {
        fragment.onPause();
        // playerView.onPause();
        super.onPause();
        Log.d("LOOOOG", "PAUSE");
//        playerView.destroySurface();
//        playerView.stop();
    }

    @Override
    protected void onDestroy() {
        fragment.onDestroyView();
        //playerView.destroySurface();
        super.onDestroy();
        Log.d("LOOOOG", "PAUSE");

        //unregisterReceiver(onComplete);

    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
////        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//
//            playerView.setFullscreen(true, true);
//
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//
//            playerView.setFullscreen(false, false);
//        }
//    }

    private ProgressDialog progressdialog;
    private int prog;
    private String FILE_PATH_TEMP, tempVideoPath;

    private class DownloadVideoFile extends AsyncTask<String, Integer, String> { // Params, Progress, Result

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog.show();
            progressdialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... urlAndName) {
            int count;
            try {
                URL url1 = new URL(urlAndName[0].trim().replaceAll(" ", "%20"));
                URLConnection connection = url1.openConnection();
                connection.connect();
                // This will be useful so that you can show a typical 0-100% progress bar
                int lengthOfFile = connection.getContentLength();

                // Make directory at on create
                InputStream input = new BufferedInputStream(url1.openStream());
                String fileName = urlAndName[1];
                if (!fileName.contains(".mp4")) {
                    fileName = fileName + ".mp4";
                }
                String path = FILE_PATH_TEMP+"/" + fileName;
                OutputStream output = new FileOutputStream(path);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publishing the progress....
                    prog = (int) (total * 100 / lengthOfFile);
                    output.write(data, 0, count);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressdialog.setProgress(prog);
                        }
                    });
                }

                output.flush();
                output.close();
                input.close();

                // Set file name to edit with video
                tempVideoPath = path;
                Log.d("TempFilePath", path+"\n"+tempVideoPath);
            } catch (Exception e) {
                e.printStackTrace();
                progressdialog.dismiss();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressdialog.dismiss();
        }
    }

}
