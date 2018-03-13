package example.sdbi.com.easypointweather.Splashscreen;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import example.sdbi.com.easypointweather.Login.MainActivity;
import example.sdbi.com.easypointweather.R;

/**
 * Created by Administrator on 2017/12/27.
 */

public class SplashScreenActivity extends Activity {
    private MyCountDownTimer mc;
    private TextView tv;

    /**
     * 是否为第一次使用
     */
//    private boolean isFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen_layout);

        View view = findViewById(R.id.mainactivity);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3000);
        view.startAnimation(alphaAnimation);
        tv = (TextView) findViewById(R.id.timer);
        mc = new MyCountDownTimer(5000, 1000);
        mc.start();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                SharedPreferences pr = getSharedPreferences("isFirst",MODE_WORLD_READABLE);
//                isFirst=pr.getBoolean("isFirst",true);
//                if (isFirst){
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
//                }else {
//                    Intent intent = new Intent(SplashScreenActivity.this, SplashScreenActivity.class);
//                    startActivity(intent);
//                }

                SplashScreenActivity.this.finish();
//                SharedPreferences.Editor editor = pr.edit();
//               editor.putBoolean("isFirst",false);
//                editor.commit();
            }
        }, 5000);
    }

    private Handler handler = new Handler();

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            tv.setText("跳过");
        }

        public void onTick(long millisUntilFinished) {
            tv.setText(millisUntilFinished / 1000 + " 秒跳过");
        }
    }
}
