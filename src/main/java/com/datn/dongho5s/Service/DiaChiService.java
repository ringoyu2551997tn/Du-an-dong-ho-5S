package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Request.DiaChiRequest;
import com.datn.dongho5s.Response.DiaChiResponse;

import java.util.List;

public interface DiaChiService {
    public DiaChiResponse createDiaChi(Integer idKhachHang,DiaChiRequest diaChiRequest) throws Exception;
    public List<DiaChi> getAllDiaChi();
    List<DiaChi> getAllDiaChiByKhachHang(KhachHang khachHang);

    List<DiaChiResponse> getDiaChiByKhachHang(Integer idKhachHang) throws Exception;
    DiaChiResponse updateDC(Integer idDiachi, DiaChiRequest diaChiRequest) throws Exception;
    DiaChiResponse updateDCDefault(Integer idKhachHang,Integer idDiachi) throws Exception;
    void delete(Integer idDiachi) ;

    String getDiaChiCuThe(DiaChiRequest diaChiRequest) throws Exception;
}
