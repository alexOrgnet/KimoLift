package com.javarush.lifttask;

public class Event {


    public static boolean probability(int probability) {

        //probability with which event occurs
        //from 0 to 100
        //max probability 100%

        int p = (int) (Math.random() * 100);
        if (p <= probability) {
            return true;
        } else return false;
    }

    /**
     * Метод получения псевдослучайного целого числа от min до max (включая max);
     */
    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;


    }

}