package com.example.think.androidfun.designpattern.observer.adapter;

/**
 * Created by kenan on 16/8/15.
 */
public class UsbToTypecAdapter implements UsbPort{

    private TypeCPort typeCPort;

    public UsbToTypecAdapter(TypeCPort typeCPort){
        this.typeCPort = typeCPort;
    }

    @Override
    public void workWithUsb() {
        typeCPort.workWithTypc();
    }
}
