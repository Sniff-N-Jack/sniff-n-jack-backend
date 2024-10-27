package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.Activity;
import com.soen342.sniffnjack.Entity.City;
import com.soen342.sniffnjack.Exceptions.ActivityAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.CityAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.InvalidActivityNameException;
import com.soen342.sniffnjack.Exceptions.InvalidCityNameException;
import com.soen342.sniffnjack.Repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/all")
    public Iterable<City> getAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/get")
    public City findCityByName(@RequestParam String name) {
        return cityRepository.findByName(name);
    }

    @PostMapping("/add")
    public City addCity(@RequestParam String name) throws CityAlreadyExistsException {
        if (cityRepository.existsByName(name)) {
            throw new CityAlreadyExistsException(name);
        }
        return cityRepository.save(new City(name));
    }

    @PostMapping("/addMultiple")
    public Iterable<City> addMultipleCities(@RequestBody List<City> cities) throws Exception{
        getCityList(cities, true);
        return cityRepository.saveAll(cities);
    }

    @DeleteMapping("/delete")
    public void deleteCity(@RequestParam String name) throws InvalidCityNameException {
        if (!cityRepository.existsByName(name)) {
            throw new InvalidCityNameException(name);
        }
        cityRepository.deleteByName(name);
    }

    @DeleteMapping("/deleteMultiple")
    public void deleteMultipleCities(@RequestBody List<City> cities) throws Exception {
        cities = getCityList(cities, false);
        cityRepository.deleteAll(cities);
    }

    private List<City> getCityList(List<City> uncheckedCityList, boolean adding) throws Exception {
        List<City> cityList = uncheckedCityList.stream().map(city -> cityRepository.findByName(city.getName())).toList();
        if (adding && !cityList.isEmpty())
            throw new CityAlreadyExistsException(cityList.stream().map(City::getName).toList());
        else if (!adding) {
            List<String> invalidActivityNames = uncheckedCityList.stream().map(City::getName).filter(name -> cityList.stream().noneMatch(city -> city.getName().equals(name))).toList();
            if (!invalidActivityNames.isEmpty()) throw new InvalidCityNameException(invalidActivityNames);
        }

        return cityList;
    }
}
