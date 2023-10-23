package com.synergisticit.controller;

import com.itextpdf.io.IOException;
import com.synergisticit.domain.Booking;
import com.synergisticit.domain.Guest;
import com.synergisticit.domain.Review;
import com.synergisticit.service.BookingService;
import com.synergisticit.service.EmailService;
import com.synergisticit.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;
    @Autowired
    EmailService emailService;

    @Autowired
    private PdfService pdfGenerationService;

    @RequestMapping(value = "/saveGuests", method = RequestMethod.POST)
    public List<Guest> saveGuests(@RequestBody List<Guest> guests) {
        return bookingService.saveGuest(guests);
    }

    @RequestMapping(value = "/findGuestsById", method = RequestMethod.POST)
    public List<Guest> findGuests(@RequestBody List<Integer> ids) {
        return bookingService.findGuestsById(ids);
    }

    @RequestMapping(value = "/saveBooking", method = RequestMethod.POST)
    public Booking saveBooking(@RequestBody Booking booking) {
        String hotelname = booking.getHotelName();
        System.out.println();
        booking = bookingService.saveBooking(booking);
        booking.setHotelName(hotelname);
        emailService.sendEmail(booking);
        return booking;
    }

    @RequestMapping(value = "/getBookings/{username}", method = RequestMethod.GET)
    public List<Booking> getBookingByUserName(@PathVariable String username) {
        return bookingService.getBookingByUserName(username);
    }

    @RequestMapping(value = "/completeBookings", method = RequestMethod.POST)
    public void completeBooking(@RequestBody List<Integer> bookingIds) {
        bookingService.completeBookings(bookingIds, "COMPLETED");
    }

    @RequestMapping(value = "/cancelBooking/{bookingId}", method = RequestMethod.GET)
    public void cancelBooking(@PathVariable Integer bookingId) {
        bookingService.updateBooking(bookingId, "CANCELED");
    }
    @RequestMapping(value = "/updateBookingReview/{bookingId}", method = RequestMethod.POST)
    public Booking updateBookingReview(@RequestBody Review review, @PathVariable Integer bookingId) {
        return bookingService.updateBookingReview(bookingId, review);
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf() throws IOException, java.io.IOException {
        byte[] pdfBytes = pdfGenerationService.generateSimplePdf("Hello, iText PDF!");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("sample.pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
