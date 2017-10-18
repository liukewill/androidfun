package base.reflect;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

/**
 * Created by kenan on 17/6/7.
 */
public class NoCall {

    private String b;

    public NoCall(String s) {
        this.b = s;
    }

    public NoCall(){

    }

    private String  noCall(String s) {
        System.out.println("no-call-" + s+b);
        return s+"hello";
    }



}
