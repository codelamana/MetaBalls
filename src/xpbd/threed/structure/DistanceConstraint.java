package xpbd.threed.structure;

import processing.core.PVector;

public class DistanceConstraint implements Constraint {

    public Particle p1, p2;
    float currentDistance;
    float restDistance;

    float compliance = 0.0f;

    public DistanceConstraint(Particle p1, Particle p2, float restDistance) {
        this.p1 = p1;
        this.p2 = p2;
        this.restDistance = restDistance;
        this.currentDistance = PVector.dist(p1.pos, p2.pos);
    }

    public DistanceConstraint(Particle p1, Particle p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentDistance = PVector.dist(p1.pos, p2.pos);
        this.restDistance = currentDistance;
    }

    @Override
    public float solve(float dt) {
        PVector direction = PVector.sub(p2.pos, p1.pos).normalize();
        float alpha = compliance / dt / dt;
        currentDistance = PVector.dist(p1.pos, p2.pos);
        PVector dp1 = direction.copy().mult((p1.invMass/(p1.invMass + p2.invMass + alpha)) * (currentDistance - restDistance));
        PVector dp2 = direction.copy().mult((-p2.invMass/(p1.invMass + p2.invMass + alpha)) * (currentDistance - restDistance));
        p1.pos.add(dp1);
        p2.pos.add(dp2);
        return 0;
    }

    @Override
    public boolean allFixed() {
        return p1.isFixed && p2.isFixed;
    }
}
