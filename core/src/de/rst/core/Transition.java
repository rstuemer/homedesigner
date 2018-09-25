package de.rst.core;

public class Transition {

    String startState;
    String endState;

    public Transition(String startState, String endState) {
        this.startState = startState;
        this.endState = endState;
    }


    public boolean match(String start, String end){

        if(this.endState.equals(end) && this.startState.equals( start))
            return  true;

        return false;
    }
}
