package trees.colony;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;
import static processing.core.PConstants.*;

public class Tree {

    ArrayList<Leaf> leafs = new ArrayList<>();
    ArrayList<Branch> branches = new ArrayList<>();

    PApplet parent;
    PVector root;

    int n;

    public Tree(PApplet parent, int n, PVector root) {
        this.parent = parent;
        this.n = n;
        this.root = root;
    }


    public void trunk() {

        generatePoints2D(n, new PVector(root.x, root.y - 200f*(float) Math.random() - 300, root.y), 150);

        //root = new PVector(0,0,0);
        PVector dir = new PVector(0, -10, 0);

        PVector tempStart = root.copy();
        PVector tempEnd = root.copy();
        Branch tempBranch;
        Branch tempParent = null;
        boolean found = false;
        do {
            tempEnd = tempEnd.add(dir);
            tempBranch = new Branch(tempStart, tempEnd, dir, tempParent);
            branches.add(tempBranch);
            tempStart = tempEnd.copy();
            tempParent = tempBranch;

            for (Leaf l : leafs) {
                if (l.findNearestBranch(branches)) found = true;
            }
            System.out.println("doing" + branches.size());
        } while (!found);
    }

    public void growTree() {
        Branch temp;
        ArrayList<Branch> newBranches = new ArrayList<>();

        if(branches.size() > 50000){
            return;
        }

        for (Leaf l : leafs) {
            l.findNearestBranch(branches);
        }

        for (Branch b : branches) {
            if (b.shouldGrow()) {
                temp = b.calculateNextBranch();
                if (temp != null) newBranches.add(temp);
            }
        }
        branches.addAll(newBranches);

        ArrayList<Leaf> removedLeafs = new ArrayList<>();
        for (Leaf l : leafs) {
            if (l.kill(branches)) removedLeafs.add(l);
        }
        leafs.removeAll(removedLeafs);
    }

    public void draw() {
        parent.stroke(255);
        parent.strokeWeight(5);

        parent.beginShape(POINTS);
        for (Leaf p : leafs) parent.vertex(p.pos.x, p.pos.y, p.pos.z);
        parent.endShape();

        parent.strokeWeight(4);

        for (Branch b : branches) {
            /*parent.beginShape(LINE);
            parent.vertex(b.start.x, b.start.y, b.start.z);
            parent.vertex(b.end.x, b.end.y, b.end.z);
            parent.endShape();*/
            //b.drawCircle(parent);
            parent.strokeWeight(1);
            parent.line(b.start.x, b.start.y, b.start.z,b.end.x, b.end.y, b.end.z);
        }

    }

    void generatePoints2D(int n, PVector center, float radius) {
        float theta, delta,  d;
        for (int i = 0; i < n; i++) {
            theta = parent.random(TWO_PI);
            delta = parent.random(TWO_PI);
            d = radius * abs(sin(parent.random(0, 1)*PI/2f));
            leafs.add(new Leaf(d * cos(theta) * sin(delta) + center.x, 2*d * sin(theta) * sin(delta) + center.y, d*cos(delta)));
        }
    }
}