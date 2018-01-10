package com.picsou.tivp;

import com.picsou.tivp.entities.City;
import org.lwjgl.opengl.GREMEDYFrameTerminator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stats {

    public static float GRAPH_SIZER = 10;

    private List<Integer> movingBikes;
    private List<Integer> storageBikes;
    private List<Integer> onStations;

    private List<Integer> problemStations;
    private List<Integer> goodStations;

    private List<Integer> emptys;
    private List<Integer> lows;
    private List<Integer> goods;
    private List<Integer> highs;
    private List<Integer> fulls;





    private int lastMoving, lastStorage, lastOnStation, lastProblemStation,  lastGoodStation;

    private int lastEmpty;
    private int lastLow;
    private int lastGood;
    private int lastHigh;
    private int lastFull;

    private PrintWriter printWriter;


    public Stats() {
        movingBikes = new ArrayList<>();
        storageBikes = new ArrayList<>();
        onStations = new ArrayList<>();
        problemStations = new ArrayList<>();
        goodStations = new ArrayList<>();

        emptys = new ArrayList<>();
        lows = new ArrayList<>();
        goods = new ArrayList<>();
        highs = new ArrayList<>();
        fulls = new ArrayList<>();


        long currentTimestamp = new Date().getTime();
        try {
            printWriter = new PrintWriter("outputs/outputs_" + currentTimestamp + "_" + Main.userRatio + ".csv");
            printWriter.println("currentTimestamp" + "," + "moving" + "," + "storage" + "," + "onStation" + "," + "problemStation" + "," + "goodStation" + "," + "empty" + "," + "low" + "," + "good" + "," + "high" + "," + "full");
            printWriter.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void addStat(int moving, int storage, int onStation, int problemStation, int goodStation, int empty, int low, int good, int high, int full, long currentTimestamp) {
        movingBikes.add(moving);
        storageBikes.add(storage);
        onStations.add(onStation);
        problemStations.add(problemStation);
        goodStations.add(goodStation);

        emptys.add(empty);
        lows.add(low);
        goods.add(good);
        highs.add(high);
        fulls.add(full);




        this.lastMoving = moving;
        this.lastStorage = storage;
        this.lastOnStation = onStation;
        this.lastProblemStation = problemStation;
        this.lastGoodStation = goodStation;

        this.lastEmpty = empty;
        this.lastLow = low;
        this.lastGood = good;
        this.lastHigh = high;
        this.lastFull = full;



        printWriter.println(currentTimestamp + "," + moving + "," + storage + "," + onStation + "," + problemStation + "," + goodStation + "," + empty + "," + low + "," + good + "," + high + "," + full);
        printWriter.flush();




    }

    public List<Integer> getMovingBikes() {
        return movingBikes;
    }

    public void setMovingBikes(List<Integer> movingBikes) {
        this.movingBikes = movingBikes;
    }

    public List<Integer> getStorageBikes() {
        return storageBikes;
    }

    public void setStorageBikes(List<Integer> storageBikes) {
        this.storageBikes = storageBikes;
    }

    public List<Integer> getOnStations() {
        return onStations;
    }

    public void setOnStations(List<Integer> onStations) {
        this.onStations = onStations;
    }

    public List<Integer> getProblemStations() {
        return problemStations;
    }

    public void setProblemStations(List<Integer> problemStations) {
        this.problemStations = problemStations;
    }

    public List<Integer> getGoodStations() {
        return goodStations;
    }

    public void setGoodStations(List<Integer> goodStations) {
        this.goodStations = goodStations;
    }

    public static float getGraphSizer() {
        return GRAPH_SIZER;
    }

    public static void setGraphSizer(float graphSizer) {
        GRAPH_SIZER = graphSizer;
    }

    public List<Integer> getEmptys() {
        return emptys;
    }

    public void setEmptys(List<Integer> emptys) {
        this.emptys = emptys;
    }

    public List<Integer> getLows() {
        return lows;
    }

    public void setLows(List<Integer> lows) {
        this.lows = lows;
    }

    public List<Integer> getGoods() {
        return goods;
    }

    public void setGoods(List<Integer> goods) {
        this.goods = goods;
    }

    public List<Integer> getHighs() {
        return highs;
    }

    public void setHighs(List<Integer> highs) {
        this.highs = highs;
    }

    public List<Integer> getFulls() {
        return fulls;
    }

    public void setFulls(List<Integer> fulls) {
        this.fulls = fulls;
    }

    public int getLastMoving() {
        return lastMoving;
    }

    public void setLastMoving(int lastMoving) {
        this.lastMoving = lastMoving;
    }

    public int getLastStorage() {
        return lastStorage;
    }

    public void setLastStorage(int lastStorage) {
        this.lastStorage = lastStorage;
    }

    public int getLastOnStation() {
        return lastOnStation;
    }

    public void setLastOnStation(int lastOnStation) {
        this.lastOnStation = lastOnStation;
    }

    public int getLastProblemStation() {
        return lastProblemStation;
    }

    public void setLastProblemStation(int lastProblemStation) {
        this.lastProblemStation = lastProblemStation;
    }

    public int getLastGoodStation() {
        return lastGoodStation;
    }

    public void setLastGoodStation(int lastGoodStation) {
        this.lastGoodStation = lastGoodStation;
    }

    public int getLastEmpty() {
        return lastEmpty;
    }

    public void setLastEmpty(int lastEmpty) {
        this.lastEmpty = lastEmpty;
    }

    public int getLastLow() {
        return lastLow;
    }

    public void setLastLow(int lastLow) {
        this.lastLow = lastLow;
    }

    public int getLastGood() {
        return lastGood;
    }

    public void setLastGood(int lastGood) {
        this.lastGood = lastGood;
    }

    public int getLastHigh() {
        return lastHigh;
    }

    public void setLastHigh(int lastHigh) {
        this.lastHigh = lastHigh;
    }

    public int getLastFull() {
        return lastFull;
    }

    public void setLastFull(int lastFull) {
        this.lastFull = lastFull;
    }

    public void drawStat(Graphics graphics, int tick, long currentTimestamp, SimpleDateFormat mainFormat) {

        int x = 0;
        int y = UserInterface.HEIGHT - 200;

        float tickWidth = UserInterface.WIDTH / (float) (3600 * 24);


        for (int it = 0; it < tick; it++) {
            graphics.setColor(Color.yellow);
            graphics.fillRect(it * tickWidth, UserInterface.HEIGHT - 150, tickWidth, - (movingBikes.get(it) / GRAPH_SIZER));

            graphics.setColor(Color.gray);
            graphics.fillRect(it * tickWidth, UserInterface.HEIGHT - (storageBikes.get(it) / GRAPH_SIZER) - 150, tickWidth, 2);

            float emptysHeight = -emptys.get(it) / GRAPH_SIZER;
            float lowsHeight = -lows.get(it) / GRAPH_SIZER;
            float goodsHeight = -goods.get(it) / GRAPH_SIZER;
            float highsHeight = -highs.get(it) / GRAPH_SIZER;
            float fullsHeight = -fulls.get(it) / GRAPH_SIZER;

            graphics.setColor(Color.black);
            graphics.fillRect(it * tickWidth, UserInterface.HEIGHT, tickWidth, emptysHeight);

            graphics.setColor(Color.blue);
            graphics.fillRect(it * tickWidth, UserInterface.HEIGHT + emptysHeight, tickWidth, lowsHeight);

            graphics.setColor(Color.green);
            graphics.fillRect(it * tickWidth, UserInterface.HEIGHT + emptysHeight + lowsHeight, tickWidth, goodsHeight);

            graphics.setColor(Color.orange);
            graphics.fillRect(it * tickWidth, UserInterface.HEIGHT + emptysHeight + lowsHeight + goodsHeight, tickWidth, highsHeight);

            graphics.setColor(Color.red);
            graphics.fillRect(it * tickWidth, UserInterface.HEIGHT + emptysHeight + lowsHeight + goodsHeight + highsHeight, tickWidth, fullsHeight);
        }

        for (int it = 0; it < (3600 * 24); it++) {
            graphics.setColor(Color.white);
            if (it % 3600 == 0) {
                graphics.fillRect(it * tickWidth, UserInterface.HEIGHT - 120, 2, - 20);
                graphics.drawString(String.valueOf(it / 3600), it * tickWidth + 5, UserInterface.HEIGHT - 140);
            }
        }




        if (lastProblemStation != 0) {
            x = UserInterface.WIDTH - 200;
            y = 0;

            graphics.setColor(Color.yellow);
            graphics.drawString(lastMoving + " moving", x, y);

            graphics.setColor(Color.lightGray);
            graphics.drawString(lastStorage + " in storage", x, y + 20);

            graphics.setColor(Color.red);
            graphics.drawString("Station NOK : " + lastProblemStation, x, y + 40);

            graphics.setColor(Color.green);
            graphics.drawString("Stations OK : " + lastGoodStation, x, y + 60);

            graphics.setColor(Color.white);
            float percentage;
            if (lastProblemStation == 0) {
                percentage = 0;
            } else {
                percentage = lastProblemStation / (float) (lastGoodStation + lastProblemStation) * 100f;
            }
            graphics.drawString("Problems : " + percentage + "%", x, y + 80);

            graphics.drawString(mainFormat.format(currentTimestamp), x, y + 100);

            graphics.drawString("Bikes Generated : " + String.valueOf(City.generatedBikes), x, y + 120);

        }

        drawLegend(graphics);
    }

    public void drawLegend(Graphics graphics) {
        float x = 80;
        float y = 0;

        graphics.setColor(Color.white);
        graphics.fillRect(x, y, 50, 19);
        graphics.setColor(Color.black);
        graphics.drawString("Empty", x, y);

        graphics.setColor(Color.blue);
        graphics.drawString("Low ( < 10% ) ", x, y + 20);

        graphics.setColor(Color.green);
        graphics.drawString("Good ( 10% - 90% )", x, y + 40);

        graphics.setColor(Color.orange);
        graphics.drawString("High ( > 90% )", x, y + 60);

        graphics.setColor(Color.red);
        graphics.drawString("Full", x, y + 80);



    }
}
