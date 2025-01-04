package com.example.focus.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEditingOutputDTO {

    private Integer id;
    private LocalDateTime estimatedCompletionDate;
    private String description;
    private String fullCameraName;
    private String sensorSize;
    private String kitLens;
    private String viewFinder;
    private String nativeISO;
    private String status;
}
