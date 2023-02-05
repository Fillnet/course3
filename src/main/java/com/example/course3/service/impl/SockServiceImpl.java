package com.example.course3.service.impl;

import com.example.course3.dto.SockRequest;
import com.example.course3.exception.ExceptionWithOperationFile;
import com.example.course3.exception.InvalidSockQuantityException;
import com.example.course3.exception.InvalidSockRequestException;
import com.example.course3.model.Color;
import com.example.course3.model.Size;
import com.example.course3.model.Sock;
import com.example.course3.service.SockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
@Data
public class SockServiceImpl implements SockService {
    private static TreeMap<Month, LinkedHashMap<Long, Sock>> sock = new TreeMap<>();
    private static long lastId = 0;
    private Map<Sock, Integer> socks = new HashMap<>();

    public SockServiceImpl(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    private FileServiceImpl fileService;

    @Override
    public void addSock(SockRequest sockRequest) {
        validateRequest(sockRequest);
        Sock sock = mapToSock(sockRequest);
        if (socks.containsValue(sock)) {
            socks.put(sock, socks.get(sock) + sockRequest.getQuantity());
        } else {
            socks.put(sock, sockRequest.getQuantity());
        }
        saveToFile();
    }

    private void decreaseSockQuantity(SockRequest sockRequest) {
        validateRequest(sockRequest);
        Sock sock = mapToSock(sockRequest);
        int sockQuantity = socks.getOrDefault(sock, 0);
        if (sockQuantity >= sockRequest.getQuantity()) {
            socks.put(sock, sockQuantity - sockRequest.getQuantity());
        } else {
            throw new InvalidSockQuantityException("Носков нет");
        }
    }

    public void removeDefectiveSock(SockRequest sockRequest) {
        decreaseSockQuantity(sockRequest);
    }

    public void issueSock(SockRequest sockRequest) {
        decreaseSockQuantity(sockRequest);
    }

    public int getSockQuantity(Color color, Size size, Integer cottonMin, Integer cottonMax) {
        int total = 0;
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            if (color != null && !entry.getKey().getColor().equals(color)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonMin != null && entry.getKey().getCottonPercent() < cottonMin) {
                continue;
            }
            if (cottonMax != null && entry.getKey().getCottonPercent() > cottonMax) {
                continue;
            }
            total += entry.getValue();
        }
        return total;
    }


    private void validateRequest(SockRequest sockRequest) {
        if (sockRequest.getColor() == null || sockRequest.getSize() == null) {
            throw new InvalidSockRequestException("Все поля должны быть заполнены!");
        }
        if (sockRequest.getQuantity() < 0) {
            throw new InvalidSockRequestException("Количество не может быть меньше 0");
        }
        if (sockRequest.getCottonPercent() < 0 || sockRequest.getCottonPercent() > 100) {
            throw new InvalidSockRequestException("Процент хлопка не может быть меньше 0% и больше 100%");
        }
    }

    private Sock mapToSock(SockRequest sockRequest) {
        return new Sock(sockRequest.getColor(), sockRequest.getSize(), sockRequest.getCottonPercent());
    }


    @Override
    public void saveToFile() {
        try {
            DataFile dataFile = new DataFile(lastId + 1, sock);
            String json = new ObjectMapper().writeValueAsString(dataFile);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ExceptionWithOperationFile("Ошибка сохранения файла");
        }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class DataFile {
        private long lastId;
        private TreeMap<Month, LinkedHashMap<Long, Sock>> sock = new TreeMap<>();
    }
}
