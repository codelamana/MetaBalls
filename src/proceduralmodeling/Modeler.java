package proceduralmodeling;

import proceduralmodeling.primitives.Primitive;
import proceduralmodeling.primitives.Quad;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;

public class Modeler extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Modeler.class.getName()}, new Modeler());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
    }

    @Override
    public void setup() {
        Quad quad = new Quad(new PVector(100, 0, 0), new PVector(200, 0, 0), new PVector(), new PVector());
    }

    @Override
    public void draw() {

    }

    @Override
    public void vertex(float x, float y, float z) {
        super.vertex(x, -y, z);
    }
}
