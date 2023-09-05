package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.Seri;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SeriRepository extends JpaRepository<Seri,Integer> {
    @Query("SELECT s FROM Seri s WHERE s.idImei LIKE %:keyword%")
    Page<Seri> findByIdImeiLike(@Param("keyword") String keyword, Pageable pageable);
    @Query(value = "select count(s) from Seri s where s.trangThai = 1 and s.chiTietSanPham.idChiTietSanPham = ?1")
    Integer countSeri (Integer chiTietSanPham);
    @Query("""
        SELECT COUNT(sr.idSeri)
        FROM Seri sr
        WHERE   sr.chiTietSanPham.idChiTietSanPham = ?1
                AND sr.trangThai = 1
                AND sr.hoaDonChiTiet is null
    """)
    int countByIdCTSPEnabled (int idCTSP);


    List<Seri> findByChiTietSanPhamAndTrangThai(ChiTietSanPham chiTietSanPham,Integer trangThai, Pageable pageable);

    @Query(nativeQuery = true, value = """
       UPDATE seri s1
       SET s1.trang_thai = 3,
           s1.id_hoa_don_chi_tiet = :idHoaDonChiTiet,
           s1.ngay_ban = CURRENT_TIMESTAMP()
       WHERE s1.id_seri IN :listSeri
    """)
    @Modifying
    @Transactional
    void themSoLuongAdmin(
        @Param("idHoaDonChiTiet") int idHoaDonChiTiet,
        @Param("listSeri") List<Integer> listSeri
    );

    @Query(nativeQuery = true, value = """
       UPDATE seri s1
       SET s1.trang_thai = 1,
           s1.id_hoa_don_chi_tiet = null,
           s1.ngay_ban = null
       WHERE s1.id_hoa_don_chi_tiet = :idHoaDonChiTiet
       LIMIT :soLuong
    """)
    @Modifying
    @Transactional
    void giamSoLuongAdmin(
            @Param("idHoaDonChiTiet") int idHoaDonChiTiet,
            @Param("soLuong") int soLuong
    );

    @Query(nativeQuery = true, value = """

                SELECT s.id_seri s
                FROM seri s
                WHERE s.trang_thai = 1
                AND   s.id_chi_tiet_san_pham = :idChiTietSanPham
                LIMIT :soLuong
    """)
    @Modifying
    @Transactional
    List<Integer> getListSeri(
            @Param("soLuong") int soLuong,
            @Param("idChiTietSanPham") int idChiTietSanPham
    );

    @Query(nativeQuery = true,value = """
        SELECT COUNT(*) AS count
        FROM seri s
        WHERE s.id_hoa_don_chi_tiet = :idHDCT
    """)
    int soLuongDaMuaByHDCT(@Param("idHDCT") int idHDCT);
    @Query(nativeQuery = true,value = """
        SELECT COUNT(*) AS count
        FROM seri s
        WHERE s.id_chi_tiet_san_pham = :idCTSP
        AND   s.id_hoa_don_chi_tiet IS NULL
    """)
    int soLuongTonByHDCT(@Param("idCTSP") int idCTSP);
    @Modifying
    @Transactional
    @Query("""
        UPDATE Seri s
        SET s.hoaDonChiTiet = null,
            s.ngayBan = null,
            s.trangThai = 1
        WHERE s.hoaDonChiTiet.idHoaDonChiTiet = ?1
    """)
    void xoaSoLuongSanPham(int idHDCT);


    @Query(value = "select s from Seri s where s.hoaDonChiTiet.idHoaDonChiTiet = ?1")
    List<Seri> findByHoaDonChiTiet (Integer idhoaDonChiTiet);
}
