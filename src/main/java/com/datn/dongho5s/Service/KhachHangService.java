package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Exception.KhachHangNotFoundException;
import com.datn.dongho5s.Response.ThongTinCaNhanResponse;
import com.datn.dongho5s.Response.ThongTinToCheckoutResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface KhachHangService {
    public static final int CUSTOMERS_PER_PAGE = 4;
    public KhachHang findKhachHangById(Integer id);

    KhachHang get(Integer id) throws KhachHangNotFoundException, Exception;

    public KhachHang updateThongTinCaNhan(Integer idKhachhang, ThongTinCaNhanResponse thongTinCaNhanResponse);
    public ThongTinCaNhanResponse getThongTinCaNhanById(Integer id);
    public ThongTinToCheckoutResponse getThongTinToCheckout(Integer id);

    public void saveKhachHang(KhachHang khachHang);

    List<KhachHang> getAllKhachHang();

    List<KhachHang> getAllPaginationVatLieu();

    Page<KhachHang> listByPage(int pageNumber, String sortField, String sortDir, String keyword);

    boolean checkUnique(Integer id, String email, String soDT);

//    void updateKhachHangEnabledStatus(Integer id, boolean enabled);
  
    KhachHang findByPhoneNumber(String phoneNumber);

}
