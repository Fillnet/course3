package com.example.course3.controller;

import com.example.course3.dto.SockRequest;
import com.example.course3.exception.InvalidSockQuantityException;
import com.example.course3.exception.InvalidSockRequestException;
import com.example.course3.model.Color;
import com.example.course3.model.Size;
import com.example.course3.service.impl.SockServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sock")
@Tag(name = ("Склад "), description = "операции с носками")
public class SockController {
    private final SockServiceImpl sockService;

    public SockController(SockServiceImpl sockService) {
        this.sockService = sockService;
    }

    @ExceptionHandler(InvalidSockRequestException.class)
    public ResponseEntity<String> handleInvalidException(InvalidSockRequestException invalidSockRequestException) {
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }

    @ExceptionHandler(InvalidSockQuantityException.class)
    public ResponseEntity<String> handleInvalidException(InvalidSockQuantityException invalidSockQuantityException) {
        return ResponseEntity.badRequest().body(invalidSockQuantityException.getMessage());
    }

    @PostMapping
    @Operation(
            summary = "Добавление товара",
            description = "Добавление носков на склад"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось добавить приход."),

            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют, или имеют некорректный формат."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны."
            )

    })
    public void addSocks(@RequestBody SockRequest sockRequest) {

        sockService.addSock(sockRequest);
    }

    @PutMapping
    @Operation(
            summary = "Выдача товара",
            description = "Отпуск носков со склада"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось произвести отпуск носков сосклада."),

            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет в наличии в нужном количестве или параметры запроса имеют некоррректный формат."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны."
            )

    })
    public void releaseSock(@RequestBody SockRequest sockRequest) {
        sockService.issueSock(sockRequest);
    }

    @GetMapping
    @Operation(
            summary = "Количество товара",
            description = "Отображение общего количества носков на складе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен."),

            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют, или имеют некорректный формат."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны."
            )

    })
    public int getSockCount(@RequestParam(required = false, name = "цвет") Color color,
                            @RequestParam(required = false, name = "размер") Size size,
                            @RequestParam(required = false, name = "минимальный процент содержания хлопка") Integer cottonMin,
                            @RequestParam(required = false, name = "максимальный процент содержания хлопка") Integer cottonMax) {
        return sockService.getSockQuantity(color, size, cottonMin, cottonMax);
    }

    @DeleteMapping
    @Operation(
            summary = "Списание",
            description = "Списание бракованых носков на складе"
    )@ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, товар списан со склада."),

            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют, или имеют некорректный формат."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны."
            )

    })
    public void removeDefectiveSock(@RequestBody SockRequest sockRequest) {
        sockService.removeDefectiveSock(sockRequest);
    }
}