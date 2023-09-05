package com.datn.dongho5s.Response;

import java.util.List;

public class JwtAdminResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String ten;
    private String email;

    private List<String> listChucVu;

    public JwtAdminResponse(String token, Integer id, String ten, String email, List<String> listChucVu) {
        this.token = token;
        this.id = id;
        this.ten = ten;
        this.email = email;
        this.listChucVu = listChucVu;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getListChucVu() {
        return listChucVu;
    }

    public void setListChucVu(List<String> listChucVu) {
        this.listChucVu = listChucVu;
    }
}
