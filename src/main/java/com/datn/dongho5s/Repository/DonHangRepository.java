package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.PhanHoi;
import com.datn.dongho5s.Service.DonHangService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {

    @Query(value = "select hdct from HoaDonChiTiet hdct where hdct.donHang.khachHang.idKhachHang = ?1 and hdct.chiTietSanPham.idChiTietSanPham = ?2 and hdct.donHang.trangThaiDonHang = 3")

    List<HoaDonChiTiet> findHDDonHang(Integer idKhachHang, Integer idChiTietSanPham);

    @Query("""
        UPDATE DonHang d
        SET d.tongTien = :tongTien
        WHERE d.idDonHang = :id
    """)
    @Modifying
    @Transactional
    void updateTongTienAdmin(@Param("id") int id,@Param("tongTien") Double tongTien);

    @Query("""
        SELECT d
        FROM DonHang d
        WHERE d.phuongThuc IS NULL 
        AND d.trangThaiDonHang = 8
        ORDER BY d.ngayTao DESC
    """)
    List<DonHang> findDonHangChuaThanhToan();

    @Query(nativeQuery = true, value = """
        SELECT SUM(h.gia_ban * h.so_luong) as tongTien
        FROM donhang d JOIN hoadonchitiet h ON d.id_don_hang = h.id_don_hang
        WHERE d.id_don_hang = :idDH
    """)
    Double tongTienAdmin(@Param("idDH") int idDH);

    @Query(value = """
                SELECT d
                FROM DonHang d
            """)
    Page<DonHang> findAll(Pageable pageable);

    @Query(value = """
                SELECT d
                FROM DonHang d
                ORDER BY d.ngayTao DESC 
            """)
    Page<DonHang> findAllSort(Pageable pageable);

    @Query(value = """
                SELECT d
                FROM DonHang d
                WHERE ( d.ngayTao  BETWEEN :dateStart AND :dateEnd )
                AND ( :status is null or d.trangThaiDonHang = :status )
            """)
    List<DonHang> findByNgayTao(
            @Param("dateStart") Date dateStart,
            @Param("dateEnd") Date dateEnd,
            @Param("status") Integer status
    );

    @Query(value = """
                SELECT d.tongTien
                FROM DonHang d
                WHERE d.idDonHang = ?1
            """)
    Double findTongTienByIdDonHang(int id);

    DonHang findByIdDonHang(int id);

    @Transactional
    @Modifying
    @Query(value = """
                UPDATE DonHang d
                SET d.trangThaiDonHang = :#{#donHang.trangThaiDonHang}
                WHERE d.idDonHang = :#{#donHang.idDonHang}
            """)
    void updateTrangThaiDonHang(@Param("donHang") DonHang donHang);

    List<DonHang> findByTrangThaiDonHang(int trangThai);


    @Query(value = "select hdct from HoaDonChiTiet hdct where hdct.donHang =?1 ")
    List<HoaDonChiTiet> findHDCTbyDH(Integer idDonhang);

    @Query(value = "select dh from DonHang dh where dh.khachHang.idKhachHang = ?1 ")
    List<DonHang> findAllHD(Integer idKhachHang);

    @Query(value = "select dh from DonHang dh where dh.khachHang.idKhachHang = ?1 and dh.trangThaiDonHang = ?2")
    List<DonHang> findHDByStatus(Integer idKhachHang, Integer trangThaiDonHang);

//    @Query("SELECT NEW com.datn.dongho5s.Entity.DonHang(o.idDonHang, o.ngayTao, o.tongTien, o.phiVanChuyen) FROM DonHang o " +
//            "WHERE o.ngayTao BETWEEN ?1 AND ?2 ORDER BY o.ngayTao ASC")
//    public List<DonHang> findByOrderBetween(Date startTime, Date endTime);

    @Query("SELECT NEW com.datn.dongho5s.Entity.DonHang(o.idDonHang, o.ngayTao, o.tongTien, o.phiVanChuyen, o.trangThaiDonHang) FROM DonHang o " +
            "WHERE o.ngayTao BETWEEN ?1 AND ?2 AND o.trangThaiDonHang = ?3 ORDER BY o.ngayTao ASC")
    public List<DonHang> findByOrderByStatusBetween(Date startTime, Date endTime, Integer status);

    public DonHang findByMaDonHang(String maDonHang);

    @Transactional
    @Modifying
    public void deleteByMaDonHang(String maDonHang);

    @Query("SELECT dh FROM DonHang dh WHERE UPPER(CONCAT( dh.maDonHang, ' ', " +
            "dh.khachHang.tenKhachHang, '', dh.diaChi)) LIKE %?1% ")
     Page<DonHang> findAllPagination(String keyword, Pageable pageable);

    @Query("SELECT dh FROM DonHang dh WHERE UPPER(CONCAT( dh.maDonHang, ' ', " +
            "dh.khachHang.tenKhachHang, '', dh.diaChi)) LIKE %?1% AND dh.trangThaiDonHang = ?2")
    Page<DonHang> findAllPaginationStatus(String keyword, Pageable pageable, Integer status);

    @Query("SELECT count(dh) FROM DonHang dh WHERE dh.trangThaiDonHang = ?1")
     Integer countDHbyStatus(Integer trangThaiDonhang);

    @Query("SELECT count(dh) FROM DonHang dh ")
    Integer countDHAll();


}
