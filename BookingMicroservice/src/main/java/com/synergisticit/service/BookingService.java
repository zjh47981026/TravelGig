package com.synergisticit.service;

import com.synergisticit.domain.Booking;
import com.synergisticit.domain.Guest;
import com.synergisticit.domain.Review;
import com.synergisticit.repository.BookingRepository;
import com.synergisticit.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    BookingRepository bookingRepository;
    public List<Guest> saveGuest(List<Guest> guests) {
        return guestRepository.saveAll(guests);
    }

    public List<Guest> findGuestsById(List<Integer> ids) {
        return guestRepository.findAllById(ids);
    }

    public Booking saveBooking(Booking booking) {

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingByUserName(String username) {
        return bookingRepository.findAllByUserName(username);
    }

    public void updateBooking(int id, String status) {
        bookingRepository.updateBookingStatus(id, status);
    }

    public void completeBookings(List<Integer> ids, String status) {
        bookingRepository.completeBookings(ids, status);
    }


    public Booking updateBookingReview(Integer id, Review review) {
       Optional<Booking> booking = bookingRepository.findById(id);
       if (booking.isPresent()) {
           Booking updatedBooking = booking.get();
           updatedBooking.setReview(review);
           return bookingRepository.save(updatedBooking);
       }
       return null;
    }
}
