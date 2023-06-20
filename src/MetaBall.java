import processing.core.PApplet;
import processing.core.PVector;

public class MetaBall {

    PVector pos;
    PVector velocity;
    float r;

    PApplet parent;

    public MetaBall(PVector pos, float r, PApplet parent) {
        this.pos = pos;
        this.r = r;
        this.velocity = new PVector();
        PVector.random2D(velocity);
        velocity.mult(3);

        this.parent = parent;
    }


    public void setPos(int mouseX, int mouseY) {
        this.pos.set(mouseX, mouseY);
    }

    public void update(){
        this.pos.add(this.velocity);

        if(pos.x > parent.width || pos.x < 0) velocity.x *= -1;
        if(pos.y > parent.height || pos.y < 0) velocity.y *= -1;
    }
}
