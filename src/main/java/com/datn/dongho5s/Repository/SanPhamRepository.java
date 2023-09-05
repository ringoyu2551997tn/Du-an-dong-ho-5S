package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Request.TimKiemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Query(value = "SELECT  * FROM sanpham where sanpham.trang_thai = 1 ORDER BY sanpham.id_san_pham DESC LIMIT 8", nativeQuery = true)
    List<SanPham> getSPnew();


    @Query(value = "SELECT h.chiTietSanPham.sanPham  FROM HoaDonChiTiet h where h.donHang.trangThaiDonHang = 3 group by h.chiTietSanPham.sanPham   ORDER BY  SUM(h.soLuong) DESC")
    List<SanPham> getSPchay();

    @Query(value = "SELECT s.listChiTietSanPham  FROM SanPham s where s.idSanPham = ?1 and s.trangThai = 1 ")
    List<ChiTietSanPham> getCTSP(Integer idSanPham);

    /**
     * @param pageable
     * @return
     * product pagination
     */
//    @Query(value = """
//        SELECT sp
//        FROM SanPham sp
//    """)
//    Page<SanPham> getPageSanPham(Pageable pageable);

    @Query("SELECT sp FROM SanPham  sp WHERE UPPER(CONCAT(sp.idSanPham,' ',sp.maSanPham,' ', sp.tenSanPham, ' ',sp.thuongHieu,' ', sp.danhMuc, ' ',sp.moTaSanPham)) LIKE %?1%")
    public Page<SanPham> findAll(String keyword, Pageable pageable);

    //@Query("SELECT th FROM ThuongHieu th WHERE UPPER(CONCAT(th.idThuongHieu, ' ', th.tenThuongHieu, ' ', th.moTaThuongHieu)) LIKE %?1%")
    //    public Page<ThuongHieu> findAll(String keyword, Pageable pageable);


    public SanPham findByTenSanPham(String tenSanPham);

    public  SanPham findByMaSanPham(String ma);
    public List<SanPham> findByThuongHieu(ThuongHieu tenSanPham);


    public SanPham findByIdSanPham(Integer idSanPham);


}
