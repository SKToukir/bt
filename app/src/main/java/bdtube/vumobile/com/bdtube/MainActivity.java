package bdtube.vumobile.com.bdtube;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bdtube.vumobile.com.bdtube.Adapter.ComedyAdapter;
import bdtube.vumobile.com.bdtube.Adapter.FitnessAdapter;
import bdtube.vumobile.com.bdtube.Adapter.HDVideoAdapter;
import bdtube.vumobile.com.bdtube.Adapter.MostLikeAdapter;
import bdtube.vumobile.com.bdtube.Adapter.MostWatchAdapter;
import bdtube.vumobile.com.bdtube.Adapter.MusicVideoAdapter;
import bdtube.vumobile.com.bdtube.Adapter.NatokAdapter;
import bdtube.vumobile.com.bdtube.Adapter.NewVideoAdapter;
import bdtube.vumobile.com.bdtube.Adapter.ShortFilmAdapter;
import bdtube.vumobile.com.bdtube.Adapter.SlidingAdapter;
import bdtube.vumobile.com.bdtube.Adapter.TelifilmAdapter;
import bdtube.vumobile.com.bdtube.Api.Config;
import bdtube.vumobile.com.bdtube.App.FAQDialog;
import bdtube.vumobile.com.bdtube.App.RecyclerTouchListener;
import bdtube.vumobile.com.bdtube.App.SubcriptionClass;
import bdtube.vumobile.com.bdtube.App.UserInfo;
import bdtube.vumobile.com.bdtube.Model.AnalyticsApplication;
import bdtube.vumobile.com.bdtube.Model.BanglaClipsClass;
import bdtube.vumobile.com.bdtube.Model.Comedy;
import bdtube.vumobile.com.bdtube.Model.FitnessClass;
import bdtube.vumobile.com.bdtube.Model.HDVideo;
import bdtube.vumobile.com.bdtube.Model.MostLike;
import bdtube.vumobile.com.bdtube.Model.MostWatchVideosClass;
import bdtube.vumobile.com.bdtube.Model.MusicVideoClass;
import bdtube.vumobile.com.bdtube.Model.NatokClass;
import bdtube.vumobile.com.bdtube.Model.NewVideo;
import bdtube.vumobile.com.bdtube.Model.PostbackOperation;
import bdtube.vumobile.com.bdtube.Model.ShortFilmClass;
import bdtube.vumobile.com.bdtube.Model.SliderClass;
import bdtube.vumobile.com.bdtube.Model.Telifilm;
import bdtube.vumobile.com.bdtube.category.BanglaMusicCat;
import bdtube.vumobile.com.bdtube.category.HDVideoCat;
import bdtube.vumobile.com.bdtube.category.NewVideoActivity;
import bdtube.vumobile.com.bdtube.notification.MyReceiver;
import bdtube.vumobile.com.bdtube.notification.NetworkedService;
import bdtube.vumobile.com.bdtube.offline.OfflineVideoActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, SearchView.OnQueryTextListener, View.OnFocusChangeListener {

    private VideoView videoView, videoViewScnd, videoThird;

    private Tracker mTracker;

    Intent i;

    private Button btnNibondhon, btnUnsubscribe;


    private SearchView searchView;
    private PendingIntent pendingIntent;

    DrawerLayout drawer;

    MediaPlayer mp = null;// = new MediaPlayer();


    private String auto_play_url = "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/Keno_Bare_Bare_by_Imran_N_Puja.mp4";

    private List<SliderClass> sliderClasses = new ArrayList<>();
    private List<NewVideo> newVideoList = new ArrayList<>();
    private List<HDVideo> hdVideoList = new ArrayList<>();
    private List<MusicVideoClass> musicVideoClassList = new ArrayList<>();
    private List<NatokClass> natokClassList = new ArrayList<>();
    private List<Telifilm> telifilmList = new ArrayList<>();
    private List<ShortFilmClass> shortFilmClassList = new ArrayList<>();
    private List<FitnessClass> fitnessClassList = new ArrayList<>();
    private List<MostWatchVideosClass> mostWatchVideosClassList = new ArrayList<>();
    private List<MostLike> mostLikeList = new ArrayList<>();
    private List<Comedy> comedyList = new ArrayList<>();

    private MostLike mostLike;
    private MostWatchVideosClass mostWatchVideosClass;
    private BanglaClipsClass banglaClipsClass;
    private ShortFilmClass shortFilmClass;
    private SliderClass sliderClass;
    private Telifilm telifilmClass;
    private NewVideo newVideoClass;
    private HDVideo hdVideoClass;
    private MusicVideoClass musicVideoClass;
    private NatokClass natokClass;
    private FitnessClass fitnessClass;
    private Comedy comedyClass;


    private ProgressDialog progressDialog = null;

    private ScrollView scrollViews;

    public static String AppsVersion = "";
    public static String webVersion = "";

    private RecyclerView recyclerNewVideo, recycler_view_hd_video, recycler_view_music_video, recycler_view_natok,
            recycler_view_telifilm, recycler_view_shortfilm, recycler_view_fitness,
            recycler_view_most_watch, recycler_view_most_like, recycler_view_comedy, slidingRecycler;

    private RecyclerView.Adapter adapterNewVideo, adapterHDVideo, adapterMusicVideo, adapterNatok, adapterTelifilm, adapterBanglaClips,
            adapterShortFilm, adapterFitness, adapterMostWatch, adapterMostLike, adapterComedy, adapterSliding;

    private TextView newvideoMore, hdvideoMore, musicVideoMore, natokMore, telifilmMore,
            shortfilmmMore, fitnessMore, comedyMore,
            mostwatchMore, mostLikeMore;

    private LinearLayout txtHome, txtNewVideo, txtHDVideo, txtMusicVideo,
            txtMovie, txtFitness, txtComedy, txtFvrt, txtUnSubcribe, txtOfflineVideo;

    private ImageView btnBuddyLink, btnBdTubeLink, btnBanglaBeatsLink, btnAmarStickerLink, btnRateMeLink,
            btnHome, btnHelpSame, btnHelp;

    private LinearLayout linear_layout_hdvideo;
    private RelativeLayout adLayout;
    private TextView txtFooter, txtSubscription;

    int firstVideo = 0, scndVideo = 0, thirdVideo = 0;

    int firstVideoStopPosition = 0, secondVideoStopPosition = 0, thirdVideoStopPosition = 0;

    private Rect scrollBound;

    private ImageButton btnVideoFirstVOff, btnVideoFirstVOn, btnVideoScndVOff, btnVideoScndVOn,
            btnVideoThirdVOff, btnVideoThirdVOn;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.jgj);
        getSupportActionBar().setTitle("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ChargingJsonArrayRequestVersion(MainActivity.this, "http://www.vumobile.biz/bdtube/test.php");

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("MainActivity")
                .setAction("Open App")
                .build());

        initUI();

        initSliderRecycler();
        initSlider();

        intiRecyclerNewVideo();
        parseNewVideo();

        initRecyclerHDVideo();
        parseHdVideo();

        initRecyclerMusicVideo();
        parseMusicVideo();

        initRecyclerNatok();
        parseNatok();

        initRecyclerTelifilm();
        parseTelifilm();

        initRecyclerShortFilm();
        parseShortFilm();

        initRecyclerFitness();
        parseFitness();

        initRecyclerMostWatch();
        parseMostWatch();

        initRecyclerMostLike();
        parseMostLike();

        initRecyclerComedy();
        parseComedy();

        Log.d("UserInfos", SplashActivity.DEVICE_ID + "\n"
                + SplashActivity.MANUFACTURE + "\n"
                + SplashActivity.DEVICE_NAME + "\n"
                + SplashActivity.DEVICE_MODEL);

        recycler_view_fitness.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_fitness, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                FitnessClass fitnessClass = fitnessClassList.get(position);
                SubcriptionClass.ContentCategoryCode = fitnessClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = fitnessClass.getContentCode();
                SubcriptionClass.ContentTitle = fitnessClass.getContentTitle();
                SubcriptionClass.ContentType = fitnessClass.getContentType();
                SubcriptionClass.physicalFileName = fitnessClass.getVideoURL();
                SubcriptionClass.artist = fitnessClass.getArtist();
                SubcriptionClass.ContentZedCode = fitnessClass.getContentZedCode();
                SubcriptionClass.totalLike = fitnessClass.getTotalLike();
                SubcriptionClass.totalView = fitnessClass.getTotalView();
                SubcriptionClass.imgUrl = fitnessClass.getImageUrl();
                SubcriptionClass.duration = fitnessClass.getDuration();
                SubcriptionClass.genre = fitnessClass.getGenre();
                SubcriptionClass.info = fitnessClass.getInfo();
                SubcriptionClass.isHD = fitnessClass.getIsHd();

                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "FE6C6D18-2F67-4EF4-AF6E-1EECD27B3AAF";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recycler_view_comedy.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_comedy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                Comedy comedy = comedyList.get(position);
                SubcriptionClass.ContentCategoryCode = comedy.getContentCategoryCode();
                SubcriptionClass.ContentCode = comedy.getContentCode();
                SubcriptionClass.ContentTitle = comedy.getContentTitle();
                SubcriptionClass.ContentType = comedy.getContentType();
                SubcriptionClass.physicalFileName = comedy.getVideoURL();
                SubcriptionClass.artist = comedy.getArtist();
                SubcriptionClass.ContentZedCode = comedy.getContentZedCode();
                SubcriptionClass.totalLike = comedy.getTotalLike();
                SubcriptionClass.totalView = comedy.getTotalView();
                SubcriptionClass.imgUrl = comedy.getImageUrl();
                SubcriptionClass.duration = comedy.getDuration();
                SubcriptionClass.genre = comedy.getGenre();
                SubcriptionClass.info = comedy.getInfo();
                SubcriptionClass.isHD = comedy.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "34F6F2F8-33B3-4DF0-9F54-CDD87E3D6392";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recycler_view_shortfilm.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_shortfilm, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                ShortFilmClass shortFilmClass = shortFilmClassList.get(position);
                SubcriptionClass.ContentCategoryCode = shortFilmClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = shortFilmClass.getContentCode();
                SubcriptionClass.ContentTitle = shortFilmClass.getContentTitle();
                SubcriptionClass.ContentType = shortFilmClass.getContentType();
                SubcriptionClass.physicalFileName = shortFilmClass.getVideoURL();
                SubcriptionClass.artist = shortFilmClass.getArtist();
                SubcriptionClass.ContentZedCode = shortFilmClass.getContentZedCode();
                SubcriptionClass.totalLike = shortFilmClass.getTotalLike();
                SubcriptionClass.totalView = shortFilmClass.getTotalView();
                SubcriptionClass.imgUrl = shortFilmClass.getImageUrl();
                SubcriptionClass.duration = shortFilmClass.getDuration();
                SubcriptionClass.genre = shortFilmClass.getGenre();
                SubcriptionClass.info = shortFilmClass.getInfo();
                SubcriptionClass.isHD = shortFilmClass.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "B97FDA1C-0D3F-4E94-88D9-D8A40FF8A3A7";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_telifilm.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_telifilm, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                Telifilm telifilm = telifilmList.get(position);
                SubcriptionClass.ContentCategoryCode = telifilm.getContentCategoryCode();
                SubcriptionClass.ContentCode = telifilm.getContentCode();
                SubcriptionClass.ContentTitle = telifilm.getContentTitle();
                SubcriptionClass.ContentType = telifilm.getContentType();
                SubcriptionClass.physicalFileName = telifilm.getVideoURL();
                SubcriptionClass.artist = telifilm.getArtist();
                SubcriptionClass.ContentZedCode = telifilm.getContentZedCode();
                SubcriptionClass.totalLike = telifilm.getTotalLike();
                SubcriptionClass.totalView = telifilm.getTotalView();
                SubcriptionClass.imgUrl = telifilm.getImageUrl();
                SubcriptionClass.duration = telifilm.getDuration();
                SubcriptionClass.genre = telifilm.getGenre();
                SubcriptionClass.info = telifilm.getInfo();
                SubcriptionClass.isHD = telifilm.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "14097C69-9203-498F-8F29-4F351FBE7BB9";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_natok.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_natok, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                NatokClass natokClass = natokClassList.get(position);
                SubcriptionClass.ContentCategoryCode = natokClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = natokClass.getContentCode();
                SubcriptionClass.ContentTitle = natokClass.getContentTitle();
                SubcriptionClass.ContentType = natokClass.getContentType();
                SubcriptionClass.physicalFileName = natokClass.getVideoURL();
                SubcriptionClass.artist = natokClass.getArtist();
                SubcriptionClass.ContentZedCode = natokClass.getContentZedCode();
                SubcriptionClass.totalLike = natokClass.getTotalLike();
                SubcriptionClass.totalView = natokClass.getTotalView();
                SubcriptionClass.imgUrl = natokClass.getImageUrl();
                SubcriptionClass.duration = natokClass.getDuration();
                SubcriptionClass.genre = natokClass.getGenre();
                SubcriptionClass.info = natokClass.getInfo();
                SubcriptionClass.isHD = natokClass.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "CEBF2EA8-1FB3-4E64-AAD2-C5320EB6A23F";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_music_video.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_music_video, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                MusicVideoClass musicVideoClass = musicVideoClassList.get(position);
                SubcriptionClass.ContentCategoryCode = musicVideoClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = musicVideoClass.getContentCode();
                SubcriptionClass.ContentTitle = musicVideoClass.getContentTitle();
                SubcriptionClass.ContentType = musicVideoClass.getContentType();
                SubcriptionClass.physicalFileName = musicVideoClass.getVideoURL();
                SubcriptionClass.artist = musicVideoClass.getArtist();
                SubcriptionClass.ContentZedCode = musicVideoClass.getContentZedCode();
                SubcriptionClass.totalLike = musicVideoClass.getTotalLike();
                SubcriptionClass.totalView = musicVideoClass.getTotalView();
                SubcriptionClass.imgUrl = musicVideoClass.getImageUrl();
                SubcriptionClass.duration = musicVideoClass.getDuration();
                SubcriptionClass.genre = musicVideoClass.getGenre();
                SubcriptionClass.info = musicVideoClass.getInfo();
                SubcriptionClass.isHD = musicVideoClass.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "E8E4F496-9CA9-4B35-BADD-9B6470BE2F74";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_most_like.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_most_like, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                MostLike mostLike = mostLikeList.get(position);
                SubcriptionClass.ContentCategoryCode = mostLike.getContentCategoryCode();
                SubcriptionClass.ContentCode = mostLike.getContentCode();
                SubcriptionClass.ContentTitle = mostLike.getContentTitle();
                SubcriptionClass.ContentType = mostLike.getContentType();
                SubcriptionClass.physicalFileName = mostLike.getVideoURL();
                SubcriptionClass.artist = mostLike.getArtist();
                SubcriptionClass.ContentZedCode = mostLike.getContentZedCode();
                SubcriptionClass.totalLike = mostLike.getTotalLike();
                SubcriptionClass.totalView = mostLike.getTotalView();
                SubcriptionClass.imgUrl = mostLike.getImageUrl();
                SubcriptionClass.duration = mostLike.getDuration();
                SubcriptionClass.genre = mostLike.getGenre();
                SubcriptionClass.info = mostLike.getInfo();
                SubcriptionClass.isHD = mostLike.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "ML";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_most_watch.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_most_watch, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                MostWatchVideosClass mostWatchVideosClass = mostWatchVideosClassList.get(position);
                SubcriptionClass.ContentCategoryCode = mostWatchVideosClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = mostWatchVideosClass.getContentCode();
                SubcriptionClass.ContentTitle = mostWatchVideosClass.getContentTitle();
                SubcriptionClass.ContentType = mostWatchVideosClass.getContentType();
                SubcriptionClass.physicalFileName = mostWatchVideosClass.getVideoURL();
                SubcriptionClass.artist = mostWatchVideosClass.getArtist();
                SubcriptionClass.ContentZedCode = mostWatchVideosClass.getContentZedCode();
                SubcriptionClass.totalLike = mostWatchVideosClass.getTotalLike();
                SubcriptionClass.totalView = mostWatchVideosClass.getTotalView();
                SubcriptionClass.imgUrl = mostWatchVideosClass.getImageUrl();
                SubcriptionClass.duration = mostWatchVideosClass.getDuration();
                SubcriptionClass.genre = mostWatchVideosClass.getGenre();
                SubcriptionClass.info = mostWatchVideosClass.getInfo();
                SubcriptionClass.isHD = mostWatchVideosClass.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "MV";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerNewVideo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerNewVideo, new RecyclerTouchListener.ClickListener() {
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
                SubcriptionClass.genre = newVideoClass.getGenre();
                SubcriptionClass.info = newVideoClass.getInfo();
                SubcriptionClass.isHD = newVideoClass.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "NV";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        slidingRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), slidingRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                SliderClass sliderClass = sliderClasses.get(position);
                SubcriptionClass.ContentCategoryCode = sliderClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = sliderClass.getContentCode();
                SubcriptionClass.ContentTitle = sliderClass.getContentTitle();
                SubcriptionClass.ContentType = sliderClass.getContentType();
                SubcriptionClass.physicalFileName = sliderClass.getPhysicalFileName();
                SubcriptionClass.artist = sliderClass.getArtist();
                SubcriptionClass.ContentZedCode = sliderClass.getContentZedCode();
                SubcriptionClass.totalLike = sliderClass.getTotalLike();
                SubcriptionClass.totalView = sliderClass.getTotalView();
                SubcriptionClass.imgUrl = sliderClass.getImageUrl();
                SubcriptionClass.duration = sliderClass.getDuration();
                SubcriptionClass.info = sliderClass.getInfo();
                SubcriptionClass.genre = sliderClass.getGenre();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "NV";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_hd_video.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_hd_video, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                VideoPreviewActivity.song_position = position;

                HDVideo hdVideo = hdVideoList.get(position);
                SubcriptionClass.ContentCategoryCode = hdVideo.getContentCategoryCode();
                SubcriptionClass.ContentCode = hdVideo.getContentCode();
                SubcriptionClass.ContentTitle = hdVideo.getContentTitle();
                SubcriptionClass.ContentType = hdVideo.getContentType();
                SubcriptionClass.physicalFileName = hdVideo.getVideoURL();
                SubcriptionClass.artist = hdVideo.getArtist();
                SubcriptionClass.ContentZedCode = hdVideo.getContentZedCode();
                SubcriptionClass.totalLike = hdVideo.getTotalLike();
                SubcriptionClass.totalView = hdVideo.getTotalView();
                SubcriptionClass.imgUrl = hdVideo.getImageUrl();
                SubcriptionClass.duration = hdVideo.getDuration();
                SubcriptionClass.genre = hdVideo.getGenre();
                SubcriptionClass.info = hdVideo.getInfo();
                SubcriptionClass.isHD = hdVideo.getIsHd();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "B8957F67-501F-4D2A-A580-3D3DFFEB4828";
                new SubcriptionClass(MainActivity.this).checkSubscription();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        try {

            Intent serviceIntent = new Intent(MainActivity.this, NetworkedService.class);
            startService(serviceIntent);
            Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 90 * 1000, pendingIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }



        playFirstVideo();

        scrollBound = new Rect();

        scrollViews.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                scrollViews.getHitRect(scrollBound);

                if (videoView.getLocalVisibleRect(scrollBound)){
                    if (firstVideo==0){
                        firstVideo++;
                        playFirstVideo();
                        Log.d("Visibilityy","Visible first");
                    }
                }else if (!videoView.getLocalVisibleRect(scrollBound)){
                    if (firstVideo==1){
                        firstVideo--;
                        pauseFirstVideo();
                        Log.d("Visibilityy","Invisible first");
                    }
                }

                if (videoViewScnd.getLocalVisibleRect(scrollBound)){
                    if (scndVideo==0){
                        scndVideo++;
                        playSecondVideo();
                        Log.d("Visibilityy","Visible second");
                    }
                }else if (!videoViewScnd.getLocalVisibleRect(scrollBound)){
                    if (scndVideo==1){
                        scndVideo--;
                        pauseSecondVideo();
                        Log.d("Visibilityy","Invisible second");
                    }
                }

                if (videoThird.getLocalVisibleRect(scrollBound)){
                    if (thirdVideo==0){
                        thirdVideo++;
                        playThirdVideo();
                        Log.d("Visibilityy","Visible third");
                    }
                }else if (!videoThird.getLocalVisibleRect(scrollBound)){
                    if (thirdVideo==1){
                        thirdVideo--;
                        pauseThirdVideo();
                        Log.d("Visibilityy","Invisible third");
                    }
                }

            }
        });

        btnVideoScndVOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVideoScndVOff.setVisibility(View.GONE);
                btnVideoScndVOn.setVisibility(View.VISIBLE);
                mp.setVolume(setVolume(), setVolume());
            }
        });

        btnVideoScndVOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVideoScndVOff.setVisibility(View.VISIBLE);
                btnVideoScndVOn.setVisibility(View.GONE);
                mp.setVolume(0, 0);
            }
        });

        btnVideoThirdVOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVideoThirdVOff.setVisibility(View.GONE);
                btnVideoThirdVOn.setVisibility(View.VISIBLE);
                mp.setVolume(setVolume(), setVolume());
            }
        });

        btnVideoThirdVOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVideoThirdVOff.setVisibility(View.VISIBLE);
                btnVideoThirdVOn.setVisibility(View.GONE);
                mp.setVolume(0, 0);
            }
        });

        btnVideoFirstVOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVideoFirstVOff.setVisibility(View.GONE);
                btnVideoFirstVOn.setVisibility(View.VISIBLE);
                mp.setVolume(setVolume(), setVolume());
            }
        });

        btnVideoFirstVOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVideoFirstVOff.setVisibility(View.VISIBLE);
                btnVideoFirstVOn.setVisibility(View.GONE);
                mp.setVolume(0, 0);
            }
        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SliderClass sliderClass = sliderClasses.get(0);
                SubcriptionClass.ContentCategoryCode = sliderClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = sliderClass.getContentCode();
                SubcriptionClass.ContentTitle = sliderClass.getContentTitle();
                SubcriptionClass.ContentType = sliderClass.getContentType();
                SubcriptionClass.physicalFileName = sliderClass.getPhysicalFileName();
                SubcriptionClass.artist = sliderClass.getArtist();
                SubcriptionClass.ContentZedCode = sliderClass.getContentZedCode();
                SubcriptionClass.totalLike = sliderClass.getTotalLike();
                SubcriptionClass.totalView = sliderClass.getTotalView();
                SubcriptionClass.imgUrl = sliderClass.getImageUrl();
                SubcriptionClass.duration = sliderClass.getDuration();
                SubcriptionClass.info = sliderClass.getInfo();
                SubcriptionClass.genre = sliderClass.getGenre();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "NV";
                new SubcriptionClass(MainActivity.this).checkSubscription();
                return false;
            }
        });

        videoViewScnd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SliderClass sliderClass = sliderClasses.get(1);
                SubcriptionClass.ContentCategoryCode = sliderClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = sliderClass.getContentCode();
                SubcriptionClass.ContentTitle = sliderClass.getContentTitle();
                SubcriptionClass.ContentType = sliderClass.getContentType();
                SubcriptionClass.physicalFileName = sliderClass.getPhysicalFileName();
                SubcriptionClass.artist = sliderClass.getArtist();
                SubcriptionClass.ContentZedCode = sliderClass.getContentZedCode();
                SubcriptionClass.totalLike = sliderClass.getTotalLike();
                SubcriptionClass.totalView = sliderClass.getTotalView();
                SubcriptionClass.imgUrl = sliderClass.getImageUrl();
                SubcriptionClass.duration = sliderClass.getDuration();
                SubcriptionClass.info = sliderClass.getInfo();
                SubcriptionClass.genre = sliderClass.getGenre();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "NV";
                new SubcriptionClass(MainActivity.this).checkSubscription();
                return false;
            }
        });

        videoThird.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SliderClass sliderClass = sliderClasses.get(2);
                SubcriptionClass.ContentCategoryCode = sliderClass.getContentCategoryCode();
                SubcriptionClass.ContentCode = sliderClass.getContentCode();
                SubcriptionClass.ContentTitle = sliderClass.getContentTitle();
                SubcriptionClass.ContentType = sliderClass.getContentType();
                SubcriptionClass.physicalFileName = sliderClass.getPhysicalFileName();
                SubcriptionClass.artist = sliderClass.getArtist();
                SubcriptionClass.ContentZedCode = sliderClass.getContentZedCode();
                SubcriptionClass.totalLike = sliderClass.getTotalLike();
                SubcriptionClass.totalView = sliderClass.getTotalView();
                SubcriptionClass.imgUrl = sliderClass.getImageUrl();
                SubcriptionClass.duration = sliderClass.getDuration();
                SubcriptionClass.info = sliderClass.getInfo();
                SubcriptionClass.genre = sliderClass.getGenre();
                SubcriptionClass.relatedContentUrl = Config.URL_NEW_VIDEO;
                SubcriptionClass.relatedCatCode = "NV";
                new SubcriptionClass(MainActivity.this).checkSubscription();
                return false;
            }
        });
    }

    private void pauseThirdVideo() {

        thirdVideoStopPosition = videoThird.getCurrentPosition();
        if (videoThird.isPlaying()){
            videoThird.pause();
        }
    }

    private void playThirdVideo() {

        String url;

        if (sliderClasses.get(2).getContentType().equalsIgnoreCase("FV")) {
            String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/" + sliderClasses.get(2).getPhysicalFileName() + ".mp4";
            url = videoURL.replaceAll(" ", "%20");
        } else {
            String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/Video Clips/D480x320/" + sliderClasses.get(2).getPhysicalFileName() + ".mp4";
            url = videoURL.replaceAll(" ", "%20");
        }


//        final Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
//                + R.raw.one);
        final Uri uri = Uri.parse(url);

        if (thirdVideoStopPosition==0) {

            videoThird.setVideoURI(uri);

            videoThird.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0,0);
                    mediaPlayer.setLooping(true);
                    videoThird.start();
                }
            });
        }else {
            thirdVideoStopPosition = videoThird.getCurrentPosition();
            videoThird.start();
        }

    }

    private void pauseSecondVideo() {

        secondVideoStopPosition = videoViewScnd.getCurrentPosition();
        if (videoViewScnd.isPlaying()){
            videoViewScnd.pause();
        }
    }

    private void playSecondVideo() {

        String url;

        if (sliderClasses.get(1).getContentType().equalsIgnoreCase("FV")) {
            String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/" + sliderClasses.get(1).getPhysicalFileName() + ".mp4";
            url = videoURL.replaceAll(" ", "%20");
        } else {
            String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/Video Clips/D480x320/" + sliderClasses.get(1).getPhysicalFileName() + ".mp4";
            url = videoURL.replaceAll(" ", "%20");
        }


//        final Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
//                + R.raw.one);
        final Uri uri = Uri.parse(url);

        if (secondVideoStopPosition==0) {

            videoViewScnd.setVideoURI(uri);

            videoViewScnd.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0,0);
                    mediaPlayer.setLooping(true);
                    videoViewScnd.start();
                }
            });
        }else {
            secondVideoStopPosition = videoViewScnd.getCurrentPosition();
            videoViewScnd.start();
        }



    }

    private void pauseFirstVideo() {

        firstVideoStopPosition = videoView.getCurrentPosition();
        if (videoView.isPlaying()){
            videoView.pause();
        }
    }

    private void playFirstVideo() {


        String url;

        if (sliderClasses.get(0).getContentType().equalsIgnoreCase("FV")) {
            String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/" + sliderClasses.get(0).getPhysicalFileName() + ".mp4";
            url = videoURL.replaceAll(" ", "%20");
        } else {
            String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/Video Clips/D480x320/" + sliderClasses.get(0).getPhysicalFileName() + ".mp4";
            url = videoURL.replaceAll(" ", "%20");
        }


//        final Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
//                + R.raw.one);
        final Uri uri = Uri.parse(url);

        if (firstVideoStopPosition==0) {

            videoView.setVideoURI(uri);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0,0);
                    mediaPlayer.setLooping(true);
                    videoView.start();
                }
            });
        }else {
            firstVideoStopPosition = videoView.getCurrentPosition();
            videoView.start();
        }
    }



    public static int getVisiblePercent(View v) {
        if (v.isShown()) {
            Rect r = new Rect();
            v.getGlobalVisibleRect(r);
            double sVisible = r.width() * r.height();
            double sTotal = v.getWidth() * v.getHeight();
            return (int) (100 * sVisible / sTotal);
        } else {
            return -1;
        }
    }

    private void initSliderRecycler() {
        slidingRecycler = (RecyclerView) findViewById(R.id.slidingRecycler);
        adapterSliding = new SlidingAdapter(MainActivity.this, sliderClasses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        slidingRecycler.setLayoutManager(mLayoutManager);

        slidingRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
    }

    private void parseComedy() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "34F6F2F8-33B3-4DF0-9F54-CDD87E3D6392");
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
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        comedyClass = new Comedy();
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

                        comedyClass.setContentCategoryCode(ContentCategoryCode);
                        comedyClass.setContentCode(ContentCode);
                        comedyClass.setTimeStamp(TimeStamp);
                        comedyClass.setContentTitle(ContentTitle);
                        comedyClass.setPreviewURL(PreviewURL);
                        comedyClass.setVideoURL(VideoURL);
                        comedyClass.setContentType(ContentType);
                        comedyClass.setValue(Value);
                        comedyClass.setArtist(Artist);
                        comedyClass.setContentZedCode(ContentZedCode);
                        comedyClass.setTotalLike(totalLike);
                        comedyClass.setTotalView(totalView);
                        comedyClass.setImageUrl(imgUrl);
                        comedyClass.setInfo(info);
                        comedyClass.setDuration(duration);
                        comedyClass.setGenre(genre);
                        comedyClass.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        comedyList.add(comedyClass);
                    }

                    recycler_view_comedy.setAdapter(adapterComedy);
                    adapterComedy.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerComedy() {
        adapterComedy = new ComedyAdapter(MainActivity.this, comedyList);
        recycler_view_comedy = (RecyclerView) findViewById(R.id.recycler_view_comedy);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_comedy.setLayoutManager(mLayoutManager);
        recycler_view_comedy.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseMostLike() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "ML");
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
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        mostLike = new MostLike();
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

                        mostLike.setContentCategoryCode(ContentCategoryCode);
                        mostLike.setContentCode(ContentCode);
                        mostLike.setTimeStamp(TimeStamp);
                        mostLike.setContentTitle(ContentTitle);
                        mostLike.setPreviewURL(PreviewURL);
                        mostLike.setVideoURL(VideoURL);
                        mostLike.setContentType(ContentType);
                        mostLike.setValue(Value);
                        mostLike.setArtist(Artist);
                        mostLike.setContentZedCode(ContentZedCode);
                        mostLike.setTotalLike(totalLike);
                        mostLike.setTotalView(totalView);
                        mostLike.setImageUrl(imgUrl);
                        mostLike.setInfo(info);
                        mostLike.setDuration(duration);
                        mostLike.setGenre(genre);
                        mostLike.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        mostLikeList.add(mostLike);
                    }

                    recycler_view_most_like.setAdapter(adapterMostLike);
                    adapterMostLike.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerMostLike() {
        adapterMostLike = new MostLikeAdapter(MainActivity.this, mostLikeList);
        recycler_view_most_like = (RecyclerView) findViewById(R.id.recycler_view_most_like);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_most_like.setLayoutManager(mLayoutManager);
        recycler_view_most_like.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseMostWatch() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "MV");
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
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        mostWatchVideosClass = new MostWatchVideosClass();
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

                        mostWatchVideosClass.setContentCategoryCode(ContentCategoryCode);
                        mostWatchVideosClass.setContentCode(ContentCode);
                        mostWatchVideosClass.setTimeStamp(TimeStamp);
                        mostWatchVideosClass.setContentTitle(ContentTitle);
                        mostWatchVideosClass.setPreviewURL(PreviewURL);
                        mostWatchVideosClass.setVideoURL(VideoURL);
                        mostWatchVideosClass.setContentType(ContentType);
                        mostWatchVideosClass.setValue(Value);
                        mostWatchVideosClass.setArtist(Artist);
                        mostWatchVideosClass.setContentZedCode(ContentZedCode);
                        mostWatchVideosClass.setTotalLike(totalLike);
                        mostWatchVideosClass.setTotalView(totalView);
                        mostWatchVideosClass.setImageUrl(imgUrl);
                        mostWatchVideosClass.setInfo(info);
                        mostWatchVideosClass.setDuration(duration);
                        mostWatchVideosClass.setGenre(genre);
                        mostWatchVideosClass.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        mostWatchVideosClassList.add(mostWatchVideosClass);
                    }

                    recycler_view_most_watch.setAdapter(adapterMostWatch);
                    adapterMostWatch.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerMostWatch() {
        adapterMostWatch = new MostWatchAdapter(MainActivity.this, mostWatchVideosClassList);
        recycler_view_most_watch = (RecyclerView) findViewById(R.id.recycler_view_most_watch);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_most_watch.setLayoutManager(mLayoutManager);
        recycler_view_most_watch.setItemAnimator(new DefaultItemAnimator());
    }

    private void initUI() {

        btnVideoFirstVOff = findViewById(R.id.btnVideoFirstVOff);
        btnVideoFirstVOn = findViewById(R.id.btnVideoFirstVOn);

        btnVideoThirdVOff = findViewById(R.id.btnVideoThirdVOff);
        btnVideoThirdVOn = findViewById(R.id.btnVideoThirdVOn);

        btnVideoScndVOff = findViewById(R.id.btnVideoScndVOff);
        btnVideoScndVOn = findViewById(R.id.btnVideoScndVOn);

        btnVideoFirstVOff.setVisibility(View.GONE);
        btnVideoFirstVOn.setVisibility(View.GONE);
        btnVideoThirdVOff.setVisibility(View.GONE);
        btnVideoThirdVOn.setVisibility(View.GONE);
        btnVideoScndVOff.setVisibility(View.GONE);
        btnVideoScndVOn.setVisibility(View.GONE);

        videoView = findViewById(R.id.videoView);
        videoViewScnd = findViewById(R.id.videoViewScnd);
        videoThird = findViewById(R.id.videoThird);

        videoView.setBackgroundResource(android.R.color.transparent);
        videoViewScnd.setBackgroundResource(android.R.color.transparent);
        videoThird.setBackgroundResource(android.R.color.transparent);

        videoView.requestFocus();

        txtSubscription = (TextView) findViewById(R.id.txtSubscription);
        btnNibondhon = (Button) findViewById(R.id.btnNibondhon);

        btnUnsubscribe = (Button) findViewById(R.id.btnUnsubscribe);
        btnUnsubscribe.setOnClickListener(this);

        linear_layout_hdvideo = (LinearLayout) findViewById(R.id.linear_layout_hdvideo);
        // Put Install Log
        PostbackOperation.makePostbackOperation(this);

        txtFooter = (TextView) findViewById(R.id.txtFooter);

        txtFooter.setText("BDTube © " + String.valueOf(SplashActivity.year) + ".All Rights Reserved.");

        scrollViews = (ScrollView) findViewById(R.id.scrollViews);


        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("অপেক্ষা করুন ...");
        progressDialog.show();

        txtHome = (LinearLayout) findViewById(R.id.txtHome);
        txtNewVideo = (LinearLayout) findViewById(R.id.txtNewVideo);
        txtHDVideo = (LinearLayout) findViewById(R.id.txtHDVideo);
        txtMusicVideo = (LinearLayout) findViewById(R.id.txtMusicVideo);
        txtMovie = (LinearLayout) findViewById(R.id.txtMovie);
        txtFitness = (LinearLayout) findViewById(R.id.txtFitness);
        txtComedy = (LinearLayout) findViewById(R.id.txtComedy);
        txtFvrt = (LinearLayout) findViewById(R.id.txtFvrt);
        txtUnSubcribe = (LinearLayout) findViewById(R.id.txtUnSubcribe);
        txtOfflineVideo = (LinearLayout) findViewById(R.id.txtOfflineVideo);

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

        btnNibondhon.setOnClickListener(this);
        txtHome.setOnClickListener(this);
        txtNewVideo.setOnClickListener(this);
        txtHDVideo.setOnClickListener(this);
        txtMusicVideo.setOnClickListener(this);
        txtMovie.setOnClickListener(this);
        txtFitness.setOnClickListener(this);
        txtComedy.setOnClickListener(this);
        txtFvrt.setOnClickListener(this);
        txtUnSubcribe.setOnClickListener(this);
        txtOfflineVideo.setOnClickListener(this);


        btnBuddyLink.setOnClickListener(this);
        btnBdTubeLink.setOnClickListener(this);
        btnBanglaBeatsLink.setOnClickListener(this);
        btnAmarStickerLink.setOnClickListener(this);
        btnRateMeLink.setOnClickListener(this);

        comedyMore = (TextView) findViewById(R.id.comedyMore);
        mostLikeMore = (TextView) findViewById(R.id.mostlikeMore);
        mostwatchMore = (TextView) findViewById(R.id.mostwatchMore);
        newvideoMore = (TextView) findViewById(R.id.newvideoMore);
        hdvideoMore = (TextView) findViewById(R.id.hdvideoMore);
        musicVideoMore = (TextView) findViewById(R.id.musicVideoMore);
        natokMore = (TextView) findViewById(R.id.natokMore);
        telifilmMore = (TextView) findViewById(R.id.telifilmMore);
        shortfilmmMore = (TextView) findViewById(R.id.shortfilmmMore);
        fitnessMore = (TextView) findViewById(R.id.fitnessMore);

        comedyMore.setOnClickListener(this);
        mostLikeMore.setOnClickListener(this);
        mostwatchMore.setOnClickListener(this);
        newvideoMore.setOnClickListener(this);
        hdvideoMore.setOnClickListener(this);
        musicVideoMore.setOnClickListener(this);
        natokMore.setOnClickListener(this);
        telifilmMore.setOnClickListener(this);
        shortfilmmMore.setOnClickListener(this);
        fitnessMore.setOnClickListener(this);


        if (SplashActivity.MSISDN.equals("wifi") || SplashActivity.MSISDN.equals("ERROR")) {
            txtUnSubcribe.setVisibility(View.GONE);
            btnUnsubscribe.setVisibility(View.GONE);
            btnNibondhon.setVisibility(View.GONE);
            txtSubscription.setVisibility(View.GONE);
        } else {
            checkSub();
        }

    }


    private void parseFitness() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "FE6C6D18-2F67-4EF4-AF6E-1EECD27B3AAF");
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
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        fitnessClass = new FitnessClass();
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

                        fitnessClass.setContentCategoryCode(ContentCategoryCode);
                        fitnessClass.setContentCode(ContentCode);
                        fitnessClass.setTimeStamp(TimeStamp);
                        fitnessClass.setContentTitle(ContentTitle);
                        fitnessClass.setPreviewURL(PreviewURL);
                        fitnessClass.setVideoURL(VideoURL);
                        fitnessClass.setContentType(ContentType);
                        fitnessClass.setValue(Value);
                        fitnessClass.setArtist(Artist);
                        fitnessClass.setContentZedCode(ContentZedCode);
                        fitnessClass.setTotalLike(totalLike);
                        fitnessClass.setTotalView(totalView);
                        fitnessClass.setImageUrl(imgUrl);
                        fitnessClass.setInfo(info);
                        fitnessClass.setDuration(duration);
                        fitnessClass.setGenre(genre);
                        fitnessClass.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        fitnessClassList.add(fitnessClass);
                    }

                    recycler_view_fitness.setAdapter(adapterFitness);
                    adapterFitness.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerFitness() {
        adapterFitness = new FitnessAdapter(MainActivity.this, fitnessClassList);
        recycler_view_fitness = (RecyclerView) findViewById(R.id.recycler_view_fitness);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_fitness.setLayoutManager(mLayoutManager);
        recycler_view_fitness.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseShortFilm() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "B97FDA1C-0D3F-4E94-88D9-D8A40FF8A3A7");
            js.put("PageTotal", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, Config.URL_NEW_VIDEO, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("JSONresponseShortFilm", response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        shortFilmClass = new ShortFilmClass();
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

                        shortFilmClass.setContentCategoryCode(ContentCategoryCode);
                        shortFilmClass.setContentCode(ContentCode);
                        shortFilmClass.setTimeStamp(TimeStamp);
                        shortFilmClass.setContentTitle(ContentTitle);
                        shortFilmClass.setPreviewURL(PreviewURL);
                        shortFilmClass.setVideoURL(VideoURL);
                        shortFilmClass.setContentType(ContentType);
                        shortFilmClass.setValue(Value);
                        shortFilmClass.setArtist(Artist);
                        shortFilmClass.setContentZedCode(ContentZedCode);
                        shortFilmClass.setTotalLike(totalLike);
                        shortFilmClass.setTotalView(totalView);
                        shortFilmClass.setImageUrl(imgUrl);
                        shortFilmClass.setInfo(info);
                        shortFilmClass.setDuration(duration);
                        shortFilmClass.setGenre(genre);
                        shortFilmClass.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        shortFilmClassList.add(shortFilmClass);
                    }

                    recycler_view_shortfilm.setAdapter(adapterShortFilm);
                    adapterShortFilm.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerShortFilm() {
        adapterShortFilm = new ShortFilmAdapter(MainActivity.this, shortFilmClassList);
        recycler_view_shortfilm = (RecyclerView) findViewById(R.id.recycler_view_shortfilm);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_shortfilm.setLayoutManager(mLayoutManager);
        recycler_view_shortfilm.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseTelifilm() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "14097C69-9203-498F-8F29-4F351FBE7BB9");
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
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        telifilmClass = new Telifilm();
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

                        telifilmClass.setContentCategoryCode(ContentCategoryCode);
                        telifilmClass.setContentCode(ContentCode);
                        telifilmClass.setTimeStamp(TimeStamp);
                        telifilmClass.setContentTitle(ContentTitle);
                        telifilmClass.setPreviewURL(PreviewURL);
                        telifilmClass.setVideoURL(VideoURL);
                        telifilmClass.setContentType(ContentType);
                        telifilmClass.setValue(Value);
                        telifilmClass.setArtist(Artist);
                        telifilmClass.setContentZedCode(ContentZedCode);
                        telifilmClass.setTotalLike(totalLike);
                        telifilmClass.setTotalView(totalView);
                        telifilmClass.setImageUrl(imgUrl);
                        telifilmClass.setInfo(info);
                        telifilmClass.setDuration(duration);
                        telifilmClass.setGenre(genre);
                        telifilmClass.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        telifilmList.add(telifilmClass);
                    }

                    recycler_view_telifilm.setAdapter(adapterTelifilm);
                    adapterTelifilm.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerTelifilm() {
        adapterTelifilm = new TelifilmAdapter(MainActivity.this, telifilmList);
        recycler_view_telifilm = (RecyclerView) findViewById(R.id.recycler_view_telifilm);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_telifilm.setLayoutManager(mLayoutManager);
        recycler_view_telifilm.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseNatok() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "CEBF2EA8-1FB3-4E64-AAD2-C5320EB6A23F");
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
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        natokClass = new NatokClass();
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

                        natokClass.setContentCategoryCode(ContentCategoryCode);
                        natokClass.setContentCode(ContentCode);
                        natokClass.setTimeStamp(TimeStamp);
                        natokClass.setContentTitle(ContentTitle);
                        natokClass.setPreviewURL(PreviewURL);
                        natokClass.setVideoURL(VideoURL);
                        natokClass.setContentType(ContentType);
                        natokClass.setValue(Value);
                        natokClass.setArtist(Artist);
                        natokClass.setContentZedCode(ContentZedCode);
                        natokClass.setTotalLike(totalLike);
                        natokClass.setTotalView(totalView);
                        natokClass.setImageUrl(imgUrl);
                        natokClass.setInfo(info);
                        natokClass.setDuration(duration);
                        natokClass.setGenre(genre);
                        natokClass.setIsHd(isHD);

                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        natokClassList.add(natokClass);
                    }

                    recycler_view_natok.setAdapter(adapterNatok);
                    adapterNatok.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerNatok() {
        adapterNatok = new NatokAdapter(MainActivity.this, natokClassList);
        recycler_view_natok = (RecyclerView) findViewById(R.id.recycler_view_natok);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_natok.setLayoutManager(mLayoutManager);
        recycler_view_natok.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseMusicVideo() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "E8E4F496-9CA9-4B35-BADD-9B6470BE2F74");
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
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        musicVideoClass = new MusicVideoClass();
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

                        musicVideoClass.setContentCategoryCode(ContentCategoryCode);
                        musicVideoClass.setContentCode(ContentCode);
                        musicVideoClass.setTimeStamp(TimeStamp);
                        musicVideoClass.setContentTitle(ContentTitle);
                        musicVideoClass.setPreviewURL(PreviewURL);
                        musicVideoClass.setVideoURL(VideoURL);
                        musicVideoClass.setContentType(ContentType);
                        musicVideoClass.setValue(Value);
                        musicVideoClass.setArtist(Artist);
                        musicVideoClass.setContentZedCode(ContentZedCode);
                        musicVideoClass.setTotalLike(totalLike);
                        musicVideoClass.setTotalView(totalView);
                        musicVideoClass.setImageUrl(imgUrl);
                        musicVideoClass.setInfo(info);
                        musicVideoClass.setDuration(duration);
                        musicVideoClass.setGenre(genre);
                        musicVideoClass.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        musicVideoClassList.add(musicVideoClass);
                    }

                    recycler_view_music_video.setAdapter(adapterMusicVideo);
                    adapterMusicVideo.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerMusicVideo() {
        adapterMusicVideo = new MusicVideoAdapter(MainActivity.this, musicVideoClassList);
        recycler_view_music_video = (RecyclerView) findViewById(R.id.recycler_view_music_video);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_music_video.setLayoutManager(mLayoutManager);
        recycler_view_music_video.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseHdVideo() {
        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "B8957F67-501F-4D2A-A580-3D3DFFEB4828");
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
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response
                                .get(i);
                        hdVideoClass = new HDVideo();
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


                        hdVideoClass.setContentCategoryCode(ContentCategoryCode);
                        hdVideoClass.setContentCode(ContentCode);
                        hdVideoClass.setTimeStamp(TimeStamp);
                        hdVideoClass.setContentTitle(ContentTitle);
                        hdVideoClass.setPreviewURL(PreviewURL);
                        hdVideoClass.setVideoURL(VideoURL);
                        hdVideoClass.setContentType(ContentType);
                        hdVideoClass.setValue(Value);
                        hdVideoClass.setArtist(Artist);
                        hdVideoClass.setContentZedCode(ContentZedCode);
                        hdVideoClass.setTotalLike(totalLike);
                        hdVideoClass.setTotalView(totalView);
                        hdVideoClass.setImageUrl(imgUrl);
                        hdVideoClass.setInfo(info);
                        hdVideoClass.setDuration(duration);
                        hdVideoClass.setGenre(genre);
                        hdVideoClass.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        hdVideoList.add(hdVideoClass);
                    }

                    recycler_view_hd_video.setAdapter(adapterHDVideo);
                    adapterHDVideo.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void initRecyclerHDVideo() {
        adapterHDVideo = new HDVideoAdapter(MainActivity.this, hdVideoList);
        recycler_view_hd_video = (RecyclerView) findViewById(R.id.recycler_view_hd_video);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_hd_video.setLayoutManager(mLayoutManager);
        recycler_view_hd_video.setItemAnimator(new DefaultItemAnimator());
    }

    private void parseNewVideo() {

        JSONObject js = new JSONObject();
        try {
            js.put("CatCode", "NV");
            js.put("PageTotal", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = js.toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, Config.URL_NEW_VIDEO, requestBody, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("JSONresponse", response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < 8; i++) {

                        JSONObject homeURL = (JSONObject) response.get(i);
                        newVideoClass = new NewVideo();
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

                        newVideoClass.setContentCategoryCode(ContentCategoryCode);
                        newVideoClass.setContentCode(ContentCode);
                        newVideoClass.setTimeStamp(TimeStamp);
                        newVideoClass.setContentTitle(ContentTitle);
                        newVideoClass.setPreviewURL(PreviewURL);
                        newVideoClass.setVideoURL(VideoURL);
                        newVideoClass.setContentType(ContentType);
                        newVideoClass.setValue(Value);
                        newVideoClass.setArtist(Artist);
                        newVideoClass.setContentZedCode(ContentZedCode);
                        newVideoClass.setTotalLike(totalLike);
                        newVideoClass.setTotalView(totalView);
                        newVideoClass.setImageUrl(imgUrl);
                        newVideoClass.setInfo(info);
                        newVideoClass.setDuration(duration);
                        newVideoClass.setGenre(genre);
                        newVideoClass.setIsHd(isHD);
                        // Log.d("JSON data", PreviewURL + "    ContentCategoryCode: " + PreviewURLserch);

                        newVideoList.add(newVideoClass);
                    }

                    recyclerNewVideo.setAdapter(adapterNewVideo);
                    adapterNewVideo.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);

    }

    private void intiRecyclerNewVideo() {
        adapterNewVideo = new NewVideoAdapter(MainActivity.this, newVideoList);
        recyclerNewVideo = (RecyclerView) findViewById(R.id.recycler_view_new_video);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerNewVideo.setLayoutManager(mLayoutManager);
        recyclerNewVideo.setItemAnimator(new DefaultItemAnimator());
    }

    private void initSlider() {

        JsonArrayRequest request = new JsonArrayRequest(Config.URL_SLIDER, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("LOOG", response.toString());
                for (int j = 0; j <= 8; j++) {
                    try {
                        sliderClass = new SliderClass();
                        JSONObject obj = response.getJSONObject(j);
                        String url = obj.getString(Config.SLIDER_IMAGE_URL).replaceAll(" ", "%20");
                        sliderClass.setContentCode(obj.getString("ContentCode"));
                        sliderClass.setContentCategoryCode(obj.getString("ContentCategoryCode"));
                        sliderClass.setContentTitle(obj.getString("ContentTitle"));
                        sliderClass.setBigPreviewUrl(obj.getString("BigPreview"));
                        sliderClass.setContentType(obj.getString("ContentType"));
                        sliderClass.setContentZedCode(obj.getString("ContentZedCode"));
                        sliderClass.setValue(obj.getString("Value"));
                        sliderClass.setArtist(obj.getString("Artist"));
                        sliderClass.setImageUrl(obj.getString("imageUrl").replaceAll(" ", "%20"));
                        sliderClass.setLiveDate(obj.getString("LiveDate"));
                        sliderClass.setTimeStamp(obj.getString("TimeStamp"));
                        sliderClass.setTotalLike(obj.getString("totalLike"));
                        sliderClass.setTotalView(obj.getString("totalView"));
                        sliderClass.setPhysicalFileName(obj.getString("PhysicalFileName"));
                        sliderClass.setDuration(obj.getString("duration"));
                        sliderClass.setInfo(obj.getString("Info"));
                        sliderClass.setGenre(obj.getString("Genre"));
                        sliderClass.setIsHd(obj.getString("isHd"));

                        Log.d("KKKKKKKKKKKK", sliderClass.getBigPreviewUrl());
                        sliderClasses.add(sliderClass);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                slidingRecycler.setAdapter(adapterSliding);
                adapterSliding.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.ic_action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextFocusChangeListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        // btnBuddyLink, btnBdTubeLink, btnBanglaBeatsLink, btnAmarStickerLink, btnRateMeLink;

        switch (v.getId()) {

            case R.id.txtHome:
                drawer.closeDrawers();
                break;
            case R.id.txtNewVideo:
                drawer.closeDrawers();
                NewVideoActivity.catTitle = "নতুন ভিডিও";
                NewVideoActivity.catCode = "NV";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.txtHDVideo:
                drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, HDVideoCat.class));
                break;
            case R.id.txtMusicVideo:
                drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, BanglaMusicCat.class));
                break;
            case R.id.txtMovie:
                drawer.closeDrawers();
                NewVideoActivity.catTitle = "মুভি";
                NewVideoActivity.catCode = "E564F048-1AD7-450A-BA81-47409FC58BFE";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.txtFitness:
                drawer.closeDrawers();
                NewVideoActivity.catTitle = "ফিটনেস";
                NewVideoActivity.catCode = "FE6C6D18-2F67-4EF4-AF6E-1EECD27B3AAF";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.txtComedy:
                drawer.closeDrawers();
                NewVideoActivity.catTitle = "কমেডি";
                NewVideoActivity.catCode = "34F6F2F8-33B3-4DF0-9F54-CDD87E3D6392";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;

            case R.id.txtUnSubcribe:
                if (SplashActivity.MSISDN.equals("wifi") || SplashActivity.MSISDN.equals("ERROR")) {
                    drawer.closeDrawers();
                    Toast.makeText(getApplicationContext(), "Please use apn!", Toast.LENGTH_LONG).show();
                } else {
                    drawer.closeDrawers();
                    unsubscribeUser(SplashActivity.MSISDN);
                }
                break;
            case R.id.btnUnsubscribe:
                if (SplashActivity.MSISDN.equals("wifi") || SplashActivity.MSISDN.equals("ERROR")) {
                    Toast.makeText(getApplicationContext(), "Please use apn!", Toast.LENGTH_LONG).show();
                } else {
                    unsubscribeUser(SplashActivity.MSISDN);
                }
                break;
            case R.id.txtOfflineVideo:
                drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, OfflineVideoActivity.class));
                break;

            case R.id.txtFvrt:
                drawer.closeDrawers();

                NewVideoActivity.catTitle = "ফেভারেট";
                NewVideoActivity.isFav = true;
                NewVideoActivity.url = Config.URL_FAVOURATE;
                i = new Intent(MainActivity.this, NewVideoActivity.class);
                startActivity(i);
