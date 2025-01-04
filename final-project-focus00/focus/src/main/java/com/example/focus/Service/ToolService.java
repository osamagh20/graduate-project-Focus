package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.ToolDTO;
import com.example.focus.DTO.ToolDTOIn;
import com.example.focus.Model.Photographer;
import com.example.focus.Model.Tool;
import com.example.focus.Repository.PhotographerRepository;
import com.example.focus.Repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;
    private final PhotographerRepository photographerRepository;

    public List<ToolDTO> getAllTools() {
        List<Tool> tools = toolRepository.findAll();
        List<ToolDTO> toolDTOS = new ArrayList<>();
        for (Tool tool : tools) {
            ToolDTO toolDTO = new ToolDTO(
                    tool.getName(),
                    tool.getDescription(),
                    tool.getCategory(),
                    tool.getBrand(),
                    tool.getToolCondition(),
                    tool.getRentalPrice(),
                    tool.getImageURL(),
                    tool.getPhotographer().getId()

            );
            toolDTOS.add(toolDTO);
        }
        return toolDTOS;
    }

    // photographer add tool
    public void addTool(Integer photographer_id, ToolDTOIn toolDTOIn) {
        Photographer photographer = photographerRepository.findPhotographersById(photographer_id);

        if(photographer==null) {
            throw new ApiException("Photographer Not Found");
        }
        Tool tool = toolRepository.findToolByName(toolDTOIn.getName());
        if(tool!=null) {
            throw new ApiException("Tool Already Exist");
        }
        Tool tool1 = new Tool();
        tool1.setName(toolDTOIn.getName());
        tool1.setToolCondition(toolDTOIn.getToolCondition());
        tool1.setPhotographer(photographer);
        tool1.setImageURL(toolDTOIn.getImageURL());
        tool1.setBrand(toolDTOIn.getBrand());
        tool1.setDescription(toolDTOIn.getDescription());
        tool1.setCategory(toolDTOIn.getCategory());
        tool1.setRentalPrice(toolDTOIn.getRentalPrice());
        toolRepository.save(tool1);
    }

//    public void addTool(Tool tool, Integer photographerId) {
//        Photographer photographer = photographerRepository.findPhotographersById(photographerId);
//        if (photographer != null) {
//            tool.setPhotographer(photographer);
//            toolRepository.save(tool);
//        } else {
//            throw new ApiException("Photographer not found");
//        }
//    }


    public void updateTool(Integer photographer_id,Integer tool_id, ToolDTOIn tool) {
        Photographer photographer = photographerRepository.findPhotographersById(photographer_id);
        if(photographer==null) {
            throw new ApiException("Photographer Not Found");
        }
        Tool existingTool = toolRepository.findToolById(tool_id);
        if (existingTool != null) {
            existingTool.setName(tool.getName());
            existingTool.setDescription(tool.getDescription());
            existingTool.setCategory(tool.getCategory());
            existingTool.setBrand(tool.getBrand());
            existingTool.setToolCondition(tool.getToolCondition());
            existingTool.setRentalPrice(tool.getRentalPrice());
            existingTool.setImageURL(tool.getImageURL());
            toolRepository.save(existingTool);
        } else {
            throw new ApiException("Tool not found");
        }
    }


    public void deleteTool(Integer photographer_id,Integer tool_id) {
        Photographer photographer = photographerRepository.findPhotographersById(photographer_id);
        if(photographer==null) {
            throw new ApiException("Photographer Not Found");
        }
        Tool existingTool = toolRepository.findToolById(tool_id);
        if (existingTool != null) {
            toolRepository.delete(existingTool);
        } else {
            throw new ApiException("Tool not found");
        }
    }


}