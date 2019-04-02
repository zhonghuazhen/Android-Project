package com.example.administrator.homework3;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Video1Activity extends AppCompatActivity {

    MediaPlayer player;

    Button startBtn;
    Button pauseBtn;
    Button stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video1);

        //播放res/raw目录下的mv2文件
        //音乐
        player=MediaPlayer.create(Video1Activity.this,R.raw.mv2);
        startBtn=(Button)findViewById(R.id.startBtn);
        pauseBtn=(Button)findViewById(R.id.pauseBtn);
        stopBtn=(Button)findViewById(R.id.stopBtn);

        //播放完成事件
          player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
              @Override
              public void onCompletion(MediaPlayer mediaPlayer) {
                  startBtn.setEnabled(true);
                  pauseBtn.setEnabled(false);
                  stopBtn.setEnabled(false);
              }
          });
          //开始按钮
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.start();//开始播放
                startBtn.setEnabled(false);
                pauseBtn.setEnabled(true);
                stopBtn.setEnabled(true);
            }
        });
        //暂停按钮
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.pause();//暂停播放
                startBtn.setEnabled(true);
                pauseBtn.setEnabled(false);
                stopBtn.setEnabled(true);
            }
        });

        //停止按钮
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();//停止播放
                startBtn.setEnabled(true);
                pauseBtn.setEnabled(true);
                stopBtn.setEnabled(false);
                try{
                    player.prepare();//为下次播放作做准备
                    player.seekTo(0);//回到音频起点
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        player.release();//释放资源
    }
}
