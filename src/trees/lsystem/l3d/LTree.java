package trees.lsystem.l3d;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;

public class LTree extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{LTree.class.getName()}, new LTree());
    }

    Tree3D tree;

    @Override
    public void settings() {
        size(800, 600, P3D);
    }

    @Override
    public void setup() {
        LSystem3D lSystem3D = new LSystem3D("F");
        Turtle3D.State initial = new Turtle3D.State(
                new Branch(new PVector(), new PVector(0,0,0)),
                new PVector(),
                new PVector(0,1,0)
        );
        Turtle3D turtle3D = new Turtle3D(initial, 50);
        tree = turtle3D.walk("F/F", this);
        tree.setParent(this);

        PeasyCam cam = new PeasyCam(this, 500);
    }

    @Override
    public void draw() {
        background(0);
        tree.draw();
        tree.drawEndCircles();
    }

    @Override
    public void line(float x1, float y1, float z1, float x2, float y2, float z2) {
        super.line(x1, -y1, z1, x2, -y2, z2);
    }

    @Override
    public void vertex(float x, float y, float z) {
        super.vertex(x, -y, z);
    }

    @Override
    public void point(float x, float y, float z) {
        super.point(x, -y, z);
    }
}
