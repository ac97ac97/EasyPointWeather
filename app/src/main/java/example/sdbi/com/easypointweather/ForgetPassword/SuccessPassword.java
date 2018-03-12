package example.sdbi.com.easypointweather.ForgetPassword;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
                    AlertDialog.Builder dialog=new AlertDialog.Builder(SuccessPassword.this);
                    dialog.setTitle("易点天气提示");
                    dialog.setIcon(R.drawable.icon_dialog                                                                                                                             );
                    dialog.setMessage("确定修改？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(SuccessPassword.this, MainActivity.class);
                            Users users=new Users();

                            users.setPassWord(inputPassword.getText().toString());
                            users.updateAll("phoneNumber=?",phoneNumber);
                            Toast.makeText(SuccessPassword.this,"修改成功",Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                    });
                   dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                          Intent i2=new Intent(SuccessPassword.this,MainActivity.class);
                           Toast.makeText(SuccessPassword.this,"修改失败",Toast.LENGTH_SHORT).show();
                           startActivity(i2);
                       }
                   });
                    dialog.show();
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
