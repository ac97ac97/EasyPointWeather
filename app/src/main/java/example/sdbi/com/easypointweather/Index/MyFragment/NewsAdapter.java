package example.sdbi.com.easypointweather.Index.MyFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.sdbi.com.easypointweather.R;
import example.sdbi.com.easypointweather.db.News;

/**
 * Created by Administrator on 2018/3/12.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> mLis;



    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView newsImage;
        TextView newsContent;
        TextView newsFromAdress;
        TextView newsDate;
        public ViewHolder(View itemView) {
            super(itemView);
            newsImage=itemView.findViewById(R.id.news_image);
            newsContent=itemView.findViewById(R.id.newsContent);
            newsFromAdress=itemView.findViewById(R.id.newsFromAdress);
            newsDate=itemView.findViewById(R.id.newsDate);
        }
    }
    public NewsAdapter(List<News> mLis) {
        this.mLis = mLis;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_fragment_news_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = mLis.get(position);
        holder.newsImage.setImageResource(news.getImg());
        holder.newsContent.setText(news.getNewsContent());
        holder.newsFromAdress.setText(news.getFromAdress());
        holder.newsDate.setText(news.getDate());
    }

    @Override
    public int getItemCount() {
        return mLis.size();
    }


}
