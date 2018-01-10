package com.picsou.tivp.guiElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Slider {

    private String name;

    private int value;

    private float max;
    private float min;
    private float length;

    private float displayX, displayY, width, height;

    public Slider() {
    }

    public Slider(String name, float min, float max, float displayX, float displayY, float width, float height) {
        this.name = name;
        this.max = max;
        this.min = min;
        this.length = max - min;

        this.displayX = displayX;
        this.displayY = displayY;
        this.width = width;
        this.height = height;

        this.value = 0;
    }

    public void configureUI(float displayX, float displayY, float width, float height) {
        this.displayX = displayX;
        this.displayY = displayY;
        this.width = width;
        this.height = height;
        this.value = 0;
    }

    public void configureValues(float min, float max) {
        this.max = max;
        this.min = min;
        this.length = max - min;
        this.value = 0;
    }



    public void update(Input input) {
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

            float mouseX = input.getMouseX();
            float mouseY = input.getMouseY();

            if (mouseX > displayX && mouseX < displayX + width && mouseY > displayY && mouseY < displayY + height) {

                int distanceFromBottom = 0;
                distanceFromBottom = (int) (height - (mouseY - displayY));
                this.value = (int) (distanceFromBottom * height / length);
            }
        }
    }

    public void draw(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawRect(displayX, displayY, width, height);

        float cursorY = height + displayY - ((value * length) / height);

        graphics.fillRect(displayX + 2, cursorY - 5, width - 4, 10);

        graphics.drawString(name, displayX, displayY - 18);

    }


    public int getValue() {
        return (int) (min + value);
    }



}
