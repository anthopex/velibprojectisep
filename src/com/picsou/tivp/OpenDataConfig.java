package com.picsou.tivp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenDataConfig {

    public static BufferedReader getRequest(String request) throws IOException {
        System.out.println("Sending get to " + request + ", waiting ....");
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozzilla/5.0");
        System.out.println("Received " + connection.getResponseCode() + " from " + request);

        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }



}
