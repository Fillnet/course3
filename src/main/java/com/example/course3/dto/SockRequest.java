package com.example.course3.dto;

import com.example.course3.model.Color;
import com.example.course3.model.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SockRequest {
    private Color color;
    private Size size;
    private int cottonPercent;
    private int quantity;
}
