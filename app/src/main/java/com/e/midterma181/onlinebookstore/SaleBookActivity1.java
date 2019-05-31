package com.e.midterma181.onlinebookstore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class SaleBookActivity1 extends AppCompatActivity {
    TextView bookName,price,num,phone,intro;
    Button sale,back;
    Spinner bookType;
    ImageView bookPhoto;
    String serverurl="http://yangyifei.000webhostapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_sale);

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
                Intent intent = new Intent(SaleBookActivity1.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BookName=bookName.getText().toString().trim();
                String BookType=bookType.getSelectedItem().toString();
                String Price= price.getText().toString().trim();
                String Num= num.getText().toString().trim();
                String Phone=phone.getText().toString().trim();
                String Intro=intro.getText().toString().trim();
                saleBook(BookName,BookType,Price,Num,Phone,Intro);
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
    private void saleBook(final String BookName,final String BookType,final String Price,final String Num,final String Phone,final String Intro){
        class saleBook extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("bookName",BookName);
                hashMap.put("bookType",BookType);
                hashMap.put("price",Price);
                hashMap.put("num",Num);
                hashMap.put("phone",Phone);
                hashMap.put("intro",Intro);
                Handler handler = new Handler();
                String s = handler.sendPostRequest(serverurl+"/saleBook.php", hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equals("success")) {
                    Toast.makeText(SaleBookActivity1.this,"Success",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SaleBookActivity1.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SaleBookActivity1.this, "Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        try {
            saleBook saleBook = new saleBook ();
            saleBook.execute();
        }catch(Exception e){}
    }
}
