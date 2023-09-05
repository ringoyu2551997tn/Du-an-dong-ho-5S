package com.datn.dongho5s.mapper;

import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Request.GioHangRequest;
import com.datn.dongho5s.Response.GiohangResponse;
import com.datn.dongho5s.Response.HoaDonChiTietResponse;
import lombok.Data;

@Data
public class HoaDonChiTietMapping {

    public  static HoaDonChiTietResponse mapEntitytoResponse(HoaDonChiTiet hoaDonChiTiet){
        HoaDonChiTietResponse hoaDonChiTietResponse =  HoaDonChiTietResponse.builder()
                .idHoaDonChiTiet(hoaDonChiTiet.getIdHoaDonChiTiet())
                .chiTietSanPham(hoaDonChiTiet.getChiTietSanPham())
                .donHang(hoaDonChiTiet.getDonHang())
                .giaBan(hoaDonChiTiet.getGiaBan())
                .soLuong(hoaDonChiTiet.getSoLuong())

                .build();
        return hoaDonChiTietResponse;
    }



}
