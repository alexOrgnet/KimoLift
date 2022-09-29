package com.javarush.lifttask;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.media.jfxmedia.events.BufferListener;

import java.util.ArrayList;

public class Main {
    static List<Floor> floors = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Starting");


        Building building = new Building();
        Elevator elevator = new Elevator();

        System.out.println("Number of floors " + building.getNumberOfFloors());

        //инициализация

        int numberOfFloors = building.getNumberOfFloors();

        int currenFloor;
        for (currenFloor = 1; currenFloor <= numberOfFloors; currenFloor++) {

            Floor floor = new Floor(currenFloor);

            floors.add(floor);


            System.out.println("Создали floor # " + (currenFloor));

            //создаю толпы пассажиров на каждом этаже
            int numberOfPassengersOnFloor = Event.rnd(1, 10);

            System.out.println("На этаже № " + currenFloor + " добавлен " + numberOfPassengersOnFloor + " пассажиров ");

            int currentPassenger;
            for (currentPassenger = 1; currentPassenger <= numberOfPassengersOnFloor; currentPassenger++) {

                int desiredFloor;
                do {
                    desiredFloor = Event.rnd(1, numberOfFloors);
                } while (desiredFloor == currenFloor);

                Passenger passenger = new Passenger(desiredFloor, currenFloor);
                floor.waitingPassengers.add(passenger);

                System.out.println("На этаже № " + currenFloor + " добавлен пассажир " + passenger + " желающий выйти на этаже " + passenger.getDesiredFloor());

            }
        }


        //лифт едет вверх
        while (elevator.isNeedForNextMove() && elevator.isDirectionUP()) {


            for (int i = elevator.getCurrentFloor()-1; i < numberOfFloors; i++) {

                int currentFloor = i + 1;

                //в лифте есть пассажиры которые выйдут на этом этаже
                if (elevator.passengers.stream().filter(p -> p.getDesiredFloor() == currentFloor).count() > 0) {

                    Iterator<Passenger> passengersInLift = elevator.passengers.iterator();//создаем итератор
                    while (passengersInLift.hasNext()) {//до тех пор, пока в лифте есть пассажиры

                        Passenger liftPassenger = passengersInLift.next();//получаем следующий элемент
                        if (liftPassenger.desiredFloor == currentFloor) {
                            //человек выходит из лифта
                            liftPassenger.finishMove();
                            passengersInLift.remove();
                            floors.get(i).waitingPassengers.add(liftPassenger);


                            System.out.println("Из лифта на этаже № " + currentFloor + " вышел " + liftPassenger);
                        }
                    }
                }

                //на этаже есть пассажиры которые поедут вверх
                if ((floors.get(i).waitingPassengers.stream().filter(p -> p.getDesiredFloor() > currentFloor).count() > 0)) {

                    Iterator<Passenger> waitingPassengers = floors.get(i).waitingPassengers.iterator();//создаем итератор
                    while (waitingPassengers.hasNext()) {//до тех пор, пока в списке есть элементы

                        Passenger nextPassenger = waitingPassengers.next();//получаем следующий элемент
                        if (nextPassenger.desiredFloor > currentFloor) {
                            //человек заходит в лифт если в лифте есть места
                            if (elevator.passengers.size() < elevator.getMaxCapacity()) {
                                waitingPassengers.remove();
                                elevator.passengers.add(nextPassenger);
                            }
                        }
                    }
                }

                //в лифте есть пассажиры которые поедут вверх
                else if (elevator.passengers.stream().filter(p -> p.getDesiredFloor() > currentFloor).count() > 0) {
                }

                //в лифте остались пассажиры которые поедут вниз
                else if (elevator.passengers.stream().filter(p -> p.getDesiredFloor() < currentFloor).count() > 0) {
                    elevator.setDirectionDown(true);
                    elevator.setDirectionUP(false);
                }
                //внизу есть люди которым нужен лифт
                else if (checkIfLiftNeedsGoingUp(floors, currentFloor)) {

                    elevator.setDirectionDown(false);
                    elevator.setDirectionUP(true);
                }
                //внизу есть люди которым нужен лифт
                else if (checkIfLiftNeedsGoingDown(floors, currentFloor)) {

                    elevator.setDirectionDown(true);
                    elevator.setDirectionUP(false);
                }

                //никого не осталось перевозить и никто не вызывает лифт
                else {
                    elevator.setNeedForNextMove(false);
                    elevator.setDirectionDown(false);
                    elevator.setDirectionUP(false);
                    break;
                }
            }
        }

