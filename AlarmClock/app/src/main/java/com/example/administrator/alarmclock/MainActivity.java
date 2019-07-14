package com.example.administrator.alarmclock;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView show1;
    TextView show2;
//    TextView show3;
    Button setTime1;
    Button setTime2;
//    Button setTime3;
    Button delete1;
    Button delete2;
//    Button delete3;
    String show1String = null;
    String show2String = null;
//    String show3String = null;
    String defalutString = "目前无设置";
    AlertDialog builder = null;
    Calendar c = Calendar.getInstance();
    private MediaPlayer mediaPlayer;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.fighter);
        SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
        show1String = settings.getString("TIME1", defalutString);
        show2String = settings.getString("TIME2", defalutString);
//        show3String = settings.getString("TIME3", defalutString);

        InitSetTime1();
        InitSetTime2();
//        InitSetTime3();
        InitDelete1();
        InitDelete2();
//        InitDelete3();

//        setmusic();

        show1.setText(show1String);
        show2.setText(show2String);
//        show3.setText(show3String);
        //获取权限
        if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限
            initPermission();
        }
    }

    private void InitSetTime1() {
        show1 = (TextView) findViewById(R.id.show1);
        setTime1 = (Button) findViewById(R.id.settime1);
        setTime1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                c.setTimeInMillis(System.currentTimeMillis());
                final int mHour = c.get(Calendar.HOUR_OF_DAY);//系统当前小时；
                final int mMinute = c.get(Calendar.MINUTE);//系统当前分钟；
                Log.d(TAG,"mHour="+mHour+"mMinute"+mMinute);
                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Log.d(TAG,"hourOfDay"+hourOfDay+"minute"+minute);

                                //判断时间是否小于当天时间，如果是就把时间设置为第二天的时间，否则为当天的时间；
                               if(hourOfDay < mHour ||(hourOfDay == mHour && minute < mMinute) ){
                                   c.setTimeInMillis(c.getTimeInMillis() + 24 * 60 * 60 * 1000);
                                }else{
                                   c.setTimeInMillis(System.currentTimeMillis());
                                }
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                                PendingIntent sender = PendingIntent.getBroadcast(
                                        MainActivity.this, 0, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager) getSystemService(ALARM_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    am.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);
                                }

                                String tmpS = format(hourOfDay) + "：" + format(minute);
                                show1.setTextColor(getResources().getColor(R.color.colorOrange));//设置好时间后显示为黄色；
                                show1.setText(tmpS);

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time1Share = getPreferences(0);
                                SharedPreferences.Editor editor = time1Share.edit();
                                editor.putString("TIME1", tmpS);
                                editor.commit();

                                Toast.makeText(MainActivity.this, "设置闹钟时间为" + tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }, mHour, mMinute, true).show();

               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                    JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), CallAlarm.class.getName()))
                            .setPeriodic(2000)
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                            .build();
                    jobScheduler.schedule(jobInfo);
                }*/
            }
        });
    }

    private void InitDelete1() {
        delete1 = (Button) findViewById(R.id.delete1);
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        MainActivity.this, 0, intent, 0);
                AlarmManager am;
                am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this, "闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                show1.setText("目前无设置");

                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME1", "目前无设置");
                editor.commit();
            }
        });
    }

    private void InitSetTime2() {
        show2 = (TextView) findViewById(R.id.show2);
        setTime2 = (Button) findViewById(R.id.settime2);
        setTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setTimeInMillis(System.currentTimeMillis());
                final int mHour = c.get(Calendar.HOUR_OF_DAY);
                final int mMinute = c.get(Calendar.MINUTE);


                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                //判断时间是否小于当天时间，如果是就把时间设置为第二天的时间，否则为当天的时间；
                                if(hourOfDay < mHour ||(hourOfDay == mHour && minute < mMinute) ){
                                    c.setTimeInMillis(c.getTimeInMillis() + 24 * 60 * 60 * 1000);
                                }else{
                                    c.setTimeInMillis(System.currentTimeMillis());
                                }
                                //c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);

                                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                                PendingIntent sender = PendingIntent.getBroadcast(
                                        MainActivity.this, 0, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager) getSystemService(ALARM_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    am.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);
                                }

                                String tmpS = format(hourOfDay) + "：" + format(minute);
                                show2.setTextColor(getResources().getColor(R.color.colorOrange));//设置好时间后显示为黄色；
                                show2.setText(tmpS);

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time1Share = getPreferences(0);
                                SharedPreferences.Editor editor = time1Share.edit();
                                editor.putString("TIME1", tmpS);
                                editor.commit();

                                Toast.makeText(MainActivity.this, "设置闹钟时间为" + tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }, mHour, mMinute, true).show();
            }
        });
    }

    private void InitDelete2() {
        delete2 = (Button) findViewById(R.id.delete2);
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        MainActivity.this, 0, intent, 0);
                AlarmManager am;
                am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this, "闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                show2.setText("目前无设置");

                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME1", "目前无设置");
                editor.commit();
            }
        });
    }

   /* private void InitSetTime3() {
        show3 = (TextView) findViewById(R.id.show3);
        setTime3 = (Button) findViewById(R.id.settime3);
        setTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setTimeInMillis(System.currentTimeMillis());
                final int mHour = c.get(Calendar.HOUR_OF_DAY);
                final int mMinute = c.get(Calendar.MINUTE);


                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                //判断时间是否小于当天时间，如果是就把时间设置为第二天的时间，否则为当天的时间；
                                if(hourOfDay < mHour ||(hourOfDay == mHour && minute < mMinute) ){
                                    c.setTimeInMillis(c.getTimeInMillis() + 24 * 60 * 60 * 1000);
                                }else{
                                    c.setTimeInMillis(System.currentTimeMillis());
                                }
                                //c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);

                                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                                PendingIntent sender = PendingIntent.getBroadcast(
                                        MainActivity.this, 0, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager) getSystemService(ALARM_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    am.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);
                                }

                                String tmpS = format(hourOfDay) + "：" + format(minute);
                                show3.setTextColor(getResources().getColor(R.color.colorOrange));//设置好时间后显示为黄色；
                                show3.setText(tmpS);

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time1Share = getPreferences(0);
                                SharedPreferences.Editor editor = time1Share.edit();
                                editor.putString("TIME1", tmpS);
                                editor.commit();

                                Toast.makeText(MainActivity.this, "设置闹钟时间为" + tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }, mHour, mMinute, true).show();
            }
        });
    }
*/
  /*  private void InitDelete3() {
        delete3 = (Button) findViewById(R.id.delete3);
        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        MainActivity.this, 0, intent, 0);
                AlarmManager am;
                am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this, "闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                show3.setText("目前无设置");

                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME1", "目前无设置");
                editor.commit();
            }
        });
    }
    */

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        mediaPlayer.stop();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mediaPlayer.stop();
            builder = new AlertDialog.Builder(MainActivity.this)

                    .setTitle("温馨提示：")
                    .setMessage("您是否要退出程序？")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    mediaPlayer.stop();
                                    MainActivity.this.finish();
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    mediaPlayer.stop();
                                    builder.dismiss();
                                }
                            }).show();
        }
        return true;
    }

    private String format(int x) {
        String s = "" + x;
        if (s.length() == 1) s = "0" + s;
        return s;
    }
    public void wakeup(){
        PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakelock=pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.SCREEN_DIM_WAKE_LOCK,"SimpleTimer");
             mWakelock.acquire();
             mWakelock.release();

    }
    public void onClick(View view){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,ChoiceMusic.class);
        startActivity(intent);
    }
