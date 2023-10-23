package com.synergisticit.service;

import com.synergisticit.domain.Hotel;
import com.synergisticit.domain.Review;
import com.synergisticit.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;

    public List<Hotel> findHotel (String  searchString) {
        searchString = "%" + searchString + "%";
        return hotelRepository.findByHotelNameLikeOrCityLikeOrStateLike(searchString, searchString, searchString);
    }

    public Hotel updateHotelReview(Integer hotelId, Review review) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            Hotel updatedHotel = hotel.get();
            List<Review> reviews = updatedHotel.getReviews();
            reviews.add(review);
            updatedHotel.setReviews(reviews);
            return hotelRepository.save(updatedHotel);
        }
        return null;
    }

    public Hotel findHotelById(Integer hotelId) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        if (hotelOptional.isPresent()) {
            return hotelOptional.get();
        }
        return null;
    }
}
