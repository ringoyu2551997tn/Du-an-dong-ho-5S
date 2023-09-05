package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Request.ChiTietSanPhamRequest;
import com.datn.dongho5s.Request.HoaDonChiTietRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HoaDonChiTietService {


    HoaDonChiTiet save(HoaDonChiTiet hdct);

    List<HoaDonChiTiet> convertToListHoaDonChiTiet(List<HoaDonChiTietRequest> list, Integer idDonHang);

    List<HoaDonChiTiet> saveAll(List<HoaDonChiTiet> listHDCT);

    Double getTongGia(List<HoaDonChiTietRequest> list);

    List<HoaDonChiTiet> getByIdDonHang(int id);

    List<HoaDonChiTiet> getByHoaDonId(DonHang donHang);

    public List<HoaDonChiTiet> getHDCTByMaDonHang(String maDonHang);

    void themSoLuongSanPham(
            int soLuong,
            ChiTietSanPham chiTietSanPham,
            DonHang donHang
    );

    void xoaHDCT(
        HoaDonChiTiet hoaDonChiTiet
    );

    HoaDonChiTiet findHoaDonChiTietById(
        int id
    );

    void xoaHDCTByIdDonHang(DonHang donHang);
}
