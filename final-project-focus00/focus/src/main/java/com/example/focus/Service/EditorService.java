package com.example.focus.Service;


import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.EditorDTO;
import com.example.focus.DTO.EditorDTOin;
import com.example.focus.DTO.PhotographerDTO;
import com.example.focus.Model.Editor;
import com.example.focus.Model.MyUser;
import com.example.focus.Model.Photographer;
import com.example.focus.Model.ProfileEditor;
import com.example.focus.Repository.EditorRepository;
import com.example.focus.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EditorService {
    private final MyUserRepository myUserRepository;
    private final EditorRepository editorRepository;

    public List<EditorDTO> getAllEditors() {
        List<Editor> editors = editorRepository.findAll();
        List<EditorDTO> editorDTOS = new ArrayList<>();

        for (Editor editor : editors) {
            EditorDTO editorDTO = new EditorDTO(
                    editor.getName(),
                    editor.getMyUser().getUsername(),
                    editor.getMyUser().getEmail(),
                    editor.getCity(),
                    editor.getPhoneNumber()
            );
            editorDTOS.add(editorDTO);
        }
        return editorDTOS;
    }


    public void EditorRegistration(EditorDTOin editorDTOin) {
        MyUser user = new MyUser();
        user.setUsername(editorDTOin.getUsername());
        user.setEmail(editorDTOin.getEmail());
        user.setPassword(editorDTOin.getPassword());
        user.setRole("EDITOR");
        myUserRepository.save(user);

        Editor editor=new Editor();
        editor.setMyUser(user);
        editor.setName(editorDTOin.getName());
        editor.setCity(editorDTOin.getCity());
        editor.setPhoneNumber(editorDTOin.getPhoneNumber());
        editorRepository.save(editor);

    }

    public void updateEditor(Integer id, EditorDTOin editorDTOin) {
        Editor existingEditor = editorRepository.findEditorById(id);
        if (existingEditor != null) {
            existingEditor.setName(editorDTOin.getName());
            existingEditor.setCity(editorDTOin.getCity());
            existingEditor.getMyUser().setUsername(editorDTOin.getUsername());
            existingEditor.getMyUser().setEmail(editorDTOin.getEmail());
            existingEditor.setPhoneNumber(editorDTOin.getPhoneNumber());
        }else {
            throw new ApiException("Editor Not Found");
        }
        editorRepository.save(existingEditor);
    }

    public void deleteEditor(Integer id) {
        MyUser myUser=myUserRepository.findMyUserById(id);
        if(myUser!=null) {
            myUserRepository.delete(myUser);
        }else{
            throw new ApiException("Editor Not Found");
        }
    }
}