package example.sdbi.com.easypointweather.Index.MyFragment;

import example.sdbi.com.easypointweather.Index.view.ImageBarnnerFramLayout;
import example.sdbi.com.easypointweather.R;
import example.sdbi.com.easypointweather.db.News;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MyFragment extends Fragment implements ImageBarnnerFramLayout.FramLayoutLisenner {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageBarnnerFramLayout mGroup;
    private List<News> newsList = new ArrayList<>();
    private int img;
    private String content;
    private String fromAdress;
    private String date;
    private int[] ids = new int[]{
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3
    };
    private NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(2000);
                    Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        InsertData.insertData();
        /**
         * 查询数据库里的新闻信息
         */
        Button queryData = view.findViewById(R.id.queryData);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = DataSupport.findBySQL("select * from News");
                for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
                    Log.d("abc",cursor.getString(cursor.getColumnIndex("img")));
                    Log.d("abc",cursor.getString(cursor.getColumnIndex("newsContent")));
                    Log.d("abc",cursor.getString(cursor.getColumnIndex("fromAdress")));
                    Log.d("abc",cursor.getString(cursor.getColumnIndex("date")));
                }
                cursor.close();

            }
        });
        getData();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(adapter);
        //我们需要计算出我们当前手机的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        C.WITTH = dm.widthPixels;
        mGroup = view.findViewById(R.id.image_group);
        mGroup.setLisenner(this);
        List<Bitmap> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ids[i]);
            list.add(bitmap);

        }
        mGroup.addBitmaps(list);
        return view;
    }

    @Override
    public void clickImageIndex(int pos) {
        if (pos == 0) {
            Intent intent = new Intent();
            intent.setData(Uri.parse("https://crown100.taobao.com/shop/view_shop.htm?user_number_id=16147210&ali_trackid=2%3Amm_118240449_17252262_62920298%3A1517634241_315_1716325806"));
            startActivity(intent);
        } else if (pos == 1) {
            Intent intent = new Intent();
            intent.setData(Uri.parse("http://www.51souli.com/news/1654.htm"));
            startActivity(intent);
        } else if (pos == 2) {
            Intent intent = new Intent();
            intent.setData(Uri.parse("https://detail.ju.taobao.com/home.htm?spm=608.brandpage.zebra-ju-minisite-item-2979_1517117309961_17.1.3d322e8a1SXmxx&id=10000070611140&item_id=538809400352"));
            startActivity(intent);
        }
    }

    private void getData() {

        List<News> newses = DataSupport.findAll(News.class);
        for (News news : newses) {
            int imgData = news.getImg();
            String contentData = news.getNewsContent();
            String fromAdressData = news.getFromAdress();
            String newsDateData = news.getDate();
                img = imgData;
                content = contentData;
                fromAdress = fromAdressData;
                date = newsDateData;
                removeDuplicate(newsList);
                newsList.add(new News(img, content, fromAdress, date));
        }

    }
    public static List<News> removeDuplicate(List<News> list)

    {
        Set set = new LinkedHashSet<News>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}
