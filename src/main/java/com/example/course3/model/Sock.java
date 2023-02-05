package com.example.course3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Sock {
    private Color color;
    private Size size;
    private int cottonPercent;

}
