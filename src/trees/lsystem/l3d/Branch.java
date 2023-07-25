package trees.lsystem.l3d;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.*;

public class Branch{

    PVector start, end;
    PVector direction;
    PVector u, v;

    float startRadius = 5;
    float endRadius = 5;
    int resolution = 10;

    ArrayList<PVector> endCircle = new ArrayList<>();
    ArrayList<PVector> startCircle = new ArrayList<>();

    ArrayList<Branch> children = new ArrayList<>();
    Branch parent;


    public Branch(PVector start, PVector end) {
        this.start = start;
        this.end = end;
        this.direction = PVector.sub(end, start);
        calculateUV();
        calculateCircle(direction, start, startRadius, resolution, startCircle);
        calculateCircle(direction, end, endRadius, resolution, endCircle);
    }

    public Branch(PVector start, PVector end, Branch parent) {
        this(start, end);
        this.parent = parent;
    }

    public void draw(PApplet p){
        p.strokeWeight(endRadius);
        p.stroke(255);
        this.line(p, start, end);
    }

    public void calculateUV(){
        calculateUV(this.direction);
    }

    public void calculateUV(PVector dir){
        u = new PVector();
        v = new PVector();

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

    public void calculateCircle(PVector dir, PVector center, float radius, int n, ArrayList<PVector> target){
        target.clear();
        PVector temp = new PVector();
        for (float i = 0; i <= TWO_PI; i += TWO_PI / n) {
            PVector.add(center, PVector.add(u.copy().mult(radius * cos(i)), v.copy().mult(radius * sin(i))), temp);
            target.add(new PVector(temp.x, temp.y, temp.z));
        }
    }

    public void drawEndCircle(PApplet p){
        drawCirclePoints(endCircle, p);
    }

    public void connectSurfaces(PApplet p){
        if(startCircle.size()!=endCircle.size()) return;
        p.fill(150);
        //p.stroke(255);
        p.noStroke();
        p.beginShape(QUAD_STRIP);
        for (int i = 0; i < startCircle.size(); i++) {
            p.vertex(startCircle.get(i).x, startCircle.get(i).y, startCircle.get(i).z);
            p.vertex(endCircle.get(i).x, endCircle.get(i).y, endCircle.get(i).z);
        }
        int i = startCircle.size()-1;
        p.vertex(startCircle.get(0).x, startCircle.get(0).y, startCircle.get(0).z);
        p.vertex(endCircle.get(0).x, endCircle.get(0).y, endCircle.get(0).z);
        p.endShape();
    }

    public void drawCirclePoints(ArrayList<PVector> points, PApplet ps){
        ps.strokeWeight(2);
        ps.stroke(255);
        for (PVector p: points) {
            ps.point(p.x, p.y, p.z);
        }
    }

    public void line(PApplet p, PVector start, PVector end) {
        p.line(start.x, start.y, start.z, end.x, end.y, end.z);
    }

    public void setStartRadius(float startRadius) {
        this.startRadius = startRadius;
        calculateCircle(direction, start, startRadius, resolution, startCircle);
    }

    public void setEndRadius(float endRadius) {
        this.endRadius = endRadius;
        calculateCircle(direction, end, endRadius, resolution, endCircle);
    }
}
