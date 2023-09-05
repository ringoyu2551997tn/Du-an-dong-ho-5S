package com.datn.dongho5s.GiaoHangNhanhService.Request;

import com.datn.dongho5s.GiaoHangNhanhService.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhiVanChuyenRequest {

    private Integer idQuanHuyen;
    private String idPhuongXa;
    private Integer soLuongSanPham;
    private Double trungBinhTheTich;

    public Integer getTrungBinhCacCanh (){
        Double theTichToanBoSanPham = Math.pow(Constant.DO_DAI_CANH_HOP_HANG,3)* this.soLuongSanPham;
        return (int) Math.cbrt(theTichToanBoSanPham);
    }
    public String getStringPhuongXa(){
        return String.valueOf(idPhuongXa);
    }

}
