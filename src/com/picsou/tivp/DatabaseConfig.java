package com.picsou.tivp;

import com.picsou.tivp.entities.Station;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





public class DatabaseConfig {
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_ADDRESS = "jdbc:mysql://localhost:3306/theisepsvelibproject?useSSL=false";
    public static final int DB_PORT = 3306;
    public static final String DB_USER = "root";
    public static final String DB_PASSWD = "";

    public Connection getConnection()  {
        try {
//            Driver driver = (Driver) Class.forName(DB_DRIVER).newInstance();

            return DriverManager.getConnection(DB_ADDRESS, DB_USER, DB_PASSWD);
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database");
            e.printStackTrace();
            return null;
        } /*catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }*/
    }


    public ResultSet fetch(String request) {
        Connection connection = getConnection();

        if (connection == null) {
            System.err.println("Unable to connect to the database");
            return null;
        }

        PreparedStatement statement;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(request);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error while executing command : " + request);
            e.printStackTrace();
            return null;
        }

        return resultSet;
    }


    public List<Station> getParisStation() {
        ResultSet stationsQuery = fetch("SELECT * FROM stationsadresses WHERE ststate_state = 'open'");
        if (stationsQuery == null) {
            System.err.println("Error while fetching paris stations");
            return null;
        }

        List<Station> stations = new ArrayList<>();

        try {
            while (stationsQuery.next()) {

                int id = stationsQuery.getInt("nb");
                String address = stationsQuery.getString("addresse");
                int max = stationsQuery.getInt("totbs");

                double longitude = stationsQuery.getDouble("lng");
                double latitude = stationsQuery.getDouble("lat");

                int actualBikes = stationsQuery.getInt("ststate_ freebk");


                Station station = new Station(id, address, longitude, latitude, max);
                station.spawnBikes(actualBikes);

                stations.add(station);

            }
        } catch (SQLException e) {
            System.err.println("Error while turning stations query into stations pojos");
            e.printStackTrace();
            return null;
        }


        return stations;
    }


    public List<Trip> getTrips() {
        ResultSet tripsQuery = fetch("SELECT * from trips where out_station | in_station != 0 and id not in (select nb from stationsadresses WHERE ststate_state = 'closed')");
        if (tripsQuery == null) {
            System.err.println("Error while fetching trips");
            return null;
        }

        List<Trip> trips = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            while (tripsQuery.next()) {
                int id = tripsQuery.getInt("id");
                int reason = tripsQuery.getInt("reason");

                long out_date= tripsQuery.getLong("out_date");
                long in_date = tripsQuery.getLong("in_date");

                long outTimestamp = dateFormat.parse(String.valueOf(out_date)).getTime();
                long inTimestamp = dateFormat.parse(String.valueOf(in_date)).getTime();

                int out_station = tripsQuery.getInt("out_station");
                int in_station = tripsQuery.getInt("in_station");

                int out_bikestand = tripsQuery.getInt("out_bikestand");
                int in_bikestand = tripsQuery.getInt("in_bikestand");

                Trip trip = new Trip(id, reason, outTimestamp, out_station, out_bikestand, inTimestamp, in_station, in_bikestand);

                if (trip.getOut_station() == 0 || trip.getIn_station() == 0) {
                    System.err.println("Trip id " + trip.getId() + " is bugged");
                } else {
                    trips.add(trip);
                }
            }

            return trips;

        } catch (SQLException e) {
            System.err.println("Error while processing trips");
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            System.err.println("Error while parsing trip dates");
            e.printStackTrace();
            return null;
        }

    }



}
