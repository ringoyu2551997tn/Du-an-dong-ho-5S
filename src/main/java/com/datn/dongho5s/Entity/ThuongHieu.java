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
import javax.persistence.Table;

@Entity
@Data
@Builder
@Table(name = "thuonghieu")
public class ThuongHieu {
    @Id
    @Column(name = "id_thuong_hieu")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idThuongHieu;

    @Column(name = "ten_thuong_hieu",length = 128, nullable = false, unique = true)
    private String tenThuongHieu;

    @Column(name = "mo_ta_thuong_hieu",length = 128, nullable = false, unique = true)
    private String moTaThuongHieu;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;

    public ThuongHieu() {
    }

    public ThuongHieu(Integer idThuongHieu, String tenThuongHieu) {
        this.idThuongHieu = idThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
    }

    public ThuongHieu(Integer idThuongHieu, String tenThuongHieu, String moTaThuongHieu, boolean enabled) {
        this.idThuongHieu = idThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
        this.moTaThuongHieu = moTaThuongHieu;
        this.enabled = enabled;
    }

    public Integer getIdThuongHieu() {
        return idThuongHieu;
    }

    public void setIdThuongHieu(Integer idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public String getMoTaThuongHieu() {
        return moTaThuongHieu;
    }

    public void setMoTaThuongHieu(String moTaThuongHieu) {
        this.moTaThuongHieu = moTaThuongHieu;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
