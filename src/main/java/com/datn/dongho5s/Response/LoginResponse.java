package com.datn.dongho5s.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Integer idKhachHang ;
    private String username ;
    private int status;
    private String message;
    private String token;
}
