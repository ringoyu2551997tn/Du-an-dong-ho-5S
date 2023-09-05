package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "khuyenmai")
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_khuyen_mai")
    private Integer idKhuyenMai;

    @Column(name = "ma_khuyen_mai", unique = true)
    private String maKhuyenMai;

    @Column(name = "ten_khuyen_mai", unique = true)
    private String tenKhuyenMai;

    @Column(name = "mo_ta_khuyen_mai")
    private String moTaKhuyenMai;

    @Column(name = "ngay_bat_dau")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayKetThuc;


    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "chiet_khau")
    private Integer chietKhau;

    // Getters and setters
    public Integer getIdKhuyenMai() {
        return idKhuyenMai;
    }

    public void setIdKhuyenMai(Integer idKhuyenMai) {
        this.idKhuyenMai = idKhuyenMai;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public String getMoTaKhuyenMai() {
        return moTaKhuyenMai;
    }

    public void setMoTaKhuyenMai(String moTaKhuyenMai) {
        this.moTaKhuyenMai = moTaKhuyenMai;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
