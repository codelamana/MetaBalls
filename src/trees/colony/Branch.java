package trees.colony;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;

public class Branch {

    PVector start;
    PVector end;
    PVector direction;
    Branch parent;
    ArrayList<Branch> children = new ArrayList<>();
    ArrayList<Leaf> attractors = new ArrayList<>();

    float length = 5;
    float attractionRadius = 100;

    private boolean deactivated = false;

    public Branch(PVector start, PVector end, PVector direction, Branch parent) {
        this.start = start;
        this.end = end;
        this.direction = direction.normalize();
        this.parent = parent;
    }


    public Branch calculateNextBranch() {
        // determine normalized Vectors
        ArrayList<PVector> normalizedDirs = new ArrayList<>();
        PVector temp = new PVector();
        PVector n = new PVector();
        for (Leaf l: attractors){
            PVector.sub(l.pos, this.end, temp);
            temp.normalize();
            n.add(temp);
        }
        n.normalize();//.mult((float) Math.random());

        // add new branch if there are attraction points
        if(attractors.size() != 0 && this.children.size() <= 4) {
            Branch newBranch = new Branch(this.end, this.end.copy().add(n.mult(length)), n, this);
            this.children.add(newBranch);
            return newBranch;
        }
        return null;
    }


    public boolean shouldGrow(){
        return attractors.size() > 0;
    }

    public void drawCircle(PApplet p){
        p.pushMatrix();
        p.translate(start.x, start.y, start.z);
        p.rotateX(PApplet.acos(1/direction.x));
        p.rotateY(PApplet.acos(1/direction.y));
        p.beginShape(PConstants.QUAD);
        p.vertex(-10, 0, 10);
        p.vertex(-10, 0, -10);
        p.vertex(10, 0, -10);
        p.vertex(10, 0, 10);
        p.endShape();
        p.popMatrix();

    }

    public void addLeaf(Leaf leaf) {
        this.attractors.add(leaf);
    }

}
