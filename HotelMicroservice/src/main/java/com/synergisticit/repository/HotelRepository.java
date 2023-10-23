package com.synergisticit.repository;

import com.synergisticit.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    public List<Hotel> findByHotelNameLikeOrCityLikeOrStateLike(String hotelName, String city, String state);
}
