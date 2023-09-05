package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonHangRequest {
    private Integer idDonHang;
    private String lyDo;
    private Integer trangThaiDonHang;
}
