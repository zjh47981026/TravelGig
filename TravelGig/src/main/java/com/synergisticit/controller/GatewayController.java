package com.synergisticit.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synergisticit.component.BookingComponent;
import com.synergisticit.component.HotelComponent;
import com.synergisticit.domain.User;
import com.synergisticit.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class GatewayController {
    @Autowired
    HotelComponent hotelComponent;

    @Autowired
    BookingComponent bookingComponent;
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveUser(@RequestBody User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(hashedPassword);
        userService.save(user);
        String response = "signup success!";
        return response;
    }

    @RequestMapping(value = "/findHotel/{searchString}", method = RequestMethod.GET)
    public JsonNode findHotel(@PathVariable String searchString) {
        return hotelComponent.findHotel(searchString);
    }

    @RequestMapping(value = "/saveGuests", method = RequestMethod.POST)
    public JsonNode saveGuests(@RequestBody JsonNode guests) {
        return bookingComponent.saveGuests(guests);
    }

    @RequestMapping(value = "/findGuestsById", method = RequestMethod.POST)
    public JsonNode findGuestsById (@RequestBody JsonNode ids) {
        return bookingComponent.findGuestsById(ids);
    }

    @RequestMapping(value = "/saveBooking", method = RequestMethod.POST)
    public JsonNode saveBooking (@RequestBody JsonNode booking, Principal principal) {
        String userName = principal.getName();
        String email = userService.findByUserName(userName).getEmail();
        ((ObjectNode) booking).put("userName", userName);
        ((ObjectNode) booking).put("userEmail", email);
        ((ObjectNode) booking).put("taxRateInPercent", 8.5f);
        ((ObjectNode) booking).put("finalCharges", (float) booking.get("price").asDouble());
        ((ObjectNode) booking).put("bonanzaDiscount", 0.0f);
        ((ObjectNode) booking).put("totalSavings", (float) booking.get("discount").asDouble());

        return bookingComponent.saveBooking(booking);
    }

    @RequestMapping(value = "/getBookings", method = RequestMethod.GET)
    public JsonNode getBookings (Principal principal) {
        return bookingComponent.getBookings(principal.getName());
    }


    @RequestMapping(value = "/cancelBooking/{bookingId}", method = RequestMethod.GET)
    public JsonNode cancelBooking (@PathVariable Integer bookingId) {
        return bookingComponent.cancelBooking(bookingId);
    }

    @RequestMapping(value = "/completeBookings", method = RequestMethod.POST)
    public JsonNode completeBooking (@RequestBody JsonNode ids) {
        return bookingComponent.completeBooking(ids);
    }

    @RequestMapping(value = "/updateBookingReview/{bookingId}", method = RequestMethod.POST)
    public JsonNode updateBookingReview (@RequestBody JsonNode review, @PathVariable Integer bookingId) {
        return bookingComponent.updateBookingReview(review, bookingId);
    }


    @RequestMapping(value = "/updateHotelReview/{hotelId}", method = RequestMethod.POST)
    public JsonNode updateHotelReview (@RequestBody JsonNode review, @PathVariable Integer hotelId) {
        return hotelComponent.updateHotelReview(review, hotelId);
    }

    @RequestMapping(value = "/findHotelById/{hotelId}", method = RequestMethod.GET)
    public JsonNode findHotelById (@PathVariable Integer hotelId) {
        return hotelComponent.findHotelById(hotelId);
    }
}
