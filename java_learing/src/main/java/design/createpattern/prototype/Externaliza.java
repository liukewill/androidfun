package design.createpattern.prototype;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by kenan on 17/7/7.
 */

public class Externaliza implements Externalizable {
    public transient String s="33333";
    public  int i=9;
    public int ii=99;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }
}
