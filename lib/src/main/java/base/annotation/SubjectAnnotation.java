package base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

/**
 * Created by kenan on 17/6/12.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Documented
public @interface SubjectAnnotation {
    public enum Priority{LOW,MEDIUM,HIGH}
    public String subjectName() default "className";
    public Priority priority() default Priority.LOW;

    Class clazz=Class.class;

}


