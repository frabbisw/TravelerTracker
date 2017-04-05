package com.example.frabbi.meem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frabbi.meem.R;

import org.w3c.dom.Text;

import static com.example.frabbi.meem.R.id.text_password;

public class ActivityLogin extends AppCompatActivity {

    private FrameLayout contentFrame;
    private LinearLayout loginContent;
    private LinearLayout registerContent;
    private TextView createAccount;
    private Button loginBtn;
    private Button register ;
    private EditText usrid;
    private EditText pswrd;


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_create_account:
                    contentFrame.removeAllViews();
                    contentFrame.addView(registerContent);
                    break;

                case R.id.btn_login_account:
                    loginToAccount();
                    break;

                case R.id.btn_register:
                    loginToAccount();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_layout);

        contentFrame = (FrameLayout)findViewById(R.id.content_frame);
        loginContent = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_login, contentFrame, false);
        registerContent = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_register, contentFrame, false);
        contentFrame.addView(loginContent);

        createAccount = (TextView) loginContent.findViewById(R.id.text_create_account);
        createAccount.setOnClickListener(clickListener);

        loginBtn = (Button) loginContent.findViewById(R.id.btn_login_account);
        loginBtn.setOnClickListener(clickListener);

        register = (Button) registerContent.findViewById(R.id.btn_register);
        register.setOnClickListener(clickListener);

        usrid = (EditText) loginContent.findViewById(R.id.userid);
        pswrd =  (EditText) loginContent.findViewById(R.id.text_password);
    }

    protected void loginToAccount(){
        if(usrid.getText().toString().isEmpty()) {
            Toast.makeText(this, "UserID required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pswrd.getText().toString().isEmpty()) {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(ActivityLogin.this,ActivityAccount.class));
        finish();
    }
}
