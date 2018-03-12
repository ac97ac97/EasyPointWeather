package example.sdbi.com.easypointweather.Index.view;

/**
 * Created by Administrator on 2018/1/25.
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 该类是实现图片轮播的核心类
 */

public class ImageBarnnertViewGroup extends ViewGroup {


    private int children;//我们viewgroup的子视图总个数
    private int childwidth;//子视图宽度
    private int childheight;//子视图高度
    private int x;//此时的x的值代表的是第一次按下的红坐标的位置，每一次移动过程中移动之前位置的横坐标
    private int index = 0;//代表的是我们每张图片的索引
    private Scroller scroller;
    /**
     * 要想实现图片单机事件的获取
     * 采用方法就是利用一个单机变量开关进行判断，在用户离开屏幕的一瞬间，判断变量开关来判断用户的操作是点击还是移动
     */
    private boolean isClick;//true为点击事件 fasle时 不是点击事件
    private ImageBarnnerListener listener;

    public ImageBarnnerListener getListener() {
        return listener;
    }

    public void setListener(ImageBarnnerListener listener) {
        this.listener = listener;
    }

    public interface ImageBarnnerListener{
        void clickImageIndex(int pos);//pos代表我们当前图片的具体索引值
    }
    private ImageBarnnerViewGroupListenner barnnerViewGroupListenner;

    public ImageBarnnerViewGroupListenner getBarnnerViewGroupListenner() {
        return barnnerViewGroupListenner;
    }

    public void setBarnnerViewGroupListenner(ImageBarnnerViewGroupListenner barnnerViewGroupListenner) {
        this.barnnerViewGroupListenner = barnnerViewGroupListenner;
    }

    /**
     * 要想实现底部远点以及底部远点切换功能的步骤思路
     * 1.我们需要自定义一个继承自frameLayout,利用frameLayout，布局特性（在同一位置放置不同的view最终显示的是最后一个view，），我们
     * 就可以实现底部原点的布局
     * 2.我们需要准备素材 底部原点的素材，我们可以利用drawable的功能去实现一个原点的图片的展示
     * 3.我们就需要继承frameLayout来自定义一S个类，在该类的实现过程中，我们去加载我们刚才自定义的图片轮播核心类和需要实现的底部原点的布局
     * linearLayout 来实现
     */

