package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.*;
import com.example.focus.Model.*;
import com.example.focus.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PhotographerService {
    private final MyUserRepository myUserRepository;
    private final PhotographerRepository photographerRepository;
    private final ProfilePhotographerRepository profilePhotographerRepository;
    private final MyOrderRepository myOrderRepository;
    private final RentToolsRepository rentToolsRepository;
    private final ToolRepository toolRepository;
    private final StudioRepository studioRepository;


    public List<PhotographerDTO> getAllPhotographers() {
        List<Photographer> photographers = photographerRepository.findAll();
        List<PhotographerDTO> photographerDTOS = new ArrayList<>();

        for (Photographer photographer : photographers) {
            PhotographerDTO photographerDTO = new PhotographerDTO(
                    photographer.getName(),
                    photographer.getMyUser().getUsername(),
                    photographer.getMyUser().getEmail(),
                    photographer.getCity(),
                    photographer.getPhone()
            );
            photographerDTOS.add(photographerDTO);
        }
        return photographerDTOS;
    }


    public void PhotographerRegistration(PhotographerDTOin photographerDTOin) {
       // String hashPass=new BCryptPasswordEncoder().encode(photographer.getPassword());

        MyUser checkUsername =myUserRepository.findMyUserByUsername(photographerDTOin.getUsername());
        MyUser checkEmail =myUserRepository.findMyUserByEmail(photographerDTOin.getEmail());
        if(checkUsername != null){
            throw new ApiException("Username already exists");
        }
        if(checkEmail != null){
            throw new ApiException("email already exists");
        }

        MyUser user = new MyUser();
        user.setUsername(photographerDTOin.getUsername());
        user.setEmail(photographerDTOin.getEmail());
        user.setPassword(photographerDTOin.getPassword());
        user.setRole("PHOTOGRAPHER");
        myUserRepository.save(user);

        Photographer photographer=new Photographer();
        photographer.setMyUser(user);
        photographer.setName(photographerDTOin.getName());
        photographer.setCity(photographerDTOin.getCity());
        photographer.setPhone(photographerDTOin.getPhoneNumber());
        photographerRepository.save(photographer);

        ProfilePhotographer profilePhotographer = new ProfilePhotographer();
        profilePhotographer.setMyUser(user);
        profilePhotographer.setNumberOfPosts(0);
         profilePhotographerRepository.save(profilePhotographer);

    }



    public void updatePhotographer(Integer id, PhotographerDTOin photographerDTOin) {
        Photographer existingPhotographer = photographerRepository.findPhotographersById(id);
        if (existingPhotographer != null) {
            existingPhotographer.setName(photographerDTOin.getName());
            existingPhotographer.setCity(photographerDTOin.getCity());
            existingPhotographer.getMyUser().setUsername(photographerDTOin.getUsername());
            existingPhotographer.getMyUser().setEmail(photographerDTOin.getEmail());
            existingPhotographer.setPhone(photographerDTOin.getPhoneNumber());
        }else {
            throw new ApiException("Photographer Not Found");
        }
        photographerRepository.save(existingPhotographer);

    }


    public void deletePhotographer(Integer id) {
  MyUser myUser=myUserRepository.findMyUserById(id);
    if(myUser!=null) {
    myUserRepository.delete(myUser);
    }else{
    throw new ApiException("Photographer Not Found");
     }
        }

    public void rentToolRequest(Integer photographer_id, Integer tool_id, RentToolsDTOIn rentTool) {
        Photographer photographer = photographerRepository.findPhotographersById(photographer_id);
        if(photographer==null) {
            throw new ApiException("photographer not found");
        }

        Tool tool = toolRepository.findToolById(tool_id);
        if(tool==null) {
            throw new ApiException("tool not found");
        }
        if (photographer_id.equals(tool.getPhotographer().getId())) {
            throw new ApiException("The photographer is the owner of the required tool.");
        }


        for (RentTools existingRent : rentToolsRepository.findAll()) {
            for(int i=0;i<rentToolsRepository.findAll().size();i++) {
                if (tool_id.equals(rentToolsRepository.findAll().get(i).getId()))  {
                    if (!(rentTool.getEndDate().isBefore(existingRent.getStartDate()) || rentTool.getStartDate().isAfter(existingRent.getEndDate()))) {
                        throw new ApiException("This tool is rented now for another photographer.");
                    }
                }
            }

        }

        long daysBetween = ChronoUnit.DAYS.between(rentTool.getStartDate(), rentTool.getEndDate());
        Double totalPrice = tool.getRentalPrice()*daysBetween;
        rentTool.setRentPrice(totalPrice);

        RentTools rentTools = new RentTools();
        rentTools.setStartDate(rentTool.getStartDate());
        rentTools.setEndDate(rentTool.getEndDate());
        rentTools.setOwner(tool.getPhotographer());
        rentTools.setRenter(photographer);
        rentTools.setRentPrice(totalPrice);
        rentTools.setTool(tool);
        rentToolsRepository.save(rentTools);

    }

    // view rent tools for photographer
    public List<ToolDTO> viewMyRentTools(Integer photographer_id) {
        Photographer photographer = photographerRepository.findPhotographersById(photographer_id);
        if(photographer==null) {
            throw new ApiException("photographer not found");
        }
        List<ToolDTO> toolDTOS = new ArrayList<>();
        List<RentTools> rentTools = rentToolsRepository.findAll();
        for (RentTools rentTool : rentTools) {
            if (rentTool.getRenter().getId().equals(photographer.getId())) {
                Tool tool = rentTool.getTool();
                ToolDTO toolDTO = new ToolDTO(tool.getName(),tool.getDescription(),tool.getCategory(),tool.getBrand(),tool.getToolCondition(),tool.getRentalPrice(),tool.getImageURL(),tool.getPhotographer().getId());
                toolDTOS.add(toolDTO);
                return toolDTOS;
            }
        }
        throw new ApiException("photographer not have rent tools");

    }

    // view rental tools photographer rent from owner
    public List<ToolDTO> viewRentalTools(Integer photographer_id) {
        Photographer photographer = photographerRepository.findPhotographersById(photographer_id);
        if(photographer==null) {
            throw new ApiException("photographer not found");
        }

        List<ToolDTO> toolDTOS = new ArrayList<>();
        List<RentTools> rentTools = rentToolsRepository.findAll();
        for (RentTools rentTool : rentTools) {
            if (rentTool.getOwner().getId().equals(photographer.getId())) {
                Tool tool = rentTool.getTool();
                ToolDTO toolDTO = new ToolDTO(tool.getName(),tool.getDescription(),tool.getCategory(),tool.getBrand(),tool.getToolCondition(),tool.getRentalPrice(),tool.getImageURL(),tool.getPhotographer().getId());
                toolDTOS.add(toolDTO);
                return toolDTOS;
            }
        }
        throw new ApiException("photographer not have rental tools");

    }

    public List<StudioDTO> getStudiosByCity(Integer photographer_id, String city) {
        Photographer photographer = photographerRepository.findPhotographersById(photographer_id);
        if(photographer==null) {
            throw new ApiException("photographer not found");
        }
        List<Studio> studios = studioRepository.findStudioByCity(city);
        if(studios==null) {
            throw new ApiException("not found studio in this city");
        }

        List<StudioDTO> studioDTOS = new ArrayList<>();
        for (Studio studio : studios) {
            StudioDTO studioDTO1 = new StudioDTO();
            studioDTO1.setName(studio.getName());
            studioDTO1.setUsername(studio.getMyUser().getUsername());
            studioDTO1.setPhoneNumber(studio.getPhoneNumber());
            studioDTO1.setEmail(studio.getMyUser().getEmail());
            studioDTO1.setAddress(studio.getAddress());
            studioDTO1.setStatus(studio.getStatus());
            studioDTO1.setCommercialRecord(studio.getCommercialRecord());
            studioDTO1.setCity(studio.getCity());
            studioDTOS.add(studioDTO1);
        }
        return studioDTOS;
    }

    public StudioDTO getSpecificStudio(Integer photographer_id,Integer studio_id) {
        Photographer photographer = photographerRepository.findPhotographersById(photographer_id);
        if(photographer==null) {
            throw new ApiException("photographer not found");
        }
        Studio studio = studioRepository.findStudioById(studio_id);
        if(studio==null) {
            throw new ApiException("studio not found");
        }
        StudioDTO studioDTO = new StudioDTO();
        studioDTO.setName(studio.getName());
        studioDTO.setUsername(studio.getMyUser().getUsername());
        studioDTO.setPhoneNumber(studio.getPhoneNumber());
        studioDTO.setEmail(studio.getMyUser().getEmail());
        studioDTO.setAddress(studio.getAddress());
        studioDTO.setStatus(studio.getStatus());
        studioDTO.setCommercialRecord(studio.getCommercialRecord());
        studioDTO.setCity(studio.getCity());
        return studioDTO;
    }


    }
