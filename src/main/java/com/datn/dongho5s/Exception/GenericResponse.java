package com.datn.dongho5s.Exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponse {
    private String message;
    private String token;
    private String error;

    public GenericResponse(String message, String token) {
        super();
        this.message = message;
    }

    public GenericResponse(String message,String token , String error) {
        super();
        this.message = message;
        this.token = token;
        this.error = error;
    }
}
