package bdtube.vumobile.com.bdtube.offline;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bdtube.vumobile.com.bdtube.Adapter.AdapterRelatedOffline;
import bdtube.vumobile.com.bdtube.App.DividerItemDecoration;
import bdtube.vumobile.com.bdtube.App.RecyclerTouchListener;
import bdtube.vumobile.com.bdtube.R;
import bdtube.vumobile.com.bdtube.db.DbModelClass;

public class OfflineVideoPlayActivity extends AppCompatActivity {

    private RecyclerView recycler_related_offline;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager mLayoutManager;

    private FullscreenVideoLayout videoView;

    public static String videoPath, total_likes, total_views, duration, info, genre;

    public static List<DbModelClass> list = new ArrayList<>();

    public TextView txtTotalViews, txtTotalLikes, txtDuration, txtInfo, txtGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_video_play);

        initUI();

        recycler_related_offline.addOnItemTouchListener(new RecyclerTouchListener(this, recycler_related_offline, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DbModelClass dbModelClass = list.get(position);
                OfflineVideoPlayActivity.videoPath = dbModelClass.getVideoFilePath();
                OfflineVideoPlayActivity.total_likes = dbModelClass.getTotal_likes();
                OfflineVideoPlayActivity.total_views = dbModelClass.getTotal_views();
                OfflineVideoPlayActivity.genre = dbModelClass.getGenre();
                OfflineVideoPlayActivity.duration = dbModelClass.getDuration();
                OfflineVideoPlayActivity.info = dbModelClass.getInfo();
                OfflineVideoPlayActivity.list = list;
                finish();
                startActivity(new Intent(OfflineVideoPlayActivity.this, OfflineVideoPlayActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        try {
            videoView.setVideoPath(videoPath);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoView.start();
                    if (mediaPlayer.isPlaying()) {
                        videoView.hideControls();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initUI() {

        adapter = new AdapterRelatedOffline(OfflineVideoPlayActivity.this, list);
        recycler_related_offline = (RecyclerView) findViewById(R.id.recycler_related_offline);
        recycler_related_offline.setAdapter(adapter);
        recycler_related_offline.setNestedScrollingEnabled(false);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recycler_related_offline.setLayoutManager(mLayoutManager);
        recycler_related_offline.setItemAnimator(new DefaultItemAnimator());
        recycler_related_offline.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter.notifyDataSetChanged();

        videoView =  findViewById(R.id.videoviews);
        videoView.setActivity(this);


        txtTotalLikes = findViewById(R.id.txtTotalLikes);
        txtTotalViews = findViewById(R.id.txtTotalViews);
        txtDuration = findViewById(R.id.txtDuration);
        txtInfo = findViewById(R.id.txtInfo);
        txtGenre = findViewById(R.id.txtGenre);

        txtTotalLikes.setText(total_likes);
        txtTotalViews.setText(total_views);
        txtInfo.setText(info);
        txtDuration.setText(duration);
        txtGenre.setText(genre);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
