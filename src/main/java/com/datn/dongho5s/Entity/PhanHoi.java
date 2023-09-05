package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "phanhoi")
public class PhanHoi {
    @Id
    @Column(name = "id_phan_hoi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPhanHoi;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "id_chi_tiet_san_pham")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "noi_dung_phan_hoi")
    private String noiDungPhanHoi;

    @Column(name = "thoi_gian_phan_hoi")
    private Timestamp thoiGianPhanHoi;

    @Column(name = "trang_thai_phan_hoi")
    private Integer trangThaiPhanHoi;

    @Column(name = "danh_gia")
    private Integer danhGia;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

}
