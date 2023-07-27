package grids;

import processing.core.PApplet;
import processing.core.PVector;

public class PerlinGrid extends PApplet {

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{PerlinGrid.class.getName()}, new PerlinGrid());
    }

    int cellSize = 20;
    int m, n;
    PVector grid[][];

    @Override
    public void settings() {
        size(800, 600, P2D);
        fullScreen();
        noiseDetail(1, 0.1f);
    }

    @Override
    public void setup() {
        n = width/cellSize + 1;
        m = height/cellSize + 1;

        grid = new PVector[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = new PVector(i*cellSize + 30*noise(i*0.5f, j*0.4f, 0),
                        j*cellSize + 30*noise(i*.3f, j*0.2f, 0.1f));
                grid[i][j] = new PVector(i*cellSize + 30*sin(3*TWO_PI*(i-j)/n),
                        j*cellSize + 15*sin(5*TWO_PI*(j+i)/n + PI/7f));
            }
        }
    }


    @Override
    public void draw() {
        background(0);
        drawHorizontalLines();
        drawVerticalLines();
    }


    public void drawHorizontalLines(){
        stroke(255);
        for (int i = 0; i < m; i++) {
            beginShape(LINE_STRIP);
            for (int j = 0; j < n; j++) {
                vertex(grid[j][i].x, grid[j][i].y);
            }
            endShape();
        }
    }

    public void drawVerticalLines(){
        stroke(255);
        for (int i = 0; i < n; i++) {
            beginShape(LINE_STRIP);
            for (int j = 0; j < m; j++) {
                vertex(grid[i][j].x, grid[i][j].y);
            }
            endShape();
        }
    }

    @Override
    public void postWindowResized(int newWidth, int newHeight) {
        System.out.println("Resized");
        n = newHeight/cellSize + 1;
        m = newWidth/cellSize + 1;

        grid = new PVector[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = new PVector(i*cellSize + 30*noise(i*0.5f, j*0.4f, 0),
                        j*cellSize + 30*noise(i*.3f, j*0.2f, 0.1f));
                grid[i][j] = new PVector(i*cellSize + 30*sin(3*TWO_PI*i/n),
                        j*cellSize + 15*sin(5*TWO_PI*(j+i)/n + PI/7f));
            }
        }
    }
}
