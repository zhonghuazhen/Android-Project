package com.example.administrator.homework3;

import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaRecorderActivity extends AppCompatActivity {

    MediaRecorder recorder1;//录音的MediaRecorder
    Button startBtn1;
    Button stopBtn1;

    MediaRecorder recorder2;//录像的MediaRecorder
    Button startBtn2;
    Button stopBtn2;
    SurfaceView view;//录像时的预览视图
    SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder);
        initAudio();
        initVideo();
    }

    //录音
    void initAudio(){
        recorder1=new MediaRecorder();
        startBtn1=(Button)findViewById(R.id.startBtn1);
        stopBtn1=(Button)findViewById(R.id.stopBtn1);

        //音频源是麦克风
        recorder1.setAudioSource(MediaRecorder.AudioSource.MIC);
        //输出格式THREE_GPP
        recorder1.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //编码格式AMR_NB
        recorder1.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //输出文件路径
        recorder1.setOutputFile("/sdcard/"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".3gp");
        stopBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    recorder1.prepare();//准备录制
                    recorder1.start();//开始录制
                    startBtn1.setEnabled(false);
                    stopBtn1.setEnabled(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        stopBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    recorder1.stop();//停止录制
                    recorder1.release();//释放资源
                    startBtn1.setEnabled(true);
                    startBtn1.setEnabled(false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    //录像
    void initVideo(){
    recorder2=new MediaRecorder();
    startBtn2=(Button)findViewById(R.id.startBtn2);
    stopBtn2=(Button)findViewById(R.id.stopBtn2);
    view=(SurfaceView)findViewById(R.id.view);
    holder=view.getHolder();

    holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    //使用holder的surface作为录像时的预览视图
        recorder2.setPreviewDisplay(holder.getSurface());
        //音频源是麦克风
        recorder2.setAudioSource(MediaRecorder.AudioSource.MIC);
        //视频源是摄像头
        recorder2.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //输出格式MPEG_4
        recorder2.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //音频编码格式是AMR_NB
        recorder2.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //视频分辨率
        recorder2.setVideoSize(320,240);
        //视频帧率
        recorder2.setVideoFrameRate(20);
        //视频编码格式H263
        recorder2.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
        //输出文件路径
        recorder2.setOutputFile("/sdcard/"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".mp4");
        startBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    recorder2.prepare();//准备录制
                    recorder2.start();//开始录制
                    startBtn2.setEnabled(false);
                    stopBtn2.setEnabled(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        stopBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    recorder2.stop();//停止录制
                    recorder2.release();//释放资源
                    startBtn2.setEnabled(true);
                    stopBtn2.setEnabled(false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
