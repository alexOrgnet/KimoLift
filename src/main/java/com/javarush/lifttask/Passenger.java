package com.javarush.lifttask;
public class Passenger {

    private int desiredFloor;

    private boolean onTheMove;

    public boolean isOnTheMove() {
        return onTheMove;
    }

    public Passenger(int desiredFloor, int currentFloor) {
        this.desiredFloor = desiredFloor;
        if (desiredFloor != currentFloor) {
            this.onTheMove = true;
        } else this.onTheMove = false;
    }

    public int getDesiredFloor() {
        return this.desiredFloor;

    }

    public void finishTrip() {
        this.onTheMove = false;
    }
}
