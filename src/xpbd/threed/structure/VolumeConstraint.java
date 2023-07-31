package xpbd.threed.structure;

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
