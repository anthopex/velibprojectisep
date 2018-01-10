package com.picsou.tivp;

import com.picsou.tivp.entities.City;
import com.picsou.tivp.entities.Station;
import com.picsou.tivp.guiElement.Slider;
import org.newdawn.slick.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserInterface extends BasicGame {

    public static int WIDTH = 1400;
    public static int HEIGHT = 950;

    public static float xDisplacement = 140.0f;
    public static float yDisplacement = -125.f;

    public static int ACCELERATOR = (30 * 60);

    public static int ZOOM = 5320;

    private City paris;
    private List<Trip> trips;

    private long currentTimestamp;
    private int tick = 0;

    private SimpleDateFormat mainFormat;

    private double xCorrection;
    private double yCorrection;

    private Station currentStation;

    private boolean paused = false;

    private PrintWriter printWriter;

    private int normalStartedTrips = 0;
    private int ratioStartedTrip = 0;

    private Slider stationDisplay;

    private Random random;

    public UserInterface() {
        super("The Isep's Velib Project");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        paris = Main.loadParis(databaseConfig);
        trips = databaseConfig.getTrips();

        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date;
        try {
            date = yyyyMMddHHmmss.parse("20131031000000");

        } catch (ParseException e) {
            e.printStackTrace();
            throw new SlickException("Error while loading date");
        }
        currentTimestamp = date.getTime();

        mainFormat = new SimpleDateFormat("dd/MM HH:mm:ss");

//        xCorrection = (EARTH_RADIUS * Math.cos(paris.getCenterLatitude()) * Math.cos(paris.getCenterLongitude()));
//        yCorrection = (EARTH_RADIUS * Math.cos(paris.getCenterLatitude()) * Math.sin(paris.getCenterLongitude()));


        xCorrection = paris.getCenterLongitude();
        yCorrection = paris.getCenterLatitude();


        try {
            printWriter = new PrintWriter("fucked_trips.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        stationDisplay = new Slider("Station display", 8, 72, 0, 500, 40, 66);

        random = new Random();

        System.out.println("Loading simulation at " + mainFormat.format(currentTimestamp));
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        manageInputs(gameContainer.getInput());
        manageMouseHover(gameContainer.getInput());

        if (tick >= (3600 * 24)) return;

        if (paused) return;

        for (int it = 0; it < ACCELERATOR; it++) {

            List<Trip> currentTrips = currentTrips();

            currentTrips.forEach(trip -> {
                if (trip.getOut_date() == currentTimestamp) {

                    double startAmount = (normalStartedTrips == 0 ? 1 : (ratioStartedTrip / (double) normalStartedTrips));

                    if (startAmount <= Main.tripRatio) {
                        startAmount = Math.ceil(Main.tripRatio);
                    } else {
                        startAmount = Math.floor(Main.tripRatio);
                    }

                    normalStartedTrips++;
                    ratioStartedTrip += startAmount;


                    trips.remove(trip); //avoid to launch the trip one more
                    for (int jt = 0; jt < startAmount; jt++) {
                        if (random.nextDouble() * 100 < Main.userRatio)
                            improveTrip(trip);


//                        System.err.println("Trying to park in " + paris.getStations().get(trip.getOut_station()).getAddress());
                        boolean success = paris.removeBikeFromStation(trip.getOut_station());
                        trips.add(trip);
                    }





                } else if (trip.getIn_date() == currentTimestamp) {
                    if (trip.isActiveUser()) {
                        improveTrip(trip);
                    }

                    boolean success = paris.addBikeInStation(trip.getIn_station());

                }
            });



            paris.addStats(tick);
            currentTimestamp += 1000;
            tick++;
//            System.out.println("End of update, current time = " + mainFormat.format(currentTimestamp));

        }


    }

    public Trip improveTrip(Trip trip) {

        if (currentTimestamp <= trip.getOut_date()) {
            Station out;
            out = paris.getStations().get(trip.getOut_station());

            if (out.getNeighbordsAmount() >= 1) {
                Station newStart = out;

                for (Station station : out.getNeighbords()) {
                    if (station.getFillPercentage() > newStart.getFillPercentage()) {
                        newStart = station;
                    }
                }

                trip.setOut_station(newStart.getId());
                trip.setActiveUser(true);

            }

        } else if (currentTimestamp <= trip.getIn_date()) {
//            System.out.println("Improving bike parking");
            Station in;
            in = paris.getStations().get(trip.getIn_station());

            if (in.getNeighbordsAmount() >= 1) {
                Station newStop = in;

                for (Station station : in.getNeighbords()) {
                    if (station.getFillPercentage() < newStop.getFillPercentage()) {
                        newStop = station;
                    }
                }

            }


        }





        return trip;
    }

    public void manageMouseHover(Input input) {
        for (Map.Entry<Integer, Station> stationEntry : paris.getStations().entrySet()) {
            double stationX = stationEntry.getValue().getDrawX();
            double stationY = stationEntry.getValue().getDrawY();

            int mouseX = input.getMouseX();
            int mouseY = input.getMouseY();

            if (Math.abs(mouseX - stationX) < 10 && Math.abs(mouseY - stationY) < 10) {
                this.currentStation = stationEntry.getValue();

            }
        }

    }

    public void manageInputs(Input input) {
        if (input.isKeyDown(Input.KEY_UP)) {
            yDisplacement += 5;
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            yDisplacement -= 5;
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            xDisplacement -= 5;
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            xDisplacement += 5;
        }

        if (input.isKeyDown(Input.KEY_Z)) {
            ZOOM += 30f;
        }
        if (input.isKeyDown(Input.KEY_S)) {
            ZOOM -= 30f;
        }


        if (input.isKeyDown(Input.KEY_RETURN)) {
            currentStation = null;
        }

        if (input.isKeyPressed(Input.KEY_SPACE)) {
            paused = !paused;
        }

        stationDisplay.update(input);

//        System.out.println(ZOOM + " - " + xDisplacement + " - " + yDisplacement);

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        if (paris.getRoads() != null) {
            paris.getRoads().forEach(road -> {
                road.draw(graphics, xCorrection, yCorrection);
            });
        }

//        if (paris.getPlaces() != null) {
//            paris.getPlaces().forEach((place) -> {
//                place.draw(graphics, xCorrection, yCorrection);
//            });
//        }


        for (Map.Entry<Integer, Station> currentStationEntry : paris.getStations().entrySet()) {

            if (currentStationEntry.getValue().getTotalPlaces() > stationDisplay.getValue()) {
                currentStationEntry.getValue().draw(graphics, (float) xCorrection, (float) yCorrection);
            }
//            System.out.println("xCorrection : " + xCorrection + " yCorrection : " + yCorrection + " lat : " + paris.getCenterLatitude() + " long : " + paris.getCenterLongitude());
        }

        if (currentStation != null) {
            currentStation.drawInfos(graphics);
        }

        paris.getStats().drawStat(graphics, tick, currentTimestamp, mainFormat);


        stationDisplay.draw(graphics);

    }



    public List<Trip> currentTrips() {
//        System.out.println("Searching in trips");
        List<Trip> currentTrips = new ArrayList<>();

        trips.forEach(trip -> {
            if (trip.getOut_date() == currentTimestamp || trip.getIn_date() == currentTimestamp)
                currentTrips.add(trip);
        });


//        System.out.println("Trips found");
        return currentTrips;
    }









}
