package example.sdbi.com.easypointweather.ForgetPassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import example.sdbi.com.easypointweather.DBInformation.Users;
import example.sdbi.com.easypointweather.Login.MainActivity;
import example.sdbi.com.easypointweather.R;

public class SuccessPassword extends AppCompatActivity {
    private EditText inputPassword,againPassword;
    private Button successEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_password);
        init();
        final Intent intent=getIntent();
        final String phoneNumber=intent.getStringExtra("phoneNumber");
        successEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputPassword.getText().toString().equals(againPassword.getText().toString())){
                    Intent i = new Intent(SuccessPassword.this, MainActivity.class);
                    Users users=new Users();
                    users.setPassWord(inputPassword.getText().toString());
                    users.updateAll("phoneNumber=?",phoneNumber);
                    Toast.makeText(SuccessPassword.this,"修改成功",Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }else {
                    inputPassword.setText("");
                    againPassword.setText("");
                    Toast.makeText(SuccessPassword.this,"密码不一致,请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void init(){
        inputPassword= (EditText) findViewById(R.id.input_password);
        againPassword= (EditText) findViewById(R.id.input_password);
        successEdt= (Button) findViewById(R.id.Indentity);
    }
}
