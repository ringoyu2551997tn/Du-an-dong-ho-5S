package com.datn.dongho5s.Controller.RestController.DonHang;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Configure.VNPayConfig;
import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.GiaoHangNhanhService.DonHangAPI;
import com.datn.dongho5s.GiaoHangNhanhService.Request.ChiTietItemRequestGHN;
import com.datn.dongho5s.GiaoHangNhanhService.Request.PhiVanChuyenRequest;
import com.datn.dongho5s.Request.DonHangRequest;
import com.datn.dongho5s.Request.ThemDonHangRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;
import com.datn.dongho5s.Response.DonHangResponse;
import com.datn.dongho5s.Response.GiohangResponse;
import com.datn.dongho5s.Response.HoaDonChiTietResponse;
import com.datn.dongho5s.Response.VNPayUrlResponse;
import com.datn.dongho5s.Service.ChiTietGioHangService;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.GioHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import com.datn.dongho5s.Service.KhachHangService;
import com.datn.dongho5s.Utils.PhuongThucThanhToan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/don-hang")
public class DonHangRestController {

    @Autowired
    KhachHangService khachHangService;
    @Autowired
    DonHangService donHangService;
    @Autowired
    HoaDonChiTietService hdctService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    GioHangService gioHangService;
    @Autowired
    ChiTietGioHangService chiTietGioHangService;


