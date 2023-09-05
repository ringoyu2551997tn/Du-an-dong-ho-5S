package com.datn.dongho5s.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsExclude;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sanpham")
public class SanPham {
    public static final int HOAT_DONG = 1;
    public static final int KHONG_HOAT_DONG = 0;
    @Id
    @Column(name = "id_san_pham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSanPham;

    @JoinColumn(name = "ma_san_pham")
    private String maSanPham;

    @ManyToOne
    @JoinColumn(name = "id_thuong_hieu")
    private ThuongHieu thuongHieu;

    @ManyToOne
    @JoinColumn(name = "id_danh_muc")
    private DanhMuc danhMuc;

    @Column(name = "ten_san_pham")
    private String tenSanPham;

    @Column(name = "mo_ta_san_pham")
    private String moTaSanPham;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany( fetch = FetchType.EAGER,mappedBy = "sanPham", cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ChiTietSanPham> listChiTietSanPham ;


    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    private List<AnhSanPham> listAnhSanPham;


    @Column(name = "main_image", nullable = false)
    private String mainImage;


    public void addExtraImage(String imageName){
        this.listAnhSanPham.add(new AnhSanPham(imageName,this));
    }

    @Transient
    public String getMainImagePath(){
        if (idSanPham == null || mainImage == null) return "/assets/images/image-thumbnail.png";
        return "/assets/images/"+ this.listAnhSanPham.get(0).getLink();
    }

    @Transient
    private String currentMainImage; // Trường ẩn để lưu tên ảnh hiện tại

    public SanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
}
