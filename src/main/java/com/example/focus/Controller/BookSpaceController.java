package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.BookSpaceDTO;
import com.example.focus.DTO.BookSpaceDTOIn;
import com.example.focus.Service.BookSpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/focus/book-space")
@RequiredArgsConstructor
public class BookSpaceController {

    private final BookSpaceService bookSpaceService;

    @GetMapping("/get-all")
    public ResponseEntity getAllBookings() {
        List<BookSpaceDTO> bookings = bookSpaceService.getAllBookings();
        return ResponseEntity.status(200).body(bookings);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getBookingById(@PathVariable Integer id) {
        BookSpaceDTO booking = bookSpaceService.getBookingById(id);
        return ResponseEntity.status(200).body(booking);
    }

    @GetMapping("/get-photographer-history/{photographerId}")
    public ResponseEntity getPhotographerHistory(@PathVariable Integer photographerId) {
        List<BookSpaceDTO> bookings = bookSpaceService.photographerHistory(photographerId);
        return ResponseEntity.status(200).body(bookings);
    }

    @PostMapping("/create-single-shift-booking")
    public ResponseEntity createSingleShiftBooking(@RequestBody @Valid BookSpaceDTOIn bookingDTOIn) {
        BookSpaceDTO booking = bookSpaceService.createSingleShiftBooking(bookingDTOIn);
        return ResponseEntity.status(200).body(booking);
    }
//
//    @PostMapping("/create-full-day-booking")
//    public ResponseEntity createFullDayBooking(@RequestBody @Valid BookSpaceDTOIn bookingDTOIn) {
//        BookSpaceDTO booking = bookSpaceService.createFullDayBooking(bookingDTOIn);
//        return ResponseEntity.status(200).body(booking);
//    }

    @PutMapping("/cancel-booking/{id}")
    public ResponseEntity CancelBooking(@PathVariable Integer id) {
        bookSpaceService.CancelBooking(id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Canceled successfully"));
    }

    @PutMapping("/update-booking-status/{id}")
    public ResponseEntity updateBookingStatus(@PathVariable Integer id, @RequestParam String status) {
        BookSpaceDTO updatedBooking = bookSpaceService.updateBookingStatus(id, status);
        return ResponseEntity.status(200).body(updatedBooking);
    }
}
