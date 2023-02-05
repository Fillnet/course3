package com.example.course3.service;

import com.example.course3.dto.SockRequest;
import com.example.course3.model.Color;
import com.example.course3.model.Size;


public interface SockService {
    void addSock(SockRequest sockRequest);


    void removeDefectiveSock(SockRequest sockRequest);

    void issueSock(SockRequest sockRequest);

    int getSockQuantity(Color color, Size size, Integer cottonMin, Integer cottonMax);

    void saveToFile();

}
