package com.example.maket.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maket.Database.AccountDatabase;
import com.example.maket.Entity.Account;
import com.example.maket.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mEdt_user;
    private EditText mEdt_pass;
    private CheckBox cb_remember;
    private Button mbtn_login;
    private TextView mImageView_addacc;
    private ImageView mImageView_cnfb;
    private ImageView mImageView_cngm;
    private TextView mImageView_Reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();


//        Bundle bundle = getIntent().getExtras();
//        String us = bundle.getString("user");
//        String ps = bundle.getString("pass");
//        mEdt_user.setText(us);
//        mEdt_pass.setText(ps);
        CheckLogin();

        mbtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            login();
            }
        });
        mImageView_addacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= new Intent(MainActivity.this,SignUpActivity2.class);
               startActivity(intent);
            }
        });

    }
    private void login(){

        SharedPreferences preferences =MainActivity.this.getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String user = mEdt_user.getText().toString();
        String pass = mEdt_pass.getText().toString();

        AccountDatabase database = AccountDatabase.getInstance(getApplicationContext());

        List<Account> accountList = database.daoAccount().ACCOUNT_LIST();

        String us = null;
        String ps = null;

        for( Account account : accountList){
            if (user.equals(account.getUser())){
                us=account.getUser();
                ps = account.getPass();
                Log.e("USER",""+us+""+ps);
                break;
            }
        }
        if(user.isEmpty()){
            mEdt_user.setError("Tên đăng nhập trống !");
            return;
        }
        if (user.equals(us) == false){
            mEdt_user.setError("Tài khoản không tồn tại !");
            return;
        }
        if(pass.isEmpty()){
            mEdt_pass.setError("Mật khẩu trống !");
            return;
        }
        if (pass.equals(ps) == false){
            mEdt_pass.setError("Mật khẩu không đúng !");
            return;
        }
        if(cb_remember.isChecked()){

            editor.putString("user",user);
            editor.putString("pass",pass);
            editor.putBoolean("check",true);
            editor.commit();

        }
        else if (cb_remember.isChecked() == false){
            editor.clear();
        }
        Intent intent = new Intent(MainActivity.this,Home_Activity2.class);
        startActivity(intent);
    }

    private void CheckLogin(){
        SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
        boolean check = preferences.getBoolean("check",false);

        if(check){
            String user = preferences.getString("user",null);
            String pass = preferences.getString("pass",null);
            mEdt_user.setText(user);
            mEdt_pass.setText(pass);
            cb_remember.isChecked();
        }
        else{
            mEdt_user.setText("");
            mEdt_pass.setText("");
        }
    }

    private void mapping() {
        mEdt_user =findViewById(R.id.edt_user);
        mEdt_pass =findViewById(R.id.edt_pass);
        cb_remember=findViewById(R.id.cb_remember);
        mbtn_login=findViewById(R.id.btn_login);
        mImageView_addacc=findViewById(R.id.imv_addacc);
        mImageView_cnfb=findViewById(R.id.imv_fb);
        mImageView_cngm=findViewById(R.id.imv_gm);
    }
}