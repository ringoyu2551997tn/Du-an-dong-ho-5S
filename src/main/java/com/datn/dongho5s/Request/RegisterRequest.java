package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String tenKhachHang;

    private String soDienThoai;

    private String email;

    private Date ngaySinh;

    private Integer gioiTinh;

    private String password;

    private Integer idTinhThanh;

    private Integer idQuanHuyen;

    private String idPhuongXa;

    private String diaChi;

}
