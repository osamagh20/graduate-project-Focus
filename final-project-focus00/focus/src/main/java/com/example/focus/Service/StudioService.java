package com.example.focus.Service;
import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.*;
//import com.example.focus.Model.BookSpace;
//import com.example.focus.Model.Space;
import com.example.focus.Model.*;
//import com.example.focus.Repository.BookSpaceRepository;
//import com.example.focus.Repository.SpaceRepository;
import com.example.focus.Repository.MyUserRepository;
import com.example.focus.Repository.ProfileStudioRepository;
import com.example.focus.Repository.SpaceRepository;
import com.example.focus.Repository.StudioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudioService {
    private final MyUserRepository myUserRepository;
    private final StudioRepository studioRepository;
    private final SpaceRepository spaceRepository;
    private final ProfileStudioRepository profileStudioRepository;
    //  private final BookSpaceRepository bookSpaceRepository;

    public List<StudioDTO> getAllStudios(){
        List<Studio> studios = studioRepository.findAll();
        List<StudioDTO> studioDTOS = new ArrayList<>();
        for (Studio studio : studios){
            StudioDTO studioDTO = new StudioDTO();
            studioDTO.setName(studio.getName());
            studioDTO.setCity(studio.getCity());
            studioDTO.setAddress(studio.getAddress());
            studioDTO.setCommercialRecord(studio.getCommercialRecord());
            studioDTO.setStatus(studio.getStatus());
            studioDTOS.add(studioDTO);
        }
        return studioDTOS;
    }
    public void registerStudio(StudioDTOIn studioDTOIn){
        MyUser checkUsername =myUserRepository.findMyUserByUsername(studioDTOIn.getUsername());
        MyUser checkEmail =myUserRepository.findMyUserByEmail(studioDTOIn.getEmail());
        if(checkUsername != null){
            throw new ApiException("Username already exists");
        }
        if(checkEmail != null){
            throw new ApiException("email already exists");
        }


        MyUser user = new MyUser();
        user.setUsername(studioDTOIn.getUsername());
        user.setEmail(studioDTOIn.getEmail());
        user.setPassword(studioDTOIn.getPassword());
        user.setRole("STUDIO");
        myUserRepository.save(user);

        Studio studio=new Studio();
        studio.setMyUser(user);
        studio.setCommercialRecord(studioDTOIn.getCommercialRecord());
        studio.setName(studioDTOIn.getName());
        studio.setCity(studioDTOIn.getCity());
        studio.setPhoneNumber(studioDTOIn.getPhoneNumber());
        studio.setAddress(studioDTOIn.getAddress());
        studio.setStatus("active");
        studioRepository.save(studio);


       // profileStudioRepository.save(profileStudio);

    }

    public void activateStudio(Integer admin_id,Integer studio_id,String status){
        MyUser user = myUserRepository.findMyUserById(admin_id);
        if(user == null){
            throw new ApiException("admin not found");
        }
        Studio studio = studioRepository.findStudioById(studio_id);
        if(studio == null){
            throw new ApiException("studio not found");
        }
        studio.setStatus(status);
    }



    // studio add new space
//    public void addSpace (Integer studio_id,SpaceDTOIn spaceDTOIn){
//        Studio studio = studioRepository.findStudioById(studio_id);
//        if(studio == null){
//            throw new ApiException("studio not found");
//        }
//        if (!studio.getStatus().equalsIgnoreCase("active") ){
//            throw new ApiException("studio is not activated");
//        }
//        Space space = spaceRepository.findSpaceByName(spaceDTOIn.getName());
//        if(space != null){
//            throw new ApiException("space name already exists");
//        }
//        Space newSpace = new Space();
//        newSpace.setName(spaceDTOIn.getName());
//        newSpace.setType(spaceDTOIn.getType());
//        newSpace.setArea(spaceDTOIn.getArea());
//        newSpace.setDescription(spaceDTOIn.getDescription());
//        newSpace.setPrice(spaceDTOIn.getPrice());
//        newSpace.setStatus("active");
//        newSpace.setImage(spaceDTOIn.getImage());
//        newSpace.setStudio(studio);
//        spaceRepository.save(newSpace);
//
//    }

//    // studio delete space
//    public void deleteSpace (Integer studio_id,String space_name){
//        Studio studio = studioRepository.findStudioById(studio_id);
//        if(studio == null){
//            throw new ApiException("studio not found");
//        }
//        if (!studio.getIsActivated()){
//            throw new ApiException("studio is not activated");
//        }
//        Space space = spaceRepository.findSpaceByName(space_name);
//        if(space == null){
//            throw new ApiException("space not found");
//        }
//        spaceRepository.delete(space);
//
//    }
//
//    // update space
//    public void updateSpace (Integer studio_id,SpaceDTOIn spaceDTOIn){
//        Studio studio = studioRepository.findStudioById(studio_id);
//        if(studio == null){
//            throw new ApiException("studio not found");
//        }
//        if (!studio.getIsActivated()){
//            throw new ApiException("studio is not activated");
//        }
//        Space space = spaceRepository.findSpaceByName(spaceDTOIn.getName());
//        if(space == null){
//            throw new ApiException("space not found");
//        }
//        space.setName(spaceDTOIn.getName());
//        space.setType(spaceDTOIn.getType());
//        space.setArea(spaceDTOIn.getArea());
//        space.setDescription(spaceDTOIn.getDescription());
//        space.setPrice(spaceDTOIn.getPrice());
//        space.setStatus(spaceDTOIn.getStatus());
//        space.setImage(spaceDTOIn.getImage());
//        spaceRepository.save(space);
//
//    }
//
//    public List<RentalStudioRequestDTO> getSpaces(){
//        List<BookSpace> rsr = bookSpaceRepository.findAll();
//        List<RentalStudioRequestDTO> rsrDTO = new ArrayList<>();
//        for (BookSpace rs : rsr){
//            RentalStudioRequestDTO rsDTO = new RentalStudioRequestDTO(rs.getStartDate(),rs.getEndDate(),rs.getStatus(),rs.getNote());
//            rsrDTO.add(rsDTO);
//
//        }
//        return rsrDTO;
//    }
//
//    // studio can accept or reject the request from photographer
//    public void acceptOrRejectRequest(Integer studio_id,Integer request_id,String response){
//        Studio studio = studioRepository.findStudioById(studio_id);
//        if(studio == null){
//            throw new ApiException("studio not found");
//        }
//        if(!studio.getIsActivated()){
//            throw new ApiException("studio is not activated");
//        }
//        BookSpace sr = bookSpaceRepository.findRentalStudioRequestById(request_id);
//        if(sr == null){
//            throw new ApiException("request not found");
//        }
//
//        sr.setStatus(response);
//
//    }


}
