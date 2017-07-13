package base.annotation;

import java.lang.annotation.Annotation;

/**
 * Created by kenan on 17/6/12.
 */
public class ZZdemo {

    public static void main(String []args){
        test();
    }



    private static void test (){
        try {
            Subject subject=new Subject();
            Class clazz=Hello.class;
            boolean isEmpty=clazz.isAnnotationPresent(SubjectAnnotation.class);
            System.out.println(isEmpty+"");

            Annotation []annotations=clazz.getAnnotations();
            System.out.println(annotations.length+"");

            for(Annotation annotation:annotations){
                System.out.println("loop");
                if(annotation instanceof SubjectAnnotation){
                    SubjectAnnotation sub=(SubjectAnnotation)annotation;
                    System.out.println(sub.subjectName()+sub.priority().name());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
