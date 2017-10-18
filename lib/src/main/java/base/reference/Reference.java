package base.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by kenan on 17/9/16.
 */

public class Reference {

    public static void main(String []args){
        String s=new String("22");
        WeakReference<String> weakReference=new WeakReference<>(s);
        System.out.println(weakReference.get());
        s=null;
        System.gc();
        System.out.println(weakReference.get());

        testPreference();

    }

    private static void testPreference(){
        ReferenceQueue<String> s=new ReferenceQueue<>();
        PhantomReference<String> phantomReference=new PhantomReference<>(new String("s"),s);
        System.out.println(phantomReference.get());



    }
}
