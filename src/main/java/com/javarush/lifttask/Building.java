package com.javarush.lifttask;

import java.util.ArrayList;
import java.util.List;

public class Building {


    public int numberOfFloors = 0;

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public Building() {

        this.numberOfFloors = Event.rnd(2, 10);

    }

}
