package bdtube.vumobile.com.bdtube.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import bdtube.vumobile.com.bdtube.R;
import bdtube.vumobile.com.bdtube.db.DbModelClass;

/**
 * Created by toukirul on 25/3/2018.
 */

public class AdapterRelatedOffline extends RecyclerView.Adapter<AdapterRelatedOffline.MyViewHolder> {

    //ImageLoader imageLoader =  AppController.getInstance().getImageLoader();;
    private Context mContext;
    private List<DbModelClass> videoHomeList;

    public AdapterRelatedOffline(Context context, List<DbModelClass> videoHomeList){
        this.mContext = context;
        this.videoHomeList = videoHomeList;
    }

    @Override
    public AdapterRelatedOffline.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_related_offline,parent,false);

        return new AdapterRelatedOffline.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRelatedOffline.MyViewHolder holder, int position) {

        DbModelClass primaryClass = videoHomeList.get(position);

        try {
            File file = new File(primaryClass.getImageFilePath(), primaryClass.getImageFileName());
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.videoImageView.setImageBitmap(bitmap);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        holder.txtTotalLikes.setText(primaryClass.getTotal_likes());
        holder.txtTotalView.setText(primaryClass.getTotal_views());
        holder.videoTitle.setText(primaryClass.getVideoTitle());

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