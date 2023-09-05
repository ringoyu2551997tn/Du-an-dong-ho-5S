package com.datn.dongho5s.Request;

import com.datn.dongho5s.Entity.KhachHang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GioHangRequest {
    private Integer idGioHang;

    private KhachHang khachHang;

    private Date ngayTaoGioHang;

    private Integer trangThaiGioHang;

    private String ghiChu;

    private Timestamp thoiGianCapNhapGioHang;

}
