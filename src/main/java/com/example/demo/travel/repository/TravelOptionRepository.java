package com.example.demo.travel.repository;

import com.example.demo.travel.entity.Airport;
import com.example.demo.travel.entity.Travel;
import com.example.demo.travel.entity.TravelOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravelOptionRepository extends JpaRepository<TravelOption, Long> {
    @Query("select to from TravelOption to where to.travel = :travel")
    List<TravelOption> findAllByTravel(Travel travel);
    @Query("select to from TravelOption to where to.travel.country=:country and to.travel.city=:city and to.travel.departureAirport=:airport")
    List<TravelOption> findOptionsByCountryAndCityAndAirport(String country, String city, Airport airport);
}
