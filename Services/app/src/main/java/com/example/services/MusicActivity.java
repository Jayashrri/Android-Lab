package com.example.services;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        findViewById(R.id.playmusic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                File sdcard = Environment.getExternalStorageDirectory();
                File audioFile = new File(sdcard.getPath() +  getResources().getString(R.string.path));
                Log.e("Path",  sdcard.getPath() +  getResources().getString(R.string.path) + "" );
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri uri = FileProvider.getUriForFile(MusicActivity.this, BuildConfig.APPLICATION_ID + ".provider",audioFile);
                intent.setDataAndType(uri, "audio/*");
                startActivity(intent);
            }
        });
    }
}
