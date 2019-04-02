package com.example.administrator.homework3;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URL;

public class Video2Activity extends AppCompatActivity {

    VideoView videoView;
    MediaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         //横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_video2);

        videoView=(VideoView)findViewById(R.id.videoView);
        controller=new MediaController(this);
        //设置播放的文件
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mv1));
        //关联VideoView和MediaController
        controller.setMediaPlayer(videoView);
        videoView.setMediaController(controller);
        //开始播放
        videoView.start();
    }
}
