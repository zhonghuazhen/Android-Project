package com.example.administrator.homework2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ProgressBarActivity extends AppCompatActivity {

    ProgressBar pb1;
    ProgressBar pb2;
    ProgressBar pb3;
    ProgressBar pb4;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        pb1=(ProgressBar)findViewById(R.id.pb1);
        pb2=(ProgressBar)findViewById(R.id.pb2);
        pb3=(ProgressBar)findViewById(R.id.pb3);
        pb4=(ProgressBar)findViewById(R.id.pb4);
        startBtn=(Button)findViewById(R.id.startBtn);
        final int max=pb4.getMax();//最大值

        final Handler handler=new Handler(){
           // @Override
            public void handelMessage(Message msg){
                int p=msg.what;
                //如果达到最大值，隐藏圆形进度条
                if(p==max){
                    pb1.setVisibility(View.GONE);
                    pb2.setVisibility(View.GONE);
                    pb3.setVisibility(View.GONE);
                    pb4.setVisibility(View.GONE);
                }
                pb4.setProgress(p);//修改条形进度条的进度
                pb4.setSecondaryProgress(p * 2);//修改条形进度条的第二进度
            };
        };
        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //显示四个进度条
                pb1.setVisibility(View.VISIBLE);
                pb2.setVisibility(View.VISIBLE);
                pb3.setVisibility(View.VISIBLE);
                pb4.setVisibility(View.VISIBLE);

                //耗电模式操作
                new Thread(){
                    @Override
                    public void run(){
                        for (int i=1;i<=max;i++){
                            try{
                                Thread.sleep(50);
                                Message msg=new Message();
                                msg.what=i;
                                handler.sendMessage(msg);//发送消息
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
