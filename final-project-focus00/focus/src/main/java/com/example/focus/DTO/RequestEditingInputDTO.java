package com.example.focus.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEditingInputDTO {

    @NotNull(message = "Estimated completion date cannot be null")
    private LocalDateTime estimatedCompletionDate;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Full camera name cannot be blank")
    private String fullCameraName;

    @NotBlank(message = "Sensor size cannot be blank")
    private String sensorSize;

    @NotBlank(message = "Kit lens cannot be blank")
    private String kitLens;

    @NotBlank(message = "View finder cannot be blank")
    private String viewFinder;

    @NotBlank(message = "Native ISO cannot be blank")
    private String nativeISO;

    @NotBlank(message = "Status cannot be blank")
    private String status;
}
