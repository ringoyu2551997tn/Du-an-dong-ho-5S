package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.NhanVien;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoaDonChiTietResponse {
    private Integer idHoaDonChiTiet;

    private DonHang donHang;

    private ChiTietSanPham chiTietSanPham;

    private Integer soLuong;

    private Double giaBan;
}
