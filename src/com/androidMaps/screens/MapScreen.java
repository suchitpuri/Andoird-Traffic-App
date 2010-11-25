package com.androidMaps.screens;


import android.os.Bundle;
import android.util.Log;
import com.android.R;
import com.androidMaps.utilities.DirectionPathOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MapScreen extends MapActivity {
    private GeoPoint geoPoint;
    private MapController myMC;
    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        geoPoint = null;
        mapView.setSatellite(false);

        String pairs[] = getDirectionData("ahmedabad", "vadodara");
        String[] lngLat = pairs[0].split(",");

        // STARTING POINT
        GeoPoint startGP = new GeoPoint(
                (int) (Double.parseDouble(lngLat[1]) * 1E6), (int) (Double
                        .parseDouble(lngLat[0]) * 1E6));

        myMC = mapView.getController();
        geoPoint = startGP;
        myMC.setCenter(geoPoint);
        myMC.setZoom(15);
        mapView.getOverlays().add(new DirectionPathOverlay(startGP, startGP));

        // NAVIGATE THE PATH

        GeoPoint gp1;
        GeoPoint gp2 = startGP;

        for (int i = 1; i < pairs.length; i++) {
            lngLat = pairs[i].split(",");
            gp1 = gp2;
            // watch out! For GeoPoint, first:latitude, second:longitude

            gp2 = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6),
                    (int) (Double.parseDouble(lngLat[0]) * 1E6));
            mapView.getOverlays().add(new DirectionPathOverlay(gp1, gp2));
            Log.d("xxx", "pair:" + pairs[i]);
        }

        // END POINT
        mapView.getOverlays().add(new DirectionPathOverlay(gp2, gp2));

        mapView.getController().animateTo(startGP);
        mapView.setBuiltInZoomControls(true);
        mapView.displayZoomControls(true);

    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    private String[] getDirectionData(String source, String destination) {

        String urlString = "http://maps.google.com/maps?f=d&hl=en&saddr="
                + source + "&daddr=" + destination
                + "&ie=UTF8&0&om=0&output=kml";
        Document doc = null;
        HttpURLConnection urlConnection = null;
        URL url = null;
        String pathConect = "";

        try {

            url = new URL(urlString.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(urlConnection.getInputStream());

        } catch (SAXException e) {
            Log.d("error", e.getMessage());
        } catch (ProtocolException e) {
            Log.d("error", e.getMessage());
        } catch (ParserConfigurationException e) {
            Log.d("error", e.getMessage());
        } catch (MalformedURLException e) {
            Log.d("error", e.getMessage());
        } catch (IOException e) {
            Log.d("error", e.getMessage());
        }

        NodeList nl = doc.getElementsByTagName("LineString");
        for (int s = 0; s < nl.getLength(); s++) {
            Node rootNode = nl.item(s);
            NodeList configItems = rootNode.getChildNodes();
            for (int x = 0; x < configItems.getLength(); x++) {
                Node lineStringNode = configItems.item(x);
                NodeList path = lineStringNode.getChildNodes();
                pathConect = path.item(0).getNodeValue();
            }
        }
        String[] tempContent = pathConect.split(" ");
        return tempContent;
    }

}


