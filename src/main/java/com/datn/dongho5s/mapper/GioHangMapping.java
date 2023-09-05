package com.datn.dongho5s.mapper;

import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.Request.ChiTietGioHangRequest;
import com.datn.dongho5s.Request.GioHangRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;
import com.datn.dongho5s.Response.GiohangResponse;
import lombok.Data;

@Data
public class GioHangMapping {

    public  static GiohangResponse mapEntitytoResponse(GioHang gioHang){
        GiohangResponse gioHangResponse =  GiohangResponse.builder()
                .idGioHang(gioHang.getIdGioHang())
                .khachHang(gioHang.getKhachHang())
                .ngayTaoGioHang(gioHang.getNgayTaoGioHang())
                .trangThaiGioHang(gioHang.getTrangThaiGioHang())
                .thoiGianCapNhapGioHang(gioHang.getThoiGianCapNhapGioHang())
                .ghiChu(gioHang.getGhiChu())
                .build();
        return gioHangResponse;
    }


    public  static GioHang mapRequestToEntity(GioHangRequest gioHangRequest){
        GioHang gioHang =  GioHang.builder()
                .idGioHang(gioHangRequest.getIdGioHang())
                .khachHang(gioHangRequest.getKhachHang())
                .ngayTaoGioHang(gioHangRequest.getNgayTaoGioHang())
                .trangThaiGioHang(gioHangRequest.getTrangThaiGioHang())
                .thoiGianCapNhapGioHang(gioHangRequest.getThoiGianCapNhapGioHang())
                .ghiChu(gioHangRequest.getGhiChu())
                .build();
        return gioHang;
    }
}
