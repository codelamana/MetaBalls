package trees.lsystem.l2d;

import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Main.class.getName()}, new Main());
    }

    @Override
    public void settings() {
        size(800, 900);
    }

    @Override
    public void setup() {
        LSystem lSystem = new LSystem("B");

        lSystem.addRule('F', "FF-[-F+F+F]+[+F-F-F]");
        //lSystem.addRule('X', "F+[[X]-X]-F[-FX]+X");
        System.out.println(lSystem.iterate());
        for (int i = 0; i < 4; i++) {
                lSystem.iterate();
        }

        background(200);

        Turtle.State init = new Turtle.State();
        init.pos = new PVector(400, height);
        init.dir = new PVector(0,-1);
        Turtle t = new Turtle(init,7);
        stroke(0, 200, 0);
        t.walk(lSystem.currentState, this);
    }

    @Override
    public void draw() {

    }
}
