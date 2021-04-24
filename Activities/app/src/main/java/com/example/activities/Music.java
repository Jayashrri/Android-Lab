package com.example.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Music extends AppCompatActivity {

    Button playMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        playMusic = (Button)findViewById(R.id.play);
    }

    public void onSubmit(View V) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

        File sdcard = Environment.getExternalStorageDirectory();
        File audioFile = new File(sdcard.getPath() + "/Music/Hypnotised.mp3");
        Uri uri = FileProvider.getUriForFile(Music.this, BuildConfig.APPLICATION_ID + ".provider", audioFile);

        intent.setDataAndType(uri, "audio/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}