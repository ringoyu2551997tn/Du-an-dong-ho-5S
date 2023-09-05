package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Repository.ChiTietGioHangRepository;
import com.datn.dongho5s.Repository.ChiTietSanPhamRepository;
import com.datn.dongho5s.Repository.GioHangRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Request.CartRequest;
import com.datn.dongho5s.Request.ChiTietGioHangRequest;
import com.datn.dongho5s.Request.ChiTietSanPhamRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;
import com.datn.dongho5s.Service.ChiTietGioHangService;
import com.datn.dongho5s.Service.SeriService;
import com.datn.dongho5s.mapper.ChiTietGioHangMapping;
import com.datn.dongho5s.mapper.ChiTietSanPhamMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {

    @Autowired
    ChiTietGioHangRepository chiTietGioHangRepository;

    @Autowired
    SeriService seriService;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Override
    public List<ChiTietGioHangResponse> getChiTietGioHang( Integer idKhachHang) {
        List<ChiTietGioHang> chiTietGioHangList = chiTietGioHangRepository.giohangChiTiet(idKhachHang);
        for (ChiTietGioHang chiTietGioHang: chiTietGioHangList  ) {
            Integer countSeri = seriService.countSeri(chiTietGioHang.getChiTietSanPham().getIdChiTietSanPham());
            if(chiTietGioHang.getSoLuongSanPham() > countSeri){
                chiTietGioHang.setSoLuongSanPham(countSeri);
            }
        }
        List<ChiTietGioHangResponse> responseList = chiTietGioHangList.stream().map(ChiTietGioHangMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }


    @Override
    public ChiTietGioHangResponse update(Integer soLuong, Integer idChiTietGioHang) throws Exception {
//        System.out.println(chiTietGioHang+ "chitiet");
        try {
            ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(idChiTietGioHang).get();
            chiTietGioHang.setSoLuongSanPham(soLuong);
            ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang));
//            GioHang gioHang = chiTietGioHangResponse.getGioHang();
//            gioHang.setThoiGianCapNhapGioHang(new Timestamp(new Date().getTime()));
//            chiTietGioHangResponse.setGioHang(gioHang);
            return chiTietGioHangResponse;
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    @Override
    public ChiTietGioHangResponse add(CartRequest cartRequest) {
        KhachHang khachHang = khachHangRepository.findById(cartRequest.getIdKhachHang()).get();
        GioHang gioHang = GioHang.builder()
                .idGioHang(null)
                .ngayTaoGioHang(new Date())
                .trangThaiGioHang(1)
                .khachHang(khachHang)
                .thoiGianCapNhapGioHang(new Timestamp(new Date().getTime()))
                .build();
        gioHangRepository.save(gioHang);
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(cartRequest.getIdChiTietSanPham()).get();
        if(seriService.countSeri(cartRequest.getIdChiTietSanPham())< cartRequest.getSoLuong()){
            return null ;
        }
        ChiTietGioHang chiTietGioHang = ChiTietGioHang.builder()
                .idChiTietGioHang(1)
                .chiTietSanPham(chiTietSanPham)
                .gioHang(gioHang)
                .ghiChu("")
                .giaBan(cartRequest.getGiaSanPham())
                .ngayTao(new Date())
                .soLuongSanPham(cartRequest.getSoLuong())
                .build();
        ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang));
        return chiTietGioHangResponse;
    }

    @Override
    public ChiTietGioHangResponse addToCart(CartRequest cartRequest) {
        try {
            GioHang gioHang = gioHangRepository.findGioHang(cartRequest.getIdKhachHang());
            if (gioHang == null) {
                return add(cartRequest);
            } else {
                ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(cartRequest.getIdChiTietSanPham()).get();
                ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findChiTietGioHangByCTSP(cartRequest.getIdChiTietSanPham(), cartRequest.getIdKhachHang());
                if (chiTietGioHang == null) {

                    ChiTietGioHang chiTietGioHang1 = ChiTietGioHang.builder()
                            .idChiTietGioHang(null)
                            .chiTietSanPham(chiTietSanPham)
                            .gioHang(gioHang)
                            .ghiChu("")
                            .giaBan(cartRequest.getGiaSanPham())
                            .ngayTao(new Date())
                            .soLuongSanPham(cartRequest.getSoLuong())
                            .build();
                    if(seriService.countSeri(cartRequest.getIdChiTietSanPham()) < cartRequest.getSoLuong()){
                     return null ;
                    }
                    ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang1));
                    return chiTietGioHangResponse;
                } else {
                    if(seriService.countSeri(cartRequest.getIdChiTietSanPham()) < chiTietGioHang.getSoLuongSanPham()+ cartRequest.getSoLuong()){
                        return  null ;
                    }else{
                        chiTietGioHang.setSoLuongSanPham(chiTietGioHang.getSoLuongSanPham()+ cartRequest.getSoLuong());
                    }
                    chiTietGioHang.setGiaBan(cartRequest.getGiaSanPham());
                    chiTietGioHang.setChiTietSanPham(chiTietSanPham);
                    ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang));
                    return chiTietGioHangResponse;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("lỗi");
            return null;
        }
    }

    @Override
    public void removeByCTSPAndKhachHang(Integer idKhachHang, HashMap<Integer,Integer> idChiTietSanPhamAndSoLuong) {
        List<ChiTietGioHang> listChiTietGioHang = new ArrayList<>();
        idChiTietSanPhamAndSoLuong.forEach((id,soLuong)->{
            ChiTietGioHang ctgh = chiTietGioHangRepository.findChiTietGioHangByCTSPVaKhachHang(id,soLuong,idKhachHang);
            if(ctgh != null) {
                listChiTietGioHang.add(ctgh);
            }
        });
        chiTietGioHangRepository.deleteAll(listChiTietGioHang);
    }


    public void delete(Integer id) {
        chiTietGioHangRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        chiTietGioHangRepository.deleteAll();
    }


}
