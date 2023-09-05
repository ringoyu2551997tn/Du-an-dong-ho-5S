package com.datn.dongho5s.Request;

import com.datn.dongho5s.Entity.AnhSanPham;
import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.DayDeo;
import com.datn.dongho5s.Entity.KhuyenMai;
import com.datn.dongho5s.Entity.KichCo;
import com.datn.dongho5s.Entity.MauSac;
import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Entity.VatLieu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChiTietSanPhamRequest {
    private Integer idChiTietSanPham;

    private SanPham sanPham;

    private DayDeo dayDeo;

    private KhuyenMai khuyenMai;

    private MauSac mauSac;

    private VatLieu vatLieu;

    private KichCo kichCo;

    private Double chieuDaiDayDeo;

    private Double duongKinhMatDongHo;

    private Double doDayMatDongHo;

    private Integer doChiuNuoc;

    private Integer trangThai;

    private Double giaSanPham;

    private Integer soLuong;
}
