package com.storageservice.service.implementation;


import java.io.IOException;

import com.storageservice.repository.FileDataRepository;
import com.storageservice.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Objects;

@Service
public class StorageServiceImplementation implements StorageService {


    private final FileDataRepository fileDataRepository;

    public StorageServiceImplementation(FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
    }

    @Value("${project.image}")
    private String uploadDirectory;


    @Override
    public byte[] getImage(String fileName) throws IOException {
        Path imagePath = Path.of(uploadDirectory, fileName);
        try {
            return Files.readAllBytes(imagePath);
        } catch (NoSuchFileException e) {
            // Handle the case where the file doesn't exist.
            throw new FileNotFoundException("Image not found: " + fileName);
        } catch (IOException e) {
            // Handle other IO exceptions.
            throw new IOException("Error reading image: " + fileName, e);
        }
    }


    @Override
    public String uploadImage(MultipartFile file, String grade) throws IOException {
        if (file.isEmpty()) {
            return "File is empty";
        }

        // Check if the file is an image (you can implement your own validation logic)
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
            return "File is not an image";
        }


        String uploadDir;

        String a ="first_sem";
        String b ="second_sem";


        // Determine the upload directory based on the grade
        if (a.equals(grade)) {
            uploadDir = "C:\\Users\\dipes\\OneDrive\\Desktop\\photo\\first_sem\\";
        } else if (b.equalsIgnoreCase(grade)) {
            uploadDir = "C:\\Users\\dipes\\OneDrive\\Desktop\\photo\\second_sem\\";
        } else {
            return "Invalid grade and its data type is ___>"+grade.getClass()+"the data type of a is "+"---->" + a.getClass();
        }

        String fileName = System.currentTimeMillis() + file.getOriginalFilename();

        try {
            file.transferTo(new File(uploadDir + fileName));
        } catch (IOException e) {
            throw new IOException("Failed to upload file");
        }

        // Return the URL or any other response indicating a successful upload
        return "File uploaded successfully. URL: " + uploadDir + fileName;

    }

}



