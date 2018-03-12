package example.sdbi.com.easypointweather.Index.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import example.sdbi.com.easypointweather.Index.MyFragment.C;
import example.sdbi.com.easypointweather.R;

/**
 * Created by Administrator on 2018/1/25.
 */

public class ImageBarnnerFramLayout extends FrameLayout implements ImageBarnnertViewGroup.ImageBarnnerViewGroupListenner,ImageBarnnertViewGroup.ImageBarnnerListener{

    private ImageBarnnertViewGroup imageBarnnertViewGroup;
    private LinearLayout linearLayout;
    private FramLayoutLisenner lisenner;

    public FramLayoutLisenner getLisenner() {
        return lisenner;
    }

    public void setLisenner(FramLayoutLisenner lisenner) {
        this.lisenner = lisenner;
    }

    public ImageBarnnerFramLayout(Context context) {
        super(context);
        initImageBarrnerViewGroup();
        initDotLinnearLayout();
    }

    public ImageBarnnerFramLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImageBarrnerViewGroup();
        initDotLinnearLayout();
    }

    public ImageBarnnerFramLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBarrnerViewGroup();
        initDotLinnearLayout();
    }

    public void addBitmaps(List<Bitmap> list) {
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = list.get(i);
            addBitmapToImageBarnnerViewGroup(bitmap);
            addDotToLinearlayout();

        }
    }
    private void addDotToLinearlayout(){
        ImageView iv=new ImageView(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5,5,5,5);
        iv.setLayoutParams(lp);
        iv.setImageResource(R.drawable.dot_nomal);
        linearLayout.addView(iv);

    }

    public void addBitmapToImageBarnnerViewGroup(Bitmap bitmap) {
        ImageView iv = new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.MATRIX);
        iv.setLayoutParams(new ViewGroup.LayoutParams(C.WITTH, ViewGroup.LayoutParams.WRAP_CONTENT));
        iv.setImageBitmap(bitmap);
        imageBarnnertViewGroup.addView(iv);
    }

    /**
     * 初始化 我们自定义的图片轮播功能的核心类
     */
    private void initImageBarrnerViewGroup() {
        imageBarnnertViewGroup = new ImageBarnnertViewGroup(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageBarnnertViewGroup.setLayoutParams(lp);
        imageBarnnertViewGroup.setBarnnerViewGroupListenner(this);//这里就是将listenner 传递给framlayout
        imageBarnnertViewGroup.setListener(this);
        addView(imageBarnnertViewGroup);

    }

    /**
     * 初始化 我们底部远点布局
     */
    private void initDotLinnearLayout() {
        linearLayout = new LinearLayout(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 40);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.GRAY);
        addView(linearLayout);

        LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(layoutParams);

        //这里有一个知识点： 就是在3.0以后我们使用的是 setAlpha(),在3.0之前我们使用的是setAlpha()但是调用的不同
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            linearLayout.setAlpha(0.5f);
        } else {
            linearLayout.getBackground().setAlpha(100);
        }
    }

    @Override
    public void selectImage(int index) {
        int count=linearLayout.getChildCount();
        for (int i=0;i<count;i++){
            ImageView iv= (ImageView) linearLayout.getChildAt(i);
            if (i == index){
                iv.setImageResource(R.drawable.dot_nomal);

            }else {
                iv.setImageResource(R.drawable.dot_select);
            }
        }
    }

    @Override
    public void clickImageIndex(int pos) {
        lisenner.clickImageIndex(pos);
    }
    public interface FramLayoutLisenner{
        void clickImageIndex(int pos);

    }
}
