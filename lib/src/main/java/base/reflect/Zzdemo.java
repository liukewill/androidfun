package base.reflect;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kenan on 17/5/10.
 * 反射:运行时 获取Class任何信息, 注Class对应字节码文件信息
 * 对属性 可访问,修改值
 * 对方法 可调用
 * 对泛型类型 可获取泛型类型信息
 *
 * 泛型 指定一个类,接口,方法的参数类型
 * 类型擦除,java伪泛型,List<String> 与List<Integeger>
 *
 */
public class Zzdemo<T>  {

    private Map< String,Integer> score;

    public static void main(String[] args) {

        genericClassParameterizedType();
    }

    public static void reflectDemo(){
        //反射调用add 添加string
        ArrayList<Integer> arrayList=new ArrayList<>();
        try {
            Class arrayClass=Class.forName(arrayList.getClass().getName());

            Method addMethod=arrayList.getClass().getMethod("add", Object.class);

            addMethod.invoke(arrayList,"加了一个string");

            System.out.println(arrayList.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reflectAPI(){
        try {
            //获取类
            Class<?> noCallClass = Class.forName("NoCall");
            Constructor<?> constructor[] = noCallClass.getConstructors();
            NoCall noCallObj = (NoCall) constructor[0].newInstance("s");

            //获取方法
            Method noCallMethod = noCallClass.getDeclaredMethod("noCall", String.class);
            noCallMethod.setAccessible(true);
            String object = (String) noCallMethod.invoke(noCallObj, "dd");
            System.out.println(object);

            //获取属性
            Field field = noCallClass.getDeclaredField("b");
            field.setAccessible(true);
            System.out.println(field.get(noCallObj).toString());
            field.set(noCallObj, "haha");
            System.out.println(field.get(noCallObj).toString());
            noCallMethod.invoke(noCallObj, "dd");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void genericFieldParameterizedType(){
        try {
            Class<Zzdemo> clazz = Zzdemo.class;
            Field f = clazz.getDeclaredField("score");
            Class<?> a = f.getType();
            System.out.println("score的类型是：" + a);
            Type gType = f.getGenericType();
            if (gType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) gType;
                Type rType = pType.getRawType();
                System.out.println("原始类型是：" + rType);
                Type[] tArgs = pType.getActualTypeArguments();
                System.out.println("泛型类型是：");
                for (int i = 0; i < tArgs.length; i++) {
                    System.out.println("第" + i + "个泛型类型是：" + tArgs[i]);
                }
            } else {
                System.out.println("获取泛型类型出错！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void genericClassParameterizedType(){
        Zzdemo<String> zzdemo=new Zzdemo<String>(){};
        Class<?> clazz=zzdemo.getClass();
        Class supperClass=clazz.getSuperclass();
        Type type= clazz.getGenericSuperclass();
        if(type instanceof ParameterizedType){
            System.out.println(type.toString());
        }

        Class clazz1 = new ArrayList<Integer>().getClass();
        Class clazz2 = new String[0].getClass();
        Class clazz3 = String.class;
        Class clazz4 = Object.class;

        System.out.println(clazz1.getGenericSuperclass());
        System.out.println(clazz2.getGenericSuperclass());
        System.out.println(clazz3.getGenericSuperclass());
        System.out.println(clazz4.getGenericSuperclass());
    }

}
