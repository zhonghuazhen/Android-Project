package com.example.administrator.homework3;

import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.hardware.Camera;//使用Camera.open要导入这个包

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    SurfaceView view;//预览视图
    SurfaceHolder holder;
    Camera camera;//相机

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_camera);

        view=(SurfaceView)findViewById(R.id.view);
        holder=view.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                camera=Camera.open();//打开相机
                try{
                    camera.setPreviewDisplay(holder);//设置预览View
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Camera.Parameters params=camera.getParameters();
                params.setPictureFormat(PixelFormat.JPEG);//图片格式
                camera.setParameters(params);
                camera.startPreview();//开始预览
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                 camera.stopPreview();//结束预览
                camera.release();//释放
            }
        });
    }
    @Override
    public  boolean onKeyDown(int keyCode,KeyEvent event){
        //按拍照键或者搜索键进行拍照，有的设备没有拍照键
        if(keyCode==KeyEvent.KEYCODE_CAMERA || keyCode==KeyEvent.KEYCODE_SEARCH){
            camera.stopPreview();
            camera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    new AsyncTask<byte[],String,String>(){
                        @Override
                        protected String doInBackground(byte[]...params){
                            File pic=new File("/sdcard/"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg");
                            try {
                                //写入到文件
                                pic.createNewFile();
                                FileOutputStream fos=new FileOutputStream(pic);
                                fos.write(params[0]);
                                fos.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute(data);
                    camera.stopPreview();
                }
            });return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
