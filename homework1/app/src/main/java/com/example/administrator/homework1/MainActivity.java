package com.example.administrator.homework1;

import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //设置窗口背景色
        getWindow().setBackgroundDrawableResource(R.color.someBgColor);
        //获取TextView控件
        TextView tv=(TextView)findViewById(R.id.myText);
        //设置TextView文字颜色和背景颜色
        tv.setTextColor(getResources().getColor(R.color.someTextColor));
        tv.setBackgroundColor(getResources().getColor(R.color.someTextBgColor));

    //TextView tv=(TextView)findViewById(R.id.myText);
    try{
        tv.setText(readXml());
    }catch (Exception e) {
        e.printStackTrace();
    }
    }

    String readXml() throws XmlPullParserException,IOException{
        XmlResourceParser xrp=getResources().getXml(R.xml.corporation);
        StringBuilder sb=new StringBuilder();
        int et=xrp.getEventType();
        while (et!= XmlPullParser.END_DOCUMENT){
            String name=xrp.getName();
            if(et==XmlPullParser.START_TAG)
                 if("corporation".equals(name))
                    sb.append(xrp.getAttributeValue(0)).append(" ");
            else if("name".equals(name)){
                xrp.next();
                sb.append(xrp.getText()).append(" ");
            }else if("country".equals(name)){
                xrp.next();
                sb.append(xrp.getText()).append("\n");
            }
            xrp.next();
            et=xrp.getEventType();
        }
        return sb.toString();
    }
}
