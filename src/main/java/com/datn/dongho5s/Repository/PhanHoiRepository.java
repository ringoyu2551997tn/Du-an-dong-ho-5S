package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.PhanHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhanHoiRepository extends JpaRepository<PhanHoi,Integer> {

    @Query(value = "select ph from PhanHoi ph where ph.chiTietSanPham.idChiTietSanPham = ?1")
    List<PhanHoi> findAll(Integer idChiTietSanPham);
    @Query(value = "select ph from PhanHoi ph where ph.khachHang.idKhachHang = ?1 and ph.chiTietSanPham.idChiTietSanPham = ?2 ")
    Optional<PhanHoi> findPhanHoi(Integer idKhachHang, Integer idChiTietSanPham);

    @Query(value = "select count(ph) from PhanHoi ph where ph.khachHang.idKhachHang = ?1 and ph.chiTietSanPham.idChiTietSanPham = ?2")
    Long countPhanHoi(Integer idKhachHang, Integer idChiTietSanPham);

    @Query(value = "SELECT COUNT(hoadonchitiet.id_hoa_don_chi_tiet) FROM hoadonchitiet JOIN donhang on hoadonchitiet.id_don_hang = donhang.id_don_hang\n" +
            "where hoadonchitiet.id_chi_tiet_san_pham = ?2 and donhang.id_khach_hang = ?1 and donhang.trang_thai_don_hang = 3",nativeQuery = true)
    Long countHDCT(Integer idKhachHang, Integer idChiTietSanPham);





}
