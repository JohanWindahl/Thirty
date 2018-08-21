package com.example.johan.dice;

import java.io.Serializable;
import java.util.ArrayList;


//Represents a game
public class Game implements Serializable {
    private ArrayList<GameRound> gameRounds; //List of gamerounds
    private SixDices sixDices; //A set of dices
    private Integer throwNumber; //Which throw in each round 1-3
    private Integer currentRound; //Which round 1-10 are we on
    private Integer currentScore; //Current total score
    private Integer currentRoundChoice; //Which choice the player has chosen in the list of rounds.
    private boolean endClickable;
    private boolean clickableDices; //Is the player supposed to be able to save dices?
    private boolean clickableGameRounds;


    //Init a new game, only game rounds are changeable
    public Game(ArrayList<GameRound> gr) {
        this.sixDices = new SixDices(); //Create a dice set
        this.gameRounds = gr;
        this.throwNumber = 0;
        this.currentRound = 0;
        this.currentScore = 0;
        this.currentRoundChoice = -1;
        this.clickableDices = false;
        this.clickableGameRounds = false;
    }

    public void setClickableGameRounds(boolean clickableGameRounds) {
        this.clickableGameRounds = clickableGameRounds;
    }

    public boolean isEndClickable() {
        return endClickable;
    }

    public void setEndClickable(boolean endClickable) {
        this.endClickable = endClickable;
    }

    public boolean isClickableGameRounds() {
        return clickableGameRounds;
    }

    public SixDices getSixDices() {
        return sixDices;
    }

    public void setSixDices(SixDices sixDices) {
        this.sixDices = sixDices;
    }

    public boolean isClickableDices() {
        return clickableDices;
    }

    public void setGameRounds(ArrayList<GameRound> gameRounds) {
        this.gameRounds = gameRounds;
    }

    public Integer getCurrentRoundChoice() {
        return currentRoundChoice;
    }

    public void setCurrentRoundChoice(Integer currentRoundChoice) {
        this.currentRoundChoice = currentRoundChoice;
    }

    public boolean dicesAreClickable() {
        return clickableDices;
    }

    public void setClickableDices(boolean clickableDices) {
        this.clickableDices = clickableDices;
    }

    public Integer getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer cs) {
        this.currentScore = cs;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public Integer getThrowNumber() {
        return throwNumber;
    }

    //Increment the throw by 1
    public void incThrowNumber() {
        this.throwNumber++;
    }

    public void setThrowNumber(Integer throwNumber) {
        this.throwNumber = throwNumber;
    }

    public Integer getRounds() {
        return currentRound;
    }

    public ArrayList<GameRound> getGameRounds() {
        return gameRounds;
    }

    //Get a list of all the game round names
    public ArrayList<String> getGameRoundsNames() {
        ArrayList<String> res = new ArrayList<String>();

        for (int i = 0;i<this.gameRounds.size();i++) {
        res.add(this.gameRounds.get(i).getName());
    }
        return res;
}

    //Increment current round by 1.
    public void incRounds() {
        this.currentRound++;
    }


    //Returns a array of strings with updated game names, with pre and post score
    public void updateGameRoundNames() {
        ArrayList<String> res = new ArrayList<String>();

        for (int i = 0;i<this.gameRounds.size();i++) {
            String pre = "";
            String post = "";

            if (gameRounds.get(i).getPreScore()<10) { //pad with a 0 for better view, if value is 1 digit
                pre = "0" + gameRounds.get(i).getPreScore();
            }
            else {
                pre = "" + gameRounds.get(i).getPreScore();
            }

            if (gameRounds.get(i).getPostScore()<10) { //pad with a 0 for better view, if value is 1 digit
                post = "0" + gameRounds.get(i).getPostScore();
            }
            else {
                post = "" + gameRounds.get(i).getPostScore();
            }
            getGameRounds().get(i).setName(gameRounds.get(i).getOrgName() + " \t|\t " + pre + " \t|\t " + post);

        }
    }

    //Set preliminary score in specific game round
    public void setGameRoundPostScore(int idx, int score) {
        this.getGameRounds().get(idx).setPostScore(score);
    }

    //Set post-score in specific game round
    public void setGameRoundPreScore(int idx, int score) {
        this.getGameRounds().get(idx).setPreScore(score);
    }

    public void refreshPreScore() {
        for (Integer i = 0; i < 10; i++) {
            if (!getGameRounds().get(i).isBeenPlayed() & clickableGameRounds) {
                if (i.equals(0)){
                    setGameRoundPreScore(0, Score.getBestScoreLow(sixDices.getDicesValuesAsArray()));
                }
                else {
                    setGameRoundPreScore(i, Score.getBestScore(sixDices.getDicesValuesAsArray(), i + 3));
                }
            }
        }
    }

    public boolean ifAnyGameRoundIsSelected() {
        for (int i = 0;i<this.gameRounds.size();i++) {
           if (this.gameRounds.get(i).isSelected()) {
               return true;
           }
        }
        return false;
    }

    public void resetAllSelections() {
        for (GameRound gr : gameRounds) {
            gr.setSelected(false);
        }
    }

}

