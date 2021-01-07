package com.sportingCenterBackEnd.dto;

import lombok.Value;

@Value
public class ApiResponse {
	private Boolean success;
	private String message;

    public ApiResponse(boolean b, String error) {
        success = b;
        message = error;
    }
}
