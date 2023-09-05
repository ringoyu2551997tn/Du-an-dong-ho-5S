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
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chitietgiohang")
public class ChiTietGioHang {
    @Id
    @Column(name = "id_chi_tiet_gio_hang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChiTietGioHang;

    @ManyToOne
    @JoinColumn(name = "id_gio_hang")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "id_chi_tiet_san_pham")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "so_luong_san_pham")
    private Integer soLuongSanPham;

    @Column(name = "gia_ban")
    private Double giaBan;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ghi_chu")
    private String ghiChu;


}
