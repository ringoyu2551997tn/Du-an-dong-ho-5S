package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.AnhSanPham;
import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.ThuongHieu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SanPhamDetailResponse {
    private Integer idSanPham;
    private ThuongHieu thuongHieu;
    private DanhMuc danhMuc;
    private List<AnhSanPham> listAnhSanPham;
    private String tenSanPham;
    private String maSanPham;
    private String moTaSanPham;
    private Double giaSanPham;
    private Integer trangThai;
    private List<ChiTietSanPham> listChiTietSanPham;
}
