package com.example.filesdk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }
                else{
                    Toast.makeText(MainActivity.this,"拒绝权限将无法使用",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //申请权限
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        Button write=(Button)findViewById(R.id.write);
        Button read=(Button)findViewById(R.id.read);

        //写SDK
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutputStream out=null;
                try {
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        File file=Environment.getExternalStorageDirectory();
                        File myFile=new File(file.getCanonicalPath()+"/"+"YanShiyue.txt");

                        FileOutputStream fileOutputStream=new FileOutputStream(myFile);

                        out=new BufferedOutputStream(fileOutputStream);
                        String content="Hi,YanShiyue";
                        try{
                            out.write(content.getBytes(StandardCharsets.UTF_8));
                        }finally {
                            if(out!=null)
                                out.close();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //读SDK
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream in=null;
                try {

                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        File file=Environment.getExternalStorageDirectory();
                        File myFile=new File(file.getCanonicalPath()+"/"+"YanShiyue.txt");
                        FileInputStream fileInputStream=new FileInputStream(myFile);

                        in=new BufferedInputStream(fileInputStream);
                        int c;
                        StringBuilder stringBuilder=new StringBuilder("");
                        try {
                            while((c=in.read())!=-1){
                                stringBuilder.append((char)c);
                            }
                            Toast.makeText(MainActivity.this,stringBuilder.toString(),Toast.LENGTH_LONG).show();
                        }finally {
                            if(in!=null)
                                in.close();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
