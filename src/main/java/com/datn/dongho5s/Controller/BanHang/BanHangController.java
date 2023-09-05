
package com.datn.dongho5s.Controller.BanHang;

import com.datn.dongho5s.Entity.*;
import com.datn.dongho5s.Export.HoaDonPdf;
import com.datn.dongho5s.Request.HoaDonAdminRequest;
import com.datn.dongho5s.Response.SanPhamAdminResponse;
import com.datn.dongho5s.Service.*;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin/ban-hang")
public class BanHangController {

    @Autowired
    SanPhamService sanPhamService;

    @Autowired
    DonHangService donHangService;

    @Autowired
    HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    KhachHangService khachHangService;

    @Autowired
    SeriService seriService;

    @Autowired
    HttpServletRequest request;

    @GetMapping
    public String getFormBanHang(
        Model model,
        HttpSession httpSession
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        HoaDonAdminRequest hoaDonAdminRequest = new HoaDonAdminRequest();
        model.addAttribute("hoaDonAdminRequest", hoaDonAdminRequest);
        model.addAttribute("thanhTien",0);
        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");

        if (httpSession.getAttribute("donHangHienTai")!= null){
            Double tongTien = 0d;
            for (HoaDonChiTiet h: donHangByMa.getListHoaDonChiTiet()) {
                    tongTien +=  h.getGiaBan() * h.getSoLuong();
            }
            model.addAttribute("thanhTien",tongTien);
            model.addAttribute("hoaDonAdminRequest", HoaDonAdminRequest
                    .builder()
                        .maHoaDon(donHangByMa.getMaDonHang())
                        .sdt(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getSoDienThoai())
                        .ngayTao(dateParseToString(donHangByMa.getNgayTao(),"yyyy-MM-dd"))
                        .tenKhachHang(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getTenKhachHang())
                    .build());
        }

        this.getListSanPham(model,1,httpSession,hoaDonAdminRequest);
        this.getListHDCT(model,1,httpSession);
        this.getListHoaDon(model,httpSession,hoaDonAdminRequest);

        return "admin/banhang/banhang";
    }


    @GetMapping("/sanpham/page/{pageNum}")
    public String getListSanPham(
        Model model,
        @PathVariable("pageNum") int pageNum,
        HttpSession httpSession,
        @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(pageNum);

        model.addAttribute("listSanPham", sanPhamList);
        model.addAttribute("listHoaDon", donHangService.findDonHangChuaThanhToan());

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", chiTietSanPhamService.getALlChiTietSanPhamPage(pageNum).getTotalPages());

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");
        model.addAttribute("thanhTien",0);
        if (donHangByMa!= null){
            List<HoaDonChiTiet> lstHDCT = hoaDonChiTietService.getHDCTByMaDonHang(donHangByMa.getMaDonHang());

            model.addAttribute("lstHDCT",lstHDCT);

            Double tongTien = 0d;

            for (HoaDonChiTiet h: donHangByMa.getListHoaDonChiTiet()) {
                tongTien += h.getGiaBan() * h.getSoLuong();
            }
            model.addAttribute("thanhTien",tongTien);
            model.addAttribute("hoaDonAdminRequest", HoaDonAdminRequest
                    .builder()
                    .maHoaDon(donHangByMa.getMaDonHang())
                    .sdt(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getSoDienThoai())
                    .ngayTao(dateParseToString(donHangByMa.getNgayTao(),"yyyy-MM-dd"))
                    .tenKhachHang(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getTenKhachHang())
                    .build());
        }

        return "admin/banhang/banhang";
    }

    @GetMapping("/hoa-don")
    public String getListHoaDon(
            Model model,
            HttpSession httpSession,
            @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1);

        model.addAttribute("listSanPham", sanPhamList);

        model.addAttribute("listHoaDon", donHangService.findDonHangChuaThanhToan());

        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", chiTietSanPhamService.getALlChiTietSanPhamPage(1).getTotalPages());

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");
        model.addAttribute("thanhTien",0);
        if (donHangByMa!= null){
            List<HoaDonChiTiet> lstHDCT = hoaDonChiTietService.getHDCTByMaDonHang(donHangByMa.getMaDonHang());

            model.addAttribute("lstHDCT",lstHDCT);

            Double tongTien = 0d;

            for (HoaDonChiTiet h: donHangByMa.getListHoaDonChiTiet()) {
                tongTien += h.getGiaBan() * h.getSoLuong();
            }
            model.addAttribute("thanhTien",tongTien);
            model.addAttribute("hoaDonAdminRequest", HoaDonAdminRequest
                    .builder()
                    .maHoaDon(donHangByMa.getMaDonHang())
                    .sdt(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getSoDienThoai())
                    .ngayTao(dateParseToString(donHangByMa.getNgayTao(),"yyyy-MM-dd"))
                    .tenKhachHang(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getTenKhachHang())
                    .build());
        }

