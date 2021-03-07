package edu.wpi.cs3733.teamO.Maps;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import java.io.IOException;
import java.util.ArrayList;

// Reference
// PlaceDetailsRequest query = PlacesApi.placeDetails(new
// GeoApiContext.Builder().apiKey(API_KEY).build(), prediction.getPrediction().placeId);
// API KEY "AIzaSyBXXka_F4Sk_WZ7pdCcS-zNatD8oLoLwJ0"

public class Directions {

  public static void main(String[] args) {
    getDirections();
  }

  private static final String API_KEY = "AIzaSyBXXka_F4Sk_WZ7pdCcS-zNatD8oLoLwJ0";

  public static void getDirections() {

    DirectionsApiRequest dirReq =
        DirectionsApi.getDirections(
            new GeoApiContext.Builder().apiKey(API_KEY).build(),
            "100 Institute Rd. Worcester MA, 01609",
            "182 Russel Street Worcester MA, 01609");

    dirReq.departureTimeNow();
    dirReq.units(Unit.IMPERIAL);
    dirReq.mode(TravelMode.DRIVING);

    DirectionsResult result = null;
    DirectionsStep dStep = new DirectionsStep();
    ArrayList<DirectionsLeg> legs = new ArrayList<>();

    try {
      result = dirReq.await();
    } catch (ApiException e) {
      e.printStackTrace();
      System.out.println("HELP");
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.out.println("HELP1");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("HELP2");
    }

    assert result != null;

    DirectionsRoute[] route = result.routes;
    ArrayList<LatLng> pointsToDestination = new ArrayList<LatLng>();

    for (int i = 0; i < route.length; i++) {

      Bounds b = route[i].bounds;
      DirectionsLeg[] currentLegs = route[i].legs;
      LatLng l1 = b.northeast;
      LatLng l2 = b.southwest;

      double lat1 = l1.lat;
      double lng1 = l1.lng;
      double lat2 = l2.lat;
      double lng2 = l2.lng;

      LatLng center = new LatLng((lat1 + lat2) / 2, (lng1 + lng2) / 2);

      for (DirectionsLeg dl : currentLegs) {
        legs.add(dl);
      }

      pointsToDestination.add(center);
    }
    ArrayList<DirectionsStep> directionsSteps = new ArrayList<>();

    int i = 0;
    // leg of current directionroute
    for (DirectionsLeg dl : legs) {

      for (int j = 0; j < dl.steps.length; j++) {

        directionsSteps.add(dl.steps[j]);
      }
    for (DirectionsRoute r : route) {
      System.out.println(r.overviewPolyline.getEncodedPath());
      System.out.println(decodePoly(r.overviewPolyline.getEncodedPath()));
    }

    System.out.println("Steps: \n");
    int stepcount = 0;

    for (DirectionsStep s : directionsSteps) {
      System.out.println("Step " + stepcount + ": " + html2text(s.htmlInstructions));
      stepcount++;
    }

    System.out.println("Ended Steps");
  }

  public static String html2text(String html) {

    boolean legit = true;
    char[] charA = html.toCharArray();
    String noHtml = "";

    int count = 0;
    for (char c : charA) {

      if (c == '<') {

        legit = false;

        if (charA[count + 1] == 'd') {
          noHtml += "\n";
        }

      } else if (c == '>') {
        legit = true;

      } else if (legit) {
        noHtml += c;
      }

      count++;
    }
    return noHtml;
  }

  private static List<LatLng> decodePoly(String encoded) {

    List<LatLng> poly = new ArrayList<LatLng>();
    int index = 0, len = encoded.length();
    int lat = 0, lng = 0;

    while (index < len) {
      int b, shift = 0, result = 0;
      do {
        b = encoded.charAt(index++) - 63;
        result |= (b & 0x1f) << shift;
        shift += 5;
      } while (b >= 0x20);
      int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
      lat += dlat;

      shift = 0;
      result = 0;
      do {
        b = encoded.charAt(index++) - 63;
        result |= (b & 0x1f) << shift;
        shift += 5;
      } while (b >= 0x20);
      int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
      lng += dlng;

      LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
      poly.add(p);
    }

    return poly;
  }
}
