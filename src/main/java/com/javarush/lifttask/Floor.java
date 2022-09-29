package com.javarush.lifttask;

import java.util.ArrayList;
import java.util.List;

public class Floor {

    int floorNumber;

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<Passenger> waitingPassengers = new ArrayList<>();

    public Floor(int number) {

        this.floorNumber = number;
    }

    public void addPassenger(Passenger passenger) {



    }
}