    //自动轮播
    private boolean isAuto=true;//默认情况下是开启自动轮播
    private Timer timer = new Timer();
    private TimerTask task;
    private Handler autoHandlet = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0://此时我们需要图片的自动轮播
                    if (++index>=children){//说明此时如果是最后一张图，我们将会从第一张图片图片重新滑动
                    index=0;
                }
                scrollTo(childwidth*index,0);//1
                    barnnerViewGroupListenner.selectImage(index);
                    break;
            }
        }
    };

    private void startAuto() {
         isAuto=true;
    }

    private void stopAuto() {
       isAuto=false;
    }

    /**
     * 采用timer，TimerTask，hander 三者相结合的方式来实现自动轮播
     * 会抽取出两个方法来控制是否启动自动轮播，称之为startAuto(),stopAuto()
     * 那么我们在2个方法的控制过程中，我们实际上希望控制的是自动开启轮播图的开关
     * 那么我们就需要一个变量参数，来作为 我们自动开启轮播图的开关，我们称之为 isAuto boolean true代表开启，false 代表关闭
     */
    public ImageBarnnertViewGroup(Context context) {
        super(context);
        initObj();
    }


    public ImageBarnnertViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBarnnertViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj() {
        scroller = new Scroller(getContext());

        task=new TimerTask() {
            @Override
            public void run() {
                if (isAuto){//开启轮播图
                    autoHandlet.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(task,100,3000);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    /**
     * 我们在自定义viewgroup中，我们必须要实现的方法有：测量-》布局-》绘制
     * 那么对于来说就是，onMeasure()
     * 我们对于绘制来说，因为我们是自定义的viewgroup容器，针对容器的绘制，
     * 其实就是容器内的子控件的绘制过程，那么我们只需要调用系统自带的绘制即可，也就说，对于viewgroup绘制过程我们不需要重写该方法
     * 调用系统自带的即可
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 由于我们要实现的是一个ViewGroup的容器，那么
         * 我们就应该需要知道该容器中的所有字视图
         * 我们要想测量我们ViewGroup的宽度和高度，那么我们就必须先要去测量子视图
         * 的宽度和高度纸盒，才能知道我们的viewGRoup的宽度和高度是多少
         */
        //1.求出子视图的个数
        //我们就可以知道子视图的个数
        children = getChildCount();
        if (0 == children) {
            setMeasuredDimension(0, 0);
        } else {
            //2.测量子视图的宽度和高度
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            //此时我们以第一个子视图为基准，也就是说我们的viewGroup的高度就是我们第一个子视图
            //的高度，宽度就是我们第一个子视图的宽度*子视图的个数
            View view = getChildAt(0);//因为此时第一个视图绝对是存在的
            childwidth = view.getMeasuredWidth();
            //3.根据子视图的宽度和高度，来求出该viewgroup的宽度和高度
            childheight = view.getMeasuredHeight();
            int width = view.getMeasuredWidth() * children;//宽度使我们所有子视图的宽度总和
            setMeasuredDimension(width, childheight);

        }

    }

    /**
     * 时间传递过程中的调用方法，我们需要调用容器的拦截方法onInterceptTouchEvent
     * 针对于该方法我们可以理解为，如果说该方法的返回值为TRUe的时候，那么我们自定义的viewgroup容器就会处理此次拦截时间
     * 如果说返回值为false的时候，那么我么自定义的viewgroup容器将不会接收此次事件的处理过程，将会继续向下传递该时间
     * 针对于我们自定义的viewgtoup 我们当然是希望我们的viewgroup 容器处理接收事件 那么我们的返回值就是true
     * 如果返回true的话真正处理该事件的方法是onTouch方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 用两种方式来实现轮播图的手动轮播
     * 1.利用scrollto、scrollby完成轮播图的手动轮播
     * 2.利用scroller 对象完成轮播图的手动轮播
     * <p>
     * 第一:我们在滑动屏幕图片的过程中，其实就是我们自定义viewgroup的子视图的移动过程，那么我们只需要知道
     * 滑动之前的横坐标，和滑动之后的纵坐标，此时我们就可以 求出我们此过程中移动的距离，我们在利用scrollby
     * 方法实现图片的滑动，所以说我们此时需要两个值，是需要我们求出的，移动之前、移动之后横坐标的值
     * <p>
     * 第二:在我们 第一次 按下得那一瞬间，此时的移动之前和移动之后的值是相等的，y也就是 我们此时按下那一瞬间的那一个点的横坐标的值
     * 横坐标的值
     * <p>
     * 第三：我们在不断的滑动过程中是不会不断调用我们的action_move那么此时我们就应该将移动之前的值和移动之后的值进行保存
     * 一遍我们能够 算出滑出的距离
     * <p>
     * 第四：在我们抬起的那一瞬间，我们需要计算出我们此时将要 滑动到哪一张图片的位置上
     * 我们此时就需要求得出将要滑动到的那张图片的索引值
     * (我们当前viewgroup的滑动位置+我们的每一张图片的k宽度/2)/我们每一张图片的宽度值
     * <p>
     * 此时我们就可以利用到scrollto方法，滑动到图片的位置上
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://表示用户按下一瞬间
                stopAuto();
                //在图片滑动过程中再次滑动不会出现重叠效果
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                isClick=true;
                x = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE://表示的是用户 按下之后 再屏幕移动的过程
                int moveX = (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance, 0);
                isClick=false;
                x = moveX;
                break;
            case MotionEvent.ACTION_UP://表示的是用户抬起的一瞬间
                int scrollX = getScrollX();
                index = (scrollX + childwidth / 2) / childwidth;
                if (index < 0) {//说明了此时已经滑动到了最左边的第一张图片
                    index = 0;
                } else if (index > children - 1) {
                    index = children - 1;//说明了此时已经滑动到了最右边最后一张图片
                }
                if (isClick){//点击事件
                    listener.clickImageIndex(index);
                }else{//非点击事件
                    int dx = index * childwidth - scrollX;
                    scroller.startScroll(scrollX, 0, dx, 0);
                    postInvalidate();//2
                    barnnerViewGroupListenner.selectImage(index);
                }


                startAuto();
                //scrollTo(index * childwidth,0);
                break;
            default:
                break;
        }
        return true;//返回true的目的是告诉我们该viewgroup容器的父view,我们已经处理好了该事件
    }

    /**
     * 继承ViewGroup必须要实现布局onLayout方法
     *
     * @param changed 代表的是当我们的viewgroup布局位置发生改变的为true，没有发生改变为false
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int leftMargin = 0;
            for (int i = 0; i < children; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin, 0, leftMargin + childwidth, childwidth);
                leftMargin += childwidth;
            }
        }

    }
    public interface ImageBarnnerViewGroupListenner{
        void selectImage(int index);
    }
}
