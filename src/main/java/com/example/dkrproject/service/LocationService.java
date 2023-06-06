package com.example.dkrproject.service;

import com.example.dkrproject.model.Location;
import com.example.dkrproject.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location getLocation(String location) {
        return locationRepository.findByName(location).stream().findFirst().orElse(null);
    }


    public Location save(Location location) {
        return locationRepository.save(location);
    }
}
