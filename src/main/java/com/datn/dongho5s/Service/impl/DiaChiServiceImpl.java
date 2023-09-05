package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Repository.DiaChiRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Request.DiaChiRequest;
import com.datn.dongho5s.Response.DiaChiResponse;
import com.datn.dongho5s.Service.DiaChiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class DiaChiServiceImpl implements DiaChiService {

    private final DiaChiRepository diaChiRepository;
    private final KhachHangRepository khachHangRepository;


    @Override
    public DiaChiResponse createDiaChi(Integer idKhachHang,DiaChiRequest diaChiRequest) throws Exception {
        KhachHang khachHang = khachHangRepository.findById(idKhachHang).get();
        if(khachHang==null) return null ;
        DiaChi d = DiaChi.builder()
                .idTinhThanh(diaChiRequest.getIdTinhThanh())
                .idQuanHuyen(diaChiRequest.getIdQuanHuyen())
                .idPhuongXa(diaChiRequest.getIdPhuongXa())
                .diaChi(diaChiRequest.getDiaChi())
                .ghiChu(diaChiRequest.getGhiChu())
                .soDienThoai(diaChiRequest.getSoDienThoai())
                .maBuuChinh(123)
                .trangThaiMacDinh(0)
                .khachHang(khachHang)
                .build();
        DiaChiResponse diaChiResponse =DiaChiResponse.builder()
                .idDiaChi(d.getIdDiaChi())
                .idTinhThanh(d.getIdTinhThanh())
                .idQuanHuyen(d.getIdQuanHuyen())
                .idPhuongXa(d.getIdPhuongXa())
                .thanhPho(getTinh(d.getIdTinhThanh()))
                .quanHuyen(getQuan(d.getIdTinhThanh(),d.getIdQuanHuyen()))
                .phuongXa(getXa(d.getIdQuanHuyen(),d.getIdPhuongXa()))
                .diaChi(d.getDiaChi())
                .ghiChu(d.getGhiChu())
                .soDienThoai(d.getSoDienThoai())
                .trangThaiMacDinh(d.getTrangThaiMacDinh())
                .khachHang(d.getKhachHang())
                .build();
         diaChiRepository.save(d);
         return diaChiResponse;
    }

    @Override
    public List<DiaChi> getAllDiaChi() {
        return diaChiRepository.findAll();
    }

    @Override
    public List<DiaChi> getAllDiaChiByKhachHang(KhachHang khachHang) {
        return diaChiRepository.findByKhachHang(khachHang);
    }

    @Override
    public List<DiaChiResponse> getDiaChiByKhachHang(Integer idKhachHang) throws Exception {
        List<DiaChi> listDiaChi =  diaChiRepository.findByIdKH(idKhachHang);
        List<DiaChiResponse> listDiaChiResponse = new ArrayList<>();
        for (DiaChi d: listDiaChi) {
            DiaChiResponse diaChiResponse =DiaChiResponse.builder()
                    .idDiaChi(d.getIdDiaChi())
                    .idTinhThanh(d.getIdTinhThanh())
                    .idQuanHuyen(d.getIdQuanHuyen())
                    .idPhuongXa(d.getIdPhuongXa())
                    .thanhPho(getTinh(d.getIdTinhThanh()))
                    .quanHuyen(getQuan(d.getIdTinhThanh(),d.getIdQuanHuyen()))
                    .phuongXa(getXa(d.getIdQuanHuyen(),d.getIdPhuongXa()))
                    .diaChi(d.getDiaChi())
                    .ghiChu(d.getGhiChu())
                    .soDienThoai(d.getSoDienThoai())
                    .trangThaiMacDinh(d.getTrangThaiMacDinh())
                    .khachHang(d.getKhachHang())
                    .build();
    listDiaChiResponse.add(diaChiResponse);
        }
        return listDiaChiResponse;
    }

    @Override
    public DiaChiResponse updateDC(Integer idDiachi, DiaChiRequest diaChiRequest
                                   ) throws Exception {
        DiaChi d  = diaChiRepository.findById(idDiachi).get();
        d.setGhiChu(diaChiRequest.getGhiChu());
        d.setIdTinhThanh(diaChiRequest.getIdTinhThanh());
        d.setIdQuanHuyen(diaChiRequest.getIdQuanHuyen());
        d.setIdPhuongXa(diaChiRequest.getIdPhuongXa());
        d.setDiaChi(diaChiRequest.getDiaChi());
        d.setSoDienThoai(diaChiRequest.getSoDienThoai());
        DiaChiResponse diaChiResponse =DiaChiResponse.builder()
                .idDiaChi(d.getIdDiaChi())
                .idTinhThanh(d.getIdTinhThanh())
                .idQuanHuyen(d.getIdQuanHuyen())
                .idPhuongXa(d.getIdPhuongXa())
                .thanhPho(getTinh(d.getIdTinhThanh()))
                .quanHuyen(getQuan(d.getIdTinhThanh(),d.getIdQuanHuyen()))
                .phuongXa(getXa(d.getIdQuanHuyen(),d.getIdPhuongXa()))
                .diaChi(d.getDiaChi())
                .ghiChu(d.getGhiChu())
                .soDienThoai(d.getSoDienThoai())
                .trangThaiMacDinh(d.getTrangThaiMacDinh())
                .khachHang(d.getKhachHang())
                .build();
        diaChiRepository.save(d);
        return diaChiResponse;
    }

    @Override
    public DiaChiResponse updateDCDefault(Integer idKhachHang,Integer idDiachi) throws Exception {
        List<DiaChi> listDiaChi =  diaChiRepository.findByIdKH(idKhachHang);
        for ( DiaChi d: listDiaChi
             ) {
            d.setTrangThaiMacDinh(0);
            diaChiRepository.save(d);
        }
        DiaChi d = diaChiRepository.findById(idDiachi).get();
        if(d == null) return null ;
        d.setTrangThaiMacDinh(1);
        diaChiRepository.save(d);
        DiaChiResponse diaChiResponse =DiaChiResponse.builder()
                .idDiaChi(d.getIdDiaChi())
                .idTinhThanh(d.getIdTinhThanh())
                .idQuanHuyen(d.getIdQuanHuyen())
                .idPhuongXa(d.getIdPhuongXa())
                .thanhPho(getTinh(d.getIdTinhThanh()))
                .quanHuyen(getQuan(d.getIdTinhThanh(),d.getIdQuanHuyen()))
                .phuongXa(getXa(d.getIdQuanHuyen(),d.getIdPhuongXa()))
                .diaChi(d.getDiaChi())
                .ghiChu(d.getGhiChu())
                .soDienThoai(d.getSoDienThoai())
                .trangThaiMacDinh(d.getTrangThaiMacDinh())
                .khachHang(d.getKhachHang())
                .build();
        return diaChiResponse;
    }

    @Override
    public void delete(Integer idDiachi) {
        diaChiRepository.deleteDC(idDiachi);
    }

    @Override
    public String getDiaChiCuThe(DiaChiRequest diaChiRequest) throws Exception {
    String diaChiCuThe = getXa(diaChiRequest.getIdQuanHuyen(), diaChiRequest.getIdPhuongXa()) + " " + getQuan(diaChiRequest.getIdTinhThanh(), diaChiRequest.getIdQuanHuyen())
            + " " + getTinh(diaChiRequest.getIdTinhThanh());
        return diaChiCuThe;
    }

    public String  getTinh(Integer idTP) throws Exception {
        HashMap<Integer,String> listTP = DiaChiCache.hashMapTinhThanh;
        String tinh = listTP.get(idTP);
        return tinh;
    }
    public String  getQuan(Integer idTP , Integer idQH) throws Exception {
        HashMap<Integer,String> listQH  = DiaChiAPI.callGetQuanHuyenAPI(idTP);
        String quan = listQH.get(idQH);
        return quan;
    }
    public String  getXa( Integer idQH, String idPX) throws Exception {
        HashMap<String, String> listPX  = DiaChiAPI.callGetPhuongXaAPI(idQH);
        String xa = listPX.get(idPX);
        return xa;
    }

}
