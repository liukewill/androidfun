package com.example.think.androidfun.butterkinfe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.BindView;
import com.example.think.androidfun.R;
import com.sina.inject_api.ViewInject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 编译时注解与运行时注解
 * 编译时：对源码进行检测，找到annotation，进行处理，生成而外面的源文件和其他文件并且编译，并且一起生成class文件
 * 运行时：使用反射获取注解内容
 *
 * 编译时注解：APT: annotation Processor tool  APT于gradle 2.2之后停止维护
 *          annotation Processor google官方内置框架
 */
public class ButterKinfeActivity extends AppCompatActivity {


    @BindView(R.id.iv)
    public ImageView imageView;

    private int [] draws={R.drawable.dog2,R.drawable.dog1};

    @GuidePages({R.drawable.dog2,R.drawable.dog1})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_kinfe);
        ViewInject.inject(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageView.setImageDrawable(getDrawable(R.drawable.dog1));
                restart();
            }
        });
        getAnnotationView();
        InjectManager.injectEvents(this);
        getImageResource();
    }
    private void onClickEvnet(View view){
        Toast.makeText(this,"eee",Toast.LENGTH_LONG).show();
    }

    /**
     * 运行时，反射获取view 反射效率低
     * butterknife实际为运行为编辑时执行
     */
    private void getAnnotationView() {//运行时 反射获取View
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.getAnnotations() != null) {
                    if (field.isAnnotationPresent(GetVIew.class)) {
                        GetVIew getview = field.getAnnotation(GetVIew.class);
                        field.set(this, findViewById(getview.value()));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    private void getImageResource(){
        try {
            Method  oncreate=this.getClass().getDeclaredMethod("onCreate",Bundle.class);
            if(oncreate.getAnnotations()!=null){
                if(oncreate.isAnnotationPresent(GuidePages.class)){
                    GuidePages guide=oncreate.getAnnotation(GuidePages.class);
                    int []res=guide.value();
//                    imageView.setImageDrawable(getDrawable(res[0]));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }




    private void restart(){
        Intent mStartActivity = new Intent(this, ButterKinfeActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);

    }
}