//                if (SplashActivity.MSISDN.equalsIgnoreCase("") || SplashActivity.MSISDN.equalsIgnoreCase("wifi") || SplashActivity.MSISDN.equalsIgnoreCase("ERROR")){
//                    NeedMSISDN needMSISDN = new NeedMSISDN();
//                    needMSISDN.NeedMsisdnDialog(MainActivity.this);
//                }else {
//                    NewVideoActivity.catTitle = "ফেভারেট";
//                    NewVideoActivity.isFav = true;
//                    NewVideoActivity.url = Config.URL_FAVOURATE;
//                    i = new Intent(MainActivity.this, NewVideoActivity.class);
//                    startActivity(i);
//                }
                break;
            case R.id.newvideoMore:
                NewVideoActivity.catTitle = "নতুন ভিডিও";
                NewVideoActivity.catCode = "NV";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.hdvideoMore:
                startActivity(new Intent(MainActivity.this, HDVideoCat.class));
                break;
            case R.id.mostwatchMore:
                NewVideoActivity.catTitle = "মোস্ট ওয়াচ";
                NewVideoActivity.catCode = "MV";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.musicVideoMore:
                startActivity(new Intent(MainActivity.this, BanglaMusicCat.class));
                break;
            case R.id.comedyMore:
                NewVideoActivity.catTitle = "কমেডি";
                NewVideoActivity.catCode = "34F6F2F8-33B3-4DF0-9F54-CDD87E3D6392";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.mostlikeMore:
                NewVideoActivity.catTitle = "মোস্ট লাইক";
                NewVideoActivity.catCode = "ML";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.natokMore:
                NewVideoActivity.catTitle = "নাটক";
                NewVideoActivity.catCode = "CEBF2EA8-1FB3-4E64-AAD2-C5320EB6A23F";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.telifilmMore:
                NewVideoActivity.catTitle = "টেলিফিল্ম";
                NewVideoActivity.catCode = "14097C69-9203-498F-8F29-4F351FBE7BB9";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.shortfilmmMore:
                NewVideoActivity.catTitle = "শর্ট ফিল্ম";
                NewVideoActivity.catCode = "B97FDA1C-0D3F-4E94-88D9-D8A40FF8A3A7";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
                break;
            case R.id.fitnessMore:
                NewVideoActivity.catTitle = "ফিটনেস";
                NewVideoActivity.catCode = "FE6C6D18-2F67-4EF4-AF6E-1EECD27B3AAF";
                NewVideoActivity.url = Config.URL_NEW_VIDEO;
                startActivity(new Intent(MainActivity.this, NewVideoActivity.class));
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

                scrollViews.fullScroll(View.FOCUS_UP);

                break;

            case R.id.btnNibondhon:

                subscribeUser(SplashActivity.MSISDN);

                break;

            case R.id.btnHelpSame:
                startActivity(new Intent(MainActivity.this, TermsAndConditionActivity.class));
