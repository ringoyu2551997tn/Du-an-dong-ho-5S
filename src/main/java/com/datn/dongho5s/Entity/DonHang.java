package com.datn.dongho5s.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "donhang")
public class DonHang {
    @Id
    @Column(name = "id_don_hang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDonHang;

    @JoinColumn(name = "ma_don_hang")
    private String maDonHang;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_giao_hang")
    private Date ngayGiaoHang;

    @Column(name = "tong_tien")
    private Double tongTien;

    @Column(name = "trang_thai_don_hang")
    private Integer trangThaiDonHang;

    @Column(name = "id_tinh_thanh")
    private Integer idTinhThanh;

    @Column(name = "id_quan_huyen")
    private Integer idQuanHuyen;

    @Column(name = "id_phuong_xa")
    private String idPhuongXa;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "phi_van_chuyen")
    private Double phiVanChuyen;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "ly_do")
    private String lyDo;

    @Column(name = "ngay_cap_nhap")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayCapNhap;

    @Column(name ="phuong_thuc")
    private Integer phuongThuc;


    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<HoaDonChiTiet> listHoaDonChiTiet ;

    public DonHang(Integer idDonHang, Date ngayTao, Double tongTien, Double phiVanChuyen) {
        this.idDonHang = idDonHang;
        this.ngayTao = ngayTao;
//        this.ngayGiaoHang = ngayGiaoHang;
        this.tongTien = tongTien;
        this.phiVanChuyen = phiVanChuyen;
    }

    public DonHang(Integer idDonHang, Date ngayTao, Double tongTien, Double phiVanChuyen, Integer trangThaiDonHang) {
        this.idDonHang = idDonHang;
        this.ngayTao = ngayTao;
//        this.ngayGiaoHang = ngayGiaoHang;
        this.tongTien = tongTien;
        this.phiVanChuyen = phiVanChuyen;
        this.trangThaiDonHang = trangThaiDonHang;
    }


    public DonHang(Double phiVanChuyen) {
        this.phiVanChuyen = phiVanChuyen;
    }

    public DonHang(Integer trangThaiDonHang) {
        this.trangThaiDonHang = trangThaiDonHang;
    }
}
