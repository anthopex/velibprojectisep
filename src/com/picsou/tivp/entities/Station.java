package com.picsou.tivp.entities;

import com.picsou.tivp.UserInterface;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Station {

    public static float ZOOM = 1000f;
    public static float TOTAL_BIKE_ADJUSTMENT = 5;

    private int id;
    private String address;

    private double longitude;
    private double latitude;

    private int totalPlaces;

    private List<Bike> bikes;

    private double drawX = 0;
    private double drawY = 0;

    private int neighbordsAmount = 0;
    private List<Station> neighbords;


    public Station(int id, String address, double longitude, double latitude, int totalPlaces) {
        this.id = id;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.totalPlaces = totalPlaces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void spawnBikes(int amount) {
        bikes = new ArrayList<>();

        for (int it = 0; it < amount; it++) {
            bikes.add(new Bike());
        }
    }

    public int getBikeAmount() {
        return bikes.size();
    }

    public int getFreeStands() {
        return totalPlaces - bikes.size();
    }

    public static float getZOOM() {
        return ZOOM;
    }

    public static void setZOOM(float ZOOM) {
        Station.ZOOM = ZOOM;
    }

    public static float getTotalBikeAdjustment() {
        return TOTAL_BIKE_ADJUSTMENT;
    }

    public static void setTotalBikeAdjustment(float totalBikeAdjustment) {
        TOTAL_BIKE_ADJUSTMENT = totalBikeAdjustment;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }

    public double getDrawX() {
        return drawX;
    }

    public void setDrawX(double drawX) {
        this.drawX = drawX;
    }

    public double getDrawY() {
        return drawY;
    }

    public void setDrawY(double drawY) {
        this.drawY = drawY;
    }

    public int getNeighbordsAmount() {
        return neighbordsAmount;
    }

    public void setNeighbordsAmount(int neighbordsAmount) {
        this.neighbordsAmount = neighbordsAmount;
    }

    public List<Station> getNeighbords() {
        return neighbords;
    }

    public void setNeighbords(List<Station> neighbords) {
        this.neighbords = neighbords;
        this.neighbordsAmount = this.neighbords.size();
    }

    public Bike popBike() {
        if (bikes.size() == 0) return null;

        Bike popedBike = bikes.get(bikes.size() - 1);
        bikes.remove(popedBike);
        return popedBike;
    }

    public boolean insertBike(Bike bike) {
        if (bikes.size() == totalPlaces) return false;
        bikes.add(bike);
        return true;
    }

    public String getStringStatus() {
        return "id : " + id + ", located at " + address + " (" + longitude + " : " + latitude + "). Bikes : " + getBikeAmount() + "/" + totalPlaces;
    }

    public void display() {
        if (getFreeStands() > totalPlaces || getBikeAmount() > totalPlaces) {
            System.err.println(getStringStatus() + " -- Station Error");
        } else {
            System.out.println(getStringStatus() + " -- Station clean");
        }

    }

    public boolean hasProblem() {
        return getBikeAmount() <= (totalPlaces / 10) || getBikeAmount() >= (9 * totalPlaces / 10);
    }

    public void draw(Graphics graphics, float xCorrection, float yCorrection) {


//        double x =  (UserInterface.EARTH_RADIUS * Math.cos(latitude) * Math.cos(longitude));// - xCorrection;
//        double y = (UserInterface.EARTH_RADIUS * Math.cos(latitude) * Math.sin(longitude));// - yCorrection;

        drawX =  (((longitude - xCorrection) * UserInterface.ZOOM) + (UserInterface.WIDTH / 2.d)) + UserInterface.xDisplacement;
        drawY = ((-(latitude - yCorrection)) * UserInterface.ZOOM + (UserInterface.HEIGHT / 2.d)) + UserInterface.yDisplacement;

//        System.out.println("Drawing " + address + " x : " + x + " y : " + y + " lat : " + latitude + " long : " + longitude );


        float adjustmentRatio = 5;

        float capatitySize;
        float amountSize;

        capatitySize = totalPlaces / adjustmentRatio;

        graphics.setColor(Color.white);
        graphics.fillOval((float) drawX - (capatitySize / 2) - 1, (float) drawY - (capatitySize / 2) - 1, capatitySize + 2, capatitySize + 2);

        if (getFillPercentage() < 10) {
            graphics.setColor(Color.blue);
        } else if (getFillPercentage() < 90) {
            graphics.setColor(Color.green);
        } else if (getFillPercentage() < 100) {
            graphics.setColor(Color.orange);
        } else if (getFillPercentage() == 100) {
            graphics.setColor(Color.red);
        }

        graphics.fillOval((float) drawX - (capatitySize / 2), (float) drawY - (capatitySize / 2), capatitySize, capatitySize);
    }

    public void drawInfos(Graphics graphics) {
        int width = 350;
        int height = 100;

        graphics.setColor(Color.white);
        graphics.fillRect((float) drawX, (float) drawY, width, height);

        String infos = address + "\n";
        infos += "Stands : " + getFreeStands() + " bikes : " + getBikeAmount() + " - total : " + totalPlaces;
        infos += "\nNeighbords amount : " + neighbordsAmount;

        graphics.setColor(Color.black);
        graphics.drawString(infos, (float) drawX, (float) drawY);



    }

    public float getFillPercentage() {
        return (getBikeAmount() / (float) totalPlaces) * 100f;
    }


}
