package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findAllByAddress(String address);
    List<Location> findAllByCityId(Long cityId);
    List<Location> findAllByCityName(String cityName);
    List<Location> findAllByRoom(String room);
    List<Location> findAllByRoomAndCityId(String room, Long cityId);
    List<Location> findAllByRoomAndCityName(String room, String cityName);
    List<Location> findAllByAddressAndCityId(String address, Long cityId);
    List<Location> findAllByAddressAndCityName(String address, String cityName);
    List<Location> findAllByAddressAndRoom(String address, String room);
    List<Location> findAllByAddressAndRoomAndCityId(String address, String room, Long cityId);
    List<Location> findAllByAddressAndRoomAndCityName(String address, String room, String cityName);
}
