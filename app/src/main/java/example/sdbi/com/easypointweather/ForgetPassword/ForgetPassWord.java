package example.sdbi.com.easypointweather.ForgetPassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import example.sdbi.com.easypointweather.R;

/**
 * Created by Administrator on 2018/3/8.
 */

public class ForgetPassWord extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(example.sdbi.com.easypointweather.R.layout.forget_password);
        final EditText saveNumber=findViewById(R.id.forgetEdt);
        Button startToNext=findViewById(R.id.startToNext);
        startToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgetPassWord.this,EdtPassword.class);
                intent.putExtra("number",saveNumber.getText().toString());
                startActivity(intent);
            }
        });
    }
}
