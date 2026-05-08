package com.boclair.meetplace.service;

import com.boclair.meetplace.domain.PlaceCategory;

import java.util.List;

public interface PlaceSearchClient {

    List<ExternalPlace> search(
            PlaceCategory category,
            double centerLatitude,
            double centerLongitude,
            int limit
    );
}
