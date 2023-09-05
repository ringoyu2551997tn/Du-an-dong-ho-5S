package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ThemDonHangRequest {
    private Integer khachHangId;
    private List<HoaDonChiTietRequest> listHoaDonChiTietRequest;
    private Integer idTinhThanh;
    private Integer idQuanHuyen;
    private String idPhuongXa;
    private String diaChi;
    private String ghiChu;
    private Integer soLuongSanPham;
    private Double phiVanChuyen;
}
