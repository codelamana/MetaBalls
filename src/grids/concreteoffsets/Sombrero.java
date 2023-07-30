package grids.concreteoffsets;

import grids.OffsetFunction;
import processing.core.PVector;

import javax.swing.text.ParagraphView;

import static processing.core.PApplet.sin;

public class Sombrero implements OffsetFunction {

    @Override
    public float dx(int i, int j, int n, int m) {
        PVector direction = new PVector(i - n/2, j - m/2);
        int newI = (i - n/2)*(i - n/2);
        int newJ = (j - m/2)*(j - m/2);
        direction.normalize();
        return direction.x * 10*sin((newI + newJ)/n);///(newI + newJ);
    }

    @Override
    public float dy(int i, int j, int n, int m) {
        PVector direction = new PVector(i - n/2, j - m/2);
        int newI = (i - n/2)*(i - n/2);
        int newJ = (j - m/2)*(j - m/2);
        direction.normalize();
        return direction.y * 10*sin((newI + newJ)/n);///(newI + newJ);
    }
}
