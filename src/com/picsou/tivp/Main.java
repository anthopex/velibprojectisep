package com.picsou.tivp;

import com.picsou.tivp.entities.City;
import com.picsou.tivp.entities.Station;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static double tripRatio;
    public static double userRatio;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        do {
            System.out.printf("Trip ratio : ");
            tripRatio = scanner.nextDouble();
//            tripRatio = 1;
        } while (tripRatio <= 0);


        do {
            System.out.printf("User ratio : ");
            userRatio = scanner.nextDouble();
//            userRatio = 0;
        } while (tripRatio < 0 && tripRatio > 100);









        try {
            AppGameContainer appGameContainer = new AppGameContainer(new UserInterface());
            appGameContainer.setDisplayMode(UserInterface.WIDTH, UserInterface.HEIGHT, false);
            appGameContainer.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }



    public static City loadParis(DatabaseConfig databaseConfig) {
        List<Station> stations = databaseConfig.getParisStation();


        City paris = new City("Paris", 48.84681513232972, 2.376929360592796);


        Map<Integer, Station> stationMap = new HashMap<>();

        stations.forEach(station -> {
            stationMap.put(station.getId(), station);
        });

        paris.setStations(stationMap);


        final int[] totalNeighbord = {0};
        paris.getStations().forEach((id, station) -> {
            station.setNeighbords(paris.findNearestStations(station, 300));
            totalNeighbord[0] += station.getNeighbordsAmount();
        });
        System.out.println("There is " + (totalNeighbord[0] / (float) paris.getStations().size() + " neighbords in average"));




        paris.loadTourism();
        paris.loadRoads();

        return paris;
    }

}
