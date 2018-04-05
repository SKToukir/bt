package bdtube.vumobile.com.bdtube.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import bdtube.vumobile.com.bdtube.Model.BanglaNatok;
import bdtube.vumobile.com.bdtube.R;

/**
 * Created by toukirul on 8/10/2017.
 */

public class BanglaNatokAdapter extends RecyclerView.Adapter<BanglaNatokAdapter.MyViewHolder> {

    //ImageLoader imageLoader =  AppController.getInstance().getImageLoader();;
    private Context mContext;
    private List<BanglaNatok> videoHomeList;

    public BanglaNatokAdapter(Context context, List<BanglaNatok> videoHomeList){
        this.mContext = context;
        this.videoHomeList = videoHomeList;
    }

    @Override
    public BanglaNatokAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video,parent,false);

        return new BanglaNatokAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BanglaNatokAdapter.MyViewHolder holder, int position) {

        BanglaNatok primaryClass = videoHomeList.get(position);
        //imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();


        // aq.id(holder.videoImageView).image(primaryClass.getImageUrl(),true,true,0,AQuery.FADE_IN);

        Log.d("ImageUrl",primaryClass.getPreviewURL());

        String isHd = primaryClass.getIsHd();
        if (isHd.equals("1")){
            holder.txtIsHdIcon.setVisibility(View.VISIBLE);
        }else {
            holder.txtIsHdIcon.setVisibility(View.GONE);
        }

        holder.txtTotalLikes.setText(primaryClass.getTotalLike());
        holder.txtTotalView.setText(primaryClass.getTotalView());
        Glide.with(mContext).load(primaryClass.getImageUrl()).into(holder.videoImageView);
        holder.videoTitle.setText(primaryClass.getContentTitle().replace("_"," "));
//        if (primaryClass.getContentType().equalsIgnoreCase("FV")){
//            String imgUrl = "http://wap.shabox.mobi/CMS/GraphicsPreview/FullVideo/" + primaryClass.getPreviewURL();
//            Picasso.with(mContext).load(imgUrl).into(holder.videoImageView);
//        }else {
//            String imgUrl = "http://wap.shabox.mobi/CMS/GraphicsPreview/Video%20Clips/" + primaryClass.getPreviewURL();
//            Picasso.with(mContext).load(imgUrl).into(holder.videoImageView);
//        }

        //Glide.with(mContext).load(primaryClass.getPreviewURL()).override(100,100).thumbnail(0.1f).into(holder.videoImageView);
        // holder.videoImageView.setImageUrl(primaryClass.getImageUrl(),imageLoader);


    }

    @Override
    public int getItemCount() {
        return videoHomeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTotalLikes,txtTotalView;
        ImageView videoImageView;
        ImageView vedio_icon;
        TextView videoTitle, txtIsHdIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtIsHdIcon = (TextView) itemView.findViewById(R.id.txtIsHdIcon);
            txtTotalLikes = (TextView) itemView.findViewById(R.id.txtTotalLikes);
            txtTotalView = (TextView) itemView.findViewById(R.id.txtTotalView);
            //vedio_icon = (ImageView) itemView.findViewById(R.id.vedio_icon);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_itemsss);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
        }
    }
}