package grids;

import com.cage.colorharmony.ColorHarmony;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

import static processing.core.PApplet.sin;
import static processing.core.PConstants.*;

public class Grid {

    int cellSize = 15;
    int m, n;
    PVector grid[][];


    ColorHarmony harmony;
    int[]    colors = new int[8];

    public Grid(int width, int height, int cellSize, int border){
        this.cellSize =  cellSize;
        this.n = (width-2*border)/cellSize + 1;
        this.m = (height-2*border)/cellSize + 1;
        grid = new PVector[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = new PVector(border + i*cellSize , border + j*cellSize);
            }
        }




    }

    public void offset(OffsetFunction f){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j].x += f.dx(i, j, n, m);
                grid[i][j].y += f.dy(i, j, n, m);
            }
        }
    }

    public void drawHorizontalLines(PApplet p){
        p.stroke(255);
        for (int i = 0; i < m; i++) {
            p.stroke(p.lerpColor(colors[0], colors[1], i/(float)m));
            p.beginShape(LINE_STRIP);
            for (int j = 0; j < n; j++) {
                p.vertex(grid[j][i].x, grid[j][i].y);
            }
            p.endShape();
        }
    }

    public void drawVerticalLines(PApplet p){
        p.stroke(255);
        for (int i = 0; i < n; i++) {
            p.stroke(p.lerpColor(colors[0], colors[1], i/(float)n));
            p.beginShape(LINE_STRIP);
            for (int j = 0; j < m; j++) {
                p.vertex(grid[i][j].x, grid[i][j].y);
            }
            p.endShape();
        }
    }

    public void setColorHarmony(ColorHarmony harmony) {
        this.harmony = harmony;
        colors = harmony.Triads("dd0af5");
    }
}
