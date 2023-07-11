package flowfield;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle {
    PVector pos = new PVector();
    PVector acc = new PVector(0,0);
    PVector vel = new PVector(0,0);

    public Particle(PVector pos) {
        this.pos = pos;
        this.vel.limit(10);
    }

    public void apply(PVector force){
        this.acc.add(force);
    }

    public void update(){
        this.vel.add(acc);
        this.vel.limit(5);
        this.pos.add(vel);

        this.acc.mult(0);
    }

    public void follow(FlowField f){
        f.addParticle(this);
    }

    public void edge(PApplet p){
        if(pos.x < 0) pos.x = p.width - 10;
        if(pos.x > p.width) pos.x = 10;

        if(pos.y < 0) pos.y = p.height - 10;
        if(pos.y > p.height) pos.y = 10;
    }

    @Override
    public String toString() {
        return "[" + pos.x + ", " + pos.y + "]";
    }
}
