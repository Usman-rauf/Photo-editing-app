package com.example.blur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.io.File;

public class editingscreen extends AppCompatActivity {
    ImageView finalimage, sharebt, fbbt, instbt, whatsappbt;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editingscreen);
        finalimage = findViewById(R.id.finalimage);
        sharebt = findViewById(R.id.sharebt);
        fbbt = findViewById(R.id.fbbt);
        instbt = findViewById(R.id.instbt);
        whatsappbt = findViewById(R.id.whatsappbt);

        finalimage.setImageURI(getIntent().getData());

        whatsappbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Image for Whats App");
                whatsappIntent.putExtra(Intent.EXTRA_STREAM, getIntent().getData());
                whatsappIntent.setType("image/*");
                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(editingscreen.this, "WhatsApp have not been installed.", Toast.LENGTH_SHORT).show();
                }
            }

        });
        fbbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook = new Intent(Intent.ACTION_SEND);
                facebook.setType("text/plain");
                facebook.setPackage("com.facebook");
                facebook.putExtra(Intent.EXTRA_TEXT, "Image for FaceBook");
                facebook.putExtra(Intent.EXTRA_TEXT, getIntent().getData());
                facebook.setType("image/*");
                facebook.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
                try {
                    startActivity(facebook);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(editingscreen.this, "FaceBook have not installed", Toast.LENGTH_SHORT).show();
                }
            }

        });
        instbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook=new Intent(Intent.ACTION_SEND);
                facebook.setType("text/plain");
                facebook.setPackage("com.instagram");
                facebook.putExtra(Intent.EXTRA_TEXT,"Image for Instagram");
                facebook.putExtra(Intent.EXTRA_TEXT,getIntent().getData());
                facebook.setType("image/*");
                facebook.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
                try {
                    startActivity(facebook);
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(editingscreen.this, "Instagram have not installed", Toast.LENGTH_SHORT).show();
                }
            }

        });
        sharebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, getIntent().getData());
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/*");
                    startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }
}