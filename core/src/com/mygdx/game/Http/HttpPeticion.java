package com.mygdx.game.Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Cesar on 28/11/2017.
 */

public class HttpPeticion {

    public static final String server="http://localhost:8080/";

    public static String peticion(String strUrl) throws MalformedURLException, IOException {
        URL url = new URL(server+strUrl);
        URLConnection con = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String result="";
        String linea;
        while ((linea = in.readLine()) != null) {
            System.out.println(linea);
            result +=linea;
        }
        return result;
    }
}
