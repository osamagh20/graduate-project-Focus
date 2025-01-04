package com.example.focus.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolDTOIn {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100 characters")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    @Size(max = 500, message = "Description should not exceed 500 characters")
    @Column(columnDefinition = "varchar(60) not null")
    private String description;

    @NotEmpty(message = "Category cannot be empty")
    @Pattern(regexp = "^(Camera|Lens|Tripods|Lighting|Photography Equipment)$", message = "Category must be one of the following: Camera, Lens, Tripods, Lighting, Photography Equipment")
    @Column(columnDefinition = "varchar(23) not null")
    private String category;

    @NotEmpty(message = "Brand cannot be empty")
    @Pattern(regexp = "^(Canon|Nikon|Sony|Olympus|Panasonic)$", message = "Brand must be one of the following: Canon, Nikon, Sony, Olympus, Panasonic")
    @Column(columnDefinition = "varchar(10) not null")
    private String brand;

    @NotEmpty(message = "Condition cannot be empty")
    @Pattern(regexp = "^(new|used)$", message = "Condition must be 'new' or 'used'")
    @Column(columnDefinition = "varchar(4) not null")
    private String toolCondition;

    @DecimalMin(value = "0.0", inclusive = false, message = "Rental price must be greater than 0")
    @Column(columnDefinition = "decimal not null")
    private Double rentalPrice;

    @NotEmpty(message = "Image URL cannot be empty")
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*$", message = "Image URL must be a valid URL")
    @Column(columnDefinition = "varchar(255) not null")
    private String imageURL;
}
