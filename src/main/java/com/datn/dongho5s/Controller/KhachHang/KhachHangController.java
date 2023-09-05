package com.datn.dongho5s.Controller.KhachHang;

import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Exception.KhachHangNotFoundException;
import com.datn.dongho5s.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class KhachHangController {
    @Autowired
    private KhachHangService service;
    @Autowired
    HttpServletRequest request;


    @GetMapping("/admin/customers")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"tenKhachHang","asc",null);
    }

    @GetMapping("/admin/customers/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField,@Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        Page<KhachHang> page = service.listByPage(pageNum,sortField,sortDir,keyword);
        List<KhachHang> listKhachHang = page.getContent();
        long startCount = (pageNum -1) * KhachHangService.CUSTOMERS_PER_PAGE +1;
        long endCount = startCount + KhachHangService.CUSTOMERS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listKhachHang",listKhachHang);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/khachhang/customers";

    }

//    @GetMapping("/admin/customers/{id}/enabled/{status}")
//    public String updateKhachHangEnabledStatus(@PathVariable("id") Integer id,
//                                                @PathVariable("status")boolean enabled,
//                                                RedirectAttributes redirectAttributes){
//        service.updateKhachHangEnabledStatus(id,enabled);
//        String status = enabled ? "online" : "offline";
//        String message = "Khách hàng có id " + id + " thay đổi trạng thái thành " + status;
//        redirectAttributes.addFlashAttribute("message",message);
//        return "redirect:/customers";
//    }

//    @GetMapping("/admin/customers/new")
//    public String newKhachHang(Model model){
//        model.addAttribute("KhachHang",new KhachHang());
//        model.addAttribute("pageTitle","Tạo Mới Khách hàng");
//        return "admin/khachhang/customer_form";
//    }
//
//    @PostMapping("/admin/customers/save")
//    public String saveKhachHang(KhachHang KhachHang, RedirectAttributes redirectAttributes){
//        service.saveKhachHang(KhachHang);
//        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
//        return "redirect:/admin/customers";
//    }
////
//    @GetMapping("/admin/customers/edit/{id}")
//    public String editKhachHang(@PathVariable(name = "id") Integer id,
//                                 Model model,
//                                 RedirectAttributes redirectAttributes){
//        try {
//            KhachHang KhachHang = service.get(id);
//            model.addAttribute("KhachHang", KhachHang);
//            model.addAttribute("pageTitle", "Update Khách hàng (ID: " + id + ")");
//            return "admin/khachhang/customer_form";
//        } catch (KhachHangNotFoundException ex) {
//            redirectAttributes.addFlashAttribute("message", ex.getMessage());
//            return "redirect:/admin/customers";
//        }
//        catch (Exception ex) {
//            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
//            return "redirect:/error";
//        }
//    }

//    @GetMapping("/admin/customers/export/csv")
//    public void exportToCSV(HttpServletResponse response) throws IOException {
//        List<KhachHang> listKhachHang = service.getAllKhachHang();
//        KhachHangCsvExporter exporter = new KhachHangCsvExporter();
//        exporter.export(listKhachHang,response);
//    }
//
//    @GetMapping("/admin/customers/export/excel")
//    public void exportToExcel(HttpServletResponse response) throws IOException {
//        List<KhachHang> listKhachHang = service.getAllKhachHang();
//        KhachHangExcelExporter exporter = new KhachHangExcelExporter();
//        exporter.export(listKhachHang,response);
//    }
}
