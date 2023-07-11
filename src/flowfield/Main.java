package flowfield;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Main.class.getName()}, new Main());
    }

    int res = 10;

    FlowField f;

    ArrayList<Particle> particles = new ArrayList<>();

    PVector[][] vectorField;
    int n, m;

    @Override
    public void settings() {
        size(800, 600);
        fullScreen();
    }

    @Override
    public void setup() {
        background(255);
        f = new FlowField(this, res);
        for (int i = 0; i < 200 ; i++) {
            //particles.add(new Particle(new PVector(220 + 10*i, 300)));
            particles.add(new Particle(new PVector(random(width-20), random(height-20))));
            particles.get(i).follow(f);
        }

        //f.draw();
        f.drawParticles();

        colorMode(HSB);
    }

    @Override
    public void draw() {
        //background(255);
        //f.draw();
        f.updateParticles();
        f.drawParticles();
    }

    @Override
    public void keyPressed() {
        if(key == ' ') {
            System.out.println("Yay");
            f.newVectorfield();
        }
    }
}
