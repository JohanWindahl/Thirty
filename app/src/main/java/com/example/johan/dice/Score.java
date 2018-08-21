package com.example.johan.dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//This is the class where all score calculations are made (included a main in here, for better understanding)
public class Score {
    private int score;
    private ArrayList<Integer> diceFaces;


    //This method will take a set of integers and a wanted sum
    //Find all possible permutations of the set
    //Then filter out those permutations that's sum does not match with the wanted sum.
    //and return those permutations total sum that match the wanted sum.
    //ex: getAllCombos([1, 2, 3, 4, 5, 6], 7) -> [[1, 2, 4], [3, 4], [2, 5], [1, 6]]

    static ArrayList<ArrayList<Integer>> getAllCombos(ArrayList<Integer> set, Integer wantedSum)
    {
        ArrayList<ArrayList<Integer>> returnMe = new ArrayList();
        ArrayList<Integer> currentRes = new ArrayList();

        for (int i = 0; i < (1<<set.size()); i++) { //Loops through all the bits in "set"
            for (int j = 0; j < set.size(); j++) { //Loops through number of elements in "set"
                if ((i & (1 << j)) > 0) { //This will generate all possible permuations of different lengths of the elements in the "set" variable
                    currentRes.add(set.get(j)); //Add to current result
                }
            }
            if (totalSum(currentRes).equals(wantedSum)) { //If the sum of all elements in each array is the same as the wanted sum, save it. This will filter out all unwanted permutations
                ArrayList<Integer> temp = new ArrayList<Integer>(currentRes);
                returnMe.add(temp); // add it to return-Array
            }
            currentRes.clear();
        }
        return returnMe;
    }


    //This method will take a list of sets of integers
    //Find all possible permutations of these sets
    //Then count all sets that are disjoint and find the one with the max number of sets.
    //ex: getBestCombo([[1, 2, 4], [3, 4], [2, 5], [1, 6]]) -> [3, 4], [2, 5], [1, 6]

    private static ArrayList<ArrayList<Integer>> getBestCombo(ArrayList<Integer> set, ArrayList<ArrayList<Integer>> subsets) {
        ArrayList res = new ArrayList();
        ArrayList<ArrayList<Integer>> newMax = new ArrayList();
        for (Integer i = 0; i < (1<<subsets.size()); i++) {
            for (Integer j = 0; j < subsets.size(); j++) {
                if ((i & (1 << j)) > 0) { //This will generate all possible permuations of different lengths of the elements in the "set" variable
                    res.add(subsets.get(j));
                }
            }
            int overlap = countDisjointSets(set,res); //Find number of non-overlapping sets
            int maxOverlap = 0;
            if (overlap>maxOverlap) { //Save the current one, if it is better than previous best
                newMax = new ArrayList<ArrayList<Integer>>(res);
            }
            res.clear();
        }
        return newMax; //Return best
    }


    //This method will take a list of sets of integers, count the number of disjoint sets in the list, sets that
    // do not share the same integers. If some of the sets has the same value, it will return 0.
    //This function is a help function for getBestCombo().
    //ex: countDisjointSets([[1, 2, 4], [3, 4]) -> 0
    //ex: countDisjointSets([[1, 2], [3, 4]) -> 2
    //ex: countDisjointSets([[1]]) -> 1
    private static Integer countDisjointSets(ArrayList<Integer> set, ArrayList<ArrayList<Integer>> res) {
        ArrayList<Integer> copyOfSet = new ArrayList<Integer>(set);
        boolean countThisArray = true;
        int returnCount = 0;

        for (ArrayList<Integer> arrList : res) {
            for (Integer arrListInteger : arrList) {

                if (copyOfSet.contains(arrListInteger)) {
                    copyOfSet.remove(arrListInteger);
                } else {
                    countThisArray = false;
                }
            }
            returnCount++;
        }
        if (countThisArray) {
            return returnCount;
        }
        else {
            return 0;
        }
    }

    //Help function that calculates totalSum of an arraylist containing Integers.
    // ex: [1,2,3,4] -> 10 (=1+2+3+4)
    private static Integer totalSum(ArrayList<Integer> res) {
        int sum = 0;
        for (int i : res) {
            sum = sum + i;
        }
        return sum;
    }

    //Help function that will take an array of arrays with Integers and extract all Integers in a single array.
    // ex: [[1,2], [3,4], [5,6]] -> [1,2,3,4,5,6]
    private static ArrayList<Integer> extractElements(ArrayList<ArrayList<Integer>> res) {
        ArrayList<Integer> result = new ArrayList<Integer>();

        for (ArrayList<Integer> al : res) {
            for (Integer i : al) {
                result.add(i);
            }
        }
        return result;
    }


    //Public method for other classes to get a more pleasant call, just a combination of the private methods
    //Returns the values of the dices used for the maximum score.
    //This is called when finding which dices to color "red"
    public static ArrayList<Integer> getDices(ArrayList allValuesAsSortedList, Integer wantedSum) {
        ArrayList<ArrayList<Integer>> subsets = getAllCombos(allValuesAsSortedList, wantedSum);
        return extractElements(getBestCombo(allValuesAsSortedList, subsets));

    }

    //Public method for other classes to get a more pleasant call, just a combination of the private methods
    //Returns the maximum number of disjoint sets in a set * the wanted sum.
    //This is called when calculating the best score for a set of dices and a chosen game round
    public static Integer getBestScore(ArrayList allValuesAsSortedList, Integer wantedSum) {
        Integer returnMe = countDisjointSets(allValuesAsSortedList, getBestCombo(allValuesAsSortedList, getAllCombos(allValuesAsSortedList, wantedSum)));
        ArrayList<ArrayList<Integer>> subsets = getAllCombos(allValuesAsSortedList, wantedSum);
        return returnMe*wantedSum;
    }

    public static Integer getBestScoreLow(ArrayList allValuesAsSortedList) {
        int result = 0;

        for (int i=0; i < allValuesAsSortedList.size(); i++) {
            int value = (int) allValuesAsSortedList.get(i);
            if (value<4) {
                result = value + result;
            }
        }

        return result;
        }


    //Here is a sample for better understanding of the different methods in this class.
    public static void main(String[] args) {

        ArrayList<Integer> diceSet = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6));

        Integer wantedSum = 7;

        System.out.println("This are our dices: " + diceSet);
        System.out.println("We want the sum: " + wantedSum);
        System.out.println("getAllCombos([1, 2, 3, 4, 5, 6],7) will return all combination of subsets that has the correct sum: " + getAllCombos(diceSet,wantedSum));
        System.out.println("countDisjointSets([[1, 2, 4], [3, 4], [2, 5], [1, 6]]) will return " + countDisjointSets(diceSet,getAllCombos(diceSet,wantedSum)) + " since there is overlapping in these sets, the element 1 appears twice");
        System.out.println("getBestCombo([[1, 2, 4], [3, 4], [2, 5], [1, 6]]) will find all different permutations of these inner sets and count the number of disjoint sets and return the best combination " + getBestCombo(diceSet,getAllCombos(diceSet,wantedSum)));
    }

}



