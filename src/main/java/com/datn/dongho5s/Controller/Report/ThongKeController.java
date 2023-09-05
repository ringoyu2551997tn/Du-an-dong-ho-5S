package com.datn.dongho5s.Controller.Report;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.StatusValue;
import com.datn.dongho5s.Export.HoaDonPdf;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class ThongKeController {
    @Autowired
    private DonHangService service;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/statisticals")
    public String listFirstPage(@RequestParam(name = "status", required = false) Integer status, Model model) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        if (status != null) {
            return listByPageStatus(1, status, model, "ngayTao", "desc", null);
        }
        return listByPage(1, model, "ngayTao", "desc", null);
    }


    @GetMapping("/admin/statisticals/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        Page<DonHang> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<DonHang> listDonHang = page.getContent();
        long startCount = (pageNum -1) * service.DONHANG_PAGE +1;
        long endCount = startCount + service.DONHANG_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        List<StatusValue> statusValues = Arrays.asList(
                new StatusValue(0, "Chờ giao hàng"),
                new StatusValue(1, "Đang chuẩn bị"),
                new StatusValue(2, "Đang giao hàng"),
                new StatusValue(3, "Hoàn thành"),
                new StatusValue(4, "Đã hủy"),
                new StatusValue(5, "Yêu cầu hoàn trả"),
                new StatusValue(6, "Đã hoàn trả")

        );

        model.addAttribute("statusValues", statusValues);

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listDonHang",listDonHang);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        model.addAttribute("sumAll",service.countDHAll());
        model.addAttribute("sumDHShipping",service.countDHbyStatus(2));
        model.addAttribute("sumDHDone",service.countDHbyStatus(3));
        model.addAttribute("sumDHCancel",service.countDHbyStatus(4));
        model.addAttribute("sumDHReturn",service.countDHbyStatus(6));
        return "admin/thongke/statisticals";

    }

    @GetMapping("/admin/statisticals/page/{status}/{pageNum}")
    private String listByPageStatus(@PathVariable(name = "pageNum") int pageNum, @PathVariable(name = "status") int status,
                                    Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                                    @Param("keyword") String keyword) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        List<DonHang> listDonHang = new ArrayList<>();
        Page<DonHang> page;
        if(status == 10){
           page = service.listByPage(pageNum, sortField, sortDir, keyword);
            listDonHang = page.getContent();
        }else {
             page = service.listByPageStatus(pageNum, sortField, sortDir, keyword, status);
             listDonHang = page.getContent();
        }
        long startCount = (pageNum - 1) * service.DONHANG_PAGE + 1;
        long endCount = Math.min(startCount + service.DONHANG_PAGE - 1, page.getTotalElements());
        List<StatusValue> statusValues = Arrays.asList(
                new StatusValue(0, "Chờ giao hàng"),
                new StatusValue(1, "Đang chuẩn bị"),
                new StatusValue(2, "Đang giao hàng"),
                new StatusValue(3, "Hoàn thành"),
                new StatusValue(4, "Đã hủy"),
                new StatusValue(5, "Yêu cầu hoàn trả"),
                new StatusValue(6, "Đã hoàn trả")

        );
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("statusValues", statusValues);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItem", page.getTotalElements());
        model.addAttribute("listDonHang", listDonHang);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sumAll", service.countDHAll());
        model.addAttribute("sumDHShipping", service.countDHbyStatus(2));
        model.addAttribute("sumDHDone", service.countDHbyStatus(3));
        model.addAttribute("sumDHCancel", service.countDHbyStatus(4));
        model.addAttribute("sumDHReturn", service.countDHbyStatus(6));
        model.addAttribute("selectedStatus", status); // Add this line

        return "admin/thongke/statisticals";
    }

    @GetMapping("/hoa-don-chi-tiet/search/{id}")
    public String getByIdDonHang(
            @PathVariable("id") int id,
            Model model,
            HttpSession session
    ) {
         session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        List<HoaDonChiTiet> lst = hoaDonChiTietService.getByIdDonHang(id);
        Double tongTien = service.tongTien(id);

        DonHang donHang = service.findById(id);

        model.addAttribute("donHang", donHang);
        model.addAttribute("diaChiCache", new DiaChiCache());
        model.addAttribute("diaChiAPI", new DiaChiAPI());

        model.addAttribute("lstHDCT", lst);
        model.addAttribute("tongTien", donHang.getTongTien());

        session.setAttribute("donHang", donHang);

        return "admin/hoadonchitiet/hoadonchitiet";
    }

    @GetMapping("/hoa-don-chi-tiet/export")
    public void exportHoaDon(
            HttpServletResponse response,
            Model model,
            HttpSession session
    ) throws Exception {


        DonHang donHang = (DonHang) session.getAttribute("donHang");

        List<HoaDonChiTiet> lst = hoaDonChiTietService.getByIdDonHang(donHang.getIdDonHang());

        HoaDonPdf hoaDonPdf = new HoaDonPdf();
        hoaDonPdf.exportToPDF(response, lst, donHang);
    }

}
