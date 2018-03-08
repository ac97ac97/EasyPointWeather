package example.sdbi.com.easypointweather.Register;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.litepal.tablemanager.Connector;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import example.sdbi.com.easypointweather.R;
import example.sdbi.com.easypointweather.DBInformation.Users;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener {

    private Button getCode;
    private Button Identity;
    private EditText PhoneEd;
    private EditText CodeEd;
    private EditText inputPassword;
    private EditText againPassword;
    private String AppKey = "233b275286a15";
    private String APPSECRET = "934f82f2f34a5c8aab87f85bdafaf675";
    public String phone;

    private String CodeText;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE && inputPassword.getText().toString().equals(againPassword.getText().toString())) {
                    Users users = new Users();
                    users.setPhoneNumber(PhoneEd.getText().toString());
                    users.setPassWord(inputPassword.getText().toString());
                    users.save();
                    Toast.makeText(getApplicationContext(), "注册成功",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {

                    Toast.makeText(getApplicationContext(), "请输入正确验证码",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(Register.this, des,
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Connector.getDatabase();
        init();
        SMSSDK.initSDK(this, AppKey, APPSECRET);
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
    }

    private void init() {

        getCode = (Button) findViewById(R.id.getCode);
        Identity = (Button) findViewById(R.id.Indentity);
        PhoneEd = (EditText) findViewById(R.id.PhoneEd);
        CodeEd = (EditText) findViewById(R.id.Code);
        inputPassword = findViewById(R.id.input_password);
        againPassword = findViewById(R.id.again_password);
        getCode.setOnClickListener(this);
        Identity.setOnClickListener(this);
    }

    private class SmsObserver extends ContentObserver {

        public SmsObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onChange(boolean selfChange) {
            // TODO Auto-generated method stub
            StringBuilder sb = new StringBuilder();
            Cursor cursor = getContentResolver().query(
                    Uri.parse("content://sms/inbox"), null, null, null, null);
            cursor.moveToNext();
            sb.append("body=" + cursor.getString(cursor.getColumnIndex("body")));

            System.out.println(sb.toString());
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(sb.toString());
            CodeText = matcher.replaceAll("");
            CodeEd.setText(CodeText);
            cursor.close();
            super.onChange(selfChange);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.getCode:
                if (!TextUtils.isEmpty(PhoneEd.getText().toString())) {
                    getContentResolver().registerContentObserver(
                            Uri.parse("content://sms"), true,
                            new SmsObserver(new Handler()));
                    SMSSDK.getVerificationCode("86", PhoneEd.getText().toString());
                    phone = PhoneEd.getText().toString();

                } else {
                    Toast.makeText(Register.this, "手机号码不可为空", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case R.id.Indentity:
                SMSSDK.submitVerificationCode("86", phone, CodeEd.getText()
                        .toString());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        getContentResolver().unregisterContentObserver(new SmsObserver(handler));
        super.onDestroy();
    }
}
