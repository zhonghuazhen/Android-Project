package com.example.administrator.homework3;

import android.content.Intent;
import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Video1Activity.class);
        startActivity(intent);

    }
    public void onClick2(View view)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Video2Activity.class);
        startActivity(intent);

    }
    public void onClick3(View view)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MediaRecorderActivity.class);
        startActivity(intent);

    }
    public void onClick4(View view)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, CameraActivity.class);
        startActivity(intent);

    }
}
