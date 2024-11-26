package com.task.taskcard.service;

import com.task.taskcard.dto.JwtResponseDTO;
import com.task.taskcard.dto.LoginRequestDTO;
import com.task.taskcard.dto.RegisterRequestDTO;

public interface AuthService {

    void registerUser(RegisterRequestDTO registerRequest);

    JwtResponseDTO login(LoginRequestDTO loginRequest);

    String getAuthenticatedUsername();
}
