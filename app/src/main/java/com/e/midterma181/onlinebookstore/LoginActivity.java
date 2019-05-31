package com.e.midterma181.onlinebookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    SharedPreferences preferences;
    TextView reg;
    EditText username,password;
    Button log;
    CheckBox remember;
    String serverurl="http://yangyifei.000webhostapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        remember=findViewById(R.id.remember);
        reg=findViewById(R.id.register);
        log=findViewById(R.id.login);

        preferences=getSharedPreferences("user_details",MODE_PRIVATE);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString();
                String Password = password.getText().toString();
                savePref(Username,Password);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username=username.getText().toString().trim();
                String Password=password.getText().toString().trim();
                loginUser(Username,Password);
            }
        });
    }
    private void savePref(String u, String p) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", u);
        editor.putString("password", p);
        editor.commit();
        Toast.makeText(this, "Preferences has been saved", Toast.LENGTH_SHORT).show();
    }
    private void loginUser(final String Username, final String Password){
        class LoginUser extends AsyncTask<Void, Void, String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this,
                        "Login","...",false,false);
            }

            @Override
            protected String doInBackground(Void... params) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("username", Username);
                    hashMap.put("password", Password);
                    Handler handler=new Handler();
                    String s = handler.sendPostRequest(serverurl + "/login.php", hashMap);
                    return s;
        }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("success")) {
                    Toast.makeText(LoginActivity.this, s,
                            Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username",Username);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
            LoginUser  ruser = new LoginUser ();
            ruser.execute();
    }
}
