package com.picsou.tivp.entities;

import com.picsou.tivp.UserInterface;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Road {

    private List<Point[]> lines = new ArrayList<>();

    private String name;
    private String type;

    public Road() {
        lines = new ArrayList<>();
    }

    public Road(List<Point[]> lines) {
        this.lines = lines;
    }

    public List<Point[]> getLines() {
        return lines;
    }

    public void setLines(List<Point[]> lines) {
        this.lines = lines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addLine(Point point1, Point point2) {
        lines.add(new Point[]{point1, point2});
    }



    public void draw(Graphics graphics, double xCorrection, double yCorrection) {

        if (lines != null) {
            for (Point[] points : lines) {

                double drawX1 =  (((points[0].getLongitude() - xCorrection) * UserInterface.ZOOM) + (UserInterface.WIDTH / 2.d)) + UserInterface.xDisplacement;
                double drawY1 = ((-(points[0].getLattitude() - yCorrection)) * UserInterface.ZOOM + (UserInterface.HEIGHT / 2.d)) + UserInterface.yDisplacement;

                double drawX2 =  (((points[1].getLongitude() - xCorrection) * UserInterface.ZOOM) + (UserInterface.WIDTH / 2.d)) + UserInterface.xDisplacement;
                double drawY2 = ((-(points[1].getLattitude() - yCorrection)) * UserInterface.ZOOM + (UserInterface.HEIGHT / 2.d)) + UserInterface.yDisplacement;

                graphics.setColor(Color.darkGray);
                graphics.drawLine((float) drawX1, (float) drawY1, (float) drawX2, (float) drawY2);

            }
        } else {
            System.err.println("No lines to draw for road " + name);
        }

    }
}
