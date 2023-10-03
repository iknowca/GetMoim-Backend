package com.example.demo.travel.repository;

import com.example.demo.travel.entity.Airport;
import com.example.demo.travel.entity.Travel;
import com.example.demo.travel.entity.TravelOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    @Query("select distinct t.country from Travel t")
    List<String> findCountries();

    @Query("select distinct t.city from Travel t where t.country=:country")
    List<String> findCitiesByCountry(String country);

    @Query("select t from Travel t join fetch t.travelOptions where t.city = :city")
    Optional<Travel> findByCity(String city);

    @Query("select t.departureAirport from Travel t where t.country = :country and t.city = :city")
    List<String> findAirportsByCountryAndCity(String country, String city);

    @Query("select t from Travel t where t.city=:city and t.departureAirport=:departureAirport")
    Optional<Travel> findByCityAndAirPort(String city, Airport departureAirport);

    @Query("select t from Travel t left join fetch t.travelOptions where t.city=:city and t.departureAirport=:departureAirport and t.country=:country")
    Optional<Travel> findByCountryCityAirPort(String country,String city,Airport departureAirport);

    @Query("select t from Travel t left join fetch t.travelOptions where :travelOption member of t.travelOptions")
    Travel findByTravelOption(TravelOption travelOption);
}
