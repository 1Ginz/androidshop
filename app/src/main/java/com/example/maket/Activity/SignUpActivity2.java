package com.example.maket.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maket.Database.AccountDatabase;
import com.example.maket.Entity.Account;
import com.example.maket.R;

import java.util.List;

public class SignUpActivity2 extends AppCompatActivity {
    private EditText mEditText_user;
    private EditText mEditText_pass1;
    private EditText mEditText_pass2;
    private Button mButton_signup;
    private Context context;
    private List<Account> accountList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        mapping();
        mButton_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = mEditText_user.getText().toString();
                String pas1 = mEditText_pass1.getText().toString();
                String pas2 =mEditText_pass2.getText().toString();
                AccountDatabase database = AccountDatabase.getInstance(getApplicationContext());
                accountList = database.daoAccount().ACCOUNT_LIST();
                for (Account account : accountList){
                    if (us.equals(account.getUser())){
                        mEditText_user.setError("Tên đăng nhập đã tồn tại");
                        return;
                    }
                }
                if(us.isEmpty()){
                    mEditText_user.setError("Tên đăng nhập trống");
                    return;
                }
                if(pas1.isEmpty()){
                    mEditText_pass1.setError("Mật khẩu trống");
                    return;
                }
                if(pas1.equals(pas2) == false){
                    mEditText_pass2.setError("Xác nhận không đúng !");
                    return;
                }
                else{
                    try {
                        Account account = new Account(us,pas1);
                        AccountDatabase database2 =AccountDatabase.getInstance(getApplicationContext());
                        database.daoAccount().insert_account(account);
                        Intent intent =new Intent(SignUpActivity2.this,MainActivity.class);
                        Toast.makeText(getApplicationContext(),
                        "Đăng kí thành công !", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }catch (Exception e){
                        Log.e("ERRO",""+e);
                    }

                }


            }
        });
    }

    private void mapping() {
        mEditText_user=findViewById(R.id.edt_user_sup);
        mEditText_pass1=findViewById(R.id.edt_pass_sup);
        mEditText_pass2=findViewById(R.id.edt_pass2_sup);
        mButton_signup=findViewById(R.id.btn_signup);
    }
}