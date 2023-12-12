package com.storageservice.controller;

import com.storageservice.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file ,@RequestParam("grade") String grade) {
        try {
            String fileName = storageService.uploadImage(file,grade);
            return ResponseEntity.ok("File uploaded successfully: " + fileName +" this is grade "+grade);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }
    }


    private final Path uploadDirectory;

    public StorageController() {
        // Specify the directory where your images are stored.
        this.uploadDirectory = Path.of("C:\\Users\\dipes\\OneDrive\\Desktop\\photo\\");
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) {
        try {
            Path imagePath = uploadDirectory.resolve(fileName);
            Resource resource = (Resource) new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Change the media type based on your image type.

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            }
        } catch (IOException e) {
            // Handle any exceptions here.
        }

        // If the image doesn't exist or can't be read, return a 404 response.
        return ResponseEntity.notFound().build();
    }
}
