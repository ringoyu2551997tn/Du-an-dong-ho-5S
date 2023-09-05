package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    @Query(value = """
                SELECT h
                FROM HoaDonChiTiet h JOIN DonHang dh ON h.donHang.idDonHang = dh.idDonHang
                WHERE h.donHang.idDonHang = ?1
                ORDER BY h.idHoaDonChiTiet ASC
            """)
    public List<HoaDonChiTiet> findHDCTBYIdDonHang(int id);

    List<HoaDonChiTiet> findByDonHang(DonHang donHang);

    @Query(value = """
        SELECT h 
        FROM HoaDonChiTiet h JOIN DonHang d ON h.donHang.idDonHang = d.idDonHang
        WHERE d.maDonHang = ?1
    """)
    public List<HoaDonChiTiet> findByMaDonHang(String maDonHang);

    @Query(value = """
        UPDATE HoaDonChiTiet h
        SET h.soLuong = h.soLuong + ?1
        WHERE h.idHoaDonChiTiet = ?2
    """)
    @Modifying
    public void updateSoLuongSanPham(int soLuong, int idHoaDonChiTiet);

    @Query(value = """
        DELETE 
        FROM HoaDonChiTiet h
        WHERE h.idHoaDonChiTiet = ?1
    """)
    @Modifying
    public void xoaHDCT(int idHoaDonChiTiet);
    public HoaDonChiTiet findByIdHoaDonChiTiet(int id);

    @Query(value = """
        UPDATE HoaDonChiTiet h
        SET h.soLuong = ?1
        WHERE h.idHoaDonChiTiet = ?2
    """)
    @Modifying
    public void updateSoLuongSanPhamWithEdit(int soLuong, int idHoaDonChiTiet);

    @Query("SELECT NEW  com.datn.dongho5s.Entity.HoaDonChiTiet(d.chiTietSanPham.sanPham.danhMuc.ten, d.soLuong," +
            "d.giaBan, d.donHang.phiVanChuyen)" + "  FROM HoaDonChiTiet d WHERE d.donHang.ngayTao BETWEEN ?1 AND ?2 AND d.donHang.trangThaiDonHang = ?3")
    public List<HoaDonChiTiet> findWithCategoryAndTimeBetween(Date startTime, Date endTime, Integer status);

    @Query("SELECT NEW  com.datn.dongho5s.Entity.HoaDonChiTiet(d.soLuong, d.chiTietSanPham.sanPham.tenSanPham," +
            "d.giaBan, d.donHang.phiVanChuyen)" + "  FROM HoaDonChiTiet d WHERE d.donHang.ngayTao BETWEEN ?1 AND ?2 AND d.donHang.trangThaiDonHang = ?3")
    public List<HoaDonChiTiet> findWithProductAndTimeBetween(Date startTime, Date endTime, Integer status);

    @Query("SELECT NEW  com.datn.dongho5s.Entity.HoaDonChiTiet(d.chiTietSanPham.idChiTietSanPham, d.soLuong, d.giaBan, " +
            "d.donHang.phiVanChuyen)" + "  FROM HoaDonChiTiet d WHERE d.donHang.ngayTao BETWEEN ?1 AND ?2 AND d.donHang.trangThaiDonHang = ?3"
            )
    public List<HoaDonChiTiet> findWithOrderDetailAndTimeBetween(Date startTime, Date endTime, Integer status);
    @Modifying
    @Transactional
    public void deleteByDonHang(DonHang donHang);

    @Query("SELECT count(hd) FROM HoaDonChiTiet hd WHERE hd.donHang.idDonHang = ?1")
    Integer countHD(Integer id);
}
