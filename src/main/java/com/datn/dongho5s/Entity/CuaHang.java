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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cuahang")
public class CuaHang {
    @Id
    @Column(name = "id_cua_hang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCuaHang;

    @Column(name = "ma_cua_hang")
    private String maCuaHang;

    @Column(name = "ten_cua_hang")
    private String tenCuaHang;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "thanh_pho")
    private String thanhPho;

    @Column(name = "quoc_gia")
    private String quocGia;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;
}
