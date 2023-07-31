package xpbd.twod.structure;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class ParticleSystem {

    ArrayList<Particle> particles = new ArrayList<>();
    ArrayList<Constraint> constraints = new ArrayList<>();

    private int numSubsteps = 5;
    private float dt = 0.2f;

    public ParticleSystem(int numSubsteps, float dt) {
        this.numSubsteps = numSubsteps;
        this.dt = dt;
    }


    public void simulate(){
        float dts = dt / numSubsteps;
        for (int i = 0; i < numSubsteps; i++) {
            for (Particle p : particles) {
                if(p.isFixed) continue;
                p.applyGravity(dts);
                p.updatePreviousPosition();
                p.updateCurrentPosition(dts);
            }

            for (Constraint c : constraints) {
                c.solve(dts);
            }

            for (Particle p : particles) {
                p.updateVelocity(dts);
            }
        }
    }

    public void draw(PApplet parent){
        for (Particle p : particles) {
            parent.strokeWeight(5);
            parent.stroke(255);
            parent.point(p.pos.x,p.pos.y);
        }

        for(Constraint c: constraints){
            if(c instanceof DistanceConstraint){
                DistanceConstraint cc = (DistanceConstraint) c;
                parent.strokeWeight(2);
                parent.line(cc.p1.pos.x, cc.p1.pos.y, cc.p2.pos.x, cc.p2.pos.y);
            }
        }
    }

    public void testSetup(int n, int m){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                particles.add(new Particle(new PVector(i* 20+100, j*20+100), 1));
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m-1; j++) {
                constraints.add(new DistanceConstraint(particles.get(i*m+j), particles.get(i*m+j+1)));
            }
        }
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < m; j++) {
                constraints.add(new DistanceConstraint(particles.get((i+1)*m+j), particles.get(i*m+j)));
            }
        }

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < m-1; j++) {
                constraints.add(new DistanceConstraint(particles.get((i+1)*m+j+1), particles.get(i*m+j)));
                constraints.add(new DistanceConstraint(particles.get((i+1)*m+j), particles.get(i*m+j+1)));
            }
        }

        for(Particle p: particles) constraints.add(new FloorConstraint(800, p));

    }



}
