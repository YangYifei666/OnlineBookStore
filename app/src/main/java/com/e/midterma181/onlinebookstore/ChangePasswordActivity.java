package com.e.midterma181.onlinebookstore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText password,password2;
    Button confrim,back;
    String serverurl="http://yangyifei.000webhostapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change);

        password=findViewById(R.id.editText2);
        password2=findViewById(R.id.editText3);

        confrim=findViewById(R.id.confirm);
        back=findViewById(R.id.back);

        sharedPreferences=getSharedPreferences("user_details", Context.MODE_PRIVATE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password=password.getText().toString().trim();
                String Password2=password2.getText().toString().trim();
                String UserName=sharedPreferences.getString("username",null);
                if (Password.equals(Password2)){
                    ChangePassword(UserName,Password);
                }else {
                    Toast.makeText(ChangePasswordActivity.this, "Please enter same password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void ChangePassword(final String Username,final String Password){
        class ChangePassword extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("username",Username);
                hashMap.put("password",Password);
                Handler handler = new Handler();
                String s = handler.sendPostRequest(serverurl+"/password_change.php", hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("success")) {
                    Toast.makeText(ChangePasswordActivity.this, "Success",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        ChangePassword  changePassword = new ChangePassword ();
        changePassword.execute();
    }
}
