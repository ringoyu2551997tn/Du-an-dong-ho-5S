package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamAdminResponse {
    private Integer idChiTietSanPham;
    private String maChiTietSanPham;
    private SanPham sanPham;
    private KhuyenMai khuyenMai;
    private MauSac mauSac;
    private Double giaSanPham;
    private Integer soLuong;
    private VatLieu vatLieu;
}
