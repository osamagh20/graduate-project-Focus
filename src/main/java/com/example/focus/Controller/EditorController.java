package com.example.focus.Controller;


import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.EditorDTO;
import com.example.focus.DTO.EditorDTOin;
import com.example.focus.Model.Editor;
import com.example.focus.Service.EditorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/focus/editor")
@RequiredArgsConstructor
public class EditorController {
    private final EditorService editorService;

    @GetMapping("/get-all")
    public ResponseEntity getAllEditors() {
        List<EditorDTO> editorDTOS = editorService.getAllEditors();
        return ResponseEntity.ok(editorDTOS);
    }

    @PostMapping("/register")
    public ResponseEntity EditorRegistration(@RequestBody @Valid EditorDTOin editor) {
        editorService.EditorRegistration(editor);
        return ResponseEntity.ok(new ApiResponse("Editor added successfully"));
    }

    @PutMapping("/update-editor/{id}")
    public ResponseEntity updateEditor(@PathVariable Integer id, @RequestBody @Valid EditorDTOin editor) {
        editorService.updateEditor(id, editor);
        return ResponseEntity.ok(new ApiResponse("Editor updated successfully"));
    }

    @DeleteMapping("/delete-editor/{id}")
    public ResponseEntity deleteEditor(@PathVariable Integer id) {
        editorService.deleteEditor(id);
        return ResponseEntity.ok(new ApiResponse("Editor deleted successfully"));
    }
}