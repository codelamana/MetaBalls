package xpbd.threed.structure;

import processing.core.PApplet;
import processing.core.PVector;
import xpbd.threed.structure.constraints.*;

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

            for(int j = 0; j < 1; j++) {
                for (Constraint c : constraints) {
                    if(c.allFixed()) continue;
                    c.solve(dts);
                }
            }

            for (Particle p : particles) {
                p.updateVelocity(dts);
            }
        }
    }

    public void draw(PApplet parent){
        for (Particle p : particles) {
            parent.strokeWeight(5);
            parent.stroke(200);
            parent.point(p.pos.x,p.pos.y, p.pos.z);
        }

        for(Constraint c: constraints){
            if(c instanceof DistanceConstraint){
                DistanceConstraint cc = (DistanceConstraint) c;
                parent.strokeWeight(2);
                parent.line(cc.p1.pos.x, cc.p1.pos.y, cc.p1.pos.z, cc.p2.pos.x, cc.p2.pos.y, cc.p2.pos.z);
            }
            if(c instanceof SphereConstraint){
                SphereConstraint cc = (SphereConstraint) c;
                parent.pushMatrix();
                parent.translate(cc.pos.x, cc.pos.y, cc.pos.z);
                parent.sphere(cc.radius);
                parent.popMatrix();
            }

            if(c instanceof JointBendingConstraint){
                JointBendingConstraint cc = (JointBendingConstraint) c;
                parent.line(cc.p1.pos.x, cc.p1.pos.y, cc.p1.pos.z, cc.p2.pos.x, cc.p2.pos.y, cc.p2.pos.z);
                parent.line(cc.p3.pos.x, cc.p3.pos.y, cc.p3.pos.z, cc.p2.pos.x, cc.p2.pos.y, cc.p2.pos.z);
            }
        }

        parent.fill(100);
        parent.beginShape();
        parent.vertex(400, 0, 400);
        parent.vertex(400, 0, -400);
        parent.vertex(-400, 0, -400);
        parent.vertex(-400, 0, 400);
        parent.endShape();
    }

    public void testSetup(int n, int m){

        Particle p1 = new Particle(new PVector(0,-300, 0), 1);
        Particle p2 = new Particle(new PVector(100, -300, 0), 1);
        Particle p3 = new Particle(new PVector(0, -300, 100), 1);
        Particle p4 = new Particle(new PVector(25, -400, 25), 1);
        Particle p5 = new Particle(new PVector(50, -200, 25), 1);
        this.particles.add(p1);
        this.particles.add(p2);
        this.particles.add(p3);
        this.particles.add(p4);
        this.particles.add(p5);

        this.constraints.add(new DistanceConstraint(p1, p2));
        this.constraints.add(new DistanceConstraint(p1, p3));
        this.constraints.add(new DistanceConstraint(p1, p4));
        this.constraints.add(new DistanceConstraint(p2, p3));
        this.constraints.add(new DistanceConstraint(p2, p4));
        this.constraints.add(new DistanceConstraint(p3, p4));
        this.constraints.add(new DistanceConstraint(p5, p1));
        this.constraints.add(new DistanceConstraint(p5, p2));
        this.constraints.add(new DistanceConstraint(p5, p3));

        for(Particle p: particles) constraints.add(new FloorConstraint(0, p));

        constraints.add(new TetVolumeConstraint(p1, p2, p3 , p4));
        constraints.add(new TetVolumeConstraint(p1, p2, p3 , p5));

    }
    public void testSetup2(int n, int m){

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                particles.add(new Particle(new PVector(i - 50,-250, j-50), 1));
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



        //particles.get(0).fix();
        //particles.get(29).fix();

        for(Particle p: particles) constraints.add(new FloorConstraint(0, p));

        constraints.add(new SphereConstraint(50, new PVector(0,-200,0), particles));

    }

    public void testBendConstraint(){

        Particle p1 = new Particle(new PVector(300, -300, 0), 1);
        Particle p2 = new Particle(new PVector(400, -300, 0), 1);
        Particle p3 = new Particle(new PVector(500, -300, 0), 1);

        JointBendingConstraint c1 = new JointBendingConstraint(p1, p2, p3);

        DistanceConstraint d1 = new DistanceConstraint(p1, p2);
        DistanceConstraint d2 = new DistanceConstraint(p2, p3);

        p1.fix();
        p2.fix();

        particles.add(p1);
        particles.add(p2);
        particles.add(p3);

        constraints.add(c1);
        constraints.add(d1);
        constraints.add(d2);

    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setParticles(ArrayList<Particle> particles) {
        this.particles = particles;
    }

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }

    public void addParticle(Particle p){
        this.particles.add(p);
    }

    public  void addConstraint(Constraint c){
        this.constraints.add(c);
    }

    public void branchTest(int n, PVector initDir, float length){
        for (int i = 0; i < n; i++) {
            particles.add(new Particle(new PVector(i*20, -200, 0), 1));
            particles.add(new Particle(new PVector(i*20, -200, 20), 1));
            particles.add(new Particle(new PVector(i*20, -220, 20), 1));
            particles.add(new Particle(new PVector(i*20, -220, 0), 1));
        }
        for (int i = 0; i < n; i++) {
            constraints.add(new DistanceConstraint(particles.get(4*i), particles.get(4*i+1)));
            constraints.add(new DistanceConstraint(particles.get(4*i+1), particles.get(4*i+2)));
            constraints.add(new DistanceConstraint(particles.get(4*i+2), particles.get(4*i+3)));
            constraints.add(new DistanceConstraint(particles.get(4*i+3), particles.get(4*i)));
        }

        for (int i = 0; i < n-1; i++) {
            constraints.add(new DistanceConstraint(particles.get(4*i),   particles.get(4*(i+1))));
            constraints.add(new DistanceConstraint(particles.get(4*i+1), particles.get(4*(i+1)+1)));
            constraints.add(new DistanceConstraint(particles.get(4*i+2), particles.get(4*(i+1)+2)));
            constraints.add(new DistanceConstraint(particles.get(4*i+3), particles.get(4*(i+1)+3)));

        }
        for (int i = 0; i < 4; i++) {
            particles.get(i).fix();
        }


    }

}
