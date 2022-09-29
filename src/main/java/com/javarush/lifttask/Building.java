package com.javarush.lifttask;

public class Building {


    public int numberOfFloors = 0;

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public Building() {

        this.numberOfFloors = Event.rnd(2, 10);

    }

}
