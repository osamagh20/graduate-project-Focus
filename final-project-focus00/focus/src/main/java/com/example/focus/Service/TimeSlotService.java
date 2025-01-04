//package com.example.focus.Service;
//
//
//import com.example.focus.ApiResponse.ApiException;
//import com.example.focus.Model.TimeSlot;
//import com.example.focus.Model.Space;
//import com.example.focus.Repository.TimeSlotRepository;
//import com.example.focus.Repository.SpaceRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Service
//@RequiredArgsConstructor
//public class TimeSlotService {
//    private final TimeSlotRepository timeSlotRepository;
//    private final SpaceRepository spaceRepository;
//
//    public List<TimeSlot> getAllTimeSlots() {
//        return timeSlotRepository.findAll();
//    }
//
//    public void addTimeSlot(TimeSlot timeSlot) {
//        Space workSpace = workSpaceRepository.findWorkSpaceById(timeSlot.getWorkspaceId());
//        if (workSpace == null) {
//            throw new ApiException("*Work Space Not Found*");
//        }
//        timeSlotRepository.save(timeSlot);
//    }
//
//    public void updateTimeSlot(Integer id, TimeSlot timeSlot) {
//        TimeSlot oldTimeSlot = timeSlotRepository.findTimeSlotById(id);
//        Space workSpace = spaceRepository.findWorkSpaceById(timeSlot.getWorkspaceId());
//
//        if (workSpace == null) {
//            throw new ApiException("*Work Space Not Found*");
//        }
//        if (oldTimeSlot == null) {
//            throw new ApiException("*TimeSlot not found*");
//        }
//        if(oldTimeSlot.getIsBooked()) {
//            throw new ApiException("*TimeSlot is booked*");
//        }
//        oldTimeSlot.setStartDateTime(timeSlot.getStartDateTime());
//        oldTimeSlot.setEndDateTime(timeSlot.getEndDateTime());
//        timeSlotRepository.save(oldTimeSlot);
//    }
//
//    public void deleteTimeSlot(Integer timeSlotId) {
//        TimeSlot timeSlot = timeSlotRepository.findDateBookingById(timeSlotId);
//        if (timeSlot == null) {
//            throw new ApiException("*TimeSlot not found*");
//        }
//        if (timeSlot.getIsBooked()) {
//            throw new ApiException("*TimeSlot is booked*");
//        }
//        timeSlotRepository.deleteById(timeSlotId);
//    }
//
//    public List<TimeSlot> getAvailableTimeSlots(Integer workspaseId) {
//        return timeSlotRepository.findBySpaceIdAndIsBooked(workspaseId, false);
//    }
//
//}
