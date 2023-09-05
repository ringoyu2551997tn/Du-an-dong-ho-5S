package com.datn.dongho5s.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAdminResponse {
    private Integer id ;
    private String name ;
    private int status;
    private String description;
    private String token;
}