    @PostMapping("/tinh-phi-van-chuyen")
    public ResponseEntity<?> getPhiVanChuyen(@RequestBody PhiVanChuyenRequest phiVanChuyenRequest) {
        try {
            Integer fee = DonHangAPI.getFee(phiVanChuyenRequest);
            return ResponseEntity.status(HttpStatus.OK).body(fee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/them-don-hang")
    public ResponseEntity<?> taoDonHang(@RequestBody ThemDonHangRequest themDonHangRequest) {
        try {
            KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
            DonHang donHang = DonHang.builder()
                    .khachHang(khachHang)
                    .ngayTao(new Date())
                    .trangThaiDonHang(0)
                    .idTinhThanh(themDonHangRequest.getIdTinhThanh())
                    .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
                    .idPhuongXa(themDonHangRequest.getIdPhuongXa())
                    .diaChi(themDonHangRequest.getDiaChi())
                    .phiVanChuyen(themDonHangRequest.getPhiVanChuyen())
                    .ghiChu(themDonHangRequest.getGhiChu())
                    .ngayCapNhap(new Date())
                    .maDonHang("DH"+System.currentTimeMillis())
                    .phuongThuc(PhuongThucThanhToan.TRA_SAU)
                    .tongTien(hdctService.getTongGia(themDonHangRequest.getListHoaDonChiTietRequest()))
                    .build();
            DonHang savedDonHang = donHangService.save(donHang);
            List<HoaDonChiTiet> listHoaDonChiTiet = hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListHoaDonChiTietRequest(), savedDonHang.getIdDonHang());
            hdctService.saveAll(listHoaDonChiTiet);
//            GiohangResponse gioHang = gioHangService.findGioHang(themDonHangRequest.getKhachHangId());
            HashMap<Integer,Integer> hashCTSPIdAndSoLuong = new HashMap();
            themDonHangRequest.getListHoaDonChiTietRequest().forEach(item->{
                hashCTSPIdAndSoLuong.put(item.getIdChiTietSanPham(),item.getSoLuong());
            });
            chiTietGioHangService.removeByCTSPAndKhachHang(khachHang.getIdKhachHang() , hashCTSPIdAndSoLuong);
            return ResponseEntity.status(HttpStatus.OK).body(savedDonHang);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    private List<ChiTietItemRequestGHN> toListChiTietItem(List<HoaDonChiTiet> listHDCT) {
        List<ChiTietItemRequestGHN> result = new ArrayList<>();
        listHDCT.forEach(hdct -> {
            ChiTietItemRequestGHN item = ChiTietItemRequestGHN.builder()
                    .giaBan(hdct.getGiaBan())
                    .soLuong(hdct.getSoLuong())
                    .ctsp(hdct.getChiTietSanPham())
                    .name(hdct.getChiTietSanPham().getSanPham().getTenSanPham())
                    .build();
            result.add(item);
        });
        return result;
    }

    private HashMap<Integer, String> getListTP() {
        return DiaChiCache.hashMapTinhThanh;
    }

    private HashMap<String, String> getListPX(Integer idTP) throws Exception {
        return DiaChiAPI.callGetPhuongXaAPI(idTP);
    }

    @PostMapping("/thanh-toan-vnpay")
    public ResponseEntity<VNPayUrlResponse> thanhToanVNPAY(@RequestBody ThemDonHangRequest themDonHangRequest) throws IOException {

        KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
        DonHang donHang = DonHang.builder()
                .khachHang(khachHang)
                .ngayTao(new Date())
                .trangThaiDonHang(0)
                .idTinhThanh(themDonHangRequest.getIdTinhThanh())
                .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
                .idPhuongXa(themDonHangRequest.getIdPhuongXa())
                .diaChi(themDonHangRequest.getDiaChi())
                .phiVanChuyen(themDonHangRequest.getPhiVanChuyen())
                .ghiChu(themDonHangRequest.getGhiChu())
                .ngayCapNhap(new Date())
                .maDonHang("DH"+System.currentTimeMillis())
                .tongTien(hdctService.getTongGia(themDonHangRequest.getListHoaDonChiTietRequest()))
                .phuongThuc(PhuongThucThanhToan.VNPAY)
                .build();
        DonHang savedDonHang = donHangService.save(donHang);
        List<HoaDonChiTiet> listHoaDonChiTiet = hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListHoaDonChiTietRequest(), savedDonHang.getIdDonHang());
        hdctService.saveAll(listHoaDonChiTiet);
        HashMap<Integer,Integer> hashCTSPIdAndSoLuong = new HashMap();
        themDonHangRequest.getListHoaDonChiTietRequest().forEach(item->{
            hashCTSPIdAndSoLuong.put(item.getIdChiTietSanPham(),item.getSoLuong());
        });
        chiTietGioHangService.removeByCTSPAndKhachHang(khachHang.getIdKhachHang() , hashCTSPIdAndSoLuong);
        Double amount = (hdctService.getTongGia(themDonHangRequest.getListHoaDonChiTietRequest())+ themDonHangRequest.getPhiVanChuyen()) * 100;
        String vnp_Version = VNPayConfig.version;
        String vnp_Command = VNPayConfig.command;
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String currCode = VNPayConfig.curr_code;
        String bank_code = VNPayConfig.bank_code;
        String vnp_TxnRef = String.valueOf(savedDonHang.getIdDonHang());
//        String vnp_OrderInfo = "Thanh Toán đơn hàng: "+vnp_TxnRef;
//        String orderType = request.getParameter(VNPayConfig.order_type);
        String location = VNPayConfig.location;
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_ReturnUrl = VNPayConfig.vnp_Returnurl;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount.intValue()));
        vnp_Params.put("vnp_CurrCode", currCode);
        vnp_Params.put("vnp_BankCode", bank_code);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", location);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return ResponseEntity.status(HttpStatus.OK).body(new VNPayUrlResponse(paymentUrl));
    }


  @GetMapping("/findAll/{idKhachHang}")
    public ResponseEntity<?> getAllDH(@PathVariable("idKhachHang") Integer idKhachHang) {
        try {
            List<DonHangResponse> responseList = donHangService.findAllHD(idKhachHang);
            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
  }
    @GetMapping("/findByStatus/{idKhachHang}")
    public ResponseEntity<?> getDHbyStatus(@PathVariable("idKhachHang") Integer idKhachHang, @PathParam("status") Integer status) {
        try {
            List<DonHangResponse> responseList = donHangService.findHDByStatus(idKhachHang,status);
            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/findHDCT/{idDonHang}")
    public ResponseEntity<?> findHDCT(@PathVariable("idDonHang") Integer idDonHang) {
        try {
            List<HoaDonChiTietResponse> responseList = donHangService.findHDCTbyDH(idDonHang);
            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDH(@RequestBody DonHangRequest donHangRequest){
        return ResponseEntity.status(HttpStatus.OK).body(donHangService.updateDH(donHangRequest));
    }

}
