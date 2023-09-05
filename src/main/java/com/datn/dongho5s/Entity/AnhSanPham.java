package com.datn.dongho5s.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "anhsanpham")
public class AnhSanPham {
    @Id
    @Column(name = "id_anh_san_pham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnhSanPham;

    @Column(name = "link")
    private String link;

    @Column(name = "ten_anh")
    private String tenAnh;

    @JsonIgnore
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

    public AnhSanPham(String tenAnh, SanPham sanPham) {
        this.tenAnh = tenAnh;
        this.sanPham = sanPham;
    }
}
