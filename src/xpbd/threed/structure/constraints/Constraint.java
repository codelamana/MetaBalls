package xpbd.threed.structure.constraints;

public interface Constraint {

    public float solve(float dt);

    public boolean allFixed();

}
