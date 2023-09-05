package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.NhanVien;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonHangAdminResponse {

    private Integer idDonHang;
    private String maDonHang;
    private Integer idNhanVien;
    private KhachHang khachHang;
    private String ngayTao;
    private String ngayCapNhap;
    private Integer trangThaiDonHang;
    private String diaChi;
    private Double tongTien;
    private Double phiVanChuyen;
    private String ghiChu;
    private String lyDo;

}
