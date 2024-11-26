package com.task.taskcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDTO {
    private Long userId;
    private String username;
    private String token;
}