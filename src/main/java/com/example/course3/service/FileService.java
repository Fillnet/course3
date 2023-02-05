package com.example.course3.service;

import com.example.course3.exception.ExceptionWithOperationFile;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    boolean saveToFile(String json);

//    FileSystemResource exportData() throws IOException;
//    String readFromFile() throws ExceptionWithOperationFile;

    File getDataFile();

    boolean cleanDataFile();

    @GetMapping(value = "/export/socks", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Сохранение данных в файл",
            description = "Сохранение данных в файл"
    )
    ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException;

//    Path createTempFile(String suffix);
}
