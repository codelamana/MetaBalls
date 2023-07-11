package metaballs;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;


public class MarchingCube3D {

    int m, n, p; // dim
    int size;

    float[][][] floatGrid;
    boolean[][][] booleanGrid;

    float threshold = 0;


    public MarchingCube3D(int m, int n, int p, int size) {
        this.m = m;
        this.n = n;
        this.p = p;
        this.size = size;

        floatGrid = new float[m][n][p];
        booleanGrid = new boolean[m][n][p];

    }

    public void drawGrid(PApplet parent){
        for (int i = 0; i < m-1; i++) {
            for (int j = 0; j < n-1; j++) {
                for (int k = 0; k < p-1; k++) {
                    parent.strokeWeight(4);
                    parent.stroke(255);
                    parent.point(i*size, j*size, k*size);
                }
            }
        }
    }

    public void testCube(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    floatGrid[i][j][k] = 20;
                    floatGrid[i+10][j][k] = 20;
                    floatGrid[i][j+10][k] = 20;
                }
            }
        }
    }

    public void testSpheres(){
        PVector center1 = new PVector(20,20,20);
        PVector center2 = new PVector(30,30,30);
        float radius = 15;

        PVector current = new PVector();
        PVector sub = new PVector();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < p; k++) {
                    current.set(i,j,k);
                    if(PVector.sub(current, center1).mag() < radius) floatGrid[i][j][k] = 10;
                    if(PVector.sub(current, center2).mag() < radius) floatGrid[i][j][k] = 10;
                }
            }
        }
    }

    public void fillFloatGrid() {
        ArrayList<int[]> centers = new ArrayList<>();
        centers.add(new int[]{20*size, 20*size, 30*size});
        centers.add(new int[]{30*size, 20*size, 30*size});
        centers.add(new int[]{20*size, 30*size, 30*size});
        centers.add(new int[]{30*size, 30*size, 30*size});
        //centers.add(new int[]{15, 15, 15});

        float rad = 7;

        float sum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < p; k++) {
                    sum = 0.0f;
                    for (int[] center : centers) {
                        System.out.println(center[0]);
                        sum += 14 * rad / dist(center, i * size, j*size, k*size);
                    }
                    floatGrid[i][j][k] = sum;
                    System.out.println(sum);
                }
            }
        }
    }

    private float dist(int[] center, int i, int i1, int i2) {
        return PApplet.dist(center[0], center[1], center[2], i, i1, i2);
    }

    public void calculateBooleanGrid(float thresh){
        this.threshold = thresh;
        for (int i = 0; i < m-1; i++) {
            for (int j = 0; j < n-1; j++) {
                for (int k = 0; k < p-1; k++) {
                    booleanGrid[i][j][k] = floatGrid[i][j][k] > thresh ? true : false;
                    //if(booleanGrid[i][j][k]) System.out.println("Added boolean point");
                }
            }
        }
    }

    public void drawBooleanGrid(PApplet parent){
        for (int i = 0; i < m-1; i++) {
            for (int j = 0; j < n-1; j++) {
                for (int k = 0; k < p-1; k++) {
                    parent.strokeWeight(4);
                    parent.stroke(255);
                    if(booleanGrid[i][j][k]) parent.point(i*size, j*size, k*size);
                }
            }
        }
    }

    public void drawPlanes(PApplet parent){
        int cubeindex;
        float[][] vert = new float[12][3];
        for (int i = 0; i < m-1; i++) {
            for (int j = 0; j < n-1; j++) {
                for (int k = 0; k < p-1; k++) {
                    cubeindex = 0;
                    if (booleanGrid[i][j+1][k+1]) cubeindex |= 1;
                    if (booleanGrid[i+1][j+1][k+1]) cubeindex |= 2;
                    if (booleanGrid[i+1][j+1][k]) cubeindex |= 4;
                    if (booleanGrid[i][j+1][k]) cubeindex |= 8;
                    if (booleanGrid[i][j][k+1]) cubeindex |= 16;
                    if (booleanGrid[i+1][j][k+1]) cubeindex |= 32;
                    if (booleanGrid[i+1][j][k]) cubeindex |= 64;
                    if (booleanGrid[i][j][k]) cubeindex |= 128;

                    if(cubeindex == 0) continue;

                    if ((LUT.edgeTable[cubeindex] & 1) == 1) vert[0] = new float[]{(i+.5f)*size, (j+1)*size, (k+1)*size};
                    if ((LUT.edgeTable[cubeindex] & 2) == 2) vert[1] = new float[]{(i+1)*size, (j+1)*size, (k+.5f)*size};
                    if ((LUT.edgeTable[cubeindex] & 4) == 4)vert[2] =  new float[]{(i+.5f)*size, (j+1)*size, (k)*size};
                    if ((LUT.edgeTable[cubeindex] & 8) == 8)vert[3] =  new float[]{(i)*size, (j+1)*size, (k+.5f)*size};
                    if ((LUT.edgeTable[cubeindex] & 16) == 16)vert[4] =   new float[]{(i+.5f)*size, (j)*size, (k+1)*size};
                    if ((LUT.edgeTable[cubeindex] & 32) == 32)vert[5] =   new float[]{(i+1)*size, (j)*size, (k+.5f)*size};
                    if ((LUT.edgeTable[cubeindex] & 64) == 64)vert[6] =   new float[]{(i+.5f)*size, (j)*size, (k)*size};
                    if ((LUT.edgeTable[cubeindex] & 128) == 128)vert[7] = new float[]{(i)*size, (j)*size, (k+.5f)*size};
                    if ((LUT.edgeTable[cubeindex] & 256) == 256)vert[8] =    new float[]{(i)*size, (j+0.5f)*size, (k+1)*size};
                    if ((LUT.edgeTable[cubeindex] & 512) == 512)vert[9] =    new float[]{(i+1)*size, (j+0.5f)*size, (k+1)*size};
                    if ((LUT.edgeTable[cubeindex] & 1024) == 1024)vert[10] = new float[]{(i+1)*size, (j+0.5f)*size, (k)*size};
                    if ((LUT.edgeTable[cubeindex] & 2048) == 2048)vert[11] = new float[]{(i)*size, (j+0.5f)*size, (k)*size};

                    if ((LUT.edgeTable[cubeindex] & 1) == 1) vert[0] = isoLerp(threshold, floatGrid[i][j+1][k+1], floatGrid[i+1][j+1][k+1],
                                                                                i, j+1, k+1, i+1, j+1, k+1);
                    if ((LUT.edgeTable[cubeindex] & 2) == 2) vert[1] = isoLerp(threshold, floatGrid[i+1][j+1][k], floatGrid[i+1][j+1][k+1],
                            i, j+1, k+1, i+1, j+1, k+1);
                    if ((LUT.edgeTable[cubeindex] & 4) == 4)vert[2] =  new float[]{(i+.5f)*size, (j+1)*size, (k)*size};
                    if ((LUT.edgeTable[cubeindex] & 8) == 8)vert[3] =  new float[]{(i)*size, (j+1)*size, (k+.5f)*size};
                    if ((LUT.edgeTable[cubeindex] & 16) == 16)vert[4] =   new float[]{(i+.5f)*size, (j)*size, (k+1)*size};
                    if ((LUT.edgeTable[cubeindex] & 32) == 32)vert[5] =   new float[]{(i+1)*size, (j)*size, (k+.5f)*size};
                    if ((LUT.edgeTable[cubeindex] & 64) == 64)vert[6] =   new float[]{(i+.5f)*size, (j)*size, (k)*size};
                    if ((LUT.edgeTable[cubeindex] & 128) == 128)vert[7] = new float[]{(i)*size, (j)*size, (k+.5f)*size};
                    if ((LUT.edgeTable[cubeindex] & 256) == 256)vert[8] =    new float[]{(i)*size, (j+0.5f)*size, (k+1)*size};
                    if ((LUT.edgeTable[cubeindex] & 512) == 512)vert[9] =    new float[]{(i+1)*size, (j+0.5f)*size, (k+1)*size};
                    if ((LUT.edgeTable[cubeindex] & 1024) == 1024)vert[10] = new float[]{(i+1)*size, (j+0.5f)*size, (k)*size};
                    if ((LUT.edgeTable[cubeindex] & 2048) == 2048)vert[11] = new float[]{(i)*size, (j+0.5f)*size, (k)*size};


                    int[] tris = LUT.triTable[cubeindex];

                    parent.strokeWeight(1);
                    parent.stroke(127);

                    for(int u = 0; u < tris.length; u+=3){
                        if(tris[u] ==-1) break;
                        parent.beginShape();
                        parent.vertex(vert[tris[u]]  [0], vert[tris[u]]  [1], vert[tris[u]]  [2]);
                        parent.vertex(vert[tris[u+1]][0], vert[tris[u+1]][1], vert[tris[u+1]][2]);
                        parent.vertex(vert[tris[u+2]][0], vert[tris[u+2]][1], vert[tris[u+2]][2]);
                        parent.endShape();

                    }

                }
            }
        }
    }

    float[] isoLerp(float isoval, float p1, float p2, int i1, int j1, int k1, int i2, int j2, int k2){
        float t = (p1 - isoval)/(p2-p1);
        return new float[]{size*(i1 + t*(i2-i1)), size*(j1 + t*(j2-j1)), size*(k1 + t*(k2-k1))};
    }


}
