package com.datn.dongho5s.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diachi")
public class DiaChi {
    @Id
    @Column(name = "id_dia_chi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDiaChi;

    @Column(name = "id_tinh_thanh")
    private Integer idTinhThanh;

    @Column(name = "id_quan_huyen")
    private Integer idQuanHuyen;

    @Column(name = "id_phuong_xa")
    private String idPhuongXa;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "ma_buu_chinh")
    private Integer maBuuChinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai_mac_dinh")
    private Integer trangThaiMacDinh;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

}
