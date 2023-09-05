package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang,Integer> {

    @Query("SELECT kh FROM KhachHang kh WHERE kh.email = :email")
    public KhachHang getKhachHangByEmail(@Param("email") String email);

 
    @Query("SELECT kh FROM KhachHang kh WHERE kh.soDienThoai = :soDienThoai")
    public KhachHang getKhachHangBySdt(@Param("soDienThoai") String soDienThoai);
  
    @Query("SELECT kh FROM KhachHang kh WHERE UPPER(CONCAT(kh.idKhachHang, ' ', kh.tenKhachHang, ' ', kh.email, '', " +
            "kh.soDienThoai, '', kh.ngaySua)) LIKE %?1%")
    public Page<KhachHang> findAll(String keyword, Pageable pageable);

//    @Query("UPDATE KhachHang kh SET kh.enabled = ?2 WHERE kh.idKhachHang = ?1")
//    @Modifying
//    void updateEnabledStatus(Integer id, boolean enabled);
    public KhachHang findByEmailAndSoDienThoai(String email, String soDienThoai);

    public KhachHang findBySoDienThoai(String phoneNumber);
}
