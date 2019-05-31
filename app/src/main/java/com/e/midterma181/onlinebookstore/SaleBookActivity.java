package com.e.midterma181.onlinebookstore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class SaleBookActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView bookName,price,num,phone,intro;
    Button sale,back;
    Spinner bookType;
    ImageView bookPhoto;
    Book book;
    String serverurl="http://yangyifei.000webhostapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_sale);

        sharedPreferences=getSharedPreferences("user_details",Context.MODE_PRIVATE);

        bookName=findViewById(R.id.bookName);
        bookType=findViewById(R.id.bookType);
        price=findViewById(R.id.price);
        num=findViewById(R.id.number);
        phone=findViewById(R.id.phone);
        intro=findViewById(R.id.introduction);
        sale=findViewById(R.id.Sale);
        back=findViewById(R.id.Back);
        bookPhoto=findViewById(R.id.imageView6);

        bookPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTakePicture();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleBookActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saleBookInput();
            }
        });
    }
    private void dialogTakePicture() {
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(this.getResources().getString(R.string.dialogtakepicture));

        alertDialogBuilder
                .setMessage(this.getResources().getString(R.string.dialogtakepicturea))
                .setCancelable(false)
                .setPositiveButton(this.getResources().getString(R.string.yesbutton),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, 1);
                        }
                    }
                })
                .setNegativeButton(this.getResources().getString(R.string.nobutton),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = ThumbnailUtils.extractThumbnail(imageBitmap,400,500);
            bookPhoto.setImageBitmap(imageBitmap);
            bookPhoto.buildDrawingCache();
            ContextWrapper cw = new ContextWrapper(this);
            File pictureFileDir = cw.getDir("basic", Context.MODE_PRIVATE);
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdir();
            }
            Log.e("FILE NAME", "" + pictureFileDir.toString());
            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                return;
            }
            FileOutputStream outStream = null;
            String photoFile = "profile.jpg";
            File outFile = new File(pictureFileDir, photoFile);
            try {
                outStream = new FileOutputStream(outFile);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                //hasimage = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void saleBookInput() {
        String BookName,Price,Num,BookType,Intro,Phone,SalerName;
        BookName = bookName.getText().toString();
        Price = price.getText().toString();
        Phone = phone.getText().toString();
        Num = num.getText().toString();
        Intro = intro.getText().toString();
        BookType = bookType.getSelectedItem().toString();
        SalerName = sharedPreferences.getString("username",null);
        book = new Book(BookName,Phone,Price,Num,Intro,BookType,SalerName);
        registerUserDialog();
    }
    private void insertData(){
        class saleBook extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("bookName",book.bookName);
                hashMap.put("bookType",book.bookType);
                hashMap.put("price",book.price);
                hashMap.put("num",book.num);
                hashMap.put("phone",book.phone);
                hashMap.put("intro",book.intro);
                hashMap.put("salerName",book.salerName);
                Handler handler = new Handler();
                String s = handler.sendPostRequest(serverurl+"/saleBook.php", hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("success")) {
                    Toast.makeText(SaleBookActivity.this,"Success",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SaleBookActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SaleBookActivity.this, "Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        try {
            saleBook saleBook = new saleBook ();
            saleBook.execute();
        }catch(Exception e){}
    }
    private void registerUserDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(this.getResources().getString(R.string.salefor)+" "+book.bookName+"?");

        alertDialogBuilder
                .setMessage(this.getResources().getString(R.string.salebook))
                .setCancelable(false)
                .setPositiveButton(this.getResources().getString(R.string.yesbutton),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        sharedPreferences=getSharedPreferences("user_details",Context.MODE_PRIVATE);
                        new Encode_image().execute(getDir(),book.phone+".jpg");
                        Toast.makeText(SaleBookActivity.this, getResources().getString(R.string.saleprocess), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(this.getResources().getString(R.string.nobutton),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public String getDir(){
        ContextWrapper cw = new ContextWrapper(this);
        File pictureFileDir = cw.getDir("basic", Context.MODE_PRIVATE);
        if (!pictureFileDir.exists()) {
            pictureFileDir.mkdir();
        }
        Log.d("GETDIR",pictureFileDir.getAbsolutePath());
        return pictureFileDir.getAbsolutePath()+"/profile.jpg";
    }
    public class Encode_image extends AsyncTask<String,String,Void> {
        private String encoded_string, image_name;
        Bitmap bitmap;

        @Override
        protected Void doInBackground(String... args) {
            String filname = args[0];
            image_name = args[1];
            bitmap = BitmapFactory.decodeFile(filname);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            makeRequest(encoded_string, image_name);
        }

        private void makeRequest(final String encoded_string, final String image_name) {
            class UploadAll extends AsyncTask<Void, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(Void... params) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("encoded_string", encoded_string);
                    map.put("image_name", image_name);
                    map.put("bookName",book.bookName);
                    map.put("bookType",book.bookType);
                    map.put("price",book.price);
                    map.put("num",book.num);
                    map.put("phone",book.phone);
                    map.put("intro",book.intro);
                    Handler handler = new Handler();//request server connection
                    String s = handler.sendPostRequest(serverurl+"/saleBook.php", map);
                    return s;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (s.equalsIgnoreCase("Success")) {
                        insertData();
                        // Toast.makeText(RegisterActivity.this, "Success Upload Image", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SaleBookActivity.this, "Failed Registration", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            UploadAll uploadall = new UploadAll();
            uploadall.execute();
        }
    }
}
