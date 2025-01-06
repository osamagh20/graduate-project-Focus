package com.example.focus.Service;


import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.EditorDTO;
import com.example.focus.DTO.EditorDTOin;
import com.example.focus.Model.*;
import com.example.focus.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EditorService {
    private final MyUserRepository myUserRepository;
    private final EditorRepository editorRepository;
    private final ProfileEditorRepository  profileEditorRepository;

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

        Editor editor=new Editor();
        editor.setMyUser(user);
        editor.setName(editorDTOin.getName());
        editor.setCity(editorDTOin.getCity());
        editor.setPhoneNumber(editorDTOin.getPhoneNumber());
        editorRepository.save(editor);

        ProfileEditor profileEditor = new ProfileEditor();
        profileEditor.setMyUser(user);
        profileEditor.setNumberOfPosts(0);

        profileEditorRepository.save(profileEditor);

        myUserRepository.save(user);

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