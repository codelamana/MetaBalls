package xpbd.threed.structure;

public interface Constraint {

    public float solve(float dt);

    public boolean allFixed();

}
