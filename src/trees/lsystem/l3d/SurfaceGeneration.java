package trees.lsystem.l3d;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class SurfaceGeneration extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{SurfaceGeneration.class.getName()}, new SurfaceGeneration());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
    }

    @Override
    public void setup() {
        background(0);
        ArrayList<PVector> startCircle = new ArrayList<>();
        ArrayList<PVector> endCircle = new ArrayList<>();

        PVector start = new PVector();
        PVector end = new PVector(0, -100, 0);

        PVector direction = PVector.sub(end, start);

        PeasyCam cam = new PeasyCam(this, 400);
        
        calculateCircle(direction, start,10, 6, startCircle);
        calculateCircle(direction, start, 10, 6, endCircle);

        drawCirclePoints(startCircle);
        drawCirclePoints(endCircle);


    }

    @Override
    public void draw() {
        background(0);
        ArrayList<PVector> startCircle = new ArrayList<>();
        ArrayList<PVector> endCircle = new ArrayList<>();

        PVector start = new PVector(0,0,0);
        PVector end = new PVector(0, -100, 0);

        PVector direction = PVector.sub(end, start);

        calculateCircle(direction, start,50, 6, startCircle);
        calculateCircle(direction, end, 10, 6, endCircle);

        drawCirclePoints(startCircle);
        drawCirclePoints(endCircle);

        connectSurfaces(startCircle, endCircle);
    }

    public void connectSurfaces(ArrayList<PVector> start, ArrayList<PVector> end){
        if(start.size()!=end.size()) return;
        noFill();
        stroke(255);
        beginShape(TRIANGLE_STRIP);
        for (int i = 0; i < start.size(); i++) {
            vertex(start.get(i).x, start.get(i).y, start.get(i).z);
            vertex(end.get(i).x, end.get(i).y, end.get(i).z);
        }
        endShape();
    }


    public void calculateUV(PVector dir){
        PVector u = new PVector();
        PVector v = new PVector();

        float L = dir.mag(); // length of Q
        float sigma = (dir.x > 0.0) ? L : -L;    // copysign( l, Q[0]) if you have it
        float h = dir.x + sigma;   // first component of householder vector
        float beta = -1.0f / (sigma * h);  // householder scale
        float f = beta * dir.y;
        u.set(f * h, 1.0f + f * dir.y, f * dir.z);
        float g = beta * dir.z;
        v.set(g * h, g * dir.y, 1f + g * dir.z);

        u.normalize();
        v.normalize();
    }

    public void calculateCircle(PVector dir, PVector center, float radius, int n, ArrayList<PVector> target) {
        PVector temp = new PVector();
        for (float i = 0; i <= TWO_PI; i += TWO_PI / n) {
            //PVector.add(center, PVector.add(u.copy().mult(radius * cos(i)), v.copy().mult(radius * sin(i))), temp);
            target.add(new PVector(temp.x, temp.y, temp.z));
        }
    }

        public void drawCirclePoints(ArrayList<PVector> points){
            strokeWeight(2);
            stroke(255);
            for (PVector p: points) {
                point(p.x, p.y, p.z);
            }
        }
}
