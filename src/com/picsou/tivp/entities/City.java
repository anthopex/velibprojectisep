package com.picsou.tivp.entities;

import com.picsou.tivp.OpenDataConfig;
import com.picsou.tivp.Stats;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class City {
    private String name;
    private double centerLatitude;
    private double centerLongitude;

    private Map<Integer, Station> stations;

    private List<Bike> movingBikes;

    private List<Bike> storage;

    private Stats stats;

    private List<KnownPlace> places;

    public static int generatedBikes = 0;

    public List<Road> roads;


    public City(String name, double centerLatitude, double centerLongitude) {
        this.name = name;
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.movingBikes = new ArrayList<>();
        this.storage = new ArrayList<>();
        this.stats = new Stats();
        this.roads = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public Map<Integer, Station> getStations() {
        return stations;
    }

    public void setStations(Map<Integer, Station> stations) {
        this.stations = stations;
    }

    public List<Bike> getMovingBikes() {
        return movingBikes;
    }

    public void setMovingBikes(List<Bike> movingBikes) {
        this.movingBikes = movingBikes;
    }

    public List<Bike> getStorage() {
        return storage;
    }

    public void setStorage(List<Bike> storage) {
        this.storage = storage;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public static int getGeneratedBikes() {
        return generatedBikes;
    }

    public static void setGeneratedBikes(int generatedBikes) {
        City.generatedBikes = generatedBikes;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public List<KnownPlace> getPlaces() {
        return places;
    }

    public void setPlaces(List<KnownPlace> places) {
        this.places = places;
    }

    public boolean removeBikeFromStation(int stationId) {
        Station currentStation = stations.get(stationId);

        if (currentStation.getBikeAmount() == 0) {
            Station newStation = findClosestStations(currentStation, true);

            System.err.println("Error while taking bike ... going to " + newStation.getAddress());

            currentStation = newStation;
        }



        if (currentStation == null) {
            return false;
        }

        Bike bikeTaken = currentStation.popBike();
        if (bikeTaken == null) {
//            System.err.println("Error while taking in a bike at " + currentStation.getId() + " (" + currentStation.getBikeAmount() + ") " + ", taking a bike from the storage");
            bikeTaken = takeBikeFromReserve();
        }

        movingBikes.add(bikeTaken);
        return true;
    }

    public Bike takeBikeFromReserve() {
        if (storage.size() == 0) {
//            System.out.println("Poping a bike out of nowhere");
            generatedBikes++;
            return new Bike();
        } else {
            Bike bike = storage.get(storage.size() - 1);
            storage.remove(bike);
            return bike;
        }
    }

    public Bike takeBikeFromMoving() {
        if (movingBikes.size() == 0) {
            System.out.println("Poping a bike from scratch");
            generatedBikes++;
            return new Bike();
        } else {
            Bike bike = movingBikes.get(movingBikes.size() - 1);
            movingBikes.remove(bike);
            return bike;
        }
    }



    public boolean addBikeInStation(int stationId) {
        Station currentStation = stations.get(stationId);

        if (currentStation.getTotalPlaces() == currentStation.getBikeAmount()) {
            Station newStation = findClosestStations(currentStation, false);

            System.err.println("Error while parking bike ... going to " + newStation.getAddress());

            currentStation = newStation;
        }



        Bike currentBike = takeBikeFromMoving();
        if (currentStation == null) {
            return false;
        }
        boolean placedBike = currentStation.insertBike(currentBike);

        if (placedBike) {
//            System.out.println("Successfully placed bike at " + currentStation.getAddress());
        } else {
//            System.err.println("Error while placing a bike at " + currentStation.getId() + " (" + currentStation.getFreeStands() + ") " + ", placing bike in storage");
            storage.add(currentBike);
        }
        movingBikes.remove(placedBike);
        return true;
    }



    public void addStats(long currentTimestamp) {

        int onStationBikeAmount = 0;
        int problemStation = 0;
        int goodStation = 0;

        int emptys = 0;
        int lows = 0;
        int goods = 0;
        int highs = 0;
        int fulls = 0;


        for (Map.Entry<Integer, Station> stationEntry : stations.entrySet()) {
            Station station = stationEntry.getValue();
            onStationBikeAmount += station.getBikeAmount();

            float filling = station.getFillPercentage();

            if (filling == 0)
                emptys++;
            else if (filling < 10)
                lows++;
            else if (filling < 90)
                goods++;
            else if (filling < 100)
                highs++;
            else if (filling == 100)
                fulls++;


            if (station.hasProblem())
                problemStation++;
            else
                goodStation++;

        }



        stats.addStat(movingBikes.size(), storage.size(), onStationBikeAmount, problemStation, goodStation, emptys, lows, goods, highs, fulls, currentTimestamp);
    }

    public List<Station> findNearestStations(Station searchStation, int distanceRatio) {
        List<Station> foundStations = new ArrayList<>();
        stations.forEach((id, currentStation) -> {

            double distX = searchStation.getLongitude() - currentStation.getLongitude();
            double distY = searchStation.getLatitude() - currentStation.getLatitude();

            double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));

            double actualDistance = (distance * 240) / 0.001705538;

            if (actualDistance < distanceRatio) {
                foundStations.add(currentStation);
            }
        });

        return foundStations;
    }

    public Station findClosestStations(Station searchStation, boolean take) {
        Station station = searchStation;
        double smallestDistance = 1000000000;

        for (Map.Entry<Integer, Station> entry : stations.entrySet()) {
            Station currentStation = entry.getValue();
            if (take) {
                if (currentStation.getBikeAmount() == 0) continue;
            } else {
                if (currentStation.getBikeAmount() == currentStation.getTotalPlaces()) continue;
            }

            double distX = station.getLongitude() - currentStation.getLongitude();
            double distY = station.getLatitude() - currentStation.getLatitude();

            double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));

            double actualDistance = (distance * 240) / 0.001705538;

            if (actualDistance < smallestDistance) {
                station = currentStation;
                smallestDistance = actualDistance;
            }

        }

        if (station.equals(searchStation)) {
            System.err.println("Not found ...");
        }

//        System.err.println("Address : " + station.getAddress());

        return station;
    }


    public void loadTourism() {
        places = new ArrayList<>();

        places.add(new KnownPlace("Notre Dame de Paris", 2.35, 48.853));
        places.add(new KnownPlace("Tour Eiffel", 2.298630399999979,48.8556475));
        places.add(new KnownPlace("Arc de Triomphe", 2.29502750000006, 48.8737917));
        places.add(new KnownPlace("Bastille", 2.3693362999999863, 48.8527753));
        places.add(new KnownPlace("Nation", 2.395909999999958, 48.848394));
        places.add(new KnownPlace("Concorde", 2.3212356999999884, 48.8656331));
        places.add(new KnownPlace("Centre Pompidou", 2.352245000000039, 48.860642));
        places.add(new KnownPlace("Isep", 2.328134900000009, 48.8453849));
        places.add(new KnownPlace("La defense", 2.241842799999972, 48.8897359));

        places.add(new KnownPlace("Montparnasse", 2.3203779000000395, 48.8410203));
        places.add(new KnownPlace("Gare du Nord", 2.3553137000000106, 48.8809481));
        places.add(new KnownPlace("Gare de Lyon", 2.374377299999992, 48.84430380000001));
        places.add(new KnownPlace("Gare de l'Est", 2.3592853000000105, 48.876779));
        places.add(new KnownPlace("Gare d'Austerlitz", 2.3661160000000336, 48.841748));



    }


    public void loadRoads() {
        System.out.println("Loading roads ....");
        BufferedReader roadsReader;
        try {
            roadsReader = OpenDataConfig.getRequest("https://opendata.paris.fr/api/records/1.0/search/?dataset=voie&rows=6440");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String inputLine;

        StringBuilder stringBuffer;
        try {
            System.out.println("Read line roads : ");
            stringBuffer = new StringBuilder();

            while ((inputLine = roadsReader.readLine()) != null) {
                stringBuffer.append(inputLine);
            }

            System.out.println("String length = " + stringBuffer.toString().length());
//            System.out.println(stringBuffer.toString());
            roadsReader.close();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject object = (JSONObject) jsonParser.parse(stringBuffer.toString());

            JSONArray records = (JSONArray) object.get("records");

            for (int it = 0; it < 6440; it++) {

                JSONObject currentReccord = (JSONObject) records.get(it);

                Road road = manageRoad(currentReccord);

                roads.add(road);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    public Road manageRoad(JSONObject record) {
        JSONObject fields = (JSONObject) record.get("fields");
        JSONObject geom = (JSONObject) fields.get("geom");

        JSONArray coordinatesArray = (JSONArray) geom.get("coordinates");

        String type = (String) geom.get("type");

        String name = (String) fields.get("l_courtmin");


        Road road = new Road();
        road.setName(name);

        if ("LineString".equals(type)) {
            road.setLines(coordinates(coordinatesArray));
        } else if ("MultiLineString".equals(type)) {
            List<Point[]> points = new ArrayList<>();

            for (Object currentLine : coordinatesArray.toArray()) {
                JSONArray currentRow = (JSONArray) currentLine;
                points.addAll(coordinates(currentRow));
            }
            road.setLines(points);

        } else {
            return null;
        }


        return road;
    }

    public List<Point[]> coordinates(JSONArray array) {
        List<Point[]> points = new ArrayList<>();

        Point point1 = null;
        Point point2 = null;
        for (int it = 0; it < array.size() - 1; it++) {
            if (it != array.size() - 1) {
                JSONArray line1 = (JSONArray) array.get(it);
                point1 = new Point((double) line1.get(0), (double) line1.get(1));
            }

            if (it != 0) {
                JSONArray line2 = (JSONArray) array.get(it + 1);
                point2 = new Point((double) line2.get(0), (double) line2.get(1));
            }

            if (it > 0 && point1 != null) {
                points.add(new Point[]{point1, point2});
            }
        }

        return points;
    }
















}
