package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math.*;

public class Main {

    static ArrayList<Stations> stations = new ArrayList<>();

    public static void main(String [] args) throws Exception {
        getConnection();
        get();
}

    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost/velibprojectisep";
            String username = "root";
            String password = "password";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");

            return conn;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static ArrayList<Stations> get() throws Exception{
        try {
            Connection con = getConnection();

            PreparedStatement statement = con.prepareStatement("SELECT id,lat,lng FROM stationsadresses");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                double lat = rs.getDouble("lat");
                double lng = rs.getDouble("lng");

                Stations station = new Stations();
                station.setId(id);
                station.setLatitude(lat);
                station.setLongitude(lng);

                stations.add(station);
            }
            System.out.println("All stations have been selected !");

        } catch(Exception e){System.out.println(e);}
        return stations;
    }

    public void trouverVoisin(Stations stationb) {
        // JE SAIS PLUS QUOI METTRE ICI POUR LUTILISER DANS LE MAIN
    }
}
