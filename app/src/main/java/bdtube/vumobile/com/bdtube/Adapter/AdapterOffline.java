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
import java.util.ArrayList;
import java.util.List;

import bdtube.vumobile.com.bdtube.R;
import bdtube.vumobile.com.bdtube.db.DbModelClass;

/**
 * Created by toukirul on 25/3/2018.
 */

public class AdapterOffline extends RecyclerView.Adapter<AdapterOffline.MyViewHolder> {

    private Context context;
    private List<DbModelClass> searchClassList = new ArrayList<>();

    public AdapterOffline(Context context, List<DbModelClass> searchClassList){
        this.context = context;
        this.searchClassList = searchClassList;
    }

    @Override
    public AdapterOffline.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_offline,parent,false);

        return new AdapterOffline.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterOffline.MyViewHolder holder, int position) {

        DbModelClass primaryClass = searchClassList.get(position);

        try {
            File file = new File(primaryClass.getImageFilePath(), primaryClass.getImageFileName());
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.videoImageView.setImageBitmap(bitmap);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        holder.videoTitle.setText(primaryClass.getVideoTitle());
        holder.txtTotalLikes.setText(primaryClass.getTotal_likes());
        holder.txtTotalView.setText(primaryClass.getTotal_views());

    }

    @Override
    public int getItemCount() {
        return searchClassList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView txtTotalLikes,txtTotalView;
        ImageView videoImageView;
        ImageView vedio_icon;
        TextView videoTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTotalLikes = (TextView) itemView.findViewById(R.id.txtTotalLikes);
            txtTotalView = (TextView) itemView.findViewById(R.id.txtTotalView);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_itemsss);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
        }
    }
}