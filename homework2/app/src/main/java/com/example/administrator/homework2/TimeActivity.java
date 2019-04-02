package com.example.administrator.homework2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AnalogClock;
import android.widget.DatePicker;
import android.widget.DigitalClock;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

public class TimeActivity extends AppCompatActivity {
     DatePicker dp;
     TimePicker tp;
     DigitalClock dc;
     AnalogClock ac;
     TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        dp=(DatePicker)findViewById(R.id.dp);
        tp=(TimePicker)findViewById(R.id.tp);
        dc=(DigitalClock)findViewById(R.id.dc);
        ac=(AnalogClock)findViewById(R.id.ac);
        tv=(TextView)findViewById(R.id.tv);

        Calendar c=Calendar.getInstance();
        dp.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        display();
                    }
                });
    }
    void display(){
        int y=dp.getYear();
        int m=dp.getMonth();
        int d=dp.getDayOfMonth();
        int h=tp.getCurrentHour();
        int mi=tp.getCurrentMinute();
        int s=Calendar.getInstance().get(Calendar.SECOND);
        tv.setText(y+"-"+m+"-"+d+"-"+" "+h+":"+mi+":"+s);
    }
}
