package xpbd.threed.structure;

import processing.core.PVector;

public class Particle {

    static PVector gravity = new PVector(0, 10, 0);

    public PVector pos;
    public PVector prevPos;
    public PVector vel;

    public float mass;
    public  float invMass;

    public float radius;

    public boolean isFixed = false;

    public Particle(PVector pos, float mass) {
        this.pos = pos;
        this.prevPos = pos.copy();
        this.vel = new PVector();
        this.mass = mass;
        this.invMass = 1/mass;
        radius = 5;
    }

    public Particle(PVector pos, float mass, float radius) {
        this.pos = pos;
        this.prevPos = pos.copy();
        this.vel = new PVector();
        this.mass = mass;
        this.invMass = 1/mass;
        this.radius = radius;
    }

    public void applyGravity(float dt){
        this.vel.add(PVector.mult(gravity, dt));
    }

    public void updatePreviousPosition(){
        this.prevPos.set(this.pos);
    }

    public void updateCurrentPosition(float dt){
        pos.add(PVector.mult(vel, dt));
    }

    public void updateVelocity(float dt){
        vel.set(PVector.sub(pos, prevPos).div(dt));
    }

    public void fix(){
        this.invMass = 0;
        this.isFixed = true;
        this.vel.set(0,0);
    }

}
