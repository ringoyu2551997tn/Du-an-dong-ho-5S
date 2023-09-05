package com.datn.dongho5s.Entity;

import com.datn.dongho5s.Repository.SeriRepository;
import com.datn.dongho5s.Service.SeriService;
import com.datn.dongho5s.Service.impl.SeriServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "chitietsanpham")
public class ChiTietSanPham {
    @Id
    @Column(name = "id_chi_tiet_san_pham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChiTietSanPham;

    @JoinColumn(name = "ma_chi_tiet_san_pham")
    private String maChiTietSanPham;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "id_day_deo")
    private DayDeo dayDeo;

    @ManyToOne
    @JoinColumn(name = "id_khuyen_mai")
    private KhuyenMai khuyenMai;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_vat_lieu")
    private VatLieu vatLieu;

    @ManyToOne
    @JoinColumn(name = "id_kich_co")
    private KichCo kichCo;

    @Column(name = "chieu_dai_day_deo")
    private Double chieuDaiDayDeo;

    @Column(name = "duong_kinh_mat_dong_ho")
    private Double duongKinhMatDongHo;

    @Column(name = "do_day_mat_dong_ho")
    private Double doDayMatDongHo;

    @Column(name = "do_chiu_nuoc")
    private Integer doChiuNuoc;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "gia_san_pham")
    private Double giaSanPham;


    @OneToMany(mappedBy = "chiTietSanPham", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<PhanHoi> listPhanHoi ;

}
