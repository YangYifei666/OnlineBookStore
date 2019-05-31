package com.e.midterma181.onlinebookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    SharedPreferences preferences;
    TextView login;
    EditText username,password;
    Button reg;
    String serverurl="http://yangyifei.000webhostapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        reg=findViewById(R.id.register);
        login=findViewById(R.id.login);

        preferences=getSharedPreferences("user_details",MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username=username.getText().toString().trim();
                String Password=password.getText().toString().trim();
                registerUser(Username,Password);
            }
        });
    }
    private void registerUser(final String Username,final String Password) {
        class RegisterUser extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this,
                        "Registration","...",false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("username",Username);
                hashMap.put("password",Password);
                Handler handler = new Handler();
                String s = handler.sendPostRequest(serverurl+"/register.php", hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("success")) {
                    Toast.makeText(RegisterActivity.this, "Success",
                            Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username",Username);
                    editor.commit();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
            RegisterUser  ruser = new RegisterUser ();
            ruser.execute();
    }
}
