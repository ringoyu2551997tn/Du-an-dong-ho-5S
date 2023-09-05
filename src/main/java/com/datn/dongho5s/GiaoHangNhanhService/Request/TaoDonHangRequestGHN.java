package com.datn.dongho5s.GiaoHangNhanhService.Request;

import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.GiaoHangNhanhService.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaoDonHangRequestGHN {
    private String note;
    private String toName;
    private String toPhone;
    private String toAddress;
    private Integer idQuanHuyen;
    private String idPhuongXa;
    private Integer weight;
    private List<ChiTietItemRequestGHN> listItems;
    private Double trungBinhTheTich;
    private Integer soLuongSanPham;
    private Integer phuongThuc;
    private Double thanhTien;

    public Integer getTrungBinhCacCanh (){
        Double theTichToanBoSanPham = Math.pow(Constant.DO_DAI_CANH_HOP_HANG,3)* this.soLuongSanPham;
        return (int) Math.cbrt(theTichToanBoSanPham);
    }
    public String getStringPhuongXa(){
        return String.valueOf(idPhuongXa);
    }
}
