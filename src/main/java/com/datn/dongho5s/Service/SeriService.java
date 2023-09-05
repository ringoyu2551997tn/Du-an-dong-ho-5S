package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.KhuyenMai;
import com.datn.dongho5s.Entity.Seri;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeriService {
    Seri save(Seri seri);
    List<Seri> saveMany(List<Seri> seri);
    Seri get(Integer id);
    Page<Seri> searchSeri(int pageNumber,int pageSize, String keyword);

    Integer countSeri (Integer idChiTietSanPham);
    List<Seri> findByChiTietSanPham(ChiTietSanPham chiTietSanPham, Integer soLuong);

    void updateSoLuongAdmin(int idHDCT, int soLuongCapNhat);
    List<Seri> findByHoaDonChiTiet (Integer idhoaDonChiTiet);
}
