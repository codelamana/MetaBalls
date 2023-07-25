package trees.lsystem.l3d;

import processing.core.PApplet;
import processing.core.PMatrix;
import processing.core.PMatrix3D;
import processing.core.PVector;

import java.util.Stack;

import static processing.core.PConstants.TWO_PI;

public class Turtle3D {

    static class State {
        PVector pos;
        PVector dir;

        Branch currentBranch;

        public State(Branch cuurrentBranch, PVector pos, PVector dir) {
            this.currentBranch = cuurrentBranch;
            this.pos = pos;
            this.dir = dir;
        }

        public State() {
        }
    }

    PVector pos, dir;

    float stepLength;

    Stack<State> memory = new Stack<>();

    public Turtle3D(State initialState, float stepLength) {
        //this.memory.push(initialState);
        this.pos = initialState.pos;
        this.dir = initialState.dir;
        this.stepLength = stepLength;
    }

    public Tree3D walk(String instructions, PApplet p){
        PVector nextPos = pos.copy();
        float angle = -(22.5f/360f) * TWO_PI;
        Branch currentBranch = null;// = new Branch(nextPos, nextPos);

        Tree3D tree = new Tree3D();
        //tree.branches.add(currentBranch);

        PMatrix3D rot = new PMatrix3D();
        for (int i = 0; i < instructions.length(); i++) { ;
            if(instructions.charAt(i) == 'F'){
                nextPos.add(PVector.mult(dir, stepLength));
                Branch b = new Branch(pos, nextPos, currentBranch);
                tree.branches.add(b);
                currentBranch = b;
                pos = nextPos.copy();
            } else if(instructions.charAt(i) == '-'){
                rot.rotateZ(-angle);
                rot.mult(dir, dir);
            } else if(instructions.charAt(i) == '+'){
                rot.rotateZ(angle);
                rot.mult(dir, dir);
            }  else if(instructions.charAt(i) == '/'){
                rot.rotateX(-angle);
                rot.mult(dir, dir);
            } else if(instructions.charAt(i) == '\\'){
                rot.rotateX(angle);
                rot.mult(dir, dir);
            }  else if(instructions.charAt(i) == '&'){
                rot.rotateY(-angle);
                rot.mult(dir, dir);
            } else if(instructions.charAt(i) == '^'){
                rot.rotateY(angle);
                rot.mult(dir, dir);
            } else if(instructions.charAt(i) == '[') {
                memory.push(new State(currentBranch, pos.copy(), dir.copy()));
            } else if(instructions.charAt(i) == ']') {
                State temp = memory.pop();
                pos = temp.pos.copy();
                dir = temp.dir.copy();
                currentBranch = temp.currentBranch;
                nextPos = pos.copy();
            }
        }

        return tree;
    }
}
