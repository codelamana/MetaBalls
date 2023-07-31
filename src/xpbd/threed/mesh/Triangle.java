package xpbd.threed.mesh;

import processing.core.PVector;

public class Triangle {

    PVector p1, p2, p3;
    PVector[] e1, e2, e3;
    PVector surfaceNormal;

    public Triangle(PVector p1, PVector p2, PVector p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        surfaceNormal = new PVector();
        PVector.cross(PVector.sub(p2, p1), PVector.sub(p2, p3), surfaceNormal);
        System.out.println(surfaceNormal);
    }
}
