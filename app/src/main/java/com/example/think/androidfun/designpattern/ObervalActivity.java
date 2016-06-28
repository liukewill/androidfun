package com.example.think.androidfun.designpattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.think.androidfun.R;
import com.example.think.androidfun.designpattern.ObseverManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ObervalActivity extends AppCompatActivity implements View.OnClickListener
,ObseverManager.OnMessageChangeListner{

    @Bind(R.id.edit)
    EditText edit;
    @Bind(R.id.click)
    Button click;
    ObseverManager obseverManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        ButterKnife.bind(this);
        obseverManager=new ObseverManager();
        obseverManager.addListener(this);
        click.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click:
                obseverManager.dataChange(edit.getText().toString());
                break;
        }
    }

    @Override
    public void onMessageChange(String s) {
        click.setText(s);
    }
}
