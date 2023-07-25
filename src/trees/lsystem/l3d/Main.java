package trees.lsystem.l3d;

import peasy.PeasyCam;
import processing.core.PApplet;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Main.class.getName()}, new Main());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
    }

    Tree3D tree;

    @Override
    public void setup() {
        background(0);

        tree = new Tree3D();
        tree.setParent(this);

        System.out.println(tree.branches.size());
        tree.recursiveTree(
                3, 6, 100,10,PI/4, 0.23f, 0.25f, 5);
        PeasyCam cam = new PeasyCam(this, 500);

        System.out.println(tree.branches.size());
        lights();
    }

    @Override
    public void draw() {
        background(0);
        //tree.draw();
        // tree.drawEndCircles();
        tree.drawSurfaces();
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
