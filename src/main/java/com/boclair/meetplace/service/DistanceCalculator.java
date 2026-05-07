package com.boclair.meetplace.service;

import org.springframework.stereotype.Component;

@Component
public class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.0088;

    public double distanceKm(double startLat, double startLon, double endLat, double endLon) {
        double latDistance = Math.toRadians(endLat - startLat);
        double lonDistance = Math.toRadians(endLon - startLon);
        double startLatRad = Math.toRadians(startLat);
        double endLatRad = Math.toRadians(endLat);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(startLatRad) * Math.cos(endLatRad)
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
