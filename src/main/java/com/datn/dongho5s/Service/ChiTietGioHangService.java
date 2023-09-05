package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Request.CartRequest;
import com.datn.dongho5s.Request.ChiTietGioHangRequest;
import com.datn.dongho5s.Request.ChiTietSanPhamRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;

import java.util.HashMap;
import java.util.List;

public interface ChiTietGioHangService {

    List<ChiTietGioHangResponse> getChiTietGioHang( Integer idKhachhnag);

    ChiTietGioHangResponse update(Integer soLuong, Integer idChiTietGioHang) throws  Exception;

    void delete(Integer id);
    void deleteAll();
    ChiTietGioHangResponse add(CartRequest cartRequest);

    ChiTietGioHangResponse addToCart(CartRequest cartRequest);

    void removeByCTSPAndKhachHang (Integer idKhachHang , HashMap<Integer,Integer> idChiTietSanPhams);
}
