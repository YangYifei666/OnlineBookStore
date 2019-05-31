package com.e.midterma181.onlinebookstore;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentOne extends Fragment {
    SharedPreferences sharedPreferences,sharedPreferencesB;
    ListView bookList;
    Dialog myDialogWindow;
    ArrayList<HashMap<String, String>> booklist;
    Spinner sploc;
    String BookName,Price,Num,Phone,Intro,SalerName,BookID;
    String serverurl="http://yangyifei.000webhostapp.com";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
    View view = inflater.inflate(R.layout.fragment_one, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences=getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        sharedPreferencesB=getActivity().getSharedPreferences("book_details", Context.MODE_PRIVATE);

        bookList=getActivity().findViewById(R.id.booklist);
        sploc = getActivity().findViewById(R.id.bookType);
        loadBook(sploc.getSelectedItem().toString());
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (booklist.get(position).get("salerName").equals(sharedPreferences.getString("username",null))){
                    loadWindowS(position);
                }else {
                    loadWindowB(position);
                }
            }
        });
        sploc.setSelection(0,false);
        sploc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadBook(sploc.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void loadWindowS(int p) {
        myDialogWindow = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        myDialogWindow.setContentView(R.layout.book_edit);
        Button back = myDialogWindow.findViewById(R.id.back);
        Button edit = myDialogWindow.findViewById(R.id.edit);
        final EditText bookName,price,num,phone,intro;
        final TextView bookID;
        bookName=myDialogWindow.findViewById(R.id.bookName);
        price=myDialogWindow.findViewById(R.id.price);
        num=myDialogWindow.findViewById(R.id.num);
        phone=myDialogWindow.findViewById(R.id.phone);
        intro=myDialogWindow.findViewById(R.id.intro);
        bookID=myDialogWindow.findViewById(R.id.bookID);
        bookName.setText(booklist.get(p).get("bookName"));
        price.setText(booklist.get(p).get("price"));
        num.setText(booklist.get(p).get("num"));
        phone.setText(booklist.get(p).get("phone"));
        intro.setText(booklist.get(p).get("intro"));
        bookID.setText(booklist.get(p).get("bookID"));
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BN=bookName.getText().toString().trim();
                String Pr=price.getText().toString().trim();
                String N=num.getText().toString().trim();
                String Ph=phone.getText().toString().trim();
                String I=intro.getText().toString().trim();
                String BID= bookID.getText().toString().trim();
                EditBook(BN,Pr,N,Ph,I,BID);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogWindow.dismiss();
            }
        });
        myDialogWindow.show();
    }
    private void EditBook(final String BookName,final String Price,final String Num,final String Phone,final String Intro,final String BookID) {
        class EditBook extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("bookName", BookName);
                hashMap.put("price", Price);
                hashMap.put("num", Num);
                hashMap.put("phone", Phone);
                hashMap.put("intro", Intro);
                hashMap.put("bookID", BookID);
                Handler handler = new Handler();
                String s = handler.sendPostRequest(serverurl + "/editBook.php", hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("success")) {
                    Toast.makeText(getActivity(), "Success",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        try {
            EditBook  editBook = new EditBook ();
            editBook.execute();
        }catch(Exception e){}
    }
        private void loadWindowB( int p){
            myDialogWindow = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
            myDialogWindow.setContentView(R.layout.book_show);
            Button back = myDialogWindow.findViewById(R.id.back);
            Button buy = myDialogWindow.findViewById(R.id.buy);
            final TextView bookName, price, num, phone, intro,salerName;
            final String BookName,Price,Book_ID,SellerName,BookNum;
            bookName = myDialogWindow.findViewById(R.id.bookName);
            price = myDialogWindow.findViewById(R.id.textView11);
            num = myDialogWindow.findViewById(R.id.num);
            phone = myDialogWindow.findViewById(R.id.phone);
            intro = myDialogWindow.findViewById(R.id.intro);
            salerName = myDialogWindow.findViewById(R.id.salerName);
            BookName = booklist.get(p).get("bookName");
            Price = booklist.get(p).get("price");
            Book_ID=booklist.get(p).get("bookID");
            SellerName=booklist.get(p).get("salerName");
            BookNum=booklist.get(p).get("num");
            bookName.setText(BookName);
            price.setText(Price);
            num.setText(BookNum);
            phone.setText(booklist.get(p).get("phone"));
            intro.setText(booklist.get(p).get("intro"));
            salerName.setText(booklist.get(p).get("salerName"));
            SharedPreferences.Editor editor = sharedPreferencesB.edit();
            editor.putString("bookName",BookName);
            editor.putString("price",Price);
            editor.putString("bookID",Book_ID);
            editor.putString("sellerName",SellerName);
            editor.putString("num",BookNum);
            editor.commit();
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),PaymentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialogWindow.dismiss();
                }
            });
            myDialogWindow.show();
        }
    private void loadBook(final  String loc){
        class LoadBook extends AsyncTask<Void,Void,String>{

            @Override
            protected String doInBackground(Void... voids){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("bookType",loc);
                Handler handler=new Handler();
                booklist = new ArrayList<>();
                String s = handler.sendPostRequest(serverurl+"/load_book.php",hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                booklist.clear();
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray  restarray=jsonObject.getJSONArray("book");
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
                        getActivity(), booklist,
                        R.layout.book, new String[]
                        {"bookName","price"}, new int[]
                        {R.id.bookName,R.id.textView11});
                bookList.setAdapter(adapter);
            }
        }
        LoadBook loadBook = new LoadBook();
        loadBook.execute();
    }
}
