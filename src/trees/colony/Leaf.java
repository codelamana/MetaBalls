package trees.colony;

import processing.core.PVector;

import java.util.ArrayList;

public class Leaf {

    PVector pos;

    float killZone = 35;

    float max_radius = 200;

    public Leaf(float x, float y){
        this.pos = new PVector(x, y);
    }

    public Leaf(float x, float y, float z) {
        this.pos = new PVector(x, y, z);
    }

    public boolean kill(ArrayList<Branch> branches){
        for(Branch b: branches){
            if(PVector.dist(b.end, this.pos) < killZone){
                return true;
            }
        }
        return false;
    }

    public boolean findNearestBranch(ArrayList<Branch> branches) {
        Branch nearest = null;
        float record = 100000;
        for(Branch b: branches){
            float d = PVector.dist(pos, b.end);
            if((nearest == null || d < record) && d < max_radius){
                nearest = b;
                record = d;
            }
        }

        if(nearest != null){
            nearest.addLeaf(this);
            return true;
        }
        return false;
    }
}
