package com.example.johan.dice;


import java.io.Serializable;

//Represents a single round in the game.
public class GameRound implements Serializable {
    private String name;
    private String orgName; //Orginal name
    private int preScore; //Calculated preliminary score
    private int postScore; //Final chosen score for this round
    private boolean beenPlayed; //Has the player played this round
    private boolean isSelected;//Is this gameround selected

    public GameRound(boolean b, String name) {
        preScore = 0;
        postScore = 0;
        this.name = name;
        this.orgName = name;
        this.beenPlayed = false;
        this.isSelected = b;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean hasBeenPlayed() {
        return beenPlayed;
    }

    public void setBeenPlayed(boolean beenPlayed) {
        this.beenPlayed = beenPlayed;
    }

    public int getPreScore() {
        return preScore;
    }

    public void setPreScore(int preScore) {
        this.preScore = preScore;
    }

    public int getPostScore() {
        return postScore;
    }

    public void setPostScore(int postScore) {
        this.postScore = postScore;
    }

    public boolean isBeenPlayed() {
        return beenPlayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}