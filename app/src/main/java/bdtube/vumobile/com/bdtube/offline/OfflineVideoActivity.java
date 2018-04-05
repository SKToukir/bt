package bdtube.vumobile.com.bdtube.offline;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bdtube.vumobile.com.bdtube.Adapter.AdapterOffline;
import bdtube.vumobile.com.bdtube.App.RecyclerTouchListener;
import bdtube.vumobile.com.bdtube.R;
import bdtube.vumobile.com.bdtube.db.DBHandler;
import bdtube.vumobile.com.bdtube.db.DbModelClass;

public class OfflineVideoActivity extends AppCompatActivity {

    private DbModelClass dbModelClass;
    private DBHandler dbHandler;
    private List<DbModelClass> list = new ArrayList<>();
    private Toolbar toolbar;

    private RecyclerView recycler_view_offline;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager mLayoutManager;
    private TextView txtOfflineText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_video);
        toolbar = (Toolbar) findViewById(R.id.toolbar_offline);
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

        parseDataFromDatabase();

        recycler_view_offline.addOnItemTouchListener(new RecyclerTouchListener(this, recycler_view_offline, new RecyclerTouchListener.ClickListener() {
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
                startActivity(new Intent(OfflineVideoActivity.this, OfflineVideoPlayActivity.class));

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void parseDataFromDatabase() {
        dbHandler = new DBHandler(this);
        dbModelClass = new DbModelClass();

        if (dbHandler.getContactsCount()>0){
            txtOfflineText.setVisibility(View.GONE);
            list.addAll(dbHandler.getAllContacts());
            adapter.notifyDataSetChanged();
        }else {
            txtOfflineText.setVisibility(View.VISIBLE);
        }
    }

    private void initUI() {

        adapter = new AdapterOffline(OfflineVideoActivity.this,list);
        recycler_view_offline = (RecyclerView) findViewById(R.id.recycler_view_offline);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recycler_view_offline.setLayoutManager(mLayoutManager);
        recycler_view_offline.setItemAnimator(new DefaultItemAnimator());
        recycler_view_offline.setAdapter(adapter);

        txtOfflineText = findViewById(R.id.txtOfflineText);

    }
}
