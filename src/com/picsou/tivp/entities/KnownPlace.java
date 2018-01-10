package com.picsou.tivp.entities;

import com.picsou.tivp.UserInterface;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class KnownPlace {
    private String name;
    private double longitude;
    private double latitude;

    public KnownPlace(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void draw(Graphics graphics, double xCorrection, double yCorrection) {
        double drawX =  (((longitude - xCorrection) * UserInterface.ZOOM) + (UserInterface.WIDTH / 2.d)) + UserInterface.xDisplacement;
        double drawY = ((-(latitude - yCorrection)) * UserInterface.ZOOM + (UserInterface.HEIGHT / 2.d)) + UserInterface.yDisplacement;

        graphics.setColor(Color.white);
        graphics.fillRect((float) drawX, (float) drawY, 200, 20);

        graphics.setColor(Color.black);
        graphics.drawString(name, (float) drawX, (float) drawY);



    }
}
