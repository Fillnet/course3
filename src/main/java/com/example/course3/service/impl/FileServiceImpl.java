package com.example.course3.service.impl;

import com.example.course3.dto.SockRequest;
import com.example.course3.exception.ExceptionWithOperationFile;
import com.example.course3.model.Sock;
import com.example.course3.service.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {
    private ObjectMapper objectMapper;
    private Map<Sock, Integer> socks = new HashMap<>();
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.of.data.file}")
    private String dataFileName;
//    @Override
//    public String readFromFile() {
//        try {
//            return Files.readString(Path.of(dataFilePath, dataFileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new ExceptionWithOperationFile("Ошибка чтения из файла");
//        }
//    }
    @Override
    public File getDataFile() {
        return new File(dataFilePath + "/" + dataFileName);
    }
    @Override
    public boolean saveToFile(String json) {
        try {
//            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка сохранения файла");
        }
    }

//    @Override
//    public FileSystemResource exportData() {
//        try {
//            Path path = Files.createTempFile("export", ".json");
//            List<SockRequest> sockRequestList = new ArrayList<>();
//            for (Map.Entry<Sock, Integer> entry : this.socks.entrySet()) {
//                sockRequestList.add(mapRequest(entry.getKey(), entry.getValue()));
//            }
//            Files.write(path, objectMapper.writeValueAsBytes(sockRequestList));
//            return new FileSystemResource(path);
//        } catch (IOException e) {
//            throw new ExceptionWithOperationFile("Ошибка экспорта файла");
//        }
//            public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
//        File dataFile = getDataFile();
//        if (dataFile.exists()) {
//            InputStreamResource resource = new InputStreamResource(new FileInputStream((dataFile)));
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .contentLength(dataFile.length())
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"SocksData.json\"")
//                    .body(resource);
//        } else {
//            return ResponseEntity.noContent().build();
//        }
//    }

//    private SockRequest mapRequest(Sock sock, int quantity) {
//        SockRequest sockRequest = new SockRequest();
//        sockRequest.setColor(sock.getColor());
//        sockRequest.setSize(sock.getSize());
//        sockRequest.setCottonPercent(sock.getCottonPercent());
//        sockRequest.setQuantity(quantity);
//        return sockRequest;
//    }

    @Override
    public boolean cleanDataFile() {

        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    @GetMapping(value = "/export/socks", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Сохранение данных в файл",
            description = "Сохранение данных в файл"
    )
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File dataFile = getDataFile();
        if (dataFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream((dataFile)));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(dataFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"SocksData.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

//    @Override
//    public Path createTempFile(String suffix) {
//        try {
//            return Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//    }

}
