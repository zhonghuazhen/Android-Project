package com.example.administrator.alarmclock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.alarmclock.Song;
import java.util.ArrayList;
import java.util.List;

public class ChoiceMusic extends AppCompatActivity {

    private static final String TAG = "ChoiceMusic";

    private ListView mListView;
    private List<Song> list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_music);
        initView();
    }
    /**
     * 初始化view
     */
    private void initView() {
        mListView = (ListView) findViewById(R.id.main_listview);
        list = new ArrayList<>();
        //把扫描到的音乐赋值给list
        list = MusicUtils.getMusicData(this);
        Log.d(TAG,"list数据"+list.toString());
        adapter = new MyAdapter(this,list);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String p = list.get(i).path;//获得歌曲的地址；
                String song=list.get(i).getSong();//获的歌曲的名字；
                Log.d(TAG,p);
                //如果存在music文件则打开，否则创建，Context.MODE_PRIVATE说明在进行写操作时会覆盖原有的数据。
                SharedPreferences perferences = getSharedPreferences("music", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = perferences.edit();//获取编辑器。
                //把获取的歌曲地址写入到music文件中。
                edit.putString("uri", p);//把歌的地址存到music中
                edit.putString("song",song);//把歌名存到music中
                edit.commit();
                new AlertDialog.Builder(ChoiceMusic.this)
                        .setIcon(R.drawable.clock)
                        .setTitle("提示")
                        .setMessage("选择了:"+song)
                        .setPositiveButton("确定"
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ChoiceMusic.this.finish();
                                    }
                                }).show();
            }
        });
    }

}
