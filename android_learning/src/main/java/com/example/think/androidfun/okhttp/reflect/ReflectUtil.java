package com.example.think.androidfun.okhttp.reflect;

import android.util.Log;
import android.widget.Button;

import com.example.think.androidfun.okhttp.bean.CloundBean;
import com.example.think.androidfun.okhttp.bean.EarthBean;
import com.example.think.androidfun.okhttp.bean.SkyBean;
import com.example.think.androidfun.okhttp.bean.WeatherBean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by kenan on 16/8/17.
 */
public class ReflectUtil {
    private Button button;

    public void reflectMethod() {

        try {
            Class<?> test1 = Class.forName("com.example.think.androidfun.okhttp.bean.WeatherBean");

            WeatherBean p = (WeatherBean) test1.newInstance();

            Class<?> classType = p.getClass();

            // 获取Method对象
            Method method = classType.getDeclaredMethod("weatherTest", String.class);

            method.setAccessible(true); // 抑制Java的访问控制检查
            // 如果不加上上面这句，将会Error: TestPrivate can not access a member of class PrivateClass with modifiers "private"
            method.invoke(p, "helloworld");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reflectField() {
        try {
//            WeatherBean weatherBean = new WeatherBean();
//            Field fields[] = weatherBean.getClass().getDeclaredFields();
            Field fields[] = button.getClass().getSuperclass().getDeclaredFields();
            String[] name = new String[fields.length];
            Object[] value = new Object[fields.length];
            Field.setAccessible(fields, true);
            for (int i = 0; i < name.length; i++) {
                name[i] = fields[i].getName();
                System.out.println(name[i] + "-> ");
                Log.i("lk", name[i] + "-> ");
                value[i] = fields[i].get(button);
                System.out.println(value[i]);
                Log.i("lk", value[i] + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reflectSupperClass() {
        try {
            WeatherBean weatherBean = (WeatherBean) Class.forName("com.example.think.androidfun.okhttp.bean.WeatherBean").newInstance();
            Log.i("kenan", "supperclass:" + weatherBean.getClass().getSuperclass().getName());
            Log.i("kenan", "supperclass:" + weatherBean.getClass().getGenericSuperclass());
            //获取父类范型
            Type type = weatherBean.getClass().getGenericSuperclass();
            //ParameterizedType参数化类型，即泛型
            ParameterizedType p = (ParameterizedType) type;
        } catch (Exception e) {

        }
    }

    public void typeReflect(){
//        SupperBean<CloundBean> bean=new SupperBean<CloundBean>() {
//        };
//        Log.i("lk","tttt--->"+bean.getTClass());

        SkyBean<EarthBean,CloundBean> skyBean=new SkyBean<EarthBean,CloundBean>() {
            @Override
            public void dosth() {

            }
        };

        skyBean.getKtype();
        skyBean.getVtype();
    }

}
