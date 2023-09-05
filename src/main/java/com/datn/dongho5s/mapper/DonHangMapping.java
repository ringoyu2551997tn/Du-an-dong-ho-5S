package com.datn.dongho5s.mapper;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Request.GioHangRequest;
import com.datn.dongho5s.Response.DonHangResponse;
import com.datn.dongho5s.Response.GiohangResponse;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TimeZone;

@Data
public class DonHangMapping {

    public static DonHangResponse mapEntitytoResponseBT(DonHang donHang) throws Exception {
        DonHangResponse donHangResponse;
        if(donHang.getIdTinhThanh() == null || donHang.getIdQuanHuyen()== null|| donHang.getIdPhuongXa() == null ){
             donHangResponse =  DonHangResponse.builder()
                    .idDonHang(donHang.getIdDonHang())
                    .maDonHang(donHang.getMaDonHang())
                    .diaChi(null)
                    .ghiChu(donHang.getGhiChu())
                    .PhuongXa(null)
                    .QuanHuyen(null)
                    .TinhThanh(null)
                    .ngayTao(donHang.getNgayTao())
                    .ngayGiaoHang(donHang.getNgayGiaoHang())
                    .khachHang(donHang.getKhachHang())
                    .phiVanChuyen(donHang.getPhiVanChuyen())
                    .trangThaiDonHang(donHang.getTrangThaiDonHang())
                    .tongTien(donHang.getTongTien())
                    .hoaDonChiTiets(donHang.getListHoaDonChiTiet())
                    .lyDo(donHang.getLyDo())
                    .build();

        }else {
             donHangResponse = DonHangResponse.builder()
                    .idDonHang(donHang.getIdDonHang())
                    .maDonHang(donHang.getMaDonHang())
                    .diaChi(donHang.getDiaChi())
                    .ghiChu(donHang.getGhiChu())
                    .PhuongXa(getXa(donHang.getIdQuanHuyen(), donHang.getIdPhuongXa()))
                    .QuanHuyen(getQuan(donHang.getIdTinhThanh(), donHang.getIdQuanHuyen()))
                    .TinhThanh(getTinh(donHang.getIdTinhThanh()))
                    .ngayTao(donHang.getNgayTao())
                    .ngayGiaoHang(donHang.getNgayGiaoHang())
                    .khachHang(donHang.getKhachHang())
                    .phiVanChuyen(donHang.getPhiVanChuyen())
                    .trangThaiDonHang(donHang.getTrangThaiDonHang())
                    .tongTien(donHang.getTongTien())
                    .hoaDonChiTiets(donHang.getListHoaDonChiTiet())
                    .lyDo(donHang.getLyDo())
                    .build();
        }
        return donHangResponse;
    }

    public  static DonHangResponse mapEntitytoResponse(DonHang donHang) throws Exception {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        DonHangResponse donHangResponse;
        if(donHang.getDiaChi() == null || donHang.getIdTinhThanh() == null || donHang.getIdQuanHuyen()== null|| donHang.getIdPhuongXa() == null ){
            donHangResponse =  DonHangResponse.builder()
                    .idDonHang(donHang.getIdDonHang())
                    .maDonHang(donHang.getMaDonHang())
                    .diaChi(null)
                    .ghiChu(donHang.getGhiChu())
                    .PhuongXa(null)
                    .QuanHuyen(null)
                    .TinhThanh(null)
                    .ngayTao(donHang.getNgayTao())
                    .ngayGiaoHang(donHang.getNgayGiaoHang())
                    .khachHang(donHang.getKhachHang())
                    .phiVanChuyen(donHang.getPhiVanChuyen())
                    .trangThaiDonHang(donHang.getTrangThaiDonHang())
                    .tongTien(donHang.getTongTien())
                    .hoaDonChiTiets(donHang.getListHoaDonChiTiet())
                    .lyDo(donHang.getLyDo())
                    .build();

        }else {
             donHangResponse = DonHangResponse.builder()
                    .idDonHang(donHang.getIdDonHang())
                    .maDonHang(donHang.getMaDonHang())
                    .diaChi(donHang.getDiaChi())
                    .ghiChu(donHang.getGhiChu())
                    .PhuongXa(getXa(donHang.getIdQuanHuyen(), donHang.getIdPhuongXa()))
                    .QuanHuyen(getQuan(donHang.getIdTinhThanh(), donHang.getIdQuanHuyen()))
                    .TinhThanh(getTinh(donHang.getIdTinhThanh()))
                    .ngayTao(donHang.getNgayTao())
                    .ngayGiaoHang(donHang.getNgayGiaoHang())
                    .khachHang(donHang.getKhachHang())
                    .phiVanChuyen(donHang.getPhiVanChuyen())
                    .trangThaiDonHang(donHang.getTrangThaiDonHang())
                    .tongTien(donHang.getTongTien())
                    .hoaDonChiTiets(donHang.getListHoaDonChiTiet())
                    .lyDo(donHang.getLyDo())
                    .ngayCapNhap(df.format(donHang.getNgayCapNhap()))
                    .build();
        }
        return donHangResponse;
    }


    public static String  getTinh(Integer idTP) throws Exception {
        HashMap<Integer,String> listTP = DiaChiCache.hashMapTinhThanh;
        String tinh = listTP.get(idTP);
        return tinh;
    }
    public static String  getQuan(Integer idTP , Integer idQH) throws Exception {
        HashMap<Integer,String> listQH  = DiaChiAPI.callGetQuanHuyenAPI(idTP);
        String quan = listQH.get(idQH);
        return quan;
    }
    public static String  getXa( Integer idQH, String idPX) throws Exception {
        HashMap<String, String> listPX  = DiaChiAPI.callGetPhuongXaAPI(idQH);
        String xa = listPX.get(idPX);
        return xa;
    }
}