//获取音乐的名称
    public void setmusic(){
        SharedPreferences sharedPreferences = getSharedPreferences("music", Context.MODE_PRIVATE);
//getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
        String song = sharedPreferences.getString("song", "");
        //把音频格式去掉
        song=song.replaceAll("[mqms]","").replaceAll("[mqms2]","").replaceAll("\\]","").replaceAll("\\[","").replaceAll(".p3","").replaceAll(".flac","");
        TextView show4=(TextView)findViewById(R.id.show4);
        show4.setText(song);
        Log.d("TAG",song);
    }


    //获取权限
    //申请两个权限，唤醒和文件读写
    //1、首先声明一个数组permissions，将需要的权限都放在里面
    String[] permissions = new String[]{Manifest.permission.WAKE_LOCK,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    //2、创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    List<String> mPermissionList = new ArrayList<>();

    private final int mRequestCode = 100;//权限请求码


    //权限判断和申请
    private void initPermission() {

        mPermissionList.clear();//清空没有通过的权限

        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        }else{
            //说明权限都已经通过，可以做你想做的事情去
        }
    }


    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                showPermissionDialog();//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
            }else{
                //全部权限通过，可以进行下一步操作。。。

            }
        }

    }

    /**
     * 不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;
    String mPackName = "com.example.administrator.alarmclock";

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，是否确定授予权限")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();

                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    @Override
    protected void onResume() {
        //onResume()是当从其他activ返回到这个activity时调用。
        setmusic();//设置show的数据
       super.onResume();
    }

    //关闭对话框
    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }

}
