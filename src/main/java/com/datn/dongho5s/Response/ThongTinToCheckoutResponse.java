package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.DiaChi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ThongTinToCheckoutResponse {
    private Integer id;
    private String tenKhachHang;
    private String soDienThoai;
    private List<DiaChi> listDiaChi;
}
