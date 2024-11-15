package com.soen342.sniffnjack.Controller;

import com.soen342.sniffnjack.Entity.City;
import com.soen342.sniffnjack.Exceptions.CityAlreadyExistsException;
import com.soen342.sniffnjack.Exceptions.CustomBadRequestException;
import com.soen342.sniffnjack.Exceptions.InvalidCityNameException;
import com.soen342.sniffnjack.Repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cities")
@Transactional
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/all")
    public Iterable<City> getAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/get")
    public City findCityByName(@RequestParam String name) throws InvalidCityNameException {
        City city = cityRepository.findByName(name);
        if (city == null) {
            throw new InvalidCityNameException(name);
        }
        return city;
    }

    @PostMapping("/add")
    public City addCity(@RequestParam String name) throws CityAlreadyExistsException {
        if (cityRepository.existsByName(name)) {
            throw new CityAlreadyExistsException(name);
        }
        return cityRepository.save(new City(name));
    }

    @PostMapping(value ="/addMultiple")
    public Iterable<City> addMultipleCities(@RequestParam List<String> names) throws Exception{
        getCityList(names, true);
        return cityRepository.saveAll(names.stream().map(City::new).toList());
    }

    @DeleteMapping("/delete")
    public void deleteCity(@RequestParam String name) throws InvalidCityNameException, CustomBadRequestException {
        if (!cityRepository.existsByName(name)) {
            throw new InvalidCityNameException(name);
        }
        try {
            cityRepository.deleteByName(name);
        } catch (Exception e) {
            throw new CustomBadRequestException("City is referenced by other entities");
        }
    }

    @DeleteMapping(value = "/deleteMultiple")
    public void deleteMultipleCities(@RequestParam List<String> names) throws Exception {
        List<City> cities = getCityList(names, false);
        cityRepository.deleteAll(cities);
    }

    @PatchMapping("/update")
    public City updateCity(@RequestParam String oldName, @RequestParam String newName) throws InvalidCityNameException, CityAlreadyExistsException {
        if (!cityRepository.existsByName(oldName)) {
            throw new InvalidCityNameException(oldName);
        }
        if (cityRepository.existsByName(newName)) {
            throw new CityAlreadyExistsException(newName);
        }
        City city = cityRepository.findByName(oldName);
        city.setName(newName);
        return cityRepository.save(city);
    }

    private List<City> getCityList(List<String> uncheckedCityList, boolean adding) throws Exception {
        List<City> cityList = uncheckedCityList.stream().map(city -> cityRepository.findByName(city)).filter(Objects::nonNull).toList();
        if (adding && !cityList.isEmpty())
            throw new CityAlreadyExistsException(cityList.stream().map(City::getName).toList());
        else if (!adding) {
            List<String> invalidACityNames = uncheckedCityList.stream().filter(name -> cityList.stream().noneMatch(city -> city.getName().equals(name))).toList();
            if (!invalidACityNames.isEmpty()) throw new InvalidCityNameException(invalidACityNames);
        }

        return cityList;
    }
}
