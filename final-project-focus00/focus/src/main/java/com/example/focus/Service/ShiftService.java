package com.example.focus.Service;

import com.example.focus.DTO.ShiftDTO;
import com.example.focus.DTO.ShiftDTOIn;
import com.example.focus.Model.Shift;
import com.example.focus.Repository.ShiftRepository;
import com.example.focus.Repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final SpaceRepository spaceRepository;

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public Shift getShiftById(Integer id) {
        Shift shift = shiftRepository.findById(id).orElse(null);
        if (shift == null) {
            throw new IllegalArgumentException("Shift not found");
        }
        return shift;
    }

    public Shift createShift(ShiftDTOIn shiftDTOIn) {
        Shift shift = new Shift();
        shift.setName(shiftDTOIn.getName());
        shift.setSpace(spaceRepository.findSpaceById(shiftDTOIn.getSpaceId()));
        shift.setStartTime(shiftDTOIn.getStartTime());
        shift.setEndTime(shiftDTOIn.getEndTime());
        shift.setStatus("Available");
        return shiftRepository.save(shift);
    }

    public Shift updateShiftStatus(Integer id, String status) {
        Shift shift = shiftRepository.findById(id).orElse(null);
        if (shift == null) {
            throw new IllegalArgumentException("Shift not found");
        }
        shift.setStatus(status);
        return shiftRepository.save(shift);
    }

    public void deleteShift(Integer id) {
        Shift shift = shiftRepository.findById(id).orElse(null);
        if (shift == null) {
            throw new IllegalArgumentException("Shift not found");
        }
        shiftRepository.delete(shift);
    }

    public List<Shift> getShiftsBySpaceId(Integer spaceId) {
        return shiftRepository.findBySpaceId(spaceId);
    }

}
