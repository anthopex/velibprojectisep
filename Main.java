package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math.*;

public class Main {

    static ArrayList<Station> stations = new ArrayList<>();

    public static void main(String [] args) throws Exception {
        getConnection();
        get();
        getTabStation();
        tableset();


//        for (int i = 0; i<stations.size();i++){
//            for (int j = i+1; j<stations.size();j++){
//                stations.get(i).trouverVoisin(stations.get(j));
//            }
//            System.out.println(stations.get(i).voisins);
//        }
    }

    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost/velib";
            String username = "root";
            String password = "";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");

            return conn;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    private static ArrayList<Station> get() throws Exception{
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT nb,lat,lng,ststate_freebk,totbs FROM stationsadresses");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("nb");
                double lat = rs.getDouble("lat");
                double lng = rs.getDouble("lng");
                int velos = rs.getInt("ststate_freebk");
                int maxBike = rs.getInt("totbs");

                Station station = new Station();
                station.setId(id);
                station.setLatitude(lat);
                station.setLongitude(lng);
                station.setMaxBike(maxBike);
                station.lesvelo.add(velos);
                stations.add(station);
            }
            statement = con.prepareStatement("SELECT id,reason,out_date,out_station,in_date,in_station FROM trips");
            rs = statement.executeQuery();
            while(rs.next()) {
                double timeout = rs.getDouble("out_date")%1000000;
                double timein = rs.getDouble("in_date")%1000000;
                for (int i =0; i<stations.size();i++){
                    Station currentstation = stations.get(i);
                    if(currentstation.getId()==rs.getInt("out_station")){
                        currentstation.departs.add(timeout%100 + ((timeout - timeout%100)%10000) * 60/100 + ((timeout - timeout%10000)%1000000)*3600/10000);
                    }
                    if(currentstation.getId()==rs.getInt("in_station")){
                        currentstation.arrivees.add(timein%100 + ((timein - timein%100)%10000) * 60/100 + ((timein - timein%10000)%1000000)*3600/10000);
                    }
                }
            }
            System.out.println("All stations have been selected !");
        } catch(Exception e){System.out.println(e);}
        return stations;
    }
    private static void getTabStation (){
        int trajetsuprtot = 0;
        for (int k = 0; k<stations.size();k++) {
            Station station = stations.get(k);
            int velos = station.lesvelo.get(0);
            int count = 0;
            int trajetsupr = 0;
            ArrayList<Double> ts = new ArrayList<Double>();
            int i = 0;
            int j = 0;
            while (station.arrivees.size() > j && station.departs.size() > i) {
                if (station.departs.get(i) > station.arrivees.get(j)) {
                    if (velos < station.maxBike) {
                        velos += 1;
                        station.lesvelo.add(velos);
                        ts.add(station.arrivees.get(j));
                        if (velos > 0.9 * station.maxBike && count != 1) {
                            station.isProblematic = true;
                            count = 1;
                            station.tsproblem.add(count);
                            station.tsproblem.add(station.arrivees.get(j));
                        }
                        if (velos > 0.1 * station.maxBike && count == 2) {
                            count = 0;
                            station.tsproblem.add(count);
                            station.tsproblem.add(station.arrivees.get(j));
                        }
                    } else {
                        trajetsupr++;
                    }
                    j++;
                } else {
                    if (velos > 0) {
                        velos += -1;
                        station.lesvelo.add(velos);
                        ts.add(station.departs.get(i));
                        if (velos < 0.1 * station.maxBike && count != 2) {
                            station.isProblematic = true;
                            count = 2;
                            station.tsproblem.add(count);
                            station.tsproblem.add(station.departs.get(i));
                        }
                        if (velos < 0.9 * station.maxBike && count == 1) {
                            count = 0;
                            station.tsproblem.add(count);
                            station.tsproblem.add(station.departs.get(i));
                        }
                    } else {
                        trajetsupr++;
                    }
                    i++;
                }
            }

            //        for (i = 0; i< lesvelo.size();i++){
            //            System.out.println(lesvelo.get(i));
            //        }
            //        for (i = 0; i< lesvelo.size();i++){
            //            System.out.println(ts.get(i));
            //        }
            trajetsuprtot += trajetsupr;
        }
        System.out.println(trajetsuprtot);
    }
    private static void Problematic(){
        int count = 0;
        for (int i = 0; i<stations.size();i++){
            if (stations.get(i).isProblematic) {
                count ++;
            }
        }
    }
    private static void Problematic(Station station){
        for (int j = 0; j<station.lesvelo.size();j++) {
            System.out.println(station.lesvelo.get(j));
        }
    }
    private static void tableset(){
        ArrayList<Integer> tableau = new ArrayList<Integer>();

        for (int i = 0;i<96;i++){
            tableau.add(0);
        }
        for (int j = 0; j<stations.size();j++){
            int increment = 0;
            int count =0;
            for (int k = 1 ; k<stations.get(j).tsproblem.size();k+=2){
                while ((double)stations.get(j).tsproblem.get(k)>900*increment){
                    tableau.set(increment,tableau.get(increment)+count);
                    increment ++;
                }
                count = (count + 1)%2;
            }
        }
        for (int l = 0 ; l<tableau.size();l++){
            System.out.println(tableau.get(l));
        }

    }

}
