package ru.hogwarts.school.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.services.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        return avatarService.uploadAvatar(studentId, avatar);
    }

    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadFromFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        avatarService.downloadAvatar(id, response);
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadFromDb(@PathVariable Long id) {
        return avatarService.downloadFromDb(id);
    }
}