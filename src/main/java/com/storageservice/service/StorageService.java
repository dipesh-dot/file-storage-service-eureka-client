package com.storageservice.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface StorageService {



    String uploadImage(MultipartFile file ,String grade) throws IOException;
    public byte[] getImage(String fileName) throws IOException;

//    byte[] getFileContent(String fileName) throws IOException;

}
