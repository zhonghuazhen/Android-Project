package com.example.administrator.homework2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AutoCompleteTextViewActivity extends AppCompatActivity {
    private static final String[] phonenumberSrt=new String[]{"88888888","86668888","77777777",
            "86666666","86145689","86143429","86123889"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete_text_view);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,phonenumberSrt);
        AutoCompleteTextView autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(adapter);
    }
}
