package com.synergisticit.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.synergisticit.domain.Booking;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(Booking booking) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Context context = new Context();
        String greeting = "Thanks " + booking.getUserName() + "!" ;
        context.setVariable("greeting", greeting);
        context.setVariable("bookingId", "" + booking.getBookingId());
        context.setVariable("hotelname", booking.getHotelName());
        context.setVariable("checkin", booking.getCheckInDate());
        context.setVariable("numRooms", "" + booking.getNoRooms());
        context.setVariable("roomType", booking.getRoomType());
        context.setVariable("numGuests", "" + booking.getGuests().size());
        context.setVariable("checkout", booking.getCheckOutDate());
        context.setVariable("username", booking.getUserName());
        context.setVariable("date", booking.getBookedOnDate());
        context.setVariable("guests", booking.getGuests());
        context.setVariable("orderTotal", "$ " + booking.getFinalCharges());
        String htmlContent = templateEngine.process("email-template", context);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        ConverterProperties props = new ConverterProperties();
        HtmlConverter.convertToPdf(htmlContent, pdfDoc, props);
        pdfDoc.close();
        // Attach the PDF
        InputStreamSource attachment = new ByteArrayResource(outputStream.toByteArray());
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(booking.getUserEmail());
            helper.setSubject("Booking Confirmation");
            helper.setText(htmlContent, true);
            helper.addAttachment("invoice.pdf", attachment);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
