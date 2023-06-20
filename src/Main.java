import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Main.class.getName()}, new Main());
    }

    ArrayList<MetaBall> balls = new ArrayList<>();

    MarchingCube march;

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        windowTitle("MetaBalls");

        for (int i = 0; i < 10; i++) {
            balls.add(new MetaBall(new PVector(random(width), random(height)), random(20, 60), this));
        }

        march = new MarchingCube(width, height, 10);
    }

    @Override
    public void draw() {
        background(127);

        //this.balls.get(0).setPos(mouseX, mouseY);

        /*loadPixels();
        float sum = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sum = 0.0f;
                for(MetaBall b: this.balls) {
                     sum += 100 *  b.r / dist(b.pos, i, j);
                }
                pixels[j * width + i] = color(sum);
                if(sum <= 127.0 && sum > 126.0) pixels[j * width + i] = color(255, 0, 0);
                march.setGridPos(i/march.size, j/march.size, sum);

            }
        }
        updatePixels();*/


        float sum = 0;
        for (int i = 0; i < march.m; i++) {
            for (int j = 0; j < march.n; j++) {
                sum = 0.0f;
                for(MetaBall b: this.balls) {
                    sum += 50 *  b.r / dist(b.pos, i * march.size, j * march.size);
                }
                march.setGridPos(i, j, sum);
            }
        }

        march.calculateBoolGrid();
        //march.drawBoolGrid(this);
        march.drawSquareLinesBinary(this);


        /*ellipseMode(RADIUS);
        for(MetaBall b: this.balls) {
            noFill();
            stroke(0);
            ellipse(b.pos.x, b.pos.y, b.r, b.r);
        }*/

        for (MetaBall ball: balls){
            ball.update();
        }


    }

    public float dist(PVector pos, float x, float y){
        return dist(pos.x, pos.y, x, y);
    }
}

