package com.datn.dongho5s.mapper;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Request.ChiTietSanPhamRequest;
import com.datn.dongho5s.Response.ChiTietSanPhamResponse;
import lombok.Data;

@Data
public class ChiTietSanPhamMapping {
    public  static ChiTietSanPhamResponse mapEntitytoResponse(ChiTietSanPham sp){
        ChiTietSanPhamResponse chiTietSanPhamResponse =  ChiTietSanPhamResponse.builder()
                .idChiTietSanPham(sp.getIdChiTietSanPham())
                .sanPham(sp.getSanPham())
                .dayDeo(sp.getDayDeo())
                .khuyenMai(sp.getKhuyenMai())
                .mauSac(sp.getMauSac())
                .vatLieu(sp.getVatLieu())
                .kichCo(sp.getKichCo())
                .chieuDaiDayDeo(sp.getChieuDaiDayDeo())
                .duongKinhMatDongHo(sp.getDuongKinhMatDongHo())
                .doDayMatDongHo(sp.getDoDayMatDongHo())
                .doChiuNuoc(sp.getDoChiuNuoc())
                .trangThai(sp.getTrangThai())
                .giaSanPham(sp.getGiaSanPham())
//                .soLuong(sp.getSoLuong())
                .listPhanHoi(sp.getListPhanHoi())
                .build();
        return chiTietSanPhamResponse;
    }

    public  static ChiTietSanPham mapRequestToEntity(ChiTietSanPhamRequest sp){
        ChiTietSanPham chiTietSanPham =  ChiTietSanPham.builder()
                .idChiTietSanPham(sp.getIdChiTietSanPham())
                .sanPham(sp.getSanPham())
                .dayDeo(sp.getDayDeo())
                .khuyenMai(sp.getKhuyenMai())
                .mauSac(sp.getMauSac())
                .vatLieu(sp.getVatLieu())
                .kichCo(sp.getKichCo())
                .chieuDaiDayDeo(sp.getChieuDaiDayDeo())
                .duongKinhMatDongHo(sp.getDuongKinhMatDongHo())
                .doDayMatDongHo(sp.getDoDayMatDongHo())
                .doChiuNuoc(sp.getDoChiuNuoc())
                .trangThai(sp.getTrangThai())
                .giaSanPham(sp.getGiaSanPham())
//                .soLuong(sp.getSoLuong())
                .build();
        return chiTietSanPham;
    }
}
