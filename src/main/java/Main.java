package com.company;

public class Main {

    public static void main(String[] args) {
        Station StationA = new Station(1 , 48.8512722, 2.3624305555555556, 20);
        Station StationC = new Station(3 , 48.8513722, 2.3626305555555556, 20);
        Station StationB = new Station(2 , 48.8499611, 2.363238888888889, 20);
        StationA.trouverVoisin(StationB);
        StationA.trouverVoisin(StationC);
        System.out.print(StationA.voisins);
    }
}
