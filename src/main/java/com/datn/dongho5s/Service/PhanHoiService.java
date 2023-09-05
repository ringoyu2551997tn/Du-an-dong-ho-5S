package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.PhanHoi;
import com.datn.dongho5s.Request.PhanHoiRequest;
import com.datn.dongho5s.Response.PhanHoiResponse;

import java.util.List;

public interface PhanHoiService {

    List<PhanHoiResponse> findAll(Integer idSanPham);

    boolean checkPhanHoi(Integer idKhachHang, Integer idSanPham);

    PhanHoiResponse addPhanHoi(PhanHoiRequest phanHoiRequest);

    Long countPH(Integer idKhachHang, Integer idChiTietSanPham);
    Long countHDCT(Integer idKhachHang, Integer idChiTietSanPham);

}
