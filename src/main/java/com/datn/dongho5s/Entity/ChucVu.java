package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
//@NoArgsConstructor
@Builder
@Table(name = "chucvu")
public class ChucVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chuc_vu")
    private Integer idChucVu;

    @Column(name = "ten_chuc_vu", length = 40, nullable = false,unique = true)
    private String tenChucVu;

    @Column(name = "TrangThai", length = 150, nullable = false)
    private String mota;

    public ChucVu() {
    }

    public ChucVu(Integer idChucVu) {
        this.idChucVu = idChucVu;
    }

    public ChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public ChucVu(String tenChucVu, String mota) {
        this.tenChucVu = tenChucVu;
        this.mota = mota;
    }

    public Integer getIdChucVu() {
        return idChucVu;
    }

    public void setIdChucVu(Integer idChucVu) {
        this.idChucVu = idChucVu;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChucVu chucVu = (ChucVu) o;
        return Objects.equals(idChucVu, chucVu.idChucVu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idChucVu);
    }

    @Override
    public String toString() {
        return this.tenChucVu;
    }
}
