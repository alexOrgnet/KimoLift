package com.javarush.lifttask;
public class Building {


    private int numberOfFloors = 0;

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public Building() {

        this.numberOfFloors = Event.rnd(2, 10);

    }

}
