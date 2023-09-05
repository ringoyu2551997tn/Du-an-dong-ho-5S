package com.datn.dongho5s.Request;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.KhachHang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhanHoiRequest {
    private String noiDungPhanHoi;
    private Integer danhGia;
    private Integer idChiTietSanPham;
    private Integer idKhachHang;
}
