package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.City;
import com.soen342.sniffnjack.Entity.Location;
import com.soen342.sniffnjack.Exceptions.InvalidCityNameException;
import com.soen342.sniffnjack.Exceptions.InvalidLocationException;
import com.soen342.sniffnjack.Repository.CityRepository;
import com.soen342.sniffnjack.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/all")
    public Iterable<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @PostMapping("/add")
    public Location addLocation(@RequestBody Location location) throws InvalidCityNameException {
        City city = cityRepository.findByName(location.getCity().getName());
        if (city == null) {
            throw new InvalidCityNameException(location.getCity().getName());
        }
        location.setCity(city);
        return locationRepository.save(location);
    }

    @DeleteMapping("/delete")
    public void deleteLocation(@RequestParam Long id) throws InvalidLocationException {
        if (!locationRepository.existsById(id)) {
            throw new InvalidLocationException();
        }
        locationRepository.deleteById(id);
    }

    @PatchMapping("/update")
    public Location updateLocation(@RequestBody Location location) throws InvalidLocationException {
        if (!locationRepository.existsById(location.getId())) {
            throw new InvalidLocationException();
        }
        return locationRepository.save(location);
    }
}
