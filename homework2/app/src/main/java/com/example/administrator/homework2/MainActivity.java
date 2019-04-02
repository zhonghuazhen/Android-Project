package com.example.administrator.homework2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
     //2019.3.27 zhz
     public void onClick(View view)
     {
          Intent intent = new Intent();
          intent.setClass(MainActivity.this, TimeActivity.class);
          startActivity(intent);

     }
    public void onClick2(View view)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AutoCompleteTextViewActivity.class);
        startActivity(intent);
    }
    public void onClick3(View view)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ProgressBarActivity.class);
        startActivity(intent);
    }
    public void onClick4(View view)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SeekBarActivity.class);
        startActivity(intent);
    }
    public void onClick5(View view)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, GalleryActivity.class);
        startActivity(intent);
    }
    public void onClick6(View view)
    {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ProgressDialogActivity.class);
        startActivity(intent);
    }
}
