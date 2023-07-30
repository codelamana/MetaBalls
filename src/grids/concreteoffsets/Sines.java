package grids.concreteoffsets;

import grids.OffsetFunction;

import static processing.core.PApplet.sin;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class Sines implements OffsetFunction {
    @Override
    public float dx(int i, int j, int n, int m) {
        return 30*sin(3*TWO_PI*(i-j)/n);
    }

    @Override
    public float dy(int i, int j, int n, int m) {
        return 30*sin(5*TWO_PI*(j+i)/n + PI/7f);
    }
}
