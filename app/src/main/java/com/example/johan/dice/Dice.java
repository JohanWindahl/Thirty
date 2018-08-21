package com.example.johan.dice;

import java.io.Serializable;

//Represents a single Dice, with a value and a color
public class Dice implements Serializable{
    public int value;
    public String color;

    public Dice(int value, String color) {
        this.value = value;
        this.color = color; //white, grey or red
    }

    //Init dice with white color and random value
    public Dice() {
        this.value = 0;
        this.color = "white";
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //generate a new random value for Dice
    public void throwAgain() {
        this.value = (int)(Math.random()*6) + 1;
    }
}
