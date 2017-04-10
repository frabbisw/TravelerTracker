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

public class ActivityLogin extends AppCompatActivity {

    private FrameLayout contentFrame;
    private LinearLayout loginContent;
    private LinearLayout registerContent;
    private TextView createAccount;
    private Button loginBtn;
    private Button register;
    private EditText getuserid;
    private EditText getpassword;
    private EditText getnewuserid;
    private EditText getnewpassword;
    private EditText getname;
    private EditText getconfirmation;
    private TextView backToLogin;

    int currentState;
    final int loginState = 1;
    final int regState = 2;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_create_account:
                    showRegPage();
                    break;

                case R.id.text_change_login:
                    showLoginPage();
                    break;

                case R.id.btn_login_account:
                    loginToAccount();
                    break;

                case R.id.btn_register:
                    openAccount();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_layout);

        contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        loginContent = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_login, contentFrame, false);
        registerContent = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_register, contentFrame, false);
        showLoginPage();

        createAccount = (TextView) loginContent.findViewById(R.id.text_create_account);
        createAccount.setOnClickListener(clickListener);

        loginBtn = (Button) loginContent.findViewById(R.id.btn_login_account);
        loginBtn.setOnClickListener(clickListener);

        register = (Button) registerContent.findViewById(R.id.btn_register);
        register.setOnClickListener(clickListener);

        getuserid = (EditText) loginContent.findViewById(R.id.userid);
        getpassword = (EditText) loginContent.findViewById(R.id.text_password);

        getnewuserid = (EditText) registerContent.findViewById(R.id.newuserid);
        getnewpassword = (EditText) registerContent.findViewById(R.id.newpassword);

        getname = (EditText) registerContent.findViewById(R.id.name);
        getconfirmation = (EditText) registerContent.findViewById(R.id.confirmpassword);

        backToLogin = (TextView) registerContent.findViewById(R.id.text_change_login);
        backToLogin.setOnClickListener(clickListener);
    }

    protected void loginToAccount() {
        if (getuserid.getText().toString().isEmpty()) {
            Toast.makeText(this, "UserID required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getpassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
            return;
        }

        String id=getuserid.getText().toString();
        String password=getpassword.getText().toString();

        //startActivity(new Intent(ActivityLogin.this, ActivityAccount.class));
        //finish();

        ISystem.loadAccount(this, id, password);
    }

    protected void openAccount() {
        if (getnewuserid.getText().toString().isEmpty()) {
            Toast.makeText(this, "UserID required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (getname.getText().toString().isEmpty()) {
            Toast.makeText(this, "Profile Name required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (getnewpassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (getconfirmation.getText().toString().isEmpty()) {
            Toast.makeText(this, "Password confirmation required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!getconfirmation.getText().toString().equals(getnewpassword.getText().toString())) {
            Toast.makeText(this, "Password not mathced. Try again", Toast.LENGTH_SHORT).show();
            return;
        }

        //startActivity(new Intent(ActivityLogin.this, ActivityAccount.class));
        //finish();

        String id = getnewuserid.getText().toString();
        String name = getname.getText().toString();
        String password = getnewpassword.getText().toString();

        ISystem.saveAccount(this, new Account(id,name,password));

    }

    private void showRegPage(){
        contentFrame.removeAllViews();
        contentFrame.addView(registerContent);
        currentState=regState;
    }

    private void showLoginPage(){
        contentFrame.removeAllViews();
        contentFrame.addView(loginContent);
        currentState=loginState;
    }

    @Override
    public void onBackPressed() {
        if(currentState==loginState) super.onBackPressed();
        else showLoginPage();
    }
}
