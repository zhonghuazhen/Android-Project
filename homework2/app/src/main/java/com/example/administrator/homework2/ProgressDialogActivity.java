package com.example.administrator.homework2;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ProgressDialogActivity extends AppCompatActivity {

    Button startBtn1;
    Button startBtn2;
    ProgressDialog pd1;
    ProgressDialog pd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);

        startBtn1=(Button)findViewById(R.id.startBtn1);
        startBtn2=(Button)findViewById(R.id.startBtn2);
        pd1=new ProgressDialog(this);
        pd2=new ProgressDialog(this);

        pd1.setMessage("请稍候......");
        pd2.setMessage("请稍候.......");
        pd2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条为条形

        final int max=100;//进度最大值
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                int p=msg.what;
                //如果达到最大值，隐藏进度条对话框
                if(p==max){
                    pd1.hide();
                    pd2.hide();
                }
                //修改条形进度条对话框的进度
                pd2.setProgress(p);
            };
        };
        startBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd1.show();
                new Thread(){
                    @Override
                    public void run(){
                        for(int i=1;i<=max;i++){
                            try{
                                Thread.sleep(50);
                                Message msg=new Message();
                                msg.what=i;
                                handler.sendMessage(msg);//发送信息
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });
        startBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd2.show();
                new Thread(){
                    @Override
                    public void run(){
                        for(int i=1;i<=max;i++){
                            try {
                                Thread.sleep(50);
                                Message msg=new Message();
                                msg.what=i;
                                handler.sendMessage(msg);//发送信息
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });
    }
}
