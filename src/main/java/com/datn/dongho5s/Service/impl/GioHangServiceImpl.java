package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.Repository.ChiTietGioHangRepository;
import com.datn.dongho5s.Repository.GioHangRepository;
import com.datn.dongho5s.Request.GioHangRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;
import com.datn.dongho5s.Response.GiohangResponse;
import com.datn.dongho5s.Service.GioHangService;
import com.datn.dongho5s.mapper.ChiTietGioHangMapping;
import com.datn.dongho5s.mapper.GioHangMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    GioHangRepository gioHangRepository;
    @Override
    public GiohangResponse addGioHang(GioHangRequest gioHangRequest) {
        GioHang gioHang = GioHangMapping.mapRequestToEntity(gioHangRequest);
        GiohangResponse gioHangResponse = GioHangMapping.mapEntitytoResponse(gioHangRepository.save(gioHang));
        return gioHangResponse;
    }

    @Override
    public GiohangResponse findGioHang(Integer idKhachHang) {
        //find khachHang 1
        GioHang gioHang = gioHangRepository.findGioHang(1);
        GiohangResponse gioHangResponse = GioHangMapping.mapEntitytoResponse(gioHang);
        return gioHangResponse;
    }
}
