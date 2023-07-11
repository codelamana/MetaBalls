package trees.colony;

import processing.core.PVector;

import java.util.ArrayList;

public class Branch {

    PVector start;
    PVector end;
    PVector direction;
    Branch parent;
    ArrayList<Branch> children = new ArrayList<>();
    ArrayList<Leaf> attractors = new ArrayList<>();

    float length = 30;
    float attractionRadius = 200;
    private boolean deactivated = false;

    public Branch(PVector start, PVector end, PVector direction, Branch parent) {
        this.start = start;
        this.end = end;
        this.direction = direction;
        this.parent = parent;
    }

    public void determineAttractors(ArrayList<Leaf> leafs) {
        for (Leaf l: leafs){
            if(PVector.dist(end, l.pos) < attractionRadius) attractors.add(l);
        }
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
        n.normalize();

        // add new branch if there are attraction points
        if(attractors.size() != 0) {
            Branch newBranch = new Branch(this.end, this.end.copy().add(n.mult(length)), n, this);
            this.children.add(newBranch);
            return newBranch;
        }
        return null;
    }

    public boolean isDeactivated(){
        return deactivated;
    }

    public void deactivate(){
        if(children.size() >= 2) this.deactivated = true;
    }

    public void addLeaf(Leaf leaf) {
    }
}
