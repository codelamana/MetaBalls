package trees.colony;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Main.class.getName()}, new Main());
    }

    ArrayList<Leaf> leafs = new ArrayList<>();
    ArrayList<Branch> branches = new ArrayList<>();

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        generatePoints2D(200, new PVector(width/2, height/2), 150);

        // build trunk ( width/2; height to lower half of sphere)
        PVector root = new PVector(width/2, height);
        PVector dir = new PVector(0,-10, 0);

        PVector tempStart = root.copy();
        PVector tempEnd = root.copy();
        Branch tempBranch;
        Branch tempParent = null;
        for (int i = 0; i < 10; i++) {
            tempEnd = tempEnd.add(dir);
            tempBranch = new Branch(tempStart, tempEnd, dir, tempParent);
            branches.add(tempBranch);
            tempStart = tempEnd.copy();
            tempParent = tempBranch;
        }
    }

    @Override
    public void draw() {
        background(0);

        Branch temp;
        ArrayList<Branch> newBranches = new ArrayList<>();

        for(Leaf l: leafs){
            l.findNearestBranch(branches);
        }

        for (Branch b: branches){
            if(!b.isDeactivated()) {
                b.determineAttractors(leafs);
                temp = b.calculateNextBranch();
                if (temp != null) newBranches.add(temp);
            }
            b.deactivate();
        }
        branches.addAll(newBranches);

        ArrayList<Leaf> removedLeafs = new ArrayList<>();
        for(Leaf l : leafs){
            if(l.kill(branches)) removedLeafs.add(l);
        }
        leafs.removeAll(removedLeafs);

        // draw leafs
        stroke(255);
        strokeWeight(5);

        beginShape(POINTS);
        for(Leaf p: leafs) vertex(p.pos.array());
        endShape();

        strokeWeight(1);
        beginShape(LINES);
        for (Branch b: branches){
            vertex(b.start.array());
            vertex(b.end.array());
        }
        endShape();

    }

    void generatePoints2D(int n, PVector center, float radius){
        float theta, d;
        for (int i = 0; i < n; i++) {
            theta = random(TWO_PI);
            d = random(20,radius);
            this.leafs.add(new Leaf(d*cos(theta) + center.x, d*sin(theta)+center.y));
            System.out.println(this.leafs.get(leafs.size()-1).toString());
        }
    }
}
