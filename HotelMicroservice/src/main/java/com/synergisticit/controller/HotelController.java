package com.synergisticit.controller;

import com.synergisticit.domain.Hotel;
import com.synergisticit.domain.Review;
import com.synergisticit.service.HotelService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HotelController {
    @Autowired
    HotelService hotelService;

    @RequestMapping(value = "/searchHotel/{searchString}", method = RequestMethod.GET)
    public List<Hotel> searchHotel(@PathVariable  String searchString) {
        return hotelService.findHotel(searchString);
    }

    @RequestMapping(value = "/updateHotelReview/{hotelId}", method = RequestMethod.POST)
    public Hotel updateHotelReview(@PathVariable Integer hotelId, @RequestBody Review review) {
        return hotelService.updateHotelReview(hotelId, review);
    }

    @RequestMapping(value = "/findHotelById/{hotelId}", method = RequestMethod.GET)
    public Hotel findHotelById(@PathVariable  Integer hotelId) {
        return hotelService.findHotelById(hotelId);
    }
}
