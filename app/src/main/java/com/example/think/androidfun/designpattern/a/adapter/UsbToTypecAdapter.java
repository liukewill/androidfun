package com.example.think.androidfun.designpattern.a.adapter;

/**
 * Created by kenan on 16/8/15.
 */
public class UsbToTypecAdapter implements TypeCPort{

    private UsbPort usbPort;

    public UsbToTypecAdapter(UsbPort usbPort){
        this.usbPort = usbPort;
    }


    @Override
    public void workWithTypc() {
        usbPort.workWithUsb();
    }
}
