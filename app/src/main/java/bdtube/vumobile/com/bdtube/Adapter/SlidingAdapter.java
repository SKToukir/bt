package bdtube.vumobile.com.bdtube.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bdtube.vumobile.com.bdtube.Model.SliderClass;
import bdtube.vumobile.com.bdtube.R;

/**
 * Created by toukirul on 2/11/2017.
 */

public class SlidingAdapter extends RecyclerView.Adapter<SlidingAdapter.MyViewHolder> {

    MediaPlayer mp = new MediaPlayer();
    SurfaceHolder surfaceHolder;
    private Context mContext;
    private List<SliderClass> pictureCoolClassList;
    int lastPosition = -1;

    public SlidingAdapter(Context context,List<SliderClass> picList){
        this.mContext = context;
        this.pictureCoolClassList = picList;
    }

    @Override
    public SlidingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_slider,parent,false);

        return new SlidingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SlidingAdapter.MyViewHolder holder, int position) {

        surfaceHolder = holder.surface_view_item.getHolder();

        SliderClass primaryClass = pictureCoolClassList.get(position);

        lastPosition = position;
//        holder.videoImageView.setVisibility(View.VISIBLE);
//        holder.surface_view_item.setVisibility(View.GONE);
//        Picasso.with(mContext).load(primaryClass.getImageUrl()).into(holder.videoImageView);
        //holder.videoImageView.setImageUrl(primaryClass.getImage_url(),imageLoader);
        // mAnimator.onBindViewHolder(holder.itemView, position);

        Log.d("ResponseSlider",primaryClass.getBigPreviewUrl());
        holder.videoImageView.setVisibility(View.VISIBLE);
        holder.surface_view_item.setVisibility(View.GONE);
        Picasso.with(mContext).load(primaryClass.getImageUrl()).into(holder.videoImageView);
        Animation animation = AnimationUtils.loadAnimation(mContext,
                R.anim.zoom_in);
        holder.itemView.startAnimation(animation);


        // uncomment below code if you want to play first video of sliding
//        if (position == 0) {
//
//            holder.surface_view_item.setVisibility(View.VISIBLE);
//            holder.videoImageView.setVisibility(View.GONE);
//
//
//            if (primaryClass.getContentType().equalsIgnoreCase("FV")) {
//
//                playFirstVideoFromList(surfaceHolder, "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/" + primaryClass.getPhysicalFileName() + ".mp4");
//
//            } else {
//
//                playFirstVideoFromList(surfaceHolder, "http://wap.shabox.mobi/CMS/Content/Graphics/Video Clips/D480x320/" + primaryClass.getPhysicalFileName() + ".mp4");
//            }
//
//        }else {
//            holder.videoImageView.setVisibility(View.VISIBLE);
//            holder.surface_view_item.setVisibility(View.GONE);
//            Picasso.with(mContext).load(primaryClass.getImageUrl()).into(holder.videoImageView);
//        }


    }

    private void playFirstVideoFromList(final SurfaceHolder surfaceHolder, final String s) {


        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mp.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d("surfacechanged", "surfacechanged");
                mp.start();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("surfacechanged", "surfaceDestroy");
                mp.pause();
            }
        });

        String url = s.replaceAll(" ", "%20");
        Log.d("surfacechanged", url);
        try {
            mp.setDataSource(url);
            mp.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("surfacechanged", ""+e.getMessage());
        }
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mp.setVolume(0,0);

        if (mp.isPlaying()){
            Log.d("surfacechanged", String.valueOf(mp.getDuration()));
        }
    }

    @Override
    public int getItemCount() {
        return pictureCoolClassList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        SurfaceView surface_view_item;

        ImageView videoImageView;
        TextView videoTitle, txtLikes, txtTotalDwn;

        public MyViewHolder(View itemView) {
            super(itemView);
            surface_view_item = (SurfaceView) itemView.findViewById(R.id.surface_view_item);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_items_slider);
        }
    }
}
