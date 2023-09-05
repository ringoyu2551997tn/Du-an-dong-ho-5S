package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Exception.ChiTietSanPhamNotFountException;
import com.datn.dongho5s.Exception.SanPhamNotFountException;
import com.datn.dongho5s.Response.SanPhamAdminResponse;
import com.datn.dongho5s.Response.TimKiemSettingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChiTietSanPhamService {
    public static final int PRODUCT_DETAIL_PER_PAGE = 10;

    int totalPageSearchSP(String key, int pageNum);

    List<SanPhamAdminResponse> searchSP(String key, int pageNum);

    TimKiemSettingResponse getTimKiemSetting ();
    ChiTietSanPham getChiTietSanPhamById(Integer id);
    ChiTietSanPham update(ChiTietSanPham chiTietSanPham);
    Page<ChiTietSanPham> findByMaSP(String maSanPham, int pageNum);

    Page<ChiTietSanPham> getALlChiTietSanPhamPage(int pageNum);

    List<SanPhamAdminResponse> getAllSanPhamAminResponse(int pageNum);

    ChiTietSanPham findByMaChiTietSanPham(String maChimaTietSanPham);
    ChiTietSanPham getChiTietSanPhamByMa (String ma);

    public List<ChiTietSanPham> listAll();


    public ChiTietSanPham save(ChiTietSanPham chiTietSanPham);

    public ChiTietSanPham get(Integer id) throws ChiTietSanPhamNotFountException;


    public boolean isUniqueChiTietSanPham(
            String maChiTietSanPham,
            String tenSanPham,
            String tenDayDeo,
            String tenMauSac,
            String tenKichCo,
            String tenVatLieu
    );

    public boolean isUniqueChiTietSanPhamUpdate(
            Integer idChiTietSanPham,
            String maChiTietSanPham,
            String tenSanPham,
            String tenDayDeo,
            String tenMauSac,
            String tenKichCo,
            String tenVatLieu);


    Page<ChiTietSanPham> listByPageAndProductName(int pageNum, String sortField, String sortDir, String keyword, String productName);

    List<ChiTietSanPham> findByIdSp(Integer IdSP);
}

