package com.e.midterma181.onlinebookstore;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FindBookActivity extends AppCompatActivity {

    TextView bookName;
    Button search,back;
    Dialog myDialogWindow;
    ListView bookList;
    ArrayList<HashMap<String, String>> booklist;
    String Book_Name;
    String BookName,Price,Num,Phone,Intro,SalerName,BookID;
    String serverurl="http://yangyifei.000webhostapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_find);

        bookName=findViewById(R.id.book_Name);
        search=findViewById(R.id.search);
        back=findViewById(R.id.back);
        bookList=findViewById(R.id.book_list);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindBookActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    loadWindow(position);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book_Name = bookName.getText().toString().trim();
                FindBook(Book_Name);
            }
        });
    }
    private void loadWindow( int p){
        myDialogWindow = new Dialog(FindBookActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        myDialogWindow.setContentView(R.layout.book_show);
        Button back = myDialogWindow.findViewById(R.id.back);
        TextView bookName, price, num, phone, intro,salerName;
        bookName = myDialogWindow.findViewById(R.id.bookName);
        price = myDialogWindow.findViewById(R.id.textView11);
        num = myDialogWindow.findViewById(R.id.num);
        phone = myDialogWindow.findViewById(R.id.phone);
        intro = myDialogWindow.findViewById(R.id.intro);
        salerName = myDialogWindow.findViewById(R.id.salerName);
        bookName.setText(booklist.get(p).get("bookName"));
        price.setText(booklist.get(p).get("price"));
        num.setText(booklist.get(p).get("num"));
        phone.setText(booklist.get(p).get("phone"));
        intro.setText(booklist.get(p).get("intro"));
        salerName.setText(booklist.get(p).get("salerName"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogWindow.dismiss();
            }
        });
        myDialogWindow.show();
    }
    private void FindBook(final String Book_Name){
        class FindBook extends AsyncTask<Void,Void,String> {
            @Override
            protected String doInBackground(Void... voids){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("bookName",Book_Name);
                Handler handler=new Handler();
                booklist = new ArrayList<>();
                String s = handler.sendPostRequest(serverurl+"/find_book.php",hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                booklist.clear();
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray restarray=jsonObject.getJSONArray("book");
                    Log.e("HANIS",jsonObject.toString());
                    for (int i=0; i<restarray.length();i++){
                        JSONObject c = restarray.getJSONObject(i);
                        BookName = c.getString("bookName");
                        Price = c.getString("price");
                        Num = c.getString("num");
                        Phone = c.getString("phone");
                        Intro = c.getString("intro");
                        SalerName = c.getString("salerName");
                        BookID = c.getString("bookID");
                        HashMap<String,String> booklisthash = new HashMap<>();
                        booklisthash.put("bookName",BookName);
                        booklisthash.put("price",Price);
                        booklisthash.put("num",Num);
                        booklisthash.put("phone",Phone);
                        booklisthash.put("intro",Intro);
                        booklisthash.put("salerName",SalerName);
                        booklisthash.put("bookID",BookID);
                        booklist.add(booklisthash);
                    }
                } catch (final JSONException e) {
                    Log.e("JSONERROR",e.toString());
                }
                ListAdapter adapter = new SimpleAdapter(
                        FindBookActivity.this, booklist,
                        R.layout.book, new String[]
                        {"bookName","price"}, new int[]
                        {R.id.bookName,R.id.textView11});
                bookList.setAdapter(adapter);
            }
        }
        FindBook findBook = new FindBook();
        findBook.execute();
    }
}
