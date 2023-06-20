import peasy.PeasyCam;
import processing.core.PApplet;

public class Marching3D extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{Marching3D.class.getName()}, new Marching3D());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
    }

    PeasyCam cam;
    MarchingCube3D marchingCube3D;

    @Override
    public void setup() {


        cam = new PeasyCam(this, 100, 100, 100, 100);
        cam.setMinimumDistance(0.1);
        cam.setMaximumDistance(1000);


        marchingCube3D = new MarchingCube3D(20,20,20, 10);

    }

    @Override
    public void draw() {
        background(0);
        marchingCube3D.testCube();
        marchingCube3D.calculateBooleanGrid(10);
        marchingCube3D.drawBooleanGrid(this);
        //marchingCube3D.drawGrid(this);

        marchingCube3D.drawPlanes(this);
    }
}
