package com.example.course3.controller;

import com.example.course3.exception.ExceptionWithOperationFile;
import com.example.course3.service.FileService;
import com.example.course3.service.SockService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService, SockService sockService) {
        this.fileService = fileService;
    }


    @Operation(
            summary = "Сохранение",
            description = "Сохранение данных в файл в формате JSON"
    )
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> downloadDataFile() {
        try {
            return fileService.downloadDataFile();
        } catch (IOException e) {
            throw new ExceptionWithOperationFile("Неудачная загрузка файла");
        }
    }




    @Operation(
            summary = "Загрузка",
            description = "Загрузка данных из файла в формате JSON"
    )
    @PutMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        fileService.cleanDataFile();
        File dataFile = fileService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
