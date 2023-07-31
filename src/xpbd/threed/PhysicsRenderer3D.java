package xpbd.threed;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;
import xpbd.threed.mesh.Body;
import xpbd.threed.structure.ParticleSystem;

public class PhysicsRenderer3D extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{PhysicsRenderer3D.class.getName()}, new PhysicsRenderer3D());
    }

    @Override
    public void settings() {
        size(1000, 800, P3D);
    }

    ParticleSystem particleSystem;

    @Override
    public void setup() {

        PeasyCam cam = new PeasyCam(this, 600);

        particleSystem = new ParticleSystem(5, 0.05f);
        particleSystem.testSetup2(30, 30);
        //particleSystem.testBendConstraint();
        //particleSystem.branchTest(10, new PVector(1,-1, 10), 20);
        /*Body b = Body.testBody();

        particleSystem = b.getAsParticleSystemWithConstraints();
        System.out.println(b);
        particleSystem.getParticles().get(0).fix();
        particleSystem.getParticles().get(1).fix();
        particleSystem.getParticles().get(2).fix();
        particleSystem.getParticles().get(3).fix();*/
    }

    @Override
    public void draw() {
        background(0);

        if(keyPressed){
            particleSystem = new ParticleSystem(5, 0.05f);
            particleSystem.testSetup2(100, 100);
        }

        particleSystem.simulate();
        particleSystem.draw(this);
    }
}
