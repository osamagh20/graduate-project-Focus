package com.example.focus.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToolDTO {

    private String name;

    private String description;

    private String category;

    private String brand;

    private String condition;

    private Double rentalPrice;

    private String imageUrl;

    private Integer photographer_id;
}