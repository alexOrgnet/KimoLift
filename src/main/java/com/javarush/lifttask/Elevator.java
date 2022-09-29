package com.javarush.lifttask;

import java.util.ArrayList;
import java.util.List;

public class Elevator {

    public List<Passenger> passengers = new ArrayList<>();

    public int maxCapacity;

    public int currentFloor;

    public boolean directionUP;
    public boolean directionDown;

    public boolean needForNextMove;

    public boolean isDirectionUP() {
        return this.directionUP;
    }

    public boolean isDirectionDown() {
        return this.directionDown;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setDirectionUP(boolean directionUP) {
        this.directionUP = directionUP;
    }

    public void setDirectionDown(boolean directionDown) {
        this.directionDown = directionDown;
    }
    public boolean isNeedForNextMove() {
        return needForNextMove;
    }

    public void setNeedForNextMove(boolean needForNextMove) {
        this.needForNextMove = needForNextMove;
    }

    public Elevator() {
        this.currentFloor = 1;
        this.maxCapacity = 5;
//        this.passengers =  new Passenger[5];
        this.directionUP = true;
        this.directionDown = false;
        this.needForNextMove = true;
    }
}





