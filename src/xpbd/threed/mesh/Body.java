package xpbd.threed.mesh;

import org.apache.batik.anim.dom.TraitInformation;
import processing.core.PVector;
import xpbd.threed.structure.Constraint;
import xpbd.threed.structure.DistanceConstraint;
import xpbd.threed.structure.Particle;
import xpbd.threed.structure.ParticleSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Body {

    ArrayList<Triangle> tris= new ArrayList<>();

    HashMap<PVector, Particle> particleHashMap = new HashMap<>();


    public Body(ArrayList<Triangle> initTriangles) {
        tris.addAll(initTriangles);
    }

    public Body() {
    }

    public ParticleSystem getAsParticleSystemWithConstraints(){
        ParticleSystem p = new ParticleSystem(5, 0.05f);

        // add Particles
        for(Triangle t: tris){

            if(!particleExists(t.p1, p.getParticles())){
                Particle t1 = new Particle(t.p1, 1);
                p.addParticle(t1);
                particleHashMap.put(t.p1, t1);
            }
            if(!particleExists(t.p2, p.getParticles())) {
                Particle t2 = new Particle(t.p2, 1);
                p.addParticle(t2);
                particleHashMap.put(t.p2, t2);
            }
            if(!particleExists(t.p3, p.getParticles())){
                Particle t3 = new Particle(t.p3, 1);
                p.addParticle(t3);
                particleHashMap.put(t.p3, t3);
            }
        }

        // add DistanceConstraints
        for (Triangle t: tris) {

            if(!distanceConstraintExists(t.p1, t.p2, p.getConstraints())){
                DistanceConstraint c = new DistanceConstraint(particleHashMap.get(t.p1), particleHashMap.get(t.p2));
                p.addConstraint(c);
            }

            if(!distanceConstraintExists(t.p3, t.p2, p.getConstraints())){
                DistanceConstraint c = new DistanceConstraint(particleHashMap.get(t.p3), particleHashMap.get(t.p2));
                p.addConstraint(c);
            }

            if(!distanceConstraintExists(t.p1, t.p3, p.getConstraints())){
                DistanceConstraint c = new DistanceConstraint(particleHashMap.get(t.p1), particleHashMap.get(t.p3));
                p.addConstraint(c);
            }

        }

        // add VolumeConstraints

        System.out.println("Body with " + p.getParticles().size() + " particles and " + p.getConstraints().size() + " constraints");

        return p;
    }

    public boolean distanceConstraintExists(PVector p1, PVector p2, ArrayList<Constraint> constraints){
        for(Constraint c: constraints){
            if(!(c instanceof DistanceConstraint)) continue;
            DistanceConstraint cc = (DistanceConstraint) c;
            if((cc.p1.pos == p1 && cc.p2.pos == p2) || (cc.p1.pos == p2 && cc.p2.pos == p1)) return true;
        }
        return false;
    }

    public boolean particleExists(PVector pVector, ArrayList<Particle> particles){
        for(Particle p: particles){
            if(p.pos == pVector) return true;
        }
        return false;
    }

    static public Body testBody(){

        ArrayList<Triangle> newTris = new ArrayList<>();
        ArrayList<PVector> pos = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            PVector p1 = new PVector(i*100, -200, 0);
            PVector p2 = new PVector(i*100, -200, 100);
            PVector p3 = new PVector(i*100, -300, 100);
            PVector p4 = new PVector(i*100, -300, 0);
            pos.add(p1);
            pos.add(p2);
            pos.add(p3);
            pos.add(p4);

            if(i == 0 || i == 4){
                Triangle t1 = new Triangle(p1, p2, p3);
                Triangle t2 = new Triangle(p1, p3, p4);
                newTris.add(t1);
                newTris.add(t2);
            }


        }

        for(int i = 0; i < 4; i++){
            newTris.add(new Triangle(pos.get(4*i + 0), pos.get(4*i + 1), pos.get(4*(i+1) + 0)));
            newTris.add(new Triangle(pos.get(4*(i+1) + 0), pos.get(4*i + 1), pos.get(4*(i+1) + 1)));

            newTris.add(new Triangle(pos.get(4*i + 1), pos.get(4*i + 2), pos.get(4*(i+1) + 1)));
            newTris.add(new Triangle(pos.get(4*(i+1) + 1), pos.get(4*i + 2), pos.get(4*(i+1) + 2)));

            newTris.add(new Triangle(pos.get(4*i + 0), pos.get(4*i + 1), pos.get(4*(i+1) + 0)));
            newTris.add(new Triangle(pos.get(4*(i+1) + 2), pos.get(4*i + 3), pos.get(4*(i+1) + 3)));

            newTris.add(new Triangle(pos.get(4*i + 3), pos.get(4*i + 0), pos.get(4*(i+1) + 3)));
            newTris.add(new Triangle(pos.get(4*(i+1) + 3), pos.get(4*i + 0), pos.get(4*(i+1) + 0)));
        }


        return new Body(newTris);
    }

    @Override
    public String toString() {
        return "";
    }
}
