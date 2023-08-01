package xpbd.threed.structure.constraints;

import xpbd.threed.structure.Particle;

import java.util.ArrayList;

public class VolumeConstraint implements Constraint{

    ArrayList<Particle> particles;

    @Override
    public float solve(float dt) {
        return 0;
    }

    @Override
    public boolean allFixed() {
        return false;
    }
}
