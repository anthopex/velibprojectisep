package com.company;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Station {
    int id;
    double longitude;
    double latitude;
    int maxBike;
    boolean isProblematic;
    List<Object> tsproblem = new ArrayList<Object>();
    List<Integer> voisins = new ArrayList<Integer>();
    List<Integer> lesvelo = new ArrayList<Integer>();
    List<Double> departs = new ArrayList<Double>();
    List<Double> arrivees = new ArrayList<Double>();

    public Station() {
        id = 0;
        latitude = 0;
        longitude = 0;
    }
    public Station(int label, double longitude, double latitude, int maxBike){
        this.id = label;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isProblematic = false;
        this.maxBike = maxBike;
    }
    public double convertRad(double degre){
        return (PI * degre)/180;
    }
    public void trouverVoisin(Station stationb){
        double R = 6378000; //Rayon de la terre en mètre
        double latB = convertRad(stationb.latitude);
        double lonB = convertRad(stationb.longitude);
        double latA = convertRad(latitude);
        double lonA = convertRad(longitude);
        double d = R * (PI/2 - asin( sin(latB) * sin(latA) + cos(lonB - lonA) * cos(latB) * cos(latA)));
        if(d < 400){
            voisins.add(stationb.id);
            stationb.voisins.add(id);
        }
    }
    public boolean estVoisin(Station stationb){
        for (int i = 0; i < voisins.size()+1;i ++){
            if (voisins.get(i) == stationb.id) {
                return true;
            }
        }
        return false;
    }

    public int getMaxBike() {
        return maxBike;
    }

    public void setMaxBike(int maxBike) {
        this.maxBike = maxBike;
    }

    public boolean isProblematic() {
        return isProblematic;
    }

    public void setProblematic(boolean problematic) {
        isProblematic = problematic;
    }

    public List<Integer> getLesvelo() {
        return lesvelo;
    }

    public void setLesvelo(List<Integer> lesvelo) {
        this.lesvelo = lesvelo;
    }

    public List<Double> getDeparts() {
        return departs;
    }

    public void setDeparts(List<Double> departs) {
        this.departs = departs;
    }

    public List<Double> getArrivees() {
        return arrivees;
    }

    public void setArrivees(List<Double> arrivees) {
        this.arrivees = arrivees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public List<Integer> getVoisins() {
        return voisins;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setVoisins(List<Integer> voisins) {
        this.voisins = voisins;
    }
}

