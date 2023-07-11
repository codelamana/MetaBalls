package metaballs;

import processing.core.PApplet;
import processing.svg.PGraphicsSVG;

public class SVGTest extends PApplet {

    PGraphicsSVG svg = new PGraphicsSVG();

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{SVGTest.class.getName()}, new SVGTest());
    }

    @Override
    public void settings() {
        size(200, 200);
    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {
        beginRecord(SVG, "test1.svg");
        stroke(0);
        noFill();
        line(50, 50, 100, 100);
        beginShape();
        vertex(100, 100);
        vertex(150, 100);
        vertex(150, 150);
        endShape();
        noLoop();
    }
}
