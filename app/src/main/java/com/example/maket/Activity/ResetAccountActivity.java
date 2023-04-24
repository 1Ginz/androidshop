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

public class ResetAccountActivity extends AppCompatActivity {
    private EditText mEditText_user;
    private EditText mEditText_pas1;
    private EditText mEditText_pas2;
    private Button mButton_reset;
    private List <Account> accountList ;
    private Context context;
    private Account account_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_account);
        mEditText_user=findViewById(R.id.edt_user_reset);
        mEditText_pas1 = findViewById(R.id.edt_pass1_reset);
        mEditText_pas2 = findViewById(R.id.edt_pass2_reset);
        mButton_reset = findViewById(R.id.btn_reset_pass);

        mButton_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user= mEditText_user.getText().toString();
                String pass1= mEditText_pas1.getText().toString();
                String pass2= mEditText_pas2.getText().toString();
                String us = null;


                AccountDatabase database =AccountDatabase.getInstance(getApplicationContext());
                accountList = database.daoAccount().ACCOUNT_LIST();

                for ( Account account : accountList){
                    if(user.equals(account.getUser())){
                        account_update=account;
                        us=account.getUser();
                        break;
                    }
                }

                if(user.equals(us) == false){
                    mEditText_user.setError("Tài khoản không tồn tại");
                    return;
                }
                if(pass1.isEmpty()){
                    mEditText_pas1.setError("Mật khẩu trống");
                    return;
                }
                if(pass2.isEmpty()){
                    mEditText_pas2.setError("Mật khẩu trống");
                    return;
                }
                if(pass2.equals(pass1) == false){
                    mEditText_pas2.setError("Xác nhận mật khẩu không đúng");
                    return;
                }
                else{
                    account_update.setPass(pass2);
                    try {
                        database.daoAccount().update_account(account_update);
                        Toast.makeText(getApplicationContext(), "Mật khẩu của bạn đã được thay đổi", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetAccountActivity.this,MainActivity.class);
                        startActivity(intent);
                    }catch (Exception e){
                        Log.e("ERRO",""+e);
                    }

                }
            }
        });



    }
}