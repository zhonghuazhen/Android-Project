package com.example.administrator.homework2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ViewSwitcher;

public class GalleryActivity extends AppCompatActivity {

    ImageSwitcher imgSwt;
    Gallery glr;

    //需要显示的图片资源，保存在drawable目录下
    int[] imgs={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,
            R.drawable.e,R.drawable.f,R.drawable.g};

    //缓存底部在Gallery中使用的ImageView
    ImageView[] galleryViews=new ImageView[imgs.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imgSwt=(ImageSwitcher)findViewById(R.id.imgSwt);
        glr=(Gallery)findViewById(R.id.glr);

        //设置ImageSwitcher显示图片的工厂
        imgSwt.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView iv=new ImageView(GalleryActivity.this);
                iv.setBackgroundColor(0xFF000000);
                iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                iv.setLayoutParams(new ImageSwitcher.LayoutParams(RadioGroup.LayoutParams.FILL_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
                return iv;
            }
        });
        //设置ImageSwitcher的进入动画
        imgSwt.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        //设置ImageSwitcher的退出动画
        imgSwt.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));

        //初始化缓存Gallery使用的ImageView
        for(int i=0;i<galleryViews.length;i++){
            ImageView iv=new ImageView(GalleryActivity.this);
            iv.setImageResource(imgs[i]);
            iv.setAdjustViewBounds(true);
            iv.setLayoutParams(new Gallery.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
           galleryViews[i]=iv;
        }
        //设置Gallery的适配器
        glr.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return galleryViews[i];
            }
        });
        //Gallery被选中的事件
        glr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                imgSwt.setImageResource(imgs[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
