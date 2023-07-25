package trees.lsystem.l2d;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Stack;

import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class Turtle {

    static class State {
        PVector pos;
        PVector dir;

        public State(PVector pos, PVector dir) {
            this.pos = pos;
            this.dir = dir;
        }

        public State() {
        }
    }

    PVector pos, dir;

    float stepLength;

    Stack<State> memory = new Stack<>();

    public Turtle(State initialState, float stepLength) {
        //this.memory.push(initialState);
        this.pos = initialState.pos;
        this.dir = initialState.dir;
        this.stepLength = stepLength;
    }

    public void walk(String instructions, PApplet p){
        PVector nextPos = pos.copy();
        float angle = -(22.5f/360f) * TWO_PI;
        p.strokeWeight(1);
        p.stroke(0);
        for (int i = 0; i < instructions.length(); i++) { ;
            if(instructions.charAt(i) == 'F'){
                nextPos.add(PVector.mult(dir, stepLength));
                p.line(pos.x, pos.y, nextPos.x, nextPos.y);
                pos = nextPos.copy();
            } else if(instructions.charAt(i) == '-'){
                dir.rotate(angle);
            } else if(instructions.charAt(i) == '+'){
                dir.rotate(-angle);
            } else if(instructions.charAt(i) == '[') {
                memory.push(new State(pos.copy(), dir.copy()));
            } else if(instructions.charAt(i) == ']') {
                State temp = memory.pop();
                pos = temp.pos.copy();
                dir = temp.dir.copy();
                nextPos = pos.copy();
            }
        }
    }
}
