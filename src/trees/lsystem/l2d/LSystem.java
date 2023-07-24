package trees.lsystem.l2d;

import java.util.HashMap;

public class LSystem {

    String currentState = "";
    HashMap<Character, String> rules;


    public LSystem(String currentState) {
        this.currentState = currentState;
        this.rules = new HashMap<>();
    }

    public void addRule(Character key, String value){
        this.rules.put(key, value);
    }

    public String iterate(){
        String newState = "";
        for (int i = 0; i < currentState.length(); i++) {
            if(rules.get(currentState.charAt(i))!= null){
                newState = newState + rules.get(currentState.charAt(i));
            } else {
                newState = newState + currentState.charAt(i);
            }
        }
        this.currentState = newState;
        return currentState;
    }
}
