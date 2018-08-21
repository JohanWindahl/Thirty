package com.example.johan.dice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

//Represents a set of 6 Dices
public class SixDices implements Serializable{
    public ArrayList<Dice> dices;

    public SixDices() {
        dices = new ArrayList(Arrays.asList(new Dice(), new Dice(), new Dice(), new Dice(), new Dice(), new Dice()));
    }

    public ArrayList<Dice> getDices() {
        return dices;
    }

    //Returns an array with the six dices as an array of integers.
    public ArrayList<Integer> getDicesValuesAsArray() {
        ArrayList<Integer> returnMe = new ArrayList();
        for (Dice d : dices) {
            returnMe.add(d.getValue());
        }
        return returnMe;
    }

    //Resets the color of all dices
    public void resetAllColors() {
        for (Dice i : dices) {
            i.setColor("white");
        }
    }

    //Rerolls all dices which is not grey
    public void reRollDices() {
        for (Dice i : dices) {
            if (!i.getColor().equals("grey")) {
                i.throwAgain();
                i.setColor("white");
            }
        }
    }

    //Activates when a dice Image is clicked
    //Flips color on one dice: grey->white and white->grey
    //Otherwise the color is red and all dices colors are reset except the clicked ones image, which is set to grey.
    public void flipColors(int i) {
        if (dices.get(i).getColor().equals("white")) {
            dices.get(i).setColor("grey");
        }
        else if (dices.get(i).getColor().equals("grey")) {
            dices.get(i).setColor("white");
        }
        else {
            for (Dice d : dices) {
                d.setColor("white");
            }
            dices.get(i).setColor("grey");
        }
    }

    //Returns a string to be printed in the List of gamerounds
    public String asText(int i) {
        return dices.get(i).getColor() + dices.get(i).getValue();
    }

    //Flips the color to red if the dice has a value of 3 or lower
    public void showRedDicesLow() {
        for (Dice d : dices) {
            if (d.getValue() < 4) {
                d.setColor("red");
            }
        }
    }

    //Flips the color to red on all dices that is a part of the winning combination score for the current multiplier
    //Matches the faces of the dices that we currently have. With the faces of the best possible current combination

    // showRedDicesMult(6) will find the best combination of dices with sum 6, and mark them as red
    // current dices value: [1,2,3,4,5,6] wanted Sum is 6
    // current best combination [1,5,2,4,6]
    // result: these dices will be marked red

    public void showRedDicesMult(Integer wantedSum) {
        ArrayList<Integer> bestDices =  Score.getDices(getDicesValuesAsArray(), wantedSum); //gets the best dice-combinations that fufills the score of "wantedsum"
        ArrayList<Integer> tempDices = new ArrayList<>(getDicesValuesAsArray()); //Gets a copy of the current dice faces
        ArrayList<Integer> tempIdx = new ArrayList<>(); //which indexes have we already marked red, do not mark these again.

        for (Integer bd : bestDices) { //loop through the best dice combination dices
            for (Integer j = 0;j<tempDices.size();j++) { //loop through the current dices
                if (bd.equals(tempDices.get(j))) { //if it has the same value
                    if (!tempIdx.contains(j)) { // and it has not already been marked
                        tempIdx.add(j);
                        dices.get(j).setColor("red"); //mark it red
                        break;
                    }
                }
            }
        }
    }

}

