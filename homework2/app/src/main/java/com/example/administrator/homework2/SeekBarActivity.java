package com.example.administrator.homework2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarActivity extends AppCompatActivity {

    SeekBar sb;
    TextView tv;

    boolean finished;//是否已完成
    boolean dragging;//是否正在拖动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);

        sb=(SeekBar)findViewById(R.id.sb);
        tv=(TextView)findViewById(R.id.tv);
        final int max=sb.getMax();//最大值

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int p=sb.getProgress();
                tv.setText("进度："+p*100/max+"%");
                if (p==max)
                    finished=true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                    dragging=true;//开始拖动
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
              dragging=false;//停止拖动
            }
        });
        new Thread(){
            @Override
            public void run(){
                while (!finished)
                    if (!dragging)
                        try{
                    Thread.sleep(50);
                    sb.incrementProgressBy(1);
                        }catch (InterruptedException e){
                    e.printStackTrace();
                        }
            }
        }.start();
    }
}
