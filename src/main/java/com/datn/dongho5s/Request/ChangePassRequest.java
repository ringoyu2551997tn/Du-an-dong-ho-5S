package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassRequest {
    private String oldPass;
    private String newPass;
    private String confirmPass;

}
