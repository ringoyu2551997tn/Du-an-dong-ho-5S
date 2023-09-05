package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.KhachHang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaChiResponse {
    private Integer idDiaChi;
    private Integer idTinhThanh;
    private Integer idQuanHuyen;
    private String idPhuongXa;
    private String thanhPho;
    private String quanHuyen;
    private String phuongXa;
    private String diaChi;
    private Integer maBuuChinh;
    private String soDienThoai;
    private String ghiChu;
    private Integer trangThaiMacDinh;
    private KhachHang khachHang;
}
