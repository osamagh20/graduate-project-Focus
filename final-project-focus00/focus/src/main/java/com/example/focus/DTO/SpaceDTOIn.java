package com.example.focus.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceDTOIn {
    @NotEmpty(message = "Please enter space name")
    private String name;
    @NotEmpty(message = "Please enter space type")
    private String type;
    @NotNull(message = "Please enter space area")
    @Positive
    private Double area;
    private String description;
    @NotNull(message = "Please enter space price")
    @PositiveOrZero(message = "price can be zero or more")
    private Double dayPrice;
    @NotNull(message = "Please enter space price")
    @PositiveOrZero(message = "price can be zero or more")
    private Double nightPrice;
    @NotNull(message = "Please enter space price")
    @PositiveOrZero(message = "price can be zero or more")
    private Double fullDayPrice;

    @NotNull(message = "Please provide a valid image URL")
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*$", message = "Image URL must be a valid URL")
    private String image;

  //  private Integer studioID;
}
