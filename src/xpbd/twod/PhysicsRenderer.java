package xpbd.twod;

import processing.core.PApplet;
import xpbd.twod.structure.ParticleSystem;

public class PhysicsRenderer extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{PhysicsRenderer.class.getName()}, new PhysicsRenderer());
    }

    @Override
    public void settings() {
        size(1000, 800);
    }

    ParticleSystem particleSystem;

    @Override
    public void setup() {
        particleSystem = new ParticleSystem(2, 0.05f);
        particleSystem.testSetup(3, 4);
    }

    @Override
    public void draw() {
        background(0);

        particleSystem.simulate();
        particleSystem.draw(this);
    }
}
