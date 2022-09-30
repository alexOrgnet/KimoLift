package com.javarush.lifttask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Main {
    static List<Floor> floors = new ArrayList<>();


    public static void main(String[] args) {

        System.out.println("Starting");

        Building building = new Building();
        Elevator elevator = new Elevator();

        System.out.println("инициализация здания в " + building.getNumberOfFloors() + " этажей");

        int numberOfFloors = building.getNumberOfFloors();

        for (int currenFloorNumber = 1; currenFloorNumber <= numberOfFloors; currenFloorNumber++) {

            Floor floor = new Floor(currenFloorNumber);
            floors.add(floor);

            int numberOfPassengersOnFloor = Event.rnd(1, 10);

            for (int currentPassenger = 1; currentPassenger <= numberOfPassengersOnFloor; currentPassenger++) {

                int desiredFloor;
                do {
                    desiredFloor = Event.rnd(1, numberOfFloors);
                } while (desiredFloor == currenFloorNumber);

                Passenger passenger = new Passenger(desiredFloor, currenFloorNumber);
                floor.addInQueue(passenger);

                System.out.println("На этаже № " + elevator.getCurrentFloor() + " добавлен  человек " + passenger.hashCode() + " желающий выйти на этаже " + passenger.getDesiredFloor());

            }
        }

        System.out.println("лифт находится на этаже " + (elevator.getCurrentFloor()));

        while (elevator.isNeedForNextMove()) {

            Floor currentFloor = floors.get(elevator.getCurrentFloor() - 1);

            //в лифте есть пассажиры которые выйдут на этом этаже
            if (elevator.passengersToLeaveOnThisFloor()) {

                List<Passenger> unBoardingPassengers = elevator.unBoardingPassengers();

                for (Passenger passenger : unBoardingPassengers) {

                    currentFloor.addInQueue(passenger);
                }
            }

            //на этаже есть пассажиры которые поедут вверх
            if (currentFloor.peopleWaitingGoingUp(elevator)) {

                Iterator<Passenger> queuePassengersOnCurrentFloor = currentFloor.waitingPassengers.iterator();//создаем итератор

                while (queuePassengersOnCurrentFloor.hasNext()) {//до тех пор, пока в списке есть элементы

                    Passenger nextPassenger = queuePassengersOnCurrentFloor.next();//получаем следующий элемент
                    if (nextPassenger.getDesiredFloor() > elevator.getCurrentFloor()) {
                        //человек заходит в лифт если в лифте есть места
                        if ((elevator.passengers.size() < elevator.getMaxCapacity() && nextPassenger.isOnTheMove())) {

                            queuePassengersOnCurrentFloor.remove();
                            elevator.boardingPassenger(nextPassenger);

                            System.out.println("На этаже № " + elevator.getCurrentFloor() + " в лифт вошел человек " + nextPassenger.hashCode() + " желающий поехать на этаж " + nextPassenger.getDesiredFloor());

                        }
                    }
                }
            }


            //на этаже есть пассажиры которые поедут вниз
            if (currentFloor.peopleWaitingGoingDown(elevator)) {

                Iterator<Passenger> queuePassengersOnCurrentFloor = currentFloor.waitingPassengers.iterator();//создаем итератор
                while (queuePassengersOnCurrentFloor.hasNext()) {//до тех пор, пока в списке есть элементы

                    Passenger nextPassenger = queuePassengersOnCurrentFloor.next();//получаем следующий элемент
                    if (nextPassenger.getDesiredFloor() < elevator.getCurrentFloor()) {
                        //человек заходит в лифт если в лифте есть места
                        if ((elevator.passengers.size() < elevator.getMaxCapacity() && nextPassenger.isOnTheMove())) {

                            queuePassengersOnCurrentFloor.remove();
                            elevator.boardingPassenger(nextPassenger);

                            System.out.println("На этаже № " + elevator.getCurrentFloor() + " в лифт вошел человек " + nextPassenger.hashCode() + " желающий поехать на этаж " + nextPassenger.getDesiredFloor());

                        }
                    }
                }
            }


            //в лифте есть пассажиры которые поедут вверх
            if (elevator.passengersGoingUp()) {

                elevator.moveUp();//едем дальше наверх
                System.out.println("В лифте " + elevator.passengers.size() + " пассажиров, они едут вверх на этаж " + elevator.getCurrentFloor());

            }

            //в лифте остались пассажиры которые поедут вниз
            else if (elevator.passengersGoingDown()) {

                elevator.moveDown();//едем вниз
                System.out.println("В лифте " + elevator.passengers.size() + " пассажиров, они едут вниз на этаж " + elevator.getCurrentFloor());

            }


            //вверху есть люди которым нужен лифт
            else if (elevator.checkIfLiftNeedsGoingUp(floors)) {

                elevator.moveUp();
                System.out.println("Наверху есть пассажиры, лифт едет за ними вверх на этаж " + elevator.getCurrentFloor());
            }

            //внизу есть люди которым нужен лифт
            else if (elevator.checkIfLiftNeedsGoingDown(floors)) {

                elevator.moveDown();//едем вниз
                System.out.println("Внизу есть пассажиры, лифт едет за ними вниз на этаж " + elevator.getCurrentFloor());
            }

            //никого не осталось перевозить и никто не вызывает лифт
            else {

                System.out.println("В лифте нет пассажиров, никто не вызывает лифт, он остановился на этаже " + elevator.getCurrentFloor());

                elevator.setNeedForNextMove(false);
                break;
            }
        }
    }
}