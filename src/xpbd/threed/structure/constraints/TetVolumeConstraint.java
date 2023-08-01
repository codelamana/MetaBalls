package xpbd.threed.structure.constraints;

import processing.core.PVector;
import xpbd.threed.structure.Particle;

public class TetVolumeConstraint implements Constraint{

    Particle p1, p2, p3, p4;
    static float compliance = 0.1f;

    float restVolume;

    public TetVolumeConstraint(Particle p1, Particle p2, Particle p3, Particle p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        restVolume = volume();
    }

    @Override
    public float solve(float dt) {

        PVector gc1 = new PVector();
        PVector gc2 = new PVector();
        PVector gc3 = new PVector();
        PVector gc4 = new PVector();

        PVector.cross(PVector.sub(p4.pos, p2.pos), PVector.sub(p3.pos, p2.pos), gc1);
        PVector.cross(PVector.sub(p3.pos, p1.pos), PVector.sub(p4.pos, p1.pos), gc2);
        PVector.cross(PVector.sub(p4.pos, p1.pos), PVector.sub(p2.pos, p1.pos), gc3);
        PVector.cross(PVector.sub(p2.pos, p1.pos), PVector.sub(p3.pos, p1.pos), gc4);

        float c = 6*(volume()-restVolume);
        float alpha = compliance/dt/dt;
        float lambda = -c/(p1.invMass * gc1.magSq() + p2.invMass * gc2.magSq() + p3.invMass * gc3.magSq() + p4.invMass * gc4.magSq() + alpha);

        p1.pos.add(gc1.mult(lambda*p1.invMass));
        p2.pos.add(gc2.mult(lambda*p2.invMass));
        p3.pos.add(gc3.mult(lambda*p3.invMass));
        p4.pos.add(gc4.mult(lambda*p4.invMass));

        return 0;
    }

    @Override
    public boolean allFixed() {
        return p1.isFixed && p2.isFixed && p3.isFixed && p4.isFixed;
    }

    public float volume(){
        PVector t = new PVector();
        PVector.cross(PVector.sub(p2.pos, p1.pos), PVector.sub(p3.pos, p1.pos), t);
        float vol = PVector.dot(t , PVector.sub(p4.pos, p1.pos))/ 6f;
        return vol;
    }
}
