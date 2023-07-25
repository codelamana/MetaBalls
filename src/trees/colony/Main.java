package trees.colony;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;

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


        // build trunk ( width/2; height to lower half of sphere)


        tree = new Tree(this, 5000,  new PVector(0,0,0));
        tree.trunk();


        PeasyCam cam = new PeasyCam(this, 1000);
        //frameRate(2);
    }

    @Override
    public void draw() {
        background(0);

        noStroke();
        fill(100);
        beginShape(QUAD);
        vertex(-200,0, 200);
        vertex(200,0, 200);
        vertex(200,0, -200);
        vertex(-200,0, -200);
        endShape();

        // draw leafs
        tree.growTree();
        tree.draw();

        System.out.println(tree.branches.size());

    }


}
