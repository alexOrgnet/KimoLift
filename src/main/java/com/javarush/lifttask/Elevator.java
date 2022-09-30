package com.javarush.lifttask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Elevator {

    public List<Passenger> passengers = new ArrayList<>();

    public int maxCapacity;

    private int currentFloor;

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

    public void moveUp() {

        this.directionUP = true;
        this.directionDown = false;

        this.currentFloor = this.currentFloor + 1;
    }

    public void moveDown() {

        this.directionUP = false;
        this.directionDown = true;

        this.currentFloor = this.currentFloor - 1;
    }

    public Elevator() {
        this.currentFloor = 1;
        this.maxCapacity = 5;
//        this.passengers =  new Passenger[5];
        this.directionUP = true;
        this.directionDown = false;
        this.needForNextMove = true;
    }


    public boolean checkIfLiftNeedsGoingDown(List<Floor> floors) {

        for (Floor floor : floors) {
            if (floor.getFloorNumber() >= this.currentFloor) {
                //continue;
            } else if (floor.waitingPassengers.stream().filter(p -> (p.getDesiredFloor() < this.currentFloor) && (p.isOnTheMove())).count() > 0) {
                return true;
            } else return false;
        }

        return false;
    }


    public boolean checkIfLiftNeedsGoingUp(List<Floor> floors) {

        for (Floor floor : floors) {

            if (floor.getFloorNumber() <= this.currentFloor) {
                //continue;
            } else if (floor.waitingPassengers.stream().filter(p -> (p.getDesiredFloor() > this.currentFloor) && (p.isOnTheMove())).count() > 0) {
                return true;
            } else return false;
        }

        return false;
    }

    public boolean passengersToLeaveOnThisFloor() {

        //в лифте есть пассажиры которые выйдут на этом этаже
        if (this.passengers.stream().filter(p -> p.getDesiredFloor() == this.currentFloor).count() > 0) {
            return true;
        } else return false;
    }

    public List<Passenger> unBoardingPassengers() {

        //Passenger[] passendersLeaving = new Passenger[this.passengers.size()];
        List<Passenger> passendersLeaving = new ArrayList<>();

        Iterator<Passenger> passengersInLift = this.passengers.iterator();//создаем итератор
        while (passengersInLift.hasNext()) {//до тех пор, пока в лифте есть пассажиры

            Passenger liftPassenger = passengersInLift.next();//получаем следующий элемент
            if (liftPassenger.getDesiredFloor() == this.getCurrentFloor()) {

                liftPassenger.finishTrip();
                passengersInLift.remove();

                passendersLeaving.add(liftPassenger);

                System.out.println("Из лифта на этаже № " + this.getCurrentFloor() + " вышел человек " + liftPassenger.hashCode());
            }
        }

        return passendersLeaving;
    }

    public void boardingPassenger(Passenger passengers) {

        this.passengers.add(passengers);

    }


    public boolean passengersGoingUp(){

        if (this.passengers.stream().filter(p -> p.getDesiredFloor() > this.getCurrentFloor()).count() > 0) {

            return true;

        } else return false;
    }
    public boolean passengersGoingDown(){

        if (this.passengers.stream().filter(p -> p.getDesiredFloor() < this.getCurrentFloor()).count() > 0) {

            return true;

        } else return false;
    }
}





