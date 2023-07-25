package trees.lsystem.l3d;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.TWO_PI;

public class Tree3D {

    Branch rootBranch;
    ArrayList<Branch> branches;

    PApplet parent;

    public Tree3D() {
        this.branches = new ArrayList<>();

        //rootBranch = new Branch(new PVector(0,0,0), new PVector(0,100,0));
        //this.branches.add(rootBranch);

    }

    public void draw(){
        for(Branch b: branches){
            b.draw(parent);
        }
    }

    public void drawEndCircles(){
        for(Branch b: branches){
            b.drawEndCircle(parent);
        }
    }

    public void drawSurfaces(){
        for(Branch b: branches){
            b.connectSurfaces(parent);
        }
    }

    public void recursiveTree(int level, int branches,
                              float length, float radius, float angle,
                              float lengthfactor, float sizefactor, int res) {
        rootBranch.setStartRadius(radius);
        rootBranch.setEndRadius(radius*sizefactor);
        recursiveTree(this.rootBranch, level, branches, length, radius*sizefactor, angle, lengthfactor, sizefactor, res);
    }

    public void recursiveTree(Branch rootBranch, int level, int branches,
                              float length, float radius, float angle,
                              float lengthfactor, float sizefactor, int res){

        if(level == 0) return;

        PVector mid = rootBranch.end;
        ArrayList<PVector> newNodes = new ArrayList<>();

        for(int i = 0; i < branches; i++){
            float newX = mid.x + length*cos(angle)*cos(i*TWO_PI/branches);
            float newY = mid.y + length * sin(angle);
            float newZ = mid.z + length*cos(angle)*sin(i*TWO_PI/branches);
            newNodes.add(new PVector(newX, newY, newZ));
        }

        ArrayList<Branch> newBranches = new ArrayList<>();

        for(PVector n: newNodes){
            Branch b = new Branch(mid, n);
            b.setStartRadius(radius);
            b.setEndRadius(radius*sizefactor);
            newBranches.add(b);
        }

        this.branches.addAll(newBranches);

        for(Branch b: newBranches){
            recursiveTree(b, level-1, branches, length*lengthfactor, radius*sizefactor,
                    angle, lengthfactor, sizefactor, res);
        }

    }


    /**
     * Sets
     */
    public void setParent(PApplet parent) {
        this.parent = parent;
    }
}
