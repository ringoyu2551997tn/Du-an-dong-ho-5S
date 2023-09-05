package com.datn.dongho5s.Controller.DonHang;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Response.DonHangAdminResponse;
import com.datn.dongho5s.Service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/don-hang")
public class DonHangAdminRestController {

    @Autowired
    private DonHangService donHangService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/findByTrangThai/{trangThai}")
    public ResponseEntity<?> findByTrangThaiDonHang(
            @PathVariable("trangThai") int trangThai
    ){
        List<DonHangAdminResponse> result = new ArrayList<>();
        if(trangThai==7){
            List<DonHang> listDH  = donHangService.getAll(1).getContent();
            listDH.forEach(item->{
                try {
                    result.add(toDonHangAdminResponse(item));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }else{
            List<DonHang> listDH = donHangService.findByTrangThaiDonHang(trangThai);
            listDH.forEach(item->{
                try {
                    result.add(toDonHangAdminResponse(item));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Collections.reverse(result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private DonHangAdminResponse toDonHangAdminResponse (DonHang donHang) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String detailDiaChi = "";

        if(donHang.getIdTinhThanh()!= null && donHang.getIdQuanHuyen()!= null && donHang.getIdQuanHuyen()!= null  ) {
            String tinhThanh = DiaChiCache.hashMapTinhThanh.get(donHang.getIdTinhThanh());
            String quanHuyen = DiaChiAPI.callGetQuanHuyenAPI(donHang.getIdTinhThanh()).get(donHang.getIdQuanHuyen());
            String phuongXa = DiaChiAPI.callGetPhuongXaAPI(donHang.getIdQuanHuyen()).get(donHang.getIdPhuongXa());
            detailDiaChi = tinhThanh + "-" + quanHuyen + "-" + phuongXa + "-" + donHang.getDiaChi();
        }
        DonHangAdminResponse result = DonHangAdminResponse.builder()
                .idDonHang(donHang.getIdDonHang())
                .maDonHang(donHang.getMaDonHang())
                .khachHang(donHang.getKhachHang())
                .ngayTao(formatter.format(donHang.getNgayTao()))
                .ngayCapNhap(formatter.format(donHang.getNgayCapNhap()))
                .trangThaiDonHang(donHang.getTrangThaiDonHang())
                .diaChi(detailDiaChi)
                .tongTien(donHang.getTongTien())
                .ghiChu(donHang.getGhiChu())
                .lyDo(donHang.getLyDo())
                .build();
        if(donHang.getPhiVanChuyen()!= null){
            result.setPhiVanChuyen(donHang.getPhiVanChuyen());
        }else{
            result.setPhiVanChuyen(0.0);
        }
        if(donHang.getNhanVien()!= null){
            result.setIdNhanVien(donHang.getNhanVien().getId());
        }
        return result;
    }

    @GetMapping("/search/date")
    public ResponseEntity<?> searchByDateStartanDateEnd(
            HttpSession httpSession,
            Model model,
            HttpServletRequest httpServletRequest
    ) {
        String dateStart = httpServletRequest.getParameter("dateStart");
        String dateEnd = httpServletRequest.getParameter("dateEnd");
        Integer status = Integer.valueOf(httpServletRequest.getParameter("status"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStartParse = null;
        Date dateEndParse = null;

        try {
            dateStartParse = format.parse(dateStart);
            dateEndParse = format.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        status = status == 7 ? null : status;
        List<DonHang> lst = donHangService.findByNgayTao(dateStartParse,dateEndParse,status);
        return ResponseEntity.status(HttpStatus.OK).body(lst);
    }
}
