package com.example.course3.service;

import com.example.course3.dto.SockRequest;

import java.io.IOException;
import java.nio.file.Path;

public interface SockService {
    void addSock(SockRequest sockRequest);

//    void readFromFile();

    void saveToFile();

//    Path createSockFile() throws IOException;
}
