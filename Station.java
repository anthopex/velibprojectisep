package com.company;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Station {
    int label;
    double longitude;
    double latitude;
    int nbVelos;
    List<Integer> voisins = new ArrayList<Integer>();

    public Station() {
        label = 0;
        latitude = 0;
        longitude = 0;
        nbVelos = 0;
    }
    public Station(int label, double longitude, double latitude, int nbVelos){
        this.label = label;
        this.nbVelos = nbVelos;
        this.longitude = longitude;
        this.latitude = latitude;
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
            voisins.add(stationb.label);
        }
    }
    public boolean estVoisin(Station stationb){
        for (int i = 0; i < voisins.size()+1;i ++){
            if (voisins.get(i) == stationb.label) {
                return true;
            }
        }
        return false;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getNbVelos() {
        return nbVelos;
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

    public void setNbVelos(int nbVelos) {
        this.nbVelos = nbVelos;
    }

    public void setVoisins(List<Integer> voisins) {
        this.voisins = voisins;
    }
}

