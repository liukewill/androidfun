package com.kenan.uilib;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kenan.uilib.R;

public class MainActivity extends Activity {
    TextView tv;
    LinearLayout child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.ll);
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        child=(LinearLayout) layoutInflater.inflate(R.layout.view_layout_params,linearLayout);

        System.out.println(3);
        System.out.println(3);
    }
}
