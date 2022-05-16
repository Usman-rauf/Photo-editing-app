package com.example.blur;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.io.ByteArrayOutputStream;
import java.net.URI;


public class MainActivity extends AppCompatActivity {

ImageView editbutton,camerabutton;
//AdView adView;
int imagerequestcode=45;
int RESULT_CODE=200;
int CAMERA_RESULT=14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        editbutton=findViewById(R.id.editbutton);
        camerabutton=findViewById(R.id.camerabutton);
//

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,imagerequestcode);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "edit photos", Toast.LENGTH_SHORT).show();
            }
        });
camerabutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},32);
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 32);
        }
        else {
            Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera,CAMERA_RESULT);
        }
    }



});
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==imagerequestcode)
        {
            if(data.getData()!=null)
            {
                Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);

                dsPhotoEditorIntent.setData(data.getData());
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Blur");
                int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
                startActivityForResult(dsPhotoEditorIntent, RESULT_CODE);
            }
        }
        if(requestCode==RESULT_CODE)
        {
            Intent i=new Intent(MainActivity.this,editingscreen.class);
            i.setData(data.getData());
            startActivity(i);
        }
        if(requestCode==CAMERA_RESULT)
        {
            Bitmap photo=(Bitmap)data.getExtras().get("data");
Uri uri=GetImageUri(photo);
            Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);

            dsPhotoEditorIntent.setData(uri);
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Blur");
            int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
            startActivityForResult(dsPhotoEditorIntent, RESULT_CODE);
            
        }
    }
    private Uri GetImageUri(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        String path=MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"Title","Convert");
        return Uri.parse(path);
    }
}