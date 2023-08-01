package proceduralmodeling.primitives;

import java.util.ArrayList;

public interface Primitive {

    public ArrayList<Primitive> splitX(float[] relativeValues);
    public ArrayList<Primitive> splitY(float[] relativeValues);

}
