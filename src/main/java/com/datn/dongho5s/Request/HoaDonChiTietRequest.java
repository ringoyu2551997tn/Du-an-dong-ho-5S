package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonChiTietRequest {
    Integer idChiTietSanPham;
    Integer soLuong;
}
