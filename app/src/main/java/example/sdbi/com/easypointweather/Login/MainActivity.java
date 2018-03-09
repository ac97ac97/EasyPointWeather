package example.sdbi.com.easypointweather.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import example.sdbi.com.easypointweather.ForgetPassword.ForgetPassWord;
import example.sdbi.com.easypointweather.Index.IndexActivity;
import example.sdbi.com.easypointweather.R;
import example.sdbi.com.easypointweather.Register.Register;
import example.sdbi.com.easypointweather.DBInformation.Users;
import example.sdbi.com.easypointweather.Splashscreen.CancelSplashScreen;

public class MainActivity extends AppCompatActivity {
    private Button login, cancel;
    private EditText accountEdt, passwordEdt;
    private TextView register;
    private TextView forgetPassword;
    private String account, password;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass, autoLogin;
    private String user, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        init();
        boolean isRemember = pref.getBoolean("remember_password", false);
        boolean isAuto = pref.getBoolean("auto_login", false);
        if (isRemember) {
            // 将账号和密码都设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdt.setText(account);
            accountEdt.setSelection(account.length());
            passwordEdt.setText(password);
            rememberPass.setChecked(true);
            if (isAuto) {
                autoLogin.setChecked(true);
                Intent intent = new Intent(MainActivity.this,
                        IndexActivity.class);
                startActivity(intent);
            }
        }

        /**
         * 注册
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
        /**
         * 登录 需要一个中间变量 将从数据库中取出的数据 暂存
         */

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Users> userses = DataSupport.findAll(Users.class);
                String account_input = accountEdt.getText().toString();
                for (Users users : userses) {
                    Log.d("abc", "phoneNumber is " + users.getPhoneNumber() + "\n" + "passWord is " + users.getPassWord());
                    account = users.getPhoneNumber();
                    password = users.getPassWord();
                    if (account_input.equals(account)) {
                        pwd = password;
                        user = account;
                    }

                }

                if (accountEdt.getText().toString().equals(user) && passwordEdt.getText().toString().equals(pwd)) {
                    Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {// 复选框是否被选中
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.commit();
                    startActivity(intent);
                } else if (accountEdt.getText().toString().equals("") || passwordEdt.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * 记住密码
         */
        rememberPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (rememberPass.isChecked()) {
                    pref.edit().putBoolean("remember_password", true).commit();
                } else {
                    pref.edit().putBoolean("remember_password", false).commit();
                }
            }
        });
        /**
         * 自动登录
         */

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (autoLogin.isChecked()) {
                    pref.edit().putBoolean("auto_login", true).commit();
                } else {
                    pref.edit().putBoolean("auto_login", false).commit();
                }
            }
        });

        /**
         * 忘记密码
         */
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgetPassWord.class);
                startActivity(intent);
            }
        });
        /**
         * 取消登录
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountEdt.setText("");
                passwordEdt.setText("");
                Intent intent = new Intent(MainActivity.this, CancelSplashScreen.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void init() {
        login = (Button) findViewById(R.id.btn_login);
        register = (TextView) findViewById(R.id.register);
        accountEdt = (EditText) findViewById(R.id.et_number);
        passwordEdt = (EditText) findViewById(R.id.et_password);
        forgetPassword = (TextView) findViewById(R.id.forget_password);
        cancel = (Button) findViewById(R.id.btn_cancel);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        autoLogin = (CheckBox) findViewById(R.id.auto_login);
    }
}