//                HelpDialog helpDialog = new HelpDialog();
//                helpDialog.Help(MainActivity.this);
                break;
            // faq
            case R.id.btnHelp:

                FAQDialog faqDialog = new FAQDialog();
                //faqDialog.FUCK(MainActivity.this);


                Log.d("MSISDNsss", SplashActivity.MSISDN);
                if (SplashActivity.MSISDN.startsWith("88018")) {
                    Log.d("MSISDNsss", "Robi");
                    FaqActivity.operator_type = "1";
                    startActivity(new Intent(MainActivity.this, FaqActivity.class));
                    //    faqDialog.FUCK(MainActivity.this, "1");
                } else if (SplashActivity.MSISDN.startsWith("88019")) {
                    Log.d("MSISDNsss", "BL");
                    FaqActivity.operator_type = "2";
                    startActivity(new Intent(MainActivity.this, FaqActivity.class));
                    //faqDialog.FUCK(MainActivity.this, "2");
                } else if (SplashActivity.MSISDN.startsWith("88015")) {
                    Log.d("MSISDNsss", "TT");
                    FaqActivity.operator_type = "3";
                    startActivity(new Intent(MainActivity.this, FaqActivity.class));
                    //faqDialog.FUCK(MainActivity.this, "3");
                } else if (SplashActivity.MSISDN.startsWith("88016")) {
                    Log.d("MSISDNsss", "Airtel");
                    FaqActivity.operator_type = "4";
                    startActivity(new Intent(MainActivity.this, FaqActivity.class));
                    //faqDialog.FUCK(MainActivity.this, "4");
                } else {
                    Log.d("MSISDNsss", "wifi");
                    FaqActivity.operator_type = "wifi";
                    startActivity(new Intent(MainActivity.this, FaqActivity.class));
//                    faqDialog.FUCK(MainActivity.this, "wifi");
                }


                break;
        }

    }

    private void subscribeUser(String MSISDN) {

        if (MSISDN.startsWith("88019")) {
            subscribeUserForBLink(MSISDN);
        } else if (MSISDN.startsWith("88018") || MSISDN.startsWith("88016")) {
            showSubDialog(MSISDN);
        }
    }

    private void showSubDialog(final String msisdn) {

        final Dialog dialogMNO = new Dialog(MainActivity.this,
                R.style.MyDialog);
        dialogMNO.setContentView(R.layout.robi_charging_dialog1);
        dialogMNO.setCancelable(true);
        final ImageButton btnYes = (ImageButton) dialogMNO.findViewById(R.id.btnYes);
        final ImageButton btnCancel = (ImageButton) dialogMNO.findViewById(R.id.btnCancelSub);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMNO.dismiss();
                dialogConfirm(msisdn);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMNO.dismiss();
            }
        });

        dialogMNO.show();

    }

    private void dialogConfirm(final String msisdn) {

        final Dialog dialogConfirm = new Dialog(MainActivity.this,
                R.style.MyDialog);
        dialogConfirm.setContentView(R.layout.subscrib_dialog);
        dialogConfirm.setCancelable(true);
        final Button btnOk = (Button) dialogConfirm.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirm.dismiss();

                // this method is for other operator
                subscribeUserOther(msisdn, MainActivity.this);

            }
        });

        dialogConfirm.show();
    }

    private void subscribeUserOther(final String msisdn, MainActivity mainActivity) {

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
                                Toast.makeText(getApplicationContext(), "Successfully subscribed!", Toast.LENGTH_LONG).show();
                                txtUnSubcribe.setVisibility(View.VISIBLE);
                                btnUnsubscribe.setVisibility(View.VISIBLE);
                                btnNibondhon.setVisibility(View.GONE);
                                txtSubscription.setVisibility(View.GONE);
                                setUserLogSubscription();
                            } else {
                                Toast.makeText(getApplicationContext(), "Subscription failed!", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void subscribeUserForBLink(String msisdn) {

        Log.d("Banglalink", "in subscribeUserForBLink() method line-331");

        final String subsUrl = "http://wap.shabox.mobi/subscriptionapi/api/Charge/SubscriptionUrl?MSISDN=" + msisdn + "&type=BDTUBE";
        final Dialog dialogMNO = new Dialog(MainActivity.this,
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
                                    } else if (url.contains("http://bdtube.mobi/?msisdn=")) {
                                        Log.d("working2", "2");
                                        dialogMNO.dismiss();

                                    }
                                }
                                setUserLogSubscription();

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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);

        dialogMNO.show();
    }

    private void setUserLogSubscription() {


        Log.d("UserInfos", SplashActivity.DEVICE_ID + "\n"
                + SplashActivity.MANUFACTURE + "\n"
                + SplashActivity.DEVICE_NAME + "\n"
                + SplashActivity.DEVICE_MODEL + "\n"
                + SplashActivity.MSISDN);


        Map<String, String> params = new HashMap();
        params.put("MSISDN", SplashActivity.MSISDN);
        params.put("DeviceModel", SplashActivity.DEVICE_MODEL);
        params.put("DeviceName", SplashActivity.DEVICE_NAME);
        params.put("DeviceManufacture", SplashActivity.MANUFACTURE);
        params.put("Type", new UserInfo().userEmail(this));
        params.put("AppName", "BdTube");

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, "https://wap.shabox.mobi/bdnewapi/DataOther/Log", parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                Log.d("SuccessLog", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.d("SuccessLog", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(jsonRequest);
    }

    private void unsubscribeUser(String msisdn) {

        Log.d("Unsubscribe", "Unsubscribe user");

        String unsub_url = "http://android.vumobile.biz/bdnewapi/Deactive/UnReg?msisdn=" + msisdn + "&service=bdtube";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, unsub_url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("Unsubscribe", "Unsubscribe user " + jsonObject);

                try {
                    String result = jsonObject.getString("Status");
                    if (result.equals("Unsubscribed")) {
                        txtUnSubcribe.setVisibility(View.GONE);
                        btnUnsubscribe.setVisibility(View.GONE);
                        txtSubscription.setVisibility(View.VISIBLE);
                        btnNibondhon.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You have successfully unsubscribed!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Unsubscribe", "Unsubscribe user " + volleyError.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        // hide keyboard
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        if (query != null && !query.isEmpty()) {
            SearchActivity.search = query;
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "Nothing to search!", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
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
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            // Google play problem versionName can be define publicly line 858

            String versionName = "";
            versionName = pinfo.versionName;

            AppsVersion = versionName;
            Log.d("versionName package", versionName + " version web " + UpdateString);

            if (!versionName.equalsIgnoreCase(UpdateString)) {

                Update();

            }
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public void Update() {


        final Dialog updateDialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
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

                final String appPackageName = getPackageName();


                try {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id="
                                    + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + appPackageName)));
                }


                //String url = "http://203.76.126.210/shaboxbuddy/All_AppUpdateLog.php?Email=" + new UserInfo().userEmail(getApplicationContext()) + "&MNO=" + SplashActivity.MSISDN + "&AppName=bdtube&AppVersion=" + AppsVersion + "";
                String url = "https://wap.shabox.mobi/shaboxbuddy/All_AppUpdateLog.php?Email=" + new UserInfo().userEmail(getApplicationContext()) + "&MNO=" + SplashActivity.MSISDN + "&AppName=bdtube&AppVersion=" + AppsVersion + "";
                WebView webView = new WebView(MainActivity.this);
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

    private void hideDialog() {

        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public int getScreenRatio() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("ScreenHeight", String.valueOf(width));
        return width / 2;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SubscriptionCheck", "SubscriptionCheck OnResume");

        if (SplashActivity.MSISDN.equals("wifi") || SplashActivity.MSISDN.equals("ERROR")) {
            txtUnSubcribe.setVisibility(View.GONE);
            btnUnsubscribe.setVisibility(View.GONE);
            btnNibondhon.setVisibility(View.GONE);
            txtSubscription.setVisibility(View.GONE);
        } else {
            checkSub();
        }
//        Log.i("Analytics", "Setting screen name: " + "MainActivity");
//        mTracker.setScreenName("Image~" + "HomePage");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void checkSub() {
        Log.d("SubscriptionCheck", "SubscriptionCheck");
        String msisdn = SplashActivity.MSISDN;
        JsonArrayRequest req = new JsonArrayRequest(Config.URL_CHECK_SUB + msisdn,
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

                            if (status.equals("Yes")) {
                                txtUnSubcribe.setVisibility(View.VISIBLE);
                                btnUnsubscribe.setVisibility(View.VISIBLE);
                                btnNibondhon.setVisibility(View.GONE);
                                txtSubscription.setVisibility(View.GONE);
                            } else {
                                txtUnSubcribe.setVisibility(View.GONE);
                                btnUnsubscribe.setVisibility(View.GONE);
                                btnNibondhon.setVisibility(View.VISIBLE);
                                txtSubscription.setVisibility(View.VISIBLE);
                            }

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(req);

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

                            if (status.equalsIgnoreCase("No")) {
                                btnNibondhon.setVisibility(View.VISIBLE);
                                txtSubscription.setVisibility(View.VISIBLE);
                            } else {
                                btnNibondhon.setVisibility(View.GONE);
                                txtSubscription.setVisibility(View.GONE);
                            }

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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(req);
    }

    private float setVolume(){
        final int max = 100;
        final double numerator = max - 100 > 0 ? Math.log(max - 100) : 0;
        final float volume = (float) (1 - (numerator / Math.log(max)));

        return volume;
    }

    private float setVolume1(){
        final int max = 100;
        final double numerator = max - 100 > 0 ? Math.log(max - 100) : 0;
        final float volume = (float) (1 - (numerator / Math.log(max)));

        return volume;
    }

    private float setVolume2(){
        final int max = 100;
        final double numerator = max - 100 > 0 ? Math.log(max - 100) : 0;
        final float volume = (float) (1 - (numerator / Math.log(max)));

        return volume;
    }
}