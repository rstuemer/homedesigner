package de.rst.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditState {


    private List<String> editstates;

    private String currentState;

    private HashMap<String, String> allowedTranstions = new HashMap<>();
    private Wall currentWall;

    public EditState() {
        this.editstates = new ArrayList<>();
        this.editstates.add("NONE");
        this.editstates.add("START_CREATION");
        this.editstates.add("ADD_SEGMENT");
        this.editstates.add("FINISH");

        this.allowedTranstions.put("NONE", "START_CREATION");
        this.allowedTranstions.put("START_CREATION", "ADD_SEGMENT,NONE");
        this.allowedTranstions.put("ADD_SEGMENT", "FINISH,ADD_SEGMENT,NONE");
        this.allowedTranstions.put("FINISH", "NONE,START_CREATION");
        this.currentState = "NONE";
    }

    public Wall getCurrentWall() {
        return currentWall;
    }

    public void setCurrentWall(Wall currentWall) {
        this.currentWall = currentWall;
    }

    public List<String> getEditstates() {
        return editstates;
    }

    public void setEditstates(List<String> editstates) {
        this.editstates = editstates;
    }

    public String getCurrentState() {
        return currentState;
    }

    public boolean changeCurrentState(String newState) {
        if (transitionAllowed(newState)) {
            this.currentState = newState;
            System.out.println("EDITSTATE _> " + newState);
            return true;
        } else {
            return false;
        }
    }

    public boolean transitionAllowed(String newState) {
        return allowedTranstions.get(this.currentState).contains(newState);
    }

    public void addEditState(String state) {
        this.editstates.add(state);
    }


}
