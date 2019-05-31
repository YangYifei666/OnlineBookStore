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
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences, sharedPreferencesU;
    TextView bookName,price;
    EditText phone,address;
    Button payment,back;
    String serverurl="http://yangyifei.000webhostapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_infor);

        bookName=findViewById(R.id.bookName);
        price=findViewById(R.id.price);

        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);

        payment=findViewById(R.id.payment);
        back=findViewById(R.id.back);

        sharedPreferences=getSharedPreferences("book_details", Context.MODE_PRIVATE);
        sharedPreferencesU=getSharedPreferences("user_details", Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        bookName.setText(sharedPreferences.getString("bookName",null));
        price.setText(sharedPreferences.getString("price",null));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BookName=sharedPreferences.getString("bookName",null);
                String Price=sharedPreferences.getString("price",null);
                String BookID=sharedPreferences.getString("bookID",null);
                String BuyerName=sharedPreferencesU.getString("username",null);
                String SellerName=sharedPreferences.getString("sellerName",null);
                String Num=sharedPreferences.getString("num",null);
                int Num1= Integer.parseInt(Num)-1;
                String Num2 = Integer.toString(Num1);
                String Phone=phone.getText().toString().trim();
                String Address=address.getText().toString().trim();
                PaymentBook(BookID,BookName,Price,BuyerName,SellerName,Phone,Address);
                SellBook(Num2,BookID);
            }
        });
    }
        private void PaymentBook(final String BookID,final String BookName,final String Price,final String BuyerName,final String SellerName,final String Phone,final String Address){
        class PaymentBook extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("bookID", BookID);
                hashMap.put("bookName", BookName);
                hashMap.put("price",Price);
                hashMap.put("phone",Phone);
                hashMap.put("address",Address);
                hashMap.put("buyerName",BuyerName);
                hashMap.put("sellerName",SellerName);
                Handler handler = new Handler();
                String s = handler.sendPostRequest(serverurl+"/payment.php", hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("success")) {
                    sharedPreferences.edit().clear();
                    sharedPreferences.edit().commit();
                    Toast.makeText(PaymentActivity.this, "Success",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PaymentActivity.this, "Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        PaymentBook  paymentBook = new PaymentBook ();
        paymentBook.execute();
    }
    private void SellBook(final String Num,final String BookID){
        class SellBook extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("bookID", BookID);
                hashMap.put("num", Num);
                Handler handler = new Handler();
                String s = handler.sendPostRequest(serverurl+"/sell.php", hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("success")) {
                    sharedPreferences.edit().clear();
                    sharedPreferences.edit().commit();
                    Toast.makeText(PaymentActivity.this, "Success",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PaymentActivity.this, "Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        SellBook  sellBook = new SellBook ();
        sellBook.execute();
    }
}
