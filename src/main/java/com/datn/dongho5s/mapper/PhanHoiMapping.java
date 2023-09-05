package com.datn.dongho5s.mapper;

import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.Entity.PhanHoi;
import com.datn.dongho5s.Request.GioHangRequest;
import com.datn.dongho5s.Request.PhanHoiRequest;
import com.datn.dongho5s.Response.GiohangResponse;
import com.datn.dongho5s.Response.PhanHoiResponse;
import com.datn.dongho5s.Service.PhanHoiService;
import lombok.Data;

@Data
public class PhanHoiMapping {


    public  static PhanHoiResponse mapEntitytoResponse(PhanHoi phanHoi){
    PhanHoiResponse phanHoiResponse =  PhanHoiResponse.builder()
            .idPhanHoi(phanHoi.getIdPhanHoi())
            .chiTietSanPham(phanHoi.getChiTietSanPham())
            .danhGia(phanHoi.getDanhGia())
            .ghiChu(phanHoi.getGhiChu())
            .noiDungPhanHoi(phanHoi.getNoiDungPhanHoi())
            .khachHang(phanHoi.getKhachHang())
            .ngaySua(phanHoi.getNgaySua())
            .thoiGianPhanHoi(phanHoi.getThoiGianPhanHoi())
            .trangThaiPhanHoi(phanHoi.getTrangThaiPhanHoi())
            .ngayTao(phanHoi.getNgayTao())
            .build();
        return phanHoiResponse;
}




}
