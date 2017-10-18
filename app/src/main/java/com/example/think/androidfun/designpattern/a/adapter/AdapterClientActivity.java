package com.example.think.androidfun.designpattern.a.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.think.androidfun.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdapterClientActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.btn)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_client);
        ButterKnife.bind(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        adapterWork();
    }

    private void adapterWork(){
        UsbPort usbPort=new UsbPort() {
            @Override
            public void workWithUsb() {
                Log.i("lk","usb port work");
            }
        };

        TypeCPort typeCPort=new UsbToTypecAdapter(usbPort);

        typeCPort.workWithTypc();
    }
}
