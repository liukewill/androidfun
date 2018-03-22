package com.example.think.androidfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TvActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    boolean open=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        button =(Button)findViewById(R.id.button1);
        textView=(TextView) findViewById(R.id.tv1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(open==true){
                    open=false;
                    button.setText("开");
                    textView.setText("电视已经关闭");
                }else{
                    open=true;
                    textView.setText("电视已经打开");
                    button.setText("关");
                }
            }
        });


    }


}
