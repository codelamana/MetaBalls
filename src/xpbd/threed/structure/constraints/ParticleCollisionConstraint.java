package xpbd.threed.structure.constraints;

import processing.core.PVector;
import xpbd.threed.structure.Particle;

public class ParticleCollisionConstraint implements Constraint{

    Particle p1, p2;
    float currentDistance;

    public ParticleCollisionConstraint(Particle p1, Particle p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public float solve(float dt) {
        PVector direction = PVector.sub(p2.pos, p1.pos).normalize();
        currentDistance = PVector.dist(p1.pos, p2.pos);
        float overlap = currentDistance - (p1.radius+p2.radius);
        if(overlap <= 0){
            PVector dp1 = direction.copy().mult((p1.invMass/(p1.invMass + p2.invMass)) * overlap);
            PVector dp2 = direction.copy().mult((-p2.invMass/(p1.invMass + p2.invMass)) * overlap);
            p1.pos.add(dp1);
            p2.pos.add(dp2);
        }
        return 0;
    }

    @Override
    public boolean allFixed() {
        return false;
    }
}
