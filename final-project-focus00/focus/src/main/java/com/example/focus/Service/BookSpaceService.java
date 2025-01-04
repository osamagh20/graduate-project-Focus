package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.BookSpaceDTO;
import com.example.focus.DTO.BookSpaceDTOIn;
import com.example.focus.Model.BookSpace;
import com.example.focus.Model.Shift;
import com.example.focus.Repository.BookSpaceRepository;
import com.example.focus.Repository.PhotographerRepository;
import com.example.focus.Repository.ShiftRepository;
import com.example.focus.Repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSpaceService {

    private final BookSpaceRepository bookSpaceRepository;
    private final ShiftRepository shiftRepository;
    private final SpaceRepository spaceRepository;
    private final PhotographerRepository photographerRepository;

    public List<BookSpaceDTO> getAllBookings() {
        List<BookSpace> bookings = bookSpaceRepository.findAll();
        List<BookSpaceDTO> bookingDTOs = new ArrayList<>();
        for (BookSpace booking : bookings) {
            bookingDTOs.add(convertToDTO(booking));
        }
        return bookingDTOs;
    }

    public BookSpaceDTO getBookingById(Integer id) {
        BookSpace booking = bookSpaceRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        return convertToDTO(booking);
    }

    public List<BookSpaceDTO> photographerHistory(Integer photographerId) {
        List<BookSpace> bookings = bookSpaceRepository.findByPhotographerId(photographerId);
        List<BookSpaceDTO> bookingDTOs = new ArrayList<>();
        for (BookSpace booking : bookings) {
            bookingDTOs.add(convertToDTO(booking));
        }
        return bookingDTOs;
    }

    public BookSpaceDTO createSingleShiftBooking(BookSpaceDTOIn bookingDTOIn) {

        Shift fullDayShift = shiftRepository.findShiftBySpaceIdAndDateAndName(
                bookingDTOIn.getSpaceId(), bookingDTOIn.getDate().atStartOfDay(), "Full Day");
        Shift shift =shiftRepository.findShiftBySpaceIdAndDateAndName(bookingDTOIn.getSpaceId(),
                bookingDTOIn.getDate().atStartOfDay(),bookingDTOIn.getShiftName());

        if (bookSpaceRepository.findBookByShiftId(fullDayShift.getId())!=null) {
            throw new ApiException("this shift is not available");
        }
        if (shift.getStatus().equals("Unavailable")) {
            throw new ApiException("this shift is not available");
        }

        if ("Night Shift".equals(shift.getName()) || "Morning Shift".equals(shift.getName())) {

            fullDayShift.setStatus("Unavailable");
            shiftRepository.save(fullDayShift);
        }

        if ("Full Day".equals(shift.getName())) {
            Shift dayShift = shiftRepository.findShiftBySpaceIdAndDateAndName(
                    bookingDTOIn.getSpaceId(), bookingDTOIn.getDate().atStartOfDay(), "Morning Shift");
            Shift nightShift = shiftRepository.findShiftBySpaceIdAndDateAndName(
                    bookingDTOIn.getSpaceId(), bookingDTOIn.getDate().atStartOfDay(), "Night Shift");
            dayShift.setStatus("Unavailable");
            nightShift.setStatus("Unavailable");
            shiftRepository.save(dayShift);
            shiftRepository.save(nightShift);
        }


        shift.setStatus("Unavailable");
        shiftRepository.save(shift);

        BookSpace booking = createBookingEntity(bookingDTOIn, shift);
        return convertToDTO(bookSpaceRepository.save(booking));
    }

//    public BookSpaceDTO createFullDayBooking(BookSpaceDTOIn bookingDTOIn) {
//        Shift shift = shiftRepository.findShiftBySpaceIdAndDateAndName(bookingDTOIn.getSpaceId(), bookingDTOIn.getDate().atStartOfDay(),bookingDTOIn.getShiftName());
//
//        if ("Full Day".equals(shift.getName())) {
//            Shift dayShift = shiftRepository.findShiftBySpaceIdAndDateAndName(
//                    bookingDTOIn.getSpaceId(), bookingDTOIn.getDate().atStartOfDay(), "Day Shift");
//            Shift nightShift = shiftRepository.findShiftBySpaceIdAndDateAndName(
//                    bookingDTOIn.getSpaceId(), bookingDTOIn.getDate().atStartOfDay(), "Night Shift");
//            dayShift.setStatus("Unavailable");
//            nightShift.setStatus("Unavailable");
//            shiftRepository.save(dayShift);
//            shiftRepository.save(nightShift);
//        }
//
//        shift.setStatus("Unavailable");
//        shiftRepository.save(shift);
//
//        BookSpace booking = createBookingEntity(bookingDTOIn, shift);
//        return convertToDTO(bookSpaceRepository.save(booking));
//    }

    public BookSpaceDTO updateBookingStatus(Integer id, String status) {
        BookSpace booking = bookSpaceRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        booking.setStatus(status);

        if ("Cancelled".equals(status)) {
            Shift shift = booking.getShift();
            shift.setStatus("Available");
            shiftRepository.save(shift);
        }
        return convertToDTO(bookSpaceRepository.save(booking));
    }

    public void deleteBooking(Integer id) {
        BookSpace booking = bookSpaceRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        Shift shift = booking.getShift();
        if (shift != null) {
            shift.setStatus("Available");
            shiftRepository.save(shift);
        }
        bookSpaceRepository.delete(booking);
    }


    private BookSpace createBookingEntity(BookSpaceDTOIn bookingDTOIn, Shift shift) {
        BookSpace booking = new BookSpace();
        booking.setPhotographerId(bookingDTOIn.getPhotographerId());
        booking.setShift(shift);
        booking.setStatus("Confirmed");
        booking.setBookingPrice(shift.getPrice());
        booking.setSpace(shift.getSpace());
        return booking;
    }

    private BookSpaceDTO convertToDTO(BookSpace booking) {
        BookSpaceDTO dto = new BookSpaceDTO();
        dto.setId(booking.getId());
        dto.setShiftId(booking.getShift().getId());
        dto.setPhotographerId(booking.getPhotographerId());
        dto.setBookingPrice(booking.getBookingPrice());
        dto.setStatus(booking.getStatus());
        dto.setShiftName(booking.getShift().getName());
        dto.setShiftDate(booking.getShift().getDate().toString());
        dto.setShiftStartTime(booking.getShift().getStartTime().toLocalTime().toString());
        dto.setShiftEndTime(booking.getShift().getEndTime().toLocalTime().toString());
        dto.setSpaceId(booking.getShift().getSpace().getId());
        dto.setSpaceName(booking.getShift().getSpace().getName());
        return dto;
    }
}
