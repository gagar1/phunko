package com.joaquimley.smsparsing;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpConnect extends AsyncTask<Void, Void, String> {

    private static final String USER_AGENT = "Mozilla/5.0";

    private String GET_URL = "https://hackalco2018-bfa4.restdb.io/rest/sensor?x-apikey=65fca61a60d53f260e7c7b8687eff4a7d2c42";

    public String sendGET() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL obj = new URL(GET_URL);
        StringBuffer response = new StringBuffer();
        java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("x-apikey", "65fca61a60d53f260e7c7b8687eff4a7d2c42");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == java.net.HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        return response.toString();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            return sendGET();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}