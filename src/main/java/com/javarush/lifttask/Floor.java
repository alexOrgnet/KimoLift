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

    public void addInQueue(Passenger p) {
        this.waitingPassengers.add(p);
    }


    public boolean peopleWaitingGoingUp(Elevator elevator) {

        if ((this.waitingPassengers.stream().filter(p -> p.getDesiredFloor() > elevator.getCurrentFloor()).count() > 0) && elevator.isDirectionUP()) {

            return true;

        } else return false;
    }

    public boolean peopleWaitingGoingDown(Elevator elevator) {

        if ((this.waitingPassengers.stream().filter(p -> p.getDesiredFloor() < elevator.getCurrentFloor()).count() > 0) && elevator.isDirectionDown()) {

            return true;

        } else return false;
    }
}
