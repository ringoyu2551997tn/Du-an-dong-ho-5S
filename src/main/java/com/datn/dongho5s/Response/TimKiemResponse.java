package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.AnhSanPham;
import com.datn.dongho5s.Entity.ChiTietSanPham;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TimKiemResponse {
    private Integer sanPhamID;
    private String tenSanPham;
    private Double giaSanPham;
    private List<AnhSanPham> listAnhSanPham;
    private List<ChiTietSanPham> listChiTietSanPham ;
}
