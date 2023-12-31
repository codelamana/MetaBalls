package trees.colony;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public class Tree {

    ArrayList<Leaf> leafs = new ArrayList<>();
    ArrayList<Branch> branches = new ArrayList<>();

    PApplet parent;
    PVector root;

    int n;

    int prev = 0;

    public Tree(PApplet parent, int n, PVector root) {
        this.parent = parent;
        this.n = n;
        this.root = root.copy();
        generatePoints2Duniform(n, new PVector(root.x, root.y - 350, root.z), 250);
        System.out.println(this.root);
    }

    public Tree(PApplet parent, int n, PVector root, ArrayList<Leaf> newLeafs) {
        this.parent = parent;
        this.n = n;
        this.root = root;
        this.leafs = newLeafs;
    }


    public void trunk() {
        //root = new PVector(0,0,0);
        System.out.println(leafs.size());
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
        } else if(prev == branches.size()){
            System.out.println("Converged");
            leafs.clear();
            return;
        }

        prev = branches.size();

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

    public void drawSurfaces(){
        for(Branch b: branches){
            b.connectSurfaces(parent);
        }
    }

    public void drawInterSurfaces(){
        for(Branch b: branches){
            b.connectInterSurfaces(parent);
        }
    }

    public void drawSingleSurfaces(){
        for(Branch b: branches){
            b.connectToPreviousSurfaces(parent);
        }
    }

    public void drawLeafs(){
        parent.stroke(255);
        parent.strokeWeight(5);

        parent.beginShape(POINTS);
        for (Leaf p : leafs) parent.vertex(p.pos.x, p.pos.y, p.pos.z);
        parent.endShape();
    }

    public void drawAttachedLeafs(){
        for(Branch b: this.branches){
            b.drawAttachedLeafs(parent);
        }
    }

    public void drawLines() {
        parent.strokeWeight(4);

        for (Branch b : branches) {
            parent.strokeWeight(1);
            parent.line(b.start.x, b.start.y, b.start.z,b.end.x, b.end.y, b.end.z);
        }

    }

    void setRadii(float startRadius){
        branches.get(0).propagateRadius(startRadius);
    }
    void setColors(){
        branches.get(0).backpropagateColor();
    }

    void generatePoints2D(int n, PVector center, float radius) {
        System.out.println("Center" + center);
        float theta, delta,  d;
        for (int i = 0; i < n; i++) {
            theta = parent.random(TWO_PI);
            delta = parent.random(TWO_PI);
            d = radius * parent.random(0f, 1);
            leafs.add(new Leaf(1.5f*d * cos(theta) * sin(delta) + center.x, d * sin(theta) * sin(delta) + center.y, d*cos(delta) + center.z));
        }
    }

    void generatePoints2Duniform(int n, PVector center, float radius) {
        System.out.println("Center" + center);
        float theta, delta,  d;
        for (int i = 0; i < n; i++) {
            theta = (float) (2 * TWO_PI * Math.random());
            delta = (float) Math.acos(Math.random() * 2 -1);
            d = (float) (radius * Math.cbrt(Math.random()));
            leafs.add(new Leaf( d * cos(theta) * sin(delta) + center.x,
                                d * sin(theta) * sin(delta) + center.y,
                                d * cos(delta) + center.z));
        }
    }
}