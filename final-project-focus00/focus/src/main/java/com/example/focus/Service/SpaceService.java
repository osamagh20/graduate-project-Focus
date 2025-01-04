package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.SpaceDTO;
import com.example.focus.DTO.SpaceDTOIn;
import com.example.focus.Model.Space;
import com.example.focus.Model.Shift;
import com.example.focus.Model.Studio;
import com.example.focus.Repository.SpaceRepository;
import com.example.focus.Repository.ShiftRepository;
import com.example.focus.Repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final ShiftRepository shiftRepository;
    private final StudioRepository studioRepository;

    public List<Space> getAllSpaces() {

        return spaceRepository.findAll();
    }

    public Space getSpaceById(Integer id) {
        Space space = spaceRepository.findSpaceById(id);
        if (space == null) {
            throw new IllegalArgumentException("Space not found");
        }
        return space;
    }

    public Space createSpace(Integer studioId,SpaceDTOIn spaceDTOIn) {
        Studio studio = studioRepository.findStudioById(studioId);
        if (studio == null) {
            throw new ApiException("Studio not found");
        }

        // Create and save the space
        Space space = new Space();
        space.setName(spaceDTOIn.getName());
        space.setType(spaceDTOIn.getType());
        space.setArea(spaceDTOIn.getArea());
        space.setDescription(spaceDTOIn.getDescription());
        space.setDayPrice(spaceDTOIn.getDayPrice());
        space.setFullDayPrice(spaceDTOIn.getFullDayPrice());
        space.setNightPrice(spaceDTOIn.getNightPrice());
        space.setImage(spaceDTOIn.getImage());
        space.setStatus("Available");
        space.setStudio(studioRepository.findStudioById(studioId));
        space = spaceRepository.save(space);

        // Automatically generate shifts for 1 year
        generateShiftsForSpace(space);

        return space;
    }

    public Space updateSpace(Integer id, SpaceDTOIn spaceDTOIn) {
        Space space = spaceRepository.findSpaceById(id);
        if (space == null) {
            throw new IllegalArgumentException("Space not found");
        }
        space.setName(spaceDTOIn.getName());
        space.setType(spaceDTOIn.getType());
        space.setArea(spaceDTOIn.getArea());
        space.setDescription(spaceDTOIn.getDescription());
        space.setNightPrice(spaceDTOIn.getNightPrice());
        space.setFullDayPrice(spaceDTOIn.getFullDayPrice());
        space.setDayPrice(spaceDTOIn.getDayPrice());
        space.setImage(spaceDTOIn.getImage());
        return spaceRepository.save(space);
    }

    public void deleteSpace(Integer id) {
        Space space = spaceRepository.findSpaceById(id);
        if (space == null) {
            throw new IllegalArgumentException("Space not found");
        }
        spaceRepository.delete(space);
    }

    public void updateSpaceStatus(Integer id, String status) {
        Space space = spaceRepository.findSpaceById(id);
        if (space == null) {
            throw new IllegalArgumentException("Space not found");
        }
        space.setStatus(status);
        spaceRepository.save(space);
    }

    private void generateShiftsForSpace(Space space) {
        List<Shift> shifts = new ArrayList<>();
        LocalDate startDate = LocalDate.now(); // Start from today
        LocalDate endDate = startDate.plusYears(1); // End 1 year later

        while (startDate.isBefore(endDate)) {
            // Create day shift (8 AM - 8 PM)
            Shift dayShift = new Shift();
            dayShift.setName("Morning Shift");
            dayShift.setDate(startDate.atStartOfDay()); // Use LocalDate for the date
            dayShift.setSpace(space); // Associate the shift with the space
            dayShift.setStartTime(startDate.atTime(8, 0)); // Set start time to 8 AM
            dayShift.setEndTime(startDate.atTime(15, 0)); // Set end time to 8 PM
            dayShift.setStatus("Available"); // Default status
            dayShift.setPrice(space.getDayPrice()); // Set price from space
            shifts.add(dayShift);

            // Create night shift ( PM - 8 AM next day)
            Shift nightShift = new Shift();
            nightShift.setName("Night Shift");
            nightShift.setDate(startDate.atStartOfDay()); // Use LocalDate for the date
            nightShift.setSpace(space);
            nightShift.setStartTime(startDate.atTime(15, 0)); // Set start time to 8 PM
            nightShift.setEndTime(startDate.atTime(20, 0)); // Set end time to 8 AM next day
            nightShift.setStatus("Available");
            nightShift.setPrice(space.getNightPrice()); // Set price from space
            shifts.add(nightShift);

            // Create full-day shift (8 AM - 8 AM next day)
            Shift fullDayShift = new Shift();
            fullDayShift.setName("Full Day");
            fullDayShift.setDate(startDate.atStartOfDay()); // Use LocalDate for the date
            fullDayShift.setSpace(space);
            fullDayShift.setStartTime(startDate.atTime(8, 0)); // Set start time to 8 AM
            fullDayShift.setEndTime(startDate.atTime(20, 0)); // Set end time to 8 AM next day
            fullDayShift.setStatus("Available");
            fullDayShift.setPrice(space.getFullDayPrice()); // Set full-day price
            shifts.add(fullDayShift);

            // Move to the next day
            startDate = startDate.plusDays(1);
        }

        // Save all generated shifts to the database
        shiftRepository.saveAll(shifts);
    }


}
