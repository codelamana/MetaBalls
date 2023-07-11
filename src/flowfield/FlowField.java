package flowfield;

import processing.core.PApplet;
import processing.core.PVector;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static processing.core.PApplet.*;

public class FlowField {

    PApplet parent;
    int n, m;
    int res;

    float zoff = 0.0f;

    int hu = 0;

    PVector[][] vectorField;

    ArrayList<Particle> particles = new ArrayList<>();

    public FlowField(PApplet parent, int res) {
        this.parent = parent;
        this.res = res;

        n = parent.width / res;
        m = parent.height / res;

        vectorField = new PVector[n][m];

        parent.noiseDetail(3);



        PVector temp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp = PVector.fromAngle(parent.noise(i*0.1f, j*0.1f, zoff) * TWO_PI);
                vectorField[i][j] = temp.setMag(1f);
            }
        }

    }

    public void newVectorfield(){
        zoff+=0.5;
        hu+=10;
        PVector temp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp = PVector.fromAngle(parent.noise(i*0.1f, j*0.1f, zoff) * TWO_PI);
                vectorField[i][j] = temp.setMag(1f);
            }
        }
    }

    public void draw(){
        PApplet p = parent;
        PVector temp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp = vectorField[i][j];
                p.strokeWeight(1);
                p.line(i*res, j*res,
                        i*res + 10*cos(temp.heading()),
                        j*res + 10*sin(temp.heading()));
            }
        }
    }

    public void addParticle(Particle n){
        particles.add(n);
    }

    public void updateParticles() {
        for(Particle p: particles){
            p.edge(parent);
            int gridx = floor(p.pos.x / res);
            int gridy = floor(p.pos.y / res);
            p.apply(this.vectorField[gridx][gridy]);
            p.update();
        }
    }

    public void drawParticles() {
        //System.out.println(particles);
        for(Particle p : particles){
            parent.strokeWeight(4);
            parent.stroke(hu%255, 255, 255, 30);
            parent.point(p.pos.x, p.pos.y);
        }
    }

}