        //лифт едет вниз
        while (elevator.isNeedForNextMove() && elevator.isDirectionDown()) {


            for (int i = elevator.getCurrentFloor()-1; i >= 0; i--) {

                int currentFloor = elevator.getCurrentFloor();

                //в лифте есть пассажиры которые выйдут на этом этаже
                if (elevator.passengers.stream().filter(p -> p.getDesiredFloor() == currentFloor).count() > 0) {

                    Iterator<Passenger> passengersInLift = elevator.passengers.iterator();//создаем итератор
                    while (passengersInLift.hasNext()) {//до тех пор, пока в лифте есть пассажиры

                        Passenger liftPassenger = passengersInLift.next();//получаем следующий элемент
                        if (liftPassenger.desiredFloor == currentFloor) {
                            //человек выходит из лифта
                            liftPassenger.finishMove();
                            passengersInLift.remove();
                            floors.get(i).waitingPassengers.add(liftPassenger);

                            System.out.println("Из лифта на этаже № " + currentFloor + " вышел " + liftPassenger);
                        }
                    }
                }


                //на этаже есть пассажиры которые поедут вниз
                if ((floors.get(i).waitingPassengers.stream().filter(p -> p.getDesiredFloor() < currentFloor).count() > 0)) {

                    Iterator<Passenger> waitingPassengers = floors.get(i).waitingPassengers.iterator();//создаем итератор
                    while (waitingPassengers.hasNext()) {//до тех пор, пока в списке есть элементы

                        Passenger nextPassenger = waitingPassengers.next();//получаем следующий элемент
                        if (nextPassenger.desiredFloor < currentFloor) {
                            //человек заходит в лифт если в лифте есть места
                            if (elevator.passengers.size() < elevator.getMaxCapacity()) {
                                waitingPassengers.remove();
                                elevator.passengers.add(nextPassenger);
                            }
                        }
                    }
                }

                //в лифте есть пассажиры которые поедут вниз
                else if (elevator.passengers.stream().filter(p -> p.getDesiredFloor() < currentFloor).count() > 0) {
                }

                //в лифте остались пассажиры которые поедут вверх
                else if (elevator.passengers.stream().filter(p -> p.getDesiredFloor() > currentFloor).count() > 0) {

                    elevator.setDirectionDown(false);
                    elevator.setDirectionUP(true);
                }
                //внизу есть люди которым нужен лифт
                else if (checkIfLiftNeedsGoingDown(floors, currentFloor)) {

                    elevator.setDirectionDown(true);
                    elevator.setDirectionUP(false);
                }
                //вверху есть люди которым нужен лифт
                else if (checkIfLiftNeedsGoingUp(floors, currentFloor)) {

                    elevator.setDirectionDown(false);
                    elevator.setDirectionUP(true);
                }

                //никого не осталось перевозить и никто не вызывает лифт
                else {
                    elevator.setNeedForNextMove(false);
                    elevator.setDirectionDown(false);
                    elevator.setDirectionUP(false);
                    break;
                }
            }
        }
    }

    public static boolean checkIfLiftNeedsGoingUp(List<Floor> floors, int currentFloor) {

        for (Floor floor : floors) {

            if (floor.getFloorNumber() <= currentFloor){
                continue;
            }

            if (floor.waitingPassengers.stream().filter(p -> (p.getDesiredFloor() > currentFloor) && (p.isOnTheMove())).count() > 0) {
                return true;
            } else return false;
        }

        return false;
    }

    public static boolean checkIfLiftNeedsGoingDown(List<Floor> floors, int currentFloor) {

        for (Floor floor : floors) {
            if (floor.getFloorNumber() >= currentFloor){
                continue;
            }
            if (floor.waitingPassengers.stream().filter(p -> (p.getDesiredFloor() < currentFloor) && (p.isOnTheMove())).count() > 0) {
                return true;
            } else return false;
        }

        return false;
    }
}
