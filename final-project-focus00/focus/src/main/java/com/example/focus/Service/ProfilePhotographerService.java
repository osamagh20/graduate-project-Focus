package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.ProfileDTO;
import com.example.focus.Model.Media;
import com.example.focus.Model.MyUser;
import com.example.focus.Model.ProfilePhotographer;
import com.example.focus.Repository.MediaRepository;
import com.example.focus.Repository.MyUserRepository;
import com.example.focus.Repository.ProfilePhotographerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfilePhotographerService {
    private final ProfilePhotographerRepository profilePhotographerRepository ;
    private final MediaRepository mediaRepository;
    private final MyUserRepository myUserRepository;

    public List<ProfilePhotographer> getAllProfiles() {
        List<ProfilePhotographer> profiles = profilePhotographerRepository.findAll();
        //List<ProfileDTO> profileDTOs = new ArrayList<>();

//        for (ProfilePhotographer profilePhotographer : profiles) {
//            ProfileDTO profileDTO = new ProfileDTO(
//                    profilePhotographer.getDescription(),
//                    profilePhotographer.getNumberOfPosts(),
//                    profilePhotographer.getImage()
//            );
//            profileDTOs.add(profileDTO);
//        }
//        return profileDTOs;

        return profiles;
    }




    public ProfilePhotographer getMyProfile(Integer photographerid ) {
       ProfilePhotographer profile = profilePhotographerRepository.findProfilePhotographerById(photographerid);
if(profile == null) {
    throw new ApiException("Photographer not found");
}
        //List<ProfileDTO> profileDTOs = new ArrayList<>();

//        for (ProfilePhotographer profilePhotographer : profiles) {
//            ProfileDTO profileDTO = new ProfileDTO(
//                    profilePhotographer.getDescription(),
//                    profilePhotographer.getNumberOfPosts(),
//                    profilePhotographer.getImage()
//            );
//            profileDTOs.add(profileDTO);
//        }
//        return profileDTOs;

        return profile;
    }


    public void addProfile(ProfilePhotographer profile) {
        profilePhotographerRepository.save(profile);
    }

    public void updateProfile(Integer id, ProfilePhotographer profile) {
        ProfilePhotographer existingProfile = profilePhotographerRepository.findProfilePhotographerById(id);
        if (existingProfile != null) {
            existingProfile.setDescription(profile.getDescription());
            existingProfile.setImage(profile.getImage());
        } else {
            throw new ApiException("Profile Not Found");
        }
        profilePhotographerRepository.save(existingProfile);
    }

    public void deleteProfile(Integer id) {
        ProfilePhotographer existingProfile = profilePhotographerRepository.findProfilePhotographerById(id);
        if (existingProfile != null) {
            profilePhotographerRepository.delete(existingProfile);
        } else {
            throw new ApiException("Profile Not Found");
        }
    }




    private  final String UPLOAD_DIR = "C:/Users/doly/Desktop/Media/Photographers/"; // المسار حيث سيتم حفظ الملفات

    // الميثود التي ستتعامل مع رفع وحفظ الملف
    public void saveMediaFile(Integer profileid, MultipartFile file) throws IOException {
        ProfilePhotographer profilePhotographer=profilePhotographerRepository.findProfilePhotographerById (profileid);
        if(profilePhotographer==null){
            throw new ApiException("ProfilePhotographer not found");
        }

        String fileName = file.getOriginalFilename();

        String fileType = getFileType(fileName);

        if ("unknown".equals(fileType)) {
            throw new ApiException("Unsupported file type. Only images and videos are allowed");
        }

        // تحديد المسار الكامل للملف في الجهاز
        Path filePath = Paths.get(UPLOAD_DIR.concat(fileName));

        // حفظ الملف على الجهاز في المجلد المحدد
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // تحديد مسار الملف الذي سيتم تخزينه في قاعدة البيانات
        String filePathString = filePath.toString();

        // هنا نقوم بإنشاء كائن `Media` لتخزين تفاصيل الملف في قاعدة البيانات
        Media media = new Media();
        media.setProfile(profilePhotographer);
        media.setMediaType(fileType);  // تعيين نوع الملف (صورة أو فيديو)
        media.setUploadDate(LocalDateTime.now());
        media.setMediaURL(filePathString);  // تعيين اسم الملف كـ URL
        media.setVisibility(true);  // أو أي منطق آخر لتحديد الظهور


        // حفظ الميديا في قاعدة البيانات
        mediaRepository.save(media);


    }































    // ميثود لتحديد نوع الملف بناءً على امتداده
    private String getFileType(String fileName) {
        String fileType = "unknown";
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
            fileType = "image";
        } else if (fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mov")) {
            fileType = "video";
        }
        return fileType;
    }




}
