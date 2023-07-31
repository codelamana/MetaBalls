package xpbd.threed.structure;

import processing.core.PApplet;
import processing.core.PVector;



public class JointBendingConstraint implements Constraint{

    Particle p1, p2, p3; // p2 is connecting particle;
    PVector d1, d3, gradP1, gradP3;
    PVector crossD1D3;
    float restAngle;
    private float compliance = 0.001f;

    public JointBendingConstraint(Particle p1, Particle p2, Particle p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        d1 = new PVector();
        d3 = new PVector();
        gradP1 = new PVector();
        gradP3 = new PVector();
        crossD1D3 = new PVector();
        this.restAngle = angle();
    }

    @Override
    public float solve(float dt) {
        float c = angle() - restAngle;
        PVector.cross(d1, d3, crossD1D3);
        PVector.cross(crossD1D3, d3, gradP3);
        PVector.cross(d1, crossD1D3, gradP1);

        gradP1.normalize();
        gradP3.normalize();

        if(p1.invMass * gradP1.magSq() + p3.invMass * gradP3.magSq() + compliance/dt/dt < 0.0001f) return 0;
        float lambda = -c/(p1.invMass * gradP1.magSq() + p3.invMass * gradP3.magSq() + compliance/dt/dt);
        p1.pos.add(gradP1.mult(lambda * p1.invMass));
        p3.pos.add(gradP3.mult(lambda * p3.invMass));
        return 0;
    }

    @Override
    public boolean allFixed() {
        return p1.isFixed && p2.isFixed && p3.isFixed;
    }

    public float angle(){
        PVector.sub(p3.pos, p2.pos, d3);
        PVector.sub(p1.pos, p2.pos, d1);
        return PApplet.acos(PVector.dot(d1, d3) / (d1.mag()*d3.mag()));
    }
}
