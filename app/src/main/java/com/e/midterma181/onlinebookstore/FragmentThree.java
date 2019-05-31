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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentThree extends Fragment {
    SharedPreferences sharedPreferences;
    Dialog myDialogWindow;
    ListView orderList;
    ArrayList<HashMap<String, String>> orderlistB,orderlistS;
    Button logout,changePassword,buyOrder,sellOrder;
    TextView name;
    String serverurl="http://yangyifei.000webhostapp.com";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name=getActivity().findViewById(R.id.username);
        logout=getActivity().findViewById(R.id.logout);
        changePassword=getActivity().findViewById(R.id.changePassword);
//        buyOrder=getActivity().findViewById(R.id.buyOrder);
        sellOrder=getActivity().findViewById(R.id.sellOrder);

        sharedPreferences=getActivity().getSharedPreferences("user_details",Context.MODE_PRIVATE);
        name.setText("Hello, "+sharedPreferences.getString("username",null));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(intent);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChangePasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
//        buyOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialogWindow = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
//                myDialogWindow.setContentView(R.layout.order_list);
//                final TextView bookName,price,phone,sellerName;
//                bookName = myDialogWindow.findViewById(R.id.textView21);
//                price = myDialogWindow.findViewById(R.id.textView22);
//                phone = myDialogWindow.findViewById(R.id.textView23);
//                sellerName = myDialogWindow.findViewById(R.id.textView24);
//                orderList=myDialogWindow.findViewById(R.id.order);
//                bookName.setText("Book Name");
//                price.setText("Price(RM)");
//                phone.setText("Phone Number");
//                sellerName.setText("Seller Name");
//                LoadOrderB(sharedPreferences.getString("username",null));
//                myDialogWindow.show();
//            }
//        });
        sellOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogWindow = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                myDialogWindow.setContentView(R.layout.order_list);
                final TextView bookName,buyerName,phone,address;
                bookName = myDialogWindow.findViewById(R.id.textView21);
                buyerName = myDialogWindow.findViewById(R.id.textView22);
                phone = myDialogWindow.findViewById(R.id.textView23);
                address = myDialogWindow.findViewById(R.id.textView24);
                orderList=myDialogWindow.findViewById(R.id.order);
                bookName.setText("Book Name");
                buyerName.setText("Buyer Name");
                phone.setText("Phone Number");
                address.setText("Address");
                LoadOrderS(sharedPreferences.getString("username",null));
                myDialogWindow.show();

            }
        });
    }
//    private void LoadOrderB(final String username){
//        class LoadOrderB extends AsyncTask<Void,Void,String> {
//            @Override
//            protected String doInBackground(Void... voids){
//                HashMap<String,String> hashMap = new HashMap<>();
//                hashMap.put("username",username);
//                Handler handler=new Handler();
//                orderlistB = new ArrayList<>();
//                String s = handler.sendPostRequest(serverurl+"/load_orderB.php",hashMap);
//                return s;
//            }
//            @Override
//            protected void onPostExecute(String s){
//                super.onPostExecute(s);
//                orderlistB.clear();
//                try{
//                    JSONObject jsonObject = new JSONObject(s);
//                    JSONArray restarray=jsonObject.getJSONArray("orderB");
//                    Log.e("HANIS",jsonObject.toString());
//                    for (int i=0; i<restarray.length();i++){
//                        JSONObject c = restarray.getJSONObject(i);
//                        String BookName = c.getString("bookName");
//                        String Price = c.getString("price");
//                        String Phone = c.getString("phone");
//                        String SellerName = c.getString("sellerName");
//                        HashMap<String,String> orderBlisthash = new HashMap<>();
//                        orderBlisthash.put("bookName",BookName);
//                        orderBlisthash.put("price",Price);
//                        orderBlisthash.put("phone",Phone);
//                        orderBlisthash.put("sellerName",SellerName);
//                        orderlistB.add(orderBlisthash);
//                    }
//                } catch (final JSONException e) {
//                    Log.e("JSONERROR",e.toString());
//                }
//                ListAdapter adapter = new SimpleAdapter(
//                        getActivity(), orderlistB,
//                        R.layout.order, new String[]
//                        {"bookName","price","phone","sellerName"}, new int[]
//                        {R.id.textView13,R.id.textView14,R.id.textView15,R.id.textView18});
//                        orderList.setAdapter(adapter);
//            }
//        }
//        LoadOrderB loadOrderB = new LoadOrderB();
//        loadOrderB.execute();
//    }
    private void LoadOrderS(final String username){
        class LoadOrderS extends AsyncTask<Void,Void,String> {
            @Override
            protected String doInBackground(Void... voids){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("username",username);
                Handler handler=new Handler();
                orderlistS = new ArrayList<>();
                String s = handler.sendPostRequest(serverurl+"/load_orderS.php",hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                orderlistS.clear();
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray restarray=jsonObject.getJSONArray("order");
                    Log.e("HANIS",jsonObject.toString());
                    for (int i=0; i<restarray.length();i++){
                        JSONObject c = restarray.getJSONObject(i);
                        String BookName = c.getString("bookName");
                        String BuyerName = c.getString("buyerName");
                        String Phone = c.getString("phone");
                        String Address = c.getString("address");
                        HashMap<String,String> orderSlisthash = new HashMap<>();
                        orderSlisthash.put("bookName",BookName);
                        orderSlisthash.put("buyerName",BuyerName);
                        orderSlisthash.put("phone",Phone);
                        orderSlisthash.put("address",Address);
                        orderlistS.add(orderSlisthash);
                    }
                } catch (final JSONException e) {
                    Log.e("JSONERROR",e.toString());
                }
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), orderlistS,
                        R.layout.order, new String[]
                        {"bookName","buyerName","phone","address"}, new int[]
                        {R.id.textView13,R.id.textView14,R.id.textView15,R.id.textView18});
                orderList.setAdapter(adapter);
            }
        }
        LoadOrderS loadOrderS = new LoadOrderS();
        loadOrderS.execute();
    }
}
