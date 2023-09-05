package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaChiRequest {
    private Integer idDiaChi;
    private Integer idTinhThanh;
    private Integer idQuanHuyen;
    private String idPhuongXa;
    private String diaChi;
    private String soDienThoai;
    private String ghiChu;

}
