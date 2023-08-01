package proceduralmodeling.primitives;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PConstants.CLOSE;
import static processing.core.PConstants.QUAD;

public class Quad implements Primitive{

    PVector c1, c2, c3, c4;
    PVector e1,e2, e3, e4;

    public Quad(PVector c1, PVector c2, PVector c3, PVector c4) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.e1 = PVector.sub(c2, c1);
        this.e2 = PVector.sub(c3, c2);
        this.e3 = PVector.sub(c4, c3);
        this.e4 = PVector.sub(c4, c1);
    }


    @Override
    public ArrayList<Primitive> splitX(float[] relativeValues) {
        // check parallel

        // Split according to axis;
        ArrayList<Primitive> temp = new ArrayList<>();

        PVector newLower = new PVector();
        PVector newUpper = new PVector();

        PVector oldLower = c1.copy();
        PVector oldUpper = c4.copy();

        PVector dirLower = c1.copy();
        PVector dirUpper = c3.copy().mult(-1);

        float lengthLower = dirLower.mag();
        float lengthUpper = dirUpper.mag();

        float oldf = 0;

        for(float f : relativeValues){
            PVector.mult(dirLower, f - oldf, newLower);
            PVector.mult(dirUpper, f - oldf, newUpper);
            temp.add(new Quad(oldLower.copy(), oldLower.copy().add(newLower),
                    oldUpper.copy().add(newUpper), oldUpper.copy()));

            oldLower = newLower;
            oldUpper = newUpper;
            oldf = f;
        }



        return temp;
    }

    @Override
    public ArrayList<Primitive> splitY(float[] relativeValues) {
        return new ArrayList<>();
    }

    public void draw(PApplet p){
        p.beginShape(QUAD);
        p.vertex(c1.x, c1.y, c1.z);
        p.vertex(c2.x, c2.y, c2.z);
        p.vertex(c3.x, c3.y, c3.z);
        p.vertex(c4.x, c4.y, c4.z);
        p.endShape();
    }
}
