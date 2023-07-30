package trees.colony;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Main.class.getName()}, new Main());
    }

    Tree tree, tree2;

    @Override
    public void settings() {
        size(800, 900, P3D);
    }

    @Override
    public void setup() {

        tree = new Tree(this, 500,  new PVector(0,0,0));
        tree.trunk();

        PeasyCam cam = new PeasyCam(this, 1000);
        //frameRate(2);

    }

    @Override
    public void draw() {
        background(0);
        lights();
        noStroke();
        fill(100);
        beginShape(QUAD);
        vertex(-200,0, 200);
        vertex(200,0, 200);
        vertex(200,0, -200);
        vertex(-200,0, -200);
        endShape();

        // draw leafs
        tree.drawLeafs();
        tree.growTree();
        //tree.drawLines();
        tree.setRadii(15);
        tree.setColors();
        //tree.drawSurfaces();
        //tree.drawInterSurfaces();
        tree.drawSingleSurfaces();
        System.out.println(tree.branches.size());

    }


}
