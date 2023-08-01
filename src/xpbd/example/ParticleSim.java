package xpbd.example;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;
import xpbd.threed.structure.constraints.FloorConstraint;
import xpbd.threed.structure.Particle;
import xpbd.threed.structure.constraints.ParticleCollisionConstraint;
import xpbd.threed.structure.ParticleSystem;

public class ParticleSim extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{ParticleSim.class.getName()}, new ParticleSim());
    }

    @Override
    public void settings() {
        size(1000, 800, P3D);
    }

    ParticleSystem particleSystem;

    @Override
    public void setup() {

        PeasyCam cam = new PeasyCam(this, 600);

        particleSystem = new ParticleSystem(1, 0.5f);


        initSystem();

    }

    @Override
    public void draw() {
        background(0);

        if(keyPressed){
            initSystem();
        }

        particleSystem.simulate();
        particleSystem.draw(this);
    }

    public void initSystem(){

        particleSystem.getParticles().clear();
        particleSystem.getConstraints().clear();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                for (int k = 0; k < 10; k++) {
                    particleSystem.addParticle(new Particle(new PVector(i*6 + random(0,2), -100 -j*6 + random(0,2), k*6 + random(0,2)), 1,2));
                }
            }
        }

        for(Particle p :particleSystem.getParticles()) particleSystem.addConstraint(new FloorConstraint(0, p));

        for(Particle p1 : particleSystem.getParticles()){
            for(Particle p2: particleSystem.getParticles()){
                if(p1 == p2) continue;
                particleSystem.addConstraint(new ParticleCollisionConstraint(p1, p2));
            }
        }
    }
}
