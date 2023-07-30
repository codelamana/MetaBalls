package trees.colony;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.QUAD_STRIP;
import static processing.core.PConstants.TWO_PI;

public class Branch {

    PVector start;
    PVector end;
    PVector direction;
    Branch parent;
    ArrayList<Branch> children = new ArrayList<>();
    ArrayList<Leaf> attractors = new ArrayList<>();

    float length = 2;


    private boolean deactivated = false;

    PVector u, v;

    float startRadius = 5;
    float endRadius = 5;
    int resolution = 8;

    ArrayList<PVector> endCircle = new ArrayList<>();
    ArrayList<PVector> startCircle = new ArrayList<>();

    boolean onTerminalBranch = false;

    public Branch(PVector start, PVector end, PVector direction, Branch parent) {
        this.start = start;
        this.end = end;
        this.direction = direction.normalize();
        this.parent = parent;
        calculateUV();
        calculateCircle(direction, start, startRadius, resolution, startCircle);
        calculateCircle(direction, end, endRadius, resolution, endCircle);
    }


    public Branch calculateNextBranch() {
        // determine normalized Vectors
        ArrayList<PVector> normalizedDirs = new ArrayList<>();
        PVector temp = new PVector();
        PVector n = new PVector();
        for (Leaf l: attractors){
            PVector.sub(l.pos, this.end, temp);
            temp.normalize();
            n.add(temp);
        }
        n.normalize();//.mult((float) Math.random());
        n.add(new PVector(0, 0.3f, 0));
        n.normalize();

        // add new branch if there are attraction points
        if(attractors.size() != 0 && this.children.size() <= 2) {
            Branch newBranch = new Branch(this.end, this.end.copy().add(n.mult(length)), n, this);
            this.children.add(newBranch);
            return newBranch;
        }
        return null;
    }


    public boolean shouldGrow(){
        return attractors.size() > 0;
    }

    public void drawCircle(PApplet p){
        p.pushMatrix();
        p.translate(start.x, start.y, start.z);
        p.rotateX(PApplet.acos(1/direction.x));
        p.rotateY(PApplet.acos(1/direction.y));
        p.beginShape(PConstants.QUAD);
        p.vertex(-10, 0, 10);
        p.vertex(-10, 0, -10);
        p.vertex(10, 0, -10);
        p.vertex(10, 0, 10);
        p.endShape();
        p.popMatrix();

    }

    public void addLeaf(Leaf leaf) {
        this.attractors.add(leaf);
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
        if(startRadius < 2) p.fill(0,255,0);
        else p.fill(139,69,19);
        //if(onTerminalBranch) p.fill(0,255,0);
        //else p.fill(139,69,19);              //p.stroke(255);
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

    public void connectInterSurfaces(PApplet p){
        if(parent == null) return;
        if(startCircle.size()!=parent.endCircle.size() ) return;
        if(startRadius < 2) p.fill(0,255,0);
        else p.fill(139,69,19);
        //if(onTerminalBranch) p.fill(0,255,0);
        //else p.fill(139,69,19);              //p.stroke(255);
        p.noStroke();
        p.beginShape(QUAD_STRIP);
        for (int i = 0; i < startCircle.size(); i++) {
            p.vertex(startCircle.get(i).x, startCircle.get(i).y, startCircle.get(i).z);
            p.vertex(parent.endCircle.get(i).x, parent.endCircle.get(i).y, parent.endCircle.get(i).z);
        }
        int i = startCircle.size()-1;
        p.vertex(startCircle.get(0).x, startCircle.get(0).y, startCircle.get(0).z);
        p.vertex(parent.endCircle.get(0).x, parent.endCircle.get(0).y, parent.endCircle.get(0).z);
        p.endShape();
    }

    public void connectToPreviousSurfaces(PApplet p){
        if(parent == null) return;
        if(endCircle.size()!=parent.endCircle.size() ) return;
        if(startRadius < 2) p.fill(0,255,0);
        else p.fill(139,69,19);
        //if(onTerminalBranch) p.fill(0,255,0);
        //else p.fill(139,69,19);              //p.stroke(255);
        p.noStroke();
        p.beginShape(QUAD_STRIP);
        for (int i = 0; i < endCircle.size(); i++) {
            p.vertex(endCircle.get(i).x, endCircle.get(i).y, endCircle.get(i).z);
            p.vertex(parent.endCircle.get(i).x, parent.endCircle.get(i).y, parent.endCircle.get(i).z);
        }
        int i = endCircle.size()-1;
        p.vertex(endCircle.get(0).x, endCircle.get(0).y, endCircle.get(0).z);
        p.vertex(parent.endCircle.get(0).x, parent.endCircle.get(0).y, parent.endCircle.get(0).z);
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

    public void propagateRadius(float newRadius){
        this.setStartRadius(newRadius);
        this.setEndRadius(newRadius*0.99f);
        for(Branch b: this.children){
            b.propagateRadius(endRadius);
        }
    }

    public boolean backpropagateColor(){
        boolean wasOnTerminalBranch = false;
        for(Branch b: children){
            wasOnTerminalBranch = b.backpropagateColor();
        }

        if(children.size() >=2){
            wasOnTerminalBranch = false;
        }

        return wasOnTerminalBranch;
    }

}
