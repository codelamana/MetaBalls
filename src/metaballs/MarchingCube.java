package metaballs;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.Random;

public class MarchingCube {

    int m, n; // resolution
    int size;
    float[][] grid;
    boolean[][] boolGrid;

    float threshold;

    Random rand = new Random();

    public MarchingCube(int width, int heigth, int size, float threshold) {
        this.m = width/size + 1;
        this.n = heigth/size + 1;

        this.size = size;

        grid =  new float[m][n];
        boolGrid = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //grid[i][j] = rand.nextInt(0, 2);
            }
        }

        this.threshold = threshold;

    }

    public PVector getGridPos(int i, int j){
        return new PVector(i*size, j*size);
    }

    public void setGridPos(int i, int j, float val){
        grid[i][j] = val;
    }

    public void calculateBoolGrid(){
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boolGrid[i][j] = grid[i][j] < this.threshold ? false : true;
            }
        }
    }

    public void drawGrid(PApplet p){

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                p.stroke(grid[i][j]);
                p.strokeWeight(5);
                p.point(i * size, j * size);
            }
        }
    }

    public void drawBoolGrid(PApplet p){

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                p.stroke(boolGrid[i][j] ? 255 : 0);
                p.strokeWeight(5);
                p.point(i * size, j * size);
            }
        }
    }

    public void drawSquareLinesBinary(PApplet p){
        boolean a,b,c,d;

        PVector one = new PVector();
        PVector two = new PVector();
        PVector three = new PVector();
        PVector four = new PVector();
        PVector _d = new PVector();

        for (int i = 0; i < m-1; i++) {
            for (int j = 0; j < n-1; j++) {
                a = boolGrid[i][j+1];
                b = boolGrid[i+1][j+1];
                c = boolGrid[i+1][j];
                d = boolGrid[i][j];

                if(a && b && c && d) continue;
                if(!(a||b||c||d)) continue;

                /*one.set(size*(i+0.5f), (j+1)*size);
                two.set(size*(i+1f), (j+.5f)*size);
                three.set(size*(i+0.5f), (j)*size);
                four.set(size*(i), (j+0.5f)*size);*/

                one = interpolate(new PVector((i)*size,(j+1)*size), new PVector((i+1)*size,
                        (j+1)*size), grid[i][j+1], grid[i+1][j+1]);
                two = interpolate(new PVector((i+1)*size,(j+1)*size), new PVector((i+1)*size,
                        (j)*size), grid[i+1][j+1], grid[i+1][j]);
                three = interpolate(new PVector((i+1)*size,(j)*size), new PVector((i)*size,
                                (j)*size), grid[i+1][j], grid[i][j]);
                four = interpolate(new PVector((i)*size,(j)*size), new PVector((i)*size,
                        (j+1)*size), grid[i][j], grid[i][j+1]);

                switch (caseLut(a,b,c,d)){
                    case 1: line(three, four, p);
                            break;
                    case 2: line(two, three, p);
                        break;
                    case 3: line(four, two, p);
                        break;
                    case 4: line(one, two, p);
                        break;
                    case 5: line(three, four, p);
                            line(one, two, p);
                        break;
                    case 6: line(three, one, p);
                        break;
                    case 7: line(one, four, p);
                        break;
                    case 8: line(one, four, p);
                        break;
                    case 9: line(three, one, p);
                        break;
                    case 10: line(four, one, p);
                             line(three, two, p);
                        break;
                    case 11: line(one, two, p);
                        break;
                    case 12: line(two, four, p);
                        break;
                    case 13: line(two, three, p);
                        break;
                    case 14: line(three, four, p);
                        break;

                }
            }
        }

    }

    public void line(PVector a, PVector b, PApplet p){
        p.strokeWeight(10);
        p.beginShape();
        //p.line(a.x, a.y, b.x, b.y);
        p.vertex(a.x, a.y);
        p.vertex(b.x, b.y);
        p.endShape();
    }

    public int caseLut(boolean a, boolean b, boolean c, boolean d){
        return (a ? 8 : 0) + (b ? 4 : 0) + (c ? 2 : 0) + (d ? 1 : 0);
    }

    public PVector interpolate(PVector a, PVector b, float start, float end){

        float t = (threshold - end)/(start - end);
        //System.out.println(t);
        if(t > 1 || t < 0){
            t=0.5f;
            return new PVector(t * a.x + (1-t) * b.x, t * a.y + (1-t) * b.y);
        }

        return new PVector(t * a.x + (1-t) * b.x, t * a.y + (1-t) * b.y);
    }
}
