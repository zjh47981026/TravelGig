package com.synergisticit.repository;

import com.synergisticit.domain.Booking;
import com.synergisticit.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByUserName(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = ?2 WHERE b.bookingId = ?1")
    void updateBookingStatus(int id, String status);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = ?2 WHERE b.bookingId in ?1")
    void completeBookings(List<Integer> ids, String status);

}
