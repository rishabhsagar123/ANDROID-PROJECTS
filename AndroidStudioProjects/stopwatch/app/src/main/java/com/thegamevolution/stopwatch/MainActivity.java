package com.thegamevolution.stopwatch;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.thegamevolution.stopwatch.R;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    private AdView adView;
    AdRequest adRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer=findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if((SystemClock.elapsedRealtime()-chronometer.getBase()>=60000)){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(MainActivity.this, "Time Over", Toast.LENGTH_SHORT).show();
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adView.performClick();
            }
        },5000);

        adView =(AdView)findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }
    public void startchronometer(View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffset);
            chronometer.start();
            running=true;

        }
    }
    public void pausechronometer(View v){
        if(running){
            chronometer.stop();
            pauseOffset=SystemClock.elapsedRealtime()-chronometer.getBase();
            running=false;
        }
    }
    public void resetchronometer(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset=0;
    }
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


}