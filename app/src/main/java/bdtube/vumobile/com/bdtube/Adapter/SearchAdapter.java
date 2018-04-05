package bdtube.vumobile.com.bdtube.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bdtube.vumobile.com.bdtube.Model.SearchClass;
import bdtube.vumobile.com.bdtube.R;

/**
 * Created by toukirul on 11/10/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context context;
    private List<SearchClass> searchClassList = new ArrayList<>();

    public SearchAdapter(Context context, List<SearchClass> searchClassList){
        this.context = context;
        this.searchClassList = searchClassList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_video,parent,false);

        return new SearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SearchClass primaryClass = searchClassList.get(position);

        String isHd = primaryClass.getIsHd();
        if (isHd.equals("1")){
            holder.txtIsHdIcon.setVisibility(View.VISIBLE);
        }else {
            holder.txtIsHdIcon.setVisibility(View.GONE);
        }

        holder.txtTotalLikes.setText(primaryClass.getTotalLike());
        holder.txtTotalView.setText(primaryClass.getTotalView());
        Glide.with(context).load(primaryClass.getImageUrl()).into(holder.videoImageView);
        holder.videoTitle.setText(primaryClass.getContentTitle().replace("_"," "));


    }

    @Override
    public int getItemCount() {
        return searchClassList.size();
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
