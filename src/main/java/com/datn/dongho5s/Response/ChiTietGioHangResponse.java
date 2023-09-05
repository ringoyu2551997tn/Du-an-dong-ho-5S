package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.GioHang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChiTietGioHangResponse {
    private Integer idChiTietGioHang;

    private GioHang gioHang;

    private ChiTietSanPham chiTietSanPham;

    private Integer soLuongSanPham;

    private Double giaBan;

    private Date ngayTao;

    private String ghiChu;
}