        return "admin/banhang/banhang";
    }

    @GetMapping("/hoa-don-chi-tiet/page/{pageNum}")
    public String getListHDCT(
            Model model,
            @PathVariable("pageNum") int pageNum,
            HttpSession httpSession
    ) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");
        List<HoaDonChiTiet> lstHDCT;
        if (donHangByMa!= null){
            lstHDCT = hoaDonChiTietService.getHDCTByMaDonHang(donHangByMa.getMaDonHang());
        } else {
            lstHDCT = hoaDonChiTietService.getHDCTByMaDonHang(null);
        }

        model.addAttribute("lstHDCT",lstHDCT);

        return "admin/banhang/banhang";
    }

    @PostMapping("/hoa-don/tao-moi")
    public String taoHoaDon(
            Model model,
            HttpSession httpSession,
            @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest, RedirectAttributes redirectAttributes
            ){

        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", chiTietSanPhamService.getALlChiTietSanPhamPage(1).getTotalPages());

        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        NhanVien nhanVien = (NhanVien) session.getAttribute("admin");
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", chiTietSanPhamService.getALlChiTietSanPhamPage(1).getTotalPages());
      
        String maDonHangCD = this.generateMaHD();

        KhachHang khachHang = (KhachHang) httpSession.getAttribute("khachHangExist");

        if (khachHang == null){
            KhachHang kh =  khachHangService.findByPhoneNumber(hoaDonAdminRequest.getSdt().trim());
            if (kh!= null){
                hoaDonAdminRequest.setTenKhachHang(kh.getTenKhachHang());
                hoaDonAdminRequest.setSdt(kh.getSoDienThoai());
                redirectAttributes.addFlashAttribute("messageTrungSDT","Số điện thoại bạn nhập đã tồn tại! Vui lòng tìm kiếm hoặc đổi số điện thoại khác!");
                return "redirect:/admin/ban-hang";
            } else{
                khachHang = KhachHang
                        .builder()
                        .tenKhachHang(hoaDonAdminRequest.getTenKhachHang())
                        .soDienThoai(hoaDonAdminRequest.getSdt())
                        .enabled(true)
                        .listDiaChi(null)
                        .email(null)
                        .gioiTinh(null)
                        .password(null)
                        .ngaySinh(null)
                        .ngaySua(new Date())
                        .thoiGianTaoTaiKhoan(null)
                        .build();

                khachHangService.saveKhachHang(khachHang);
            }
        }

        DonHang donHang = DonHang
                .builder()
                .maDonHang(maDonHangCD)
                .trangThaiDonHang(8)
                .tongTien(0d)
                .nhanVien(nhanVien)
                .ngayCapNhap(new Date())
                .ngayGiaoHang(new Date())
                .khachHang(khachHang)
                .ngayTao(new Date())
                .build();

        donHangService.save(donHang);

        DonHang donHangByMa = donHangService.findByMaDonHang(maDonHangCD);

        httpSession.setAttribute("donHangHienTai",donHangByMa);

        return "redirect:/admin/ban-hang/hoa-don/" + maDonHangCD;
    }

    @GetMapping("/khach-hang/tim-kiem")
    public String findKHByPfindhoneNumber(
            Model model,
            @RequestParam("phoneNumber") String phoneNumber,
            @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest,
            HttpSession httpSession
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        KhachHang khachHang = khachHangService.findByPhoneNumber(phoneNumber);
        model.addAttribute("thanhTien",0);
        if (khachHang!= null){
            hoaDonAdminRequest = HoaDonAdminRequest
                    .builder()
                    .maHoaDon("")
                    .sdt(khachHang.getSoDienThoai().trim())
                    .ngayTao(dateParseToString(new Date(),"yyyy-MM-dd"))
                    .tenKhachHang(khachHang.getTenKhachHang().trim())
                    .build();
            model.addAttribute("hoaDonAdminRequest",hoaDonAdminRequest);

            httpSession.setAttribute("khachHangExist",khachHang);
        }

        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1);

        model.addAttribute("listSanPham",sanPhamList);

        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", chiTietSanPhamService.getALlChiTietSanPhamPage(1).getTotalPages());

        model.addAttribute("listHoaDon", donHangService.findDonHangChuaThanhToan());
        return "admin/banhang/banhang";
    }
    @GetMapping("/tim-kiem")
    public String searchSP(
        @RequestParam("searchSP") String key,
        Model model,
        HttpSession httpSession,
        @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest
    ){

        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        // set list san pham
        model.addAttribute("listSanPham",chiTietSanPhamService.searchSP(key.trim(),1));

        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", chiTietSanPhamService.totalPageSearchSP(key.trim(),1));

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");
        model.addAttribute("thanhTien",0);

        if (donHangByMa!= null){
            List<HoaDonChiTiet> lstHDCT = hoaDonChiTietService.getHDCTByMaDonHang(donHangByMa.getMaDonHang());

            model.addAttribute("lstHDCT",lstHDCT);

            Double tongTien = 0d;

            for (HoaDonChiTiet h: donHangByMa.getListHoaDonChiTiet()) {
                tongTien += h.getGiaBan() * h.getSoLuong();
            }
            model.addAttribute("thanhTien",tongTien);

            model.addAttribute("hoaDonAdminRequest", HoaDonAdminRequest
                    .builder()
                    .maHoaDon(donHangByMa.getMaDonHang())
                    .sdt(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getSoDienThoai())
                    .ngayTao(dateParseToString(donHangByMa.getNgayTao(),"yyyy-MM-dd"))
                    .tenKhachHang(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getTenKhachHang())
                    .build());
        }

        model.addAttribute("listHoaDon", donHangService.findDonHangChuaThanhToan());
        return "admin/banhang/banhang";
    }

    @GetMapping("/khach-hang/api/{phoneNumber}")
    @ResponseBody
    public ResponseEntity<String> findKHByPfindhoneNumber(
        @PathVariable("phoneNumber") String phoneNumber
    ){

        KhachHang khachHang = khachHangService.findByPhoneNumber(phoneNumber);
        if (khachHang!= null) {
            return ResponseEntity.status(HttpStatus.OK).body(khachHang.getSoDienThoai());
        }
        return null;
    }
    @GetMapping("/seri/api/{idHDCT}")
    @ResponseBody
    public ResponseEntity<Integer> validateImeiUpdate(
        @PathVariable("idHDCT") int idHDCT
    ){
        Integer soLuongMax = donHangService.soLuongImeiCoTheCapNhat(idHDCT);
        if (soLuongMax!= null) {
            return ResponseEntity.status(HttpStatus.OK).body(soLuongMax);
        }
        return null;
    }

    @GetMapping("/hoa-don/{maHoaDon}")
    public String chonHoaDon(
        @PathVariable("maHoaDon") String maHoaDon,
        Model model,
        HttpSession httpSession,
        @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest,
        HttpServletResponse response
    ) throws IOException {
      
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        DonHang donHangByMa = donHangService.findByMaDonHang(maHoaDon);

        if (donHangByMa==null){
            response.sendRedirect("/admin/ban-hang");
        } else {
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", chiTietSanPhamService.getALlChiTietSanPhamPage(1).getTotalPages());

            httpSession.setAttribute("donHangHienTai",donHangByMa);

            Double tongTien = 0d;

            for (HoaDonChiTiet h: donHangByMa.getListHoaDonChiTiet()) {
                tongTien += h.getGiaBan() * h.getSoLuong();
            }

            model.addAttribute("thanhTien",tongTien);
            hoaDonAdminRequest = HoaDonAdminRequest
                    .builder()
                    .maHoaDon(donHangByMa.getMaDonHang())
                    .sdt(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getSoDienThoai())
                    .ngayTao(dateParseToString(donHangByMa.getNgayTao(),"yyyy-MM-dd"))
                    .tenKhachHang(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getTenKhachHang())
                    .build();
            model.addAttribute("hoaDonAdminRequest",hoaDonAdminRequest);

            // set list san pham
            List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1);

            model.addAttribute("listSanPham",sanPhamList);

            //set list hdct

            List<HoaDonChiTiet> lstHDCT = hoaDonChiTietService.getHDCTByMaDonHang(donHangByMa.getMaDonHang());

            model.addAttribute("lstHDCT",lstHDCT);

            //set list ctsp

            model.addAttribute("lstCTSP",chiTietSanPhamService.getAllSanPhamAminResponse(1));
            model.addAttribute("listHoaDon", donHangService.findDonHangChuaThanhToan());
        }


        return "admin/banhang/banhang";
    }

    @PostMapping("/them/{maCTSP}/{soLuong}")
    public String themSoLuongSanPham(
        @PathVariable("maCTSP") String maCTSP,
        @PathVariable("soLuong") int soLuong,
        HttpSession httpSession,
        Model model
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        // them san pham hoac cap nhat san pham neu ctsp đã tồn tại
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findByMaChiTietSanPham(maCTSP);

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");

        hoaDonChiTietService.themSoLuongSanPham(soLuong,chiTietSanPham,donHangByMa);

        donHangService.updateTongTienAdmin(donHangByMa.getIdDonHang());

        return "redirect:/admin/ban-hang/hoa-don/" + donHangByMa.getMaDonHang();
    }

    @PostMapping("/hoa-don-chi-tiet/{idHDCT}")
    public String xoaHDCT(
            @PathVariable("idHDCT") int idHDCT,
            HttpSession httpSession,
            Model model
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        // xoa hoa don chi tiet
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.findHoaDonChiTietById(idHDCT);
        hoaDonChiTietService.xoaHDCT(hoaDonChiTiet);

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");


        donHangService.updateTongTienAdmin(donHangByMa.getIdDonHang());

        return "redirect:/admin/ban-hang/hoa-don/" + donHangByMa.getMaDonHang();
    }

    @PostMapping("/hoa-don-chi-tiet/sua/{idHDCT}/so-luong/{soLuongCapNhat}")
    public String updateHDCT(
            @PathVariable("idHDCT") int idHDCT,
            @PathVariable("soLuongCapNhat") int soLuongCapNhat,
            HttpSession httpSession,
            Model model
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        seriService.updateSoLuongAdmin(idHDCT, soLuongCapNhat);

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");

        //update tong tien
        donHangService.updateTongTienAdmin(donHangByMa.getIdDonHang());
        return "redirect:/admin/ban-hang/hoa-don/" + donHangByMa.getMaDonHang();
    }

    @PostMapping("/hoa-don/thanh-toan")
    public String thanhToan(
        HttpSession httpSession
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        DonHang donHang = (DonHang) httpSession.getAttribute("donHangHienTai");
        // chuyen trang thai hoan thanh don hang
        donHang.setTrangThaiDonHang(3);
        //thanh toan
        donHangService.thanhToanAdmin(donHang);

        httpSession.removeAttribute("donHangHienTai");
        httpSession.removeAttribute("khachHangExist");
        return "redirect:/admin/ban-hang";
    }

    @GetMapping("/hoa-don/xuat-hoa-don")
    public void xuatHoaDon(
        HttpServletResponse response,
        HttpSession httpSession
    ) throws Exception {

        DonHang donHang = (DonHang) httpSession.getAttribute("donHangHienTai");

        //xuat
        List<HoaDonChiTiet> lst = hoaDonChiTietService.getByIdDonHang(donHang.getIdDonHang());
        HoaDonPdf hoaDonPdf = new HoaDonPdf();
        hoaDonPdf.exportToPDF(response, lst, donHang);
    }

    @PostMapping("/hoa-don/huy")
    public String huyHoaDon(
        HttpSession httpSession
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        DonHang donHang = (DonHang) httpSession.getAttribute("donHangHienTai");
        // huy hoa don
        donHangService.xoaDonHangAdmin(donHang);

        httpSession.removeAttribute("donHangHienTai");
        httpSession.removeAttribute("khachHangExist");

        return "redirect:/admin/ban-hang";
    }
    public String generateMaHD(){
        return "DH" +
                new Date().toString().toUpperCase().replaceAll("[^a-zA-Z0-9]", "").substring(2,7) +
                UUID.randomUUID().toString().toUpperCase().replaceAll("[^a-zA-Z0-9]", "").substring(2,8);
    }

    public Date stringParseToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String dateParseToString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
