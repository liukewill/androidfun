package design.createpattern.prototype;

import java.io.Serializable;

/**
 * Created by kenan on 17/6/16.
 */
public class SubType implements Cloneable{

    public String typeName;
    public int  typeNum;
    public transient Listener listener=new Listener() {
        @Override
        public void notifyChange() {

        }
    };

    public SubType(String typeName, int typeNum) {
        this.typeName = typeName;
        this.typeNum = typeNum;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public interface Listener {
        int i=2;
        void notifyChange();
    }
}
