package metaballs;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Main.class.getName()}, new Main());
    }

    ArrayList<MetaBall> balls = new ArrayList<>();

    ArrayList<MarchingCube> marches = new ArrayList<>();

    @Override
    public void settings() {
        size(800, 600);
        fullScreen();
    }

    @Override
    public void setup() {
        windowTitle("MetaBalls");


        for (int i = 0; i < 10; i++) {
            balls.add(new MetaBall(new PVector(random(200,width-200), random(200, height-200)), random(20, 60), this));
        }


        for (float i = 8; i < 16; i+=0.7) {
            this.marches.add(new MarchingCube(width, height, 10, 0.5f * i*i));
        }


    }

    @Override
    public void draw() {
        background(255);

        beginRecord(SVG, "newtest.svg");

        float sum = 0;
        for (int i = 0; i < marches.get(0).m; i++) {
            for (int j = 0; j < marches.get(0).n; j++) {
                sum = 0.0f;
                for(MetaBall b: this.balls) {
                    sum += 50 *  b.r / dist(b.pos, i * marches.get(0).size, j * marches.get(0).size);
                }
                for(MarchingCube m : this.marches) {
                    m.setGridPos(i, j, sum);
                }
            }
        }

        colorMode(HSB);
        for(MarchingCube m : this.marches) {
            m.calculateBoolGrid();
            stroke(m.threshold*2, 255, 255 );
            m.drawSquareLinesBinary(this);
        }

        for (MetaBall ball: balls){
            ball.update();
        }

        endRecord();
    }

    public float dist(PVector pos, float x, float y){
        return dist(pos.x, pos.y, x, y);
    }


}

