package com.datn.dongho5s.Request;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.GioHang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietGioHangRequest {
    private Integer idChiTietGioHang;

    private GioHang gioHang;

    private ChiTietSanPham chiTietSanPham;

    private Integer soLuongSanPham;

    private Double giaBan;

    private Date ngayTao;

    private String ghiChu;
}
