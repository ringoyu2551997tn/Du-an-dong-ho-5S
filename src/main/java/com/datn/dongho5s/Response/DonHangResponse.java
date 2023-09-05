package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.NhanVien;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonHangResponse {
    private Integer idDonHang;

    private String maDonHang;

    private NhanVien nhanVien;

    private KhachHang khachHang;

    private Date ngayTao;

    private Date ngayGiaoHang;

    private Double tongTien;

    private Integer trangThaiDonHang;

    private String TinhThanh;

    private String QuanHuyen;

    private String PhuongXa;

    private String diaChi;

    private Double phiVanChuyen;

    private  String ngayCapNhap;

    private String ghiChu;
    private List<HoaDonChiTiet> hoaDonChiTiets;
    private String lyDo;
}
