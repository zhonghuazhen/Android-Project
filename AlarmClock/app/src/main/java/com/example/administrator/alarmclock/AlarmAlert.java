package com.example.administrator.alarmclock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class AlarmAlert extends Activity implements DeviceKeyMonitor.OnKeyListener {
    private MediaPlayer mediaPlayer;
    private static final String TAG = "MainActivity";
    int left,right,top,bottom;
    int condition;//判断uri是否为空。
    //计数
    TextView txt;
    private int count = 0;
    //屏蔽home键

   //屏蔽多任务键
    private DeviceKeyMonitor mKeyMonitor;
    private boolean isClickRecent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //屏蔽任务键
        mKeyMonitor = new DeviceKeyMonitor(this, this);
        setContentView(R.layout.activity_alarm_alert);
        //播放音乐
        SharedPreferences sharedPreferences = getSharedPreferences("music", Context.MODE_PRIVATE);
//getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
        String uri = sharedPreferences.getString("uri", "");
        Log.d(TAG,"选择的音乐的地址"+uri);
        if(uri=="")
        {
            condition=0;
        }
        else
            condition=1;
        Log.d(TAG,"状况"+condition);
        if (condition==1)
        {
            //          判断是否使用了本地音乐，否则就使用默认的音乐。
            try {
                mediaPlayer = new MediaPlayer();
//         重置音频文件，防止多次点击会报错
                mediaPlayer.reset();
//        调用方法传进播放地址
                Log.d(TAG, "播放地址" + uri);
                mediaPlayer.setDataSource(uri);

//            异步准备资源，防止卡顿
               mediaPlayer.prepareAsync();
//            调用音频的监听方法，音频准备完毕后响应该方法进行音乐播放
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            mediaPlayer = MediaPlayer.create(this, R.raw.fighter);
            mediaPlayer.start();
        }
        final Button btn = (Button) findViewById(R.id.btn);
        //btn.set



        txt = (TextView) findViewById(R.id.txt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                txt.setText(String.valueOf(count));
                BtnLoactio(btn);//每点击一次btn就换位置；
                if (count == 10) {//当次数达到100次就关闭音乐；
                    AlarmAlert.this.finish();
                    mediaPlayer.stop();
                }
            }

        });


       /* new AlertDialog.Builder(AlarmAlert.this)
                .setIcon(R.drawable.clock)
                .setTitle("闹钟响了")
                .setMessage("时间到了！")
                .setPositiveButton("关掉"
                        , new DialogInterface.OnClickListener() {
                            @Override
                           public void onClick(DialogInterface dialog, int which) {


                                AlarmAlert.this.finish();
                                mediaPlayer.stop();

                            }
                        }).show();
        }*/


    }
    //屏蔽home键


    //屏蔽返回键
    @Override
    public void onBackPressed() {
        // super.onBackPressed();//注释掉这行,back键不退出activity
    }
    //屏蔽hoome键



//屏蔽任务键
/*******************************************/
    @Override
    protected void onResume() {
        super.onResume();
        if (isClickRecent){
            isClickRecent = false;
            //处理按了多任务键的逻辑
        } else {
            //处理原本的resume逻辑
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(isClickRecent){
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.moveTaskToFront(getTaskId(), 0);
        } else {
            //处理原本的pause逻辑
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
//            Log.d("DeviceKeyMonitor", "按了返回键");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onHomeClick() {

//        Log.d("DeviceKeyMonitor", "按了Home键");
    }

    @Override
    public void onRecentClick() {
        isClickRecent = true;
//        Log.d("DeviceKeyMonitor", "按了多任务键");
    }

    @Override
    protected void onDestroy() {
        mKeyMonitor.unregister();
        super.onDestroy();
    }

/***********************************************/

    public void BtnLoactio(Button btn){
        Random random=new Random();
        left=random.nextInt(300);//左边位置;
        top=random.nextInt(600);//上边位置;
         //right=random.nextInt(200);//右边位置;
       // bottom=random.nextInt(400);
        RelativeLayout.LayoutParams layout=(RelativeLayout.LayoutParams)btn.getLayoutParams();
        //获得button控件的位置属性，需要注意的是，可以将button换成想变化位置的其它控件
        layout.setMargins(left,top,0,0);
        //设置button的新位置属性,left，top，right，bottom
        btn.setLayoutParams(layout);
        //将新的位置加入button控件中
    }

}


