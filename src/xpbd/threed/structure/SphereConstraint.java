package xpbd.threed.structure;

import processing.core.PVector;

import java.util.ArrayList;

public class SphereConstraint implements Constraint{

    float radius;
    PVector pos;

    ArrayList<Particle> particles = new ArrayList<>();

    public SphereConstraint(float radius, PVector pos, ArrayList<Particle> particles) {
        this.radius = radius;
        this.pos = pos;
        this.particles = particles;
    }


    @Override
    public float solve(float dt) {
        float currentDist = 0;
        PVector dir = new PVector();
        for(Particle p: particles){
            currentDist = PVector.dist(pos, p.pos);
            if(currentDist < radius){
                PVector.sub(p.pos, pos, dir);
                dir.setMag(radius-currentDist);
                p.pos.add(dir);
            }
        }
        return 0;
    }

    @Override
    public boolean allFixed() {
        return false;
    }
}
