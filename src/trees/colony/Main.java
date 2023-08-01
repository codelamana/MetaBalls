package trees.colony;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Main.class.getName()}, new Main());
    }


    ArrayList<Tree> trees = new ArrayList<>();

    ArrayList<Leaf> globalLeafsLeft = new ArrayList<>();
    ArrayList<Leaf> globalLeafsRight = new ArrayList<>();

    @Override
    public void settings() {
        size(800, 900, P3D);
        fullScreen();
    }

    @Override
    public void setup() {

        for (int i = 0; i < 500; i++) {
            globalLeafsLeft.add(new Leaf(random(-400, 50),
                                    random(-500,-200),
                                    random(-400, 400)));

            globalLeafsRight.add(new Leaf(random(-50, 400),
                    random(-500,-200),
                    random(-400, 400)));
        }

        //trees.add(new Tree(this, 750,  new PVector(0,0,0)));
        //trees.get(0).trunk();

        for(int i = 0; i < 5;i++){
            trees.add(new Tree(this, 500,  new PVector(-300,0, (i-2.5f)*150)));
            trees.get(i).trunk();
        }
        for(int i = 5; i < 10;i++){
            trees.add(new Tree(this, 500,  new PVector(300,0, (i-7.5f)*150)));
            trees.get(i).trunk();
        }


        PeasyCam cam = new PeasyCam(this, 0, -250 , 0, 500);
        //frameRate(2);


    }

    @Override
    public void draw() {
        background(0);
        lights();
        noStroke();
        fill(100);
        beginShape(QUAD);
        vertex(-5000,0, 5000);
        vertex(5000,0, 5000);
        vertex(5000,0, -5000);
        vertex(-5000,0, -5000);
        endShape();

        //drawLeafs();

        for (Tree t: trees){
            t.drawLeafs();
            t.growTree();
            t.setRadii(8);
            t.setColors();
            t.drawSingleSurfaces();
            t.drawAttachedLeafs();
        }

        //System.out.println(tree.branches.size());

    }

    public void drawLeafs(){
        stroke(255);
        strokeWeight(5);

        beginShape(POINTS);
        for (Leaf p : globalLeafsLeft) vertex(p.pos.x, p.pos.y, p.pos.z);
        for (Leaf p : globalLeafsRight) vertex(p.pos.x, p.pos.y, p.pos.z);
        endShape();
    }


}
