package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Main {

    public static void main(String [] args) throws Exception {
        getConnection();
        get();

    }

    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost/velibtest";
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
            PreparedStatement statement = con.prepareStatement("SELECT nb,lat,lng FROM stations");

            ResultSet rs = statement.executeQuery();

            ArrayList Resultat = new ArrayList();

            while(rs.next()){
                Resultat.add(rs.getInt("nb"));
                Resultat.add(rs.getFloat("lat"));
                Resultat.add(rs.getFloat("lng"));
            }

            System.out.println("All records have been selected !");


        } catch(Exception e){System.out.println(e);}
        return null;
    }


    public double convertRad(double degre) { return (Math.PI * degre)/180;}



    /* public double DistanceTo(double latA, double lonA) {
        double R = 6378000; // Rayon de la terre en Mètres
        latA = convertRad(latA);
        lonA = convertRad(lonA);
        double latB = convertRad(latitude);
        double lonB = convertRad(longitude);
        double d = R * (Math.PI/2 - asin( sin(latB) * sin(latA) + cos(lonB - lonA) * cos(latB) * cos(latA)));
        return d;
    } */

}






/* Stations stations = new Stations(rs.getInt("nb"), rs.getFloat("lat"), rs.getFloat("lng"));
                Resultat.add(stations); */

                /* System.out.print(rs.getInt("nb")+"\t");
                System.out.print(rs.getFloat("lat")+"\t");
                System.out.print(rs.getFloat("lng")+"\t"); */

           /* System.out.print("List of stations : ");
            Iterator itr = Resultat.iterator();

            while(itr.hasNext()) {
                float element = (float) itr.next();
                System.out.print(element + " ");
            }
            System.out.println(); */