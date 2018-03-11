package example.sdbi.com.easypointweather.Index;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import example.sdbi.com.easypointweather.Index.FoundFragment.FoundFragment;
import example.sdbi.com.easypointweather.Index.HomeFragment.HomeFragment;
import example.sdbi.com.easypointweather.Index.MyFragment.MyFragment;
import example.sdbi.com.easypointweather.Login.MainActivity;
import example.sdbi.com.easypointweather.R;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener{
    // 初始化顶部栏显示
    private ImageView titleLeftImv;
    private TextView titleTv;
    // 定义4个Fragment对象
    private HomeFragment fg1;
    private FoundFragment fg2;
    private MyFragment fg3;
    // 帧布局对象，用来存放Fragment对象
    private FrameLayout frameLayout;
    // 定义每个选项中的相关控件
    private RelativeLayout home_press,found_press,my_press,titleHead;
//    private FrameLayout titleHeadHome;
    private ImageView home_press_iv,found_press_iv,my_press_iv;
    private TextView home_press_tv,found_press_tv,my_press_tv;
    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;
//    private Toolbar toolbarHome;
    private LinearLayout navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_index);
        fragmentManager = getSupportFragmentManager();
        initView(); // 初始化界面控件
        setChioceItem(0); // 初始化页面加载时显示第一个选项卡
    }

    /**
     * 初始化页面
     */
    private void initView() {
//        // 初始化页面标题栏
        titleLeftImv = (ImageView) findViewById(R.id.title_imv);
        titleLeftImv.setRotation(90f);
        titleLeftImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexActivity.this, MainActivity.class));
            }
        });

        // 初始化底部导航栏的控件
        navigation= (LinearLayout) findViewById(R.id.navigation);
//        titleHead= (RelativeLayout) findViewById(R.id.title_head);
//        titleHeadHome= (FrameLayout) findViewById(R.id.title_home_layour);
        home_press= (RelativeLayout) findViewById(R.id.home_press);
        found_press= (RelativeLayout) findViewById(R.id.found_press);
        my_press=(RelativeLayout) findViewById(R.id.my_press);
        home_press_iv=(ImageView) findViewById(R.id.home_press_iv);
        found_press_iv=(ImageView) findViewById(R.id.found_press_iv);
        my_press_iv=(ImageView) findViewById(R.id.my_press_iv);
        home_press_tv=(TextView) findViewById(R.id.home_press_tv);
        found_press_tv=(TextView) findViewById(R.id.found_press_tv);
        my_press_tv=(TextView) findViewById(R.id.my_press_tv);
//        toolbarHome= (Toolbar) findViewById(R.id.toorbar);
        home_press.setOnClickListener(this);
        found_press.setOnClickListener(this);
        my_press.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.home_press:
                setChioceItem(0);
//                if (titleHeadHome.getVisibility()==View.GONE){
//                    titleHeadHome.setVisibility(View.VISIBLE);
//                    titleHead.setVisibility(View.GONE);
//                }else {
//                    titleHead.setVisibility(View.GONE);
//                }

//                setSupportActionBar(toolbarHome);
                titleTv = (TextView) findViewById(R.id.title_text_tv);
                titleTv.setText("中 国");
                break;
            case R.id.found_press:
//                if (titleHead.getVisibility()==View.GONE){
//                    titleHead.setVisibility(View.VISIBLE);
//                    titleHeadHome.setVisibility(View.GONE);
//                }else {
//                    titleHeadHome.setVisibility(View.GONE);
//                }

                titleTv = (TextView) findViewById(R.id.title_text_tv);
                titleTv.setText("定 位");
                setChioceItem(1);
                break;
            case R.id.my_press:
//                if (titleHead.getVisibility()==View.GONE){
//                    titleHead.setVisibility(View.VISIBLE);
//                    titleHeadHome.setVisibility(View.GONE);
//                }else {
//                    titleHeadHome.setVisibility(View.GONE);
//                }
                setChioceItem(2);
                titleTv = (TextView) findViewById(R.id.title_text_tv);
                titleTv.setText("我 的");
                break;
            default:
                break;
        }
    }

    /**
     * 设置点击选项卡的事件处理
     *
     * @param index
     *            选项卡的标号：0, 1, 2, 3
     */
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
                // firstImage.setImageResource(R.drawable.XXXX); 需要的话自行修改
                home_press_iv.setImageResource(R.drawable.home_press);
                home_press_tv.setTextColor(Color.rgb(19, 181, 177));
                // 如果fg1为空，则创建一个并添加到界面上
                if (fg1 == null) {
                    fg1 = new HomeFragment();
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
                    // 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(fg1);

                }
                break;
            case 1:
                navigation.setVisibility(View.GONE);
                // secondImage.setImageResource(R.drawable.XXXX);
                found_press_iv.setImageResource(R.drawable.found_press);
                found_press_tv.setTextColor(Color.rgb(19, 181, 177));
                if (fg2 == null) {
                    fg2 = new FoundFragment();
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                break;
            case 2:
                // thirdImage.setImageResource(R.drawable.XXXX);
                my_press_iv.setImageResource(R.drawable.my_press);
                my_press_tv.setTextColor(Color.rgb(19, 181, 177));
                if (fg3 == null) {
                    fg3 = new MyFragment();
                    fragmentTransaction.add(R.id.content, fg3);
                } else {
                    fragmentTransaction.show(fg3);
                }
                break;

        }
        fragmentTransaction.commit(); // 提交
    }

    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
        home_press_iv.setImageResource(R.drawable.home);
        home_press_tv.setTextColor(Color.rgb(19, 181, 177));
        found_press_iv.setImageResource(R.drawable.found);
        found_press_tv.setTextColor(Color.rgb(19, 181, 177));
        my_press_iv.setImageResource(R.drawable.my);
        my_press_tv.setTextColor(Color.rgb(19, 181, 177));

    }

    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.hide(fg1);
        }
        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }

    }
}