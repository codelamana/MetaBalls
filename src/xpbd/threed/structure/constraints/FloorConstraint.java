package xpbd.threed.structure.constraints;

import xpbd.threed.structure.Particle;

public class FloorConstraint implements Constraint {

    float height = 800;

    Particle p;

    public FloorConstraint(float height, Particle p) {
        this.height = height;
        this.p = p;
    }

    @Override
    public float solve(float dt) {
        if(p.pos.y + p.radius > height) {
            p.pos.y = height;
        }
        return 0;
    }

    @Override
    public boolean allFixed() {
        return p.isFixed;
    }
}
