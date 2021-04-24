package com.example.settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class ControlActivity extends AppCompatActivity {

    Context context;
    AudioManager audioManager;
    BluetoothAdapter bluetoothAdapter;

    SeekBar brightnessBar;
    SeekBar volumeBar;
    ToggleButton bluetooth;
    ToggleButton orientation;

    int brightness;
    int volume;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        context = getApplicationContext();

        orientation = (ToggleButton)findViewById(R.id.orientation);
        orientation.setChecked(false);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetooth = (ToggleButton)findViewById(R.id.bluetooth);
        if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
            bluetooth.setChecked(true);
        }

        brightnessBar = (SeekBar)findViewById(R.id.brightness);
        brightnessBar.setMax(255);
        brightnessBar.setKeyProgressIncrement(1);
        brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
        brightnessBar.setProgress(brightness);

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightness = progress * 255 / 100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
            }
        });

        volumeBar = (SeekBar)findViewById(R.id.volume);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        volumeBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeBar.setProgress(volume);

        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, progress, 0);
            }
        });
    }

    public void onBluetooth(View V) {
        if(bluetoothAdapter != null) {
            if(bluetooth.isChecked()) {
                bluetoothAdapter.disable();
            } else {
                bluetoothAdapter.enable();
            }
        }
    }

    public void onOrientation(View V) {
        if(!orientation.isChecked()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}