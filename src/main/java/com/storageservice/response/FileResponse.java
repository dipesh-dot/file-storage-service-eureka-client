package com.storageservice.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
public class FileResponse  {
    String fileName;
    String message;
}
