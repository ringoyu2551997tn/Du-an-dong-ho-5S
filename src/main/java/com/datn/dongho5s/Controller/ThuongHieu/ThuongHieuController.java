package com.datn.dongho5s.Controller.ThuongHieu;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Exception.ThuongHieuNotFoundException;
import com.datn.dongho5s.Export.DanhMucCsvExporter;
import com.datn.dongho5s.Export.DanhMucExcelExporter;
import com.datn.dongho5s.Export.ThuongHieuCsvExporter;
import com.datn.dongho5s.Export.ThuongHieuExcelExporter;
import com.datn.dongho5s.Service.ThuongHieuService;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ThuongHieuController {
    @Autowired
    private ThuongHieuService service;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/brands")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"tenThuongHieu","asc",null);
    }

    @GetMapping("/admin/brands/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField,@Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        Page<ThuongHieu> page = service.listByPage(pageNum,sortField,sortDir,keyword);
        List<ThuongHieu> listThuongHieu = page.getContent();

        long startCount = (pageNum -1) * ThuongHieuService.BRANDS_PER_PAGE +1;
        long endCount = startCount + ThuongHieuService.BRANDS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listThuongHieu",listThuongHieu);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/thuonghieu/brands";

    }

    @GetMapping("/admin/brands/{id}/enabled/{status}")
    public String updateThuongHieuEnabledStatus(@PathVariable("id") Integer id,
                                                @PathVariable("status")boolean enabled,
                                                RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        service.updateThuongHieuEnabledStatus(id,enabled);
        String status = enabled ? "online" : "offline";
        String message = "Thương Hiệu có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/admin/brands";
    }

    @GetMapping("/admin/brands/new")
    public String newThuongHieu(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        model.addAttribute("thuongHieu",new ThuongHieu());
        model.addAttribute("pageTitle","Tạo Mới Thương Hiệu");
        return "admin/thuonghieu/brands_form";
    }

    @PostMapping("/admin/brands/save")
    public String saveThuongHieu(ThuongHieu thuongHieu, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        service.save(thuongHieu);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/brands";
    }

    @GetMapping("/admin/brands/edit/{id}")
    public String editThuongHieu(@PathVariable(name = "id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes){
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null ){
                return "redirect:/login-admin" ;
            }
            ThuongHieu thuongHieu = service.get(id);
            model.addAttribute("thuongHieu", thuongHieu);
            model.addAttribute("pageTitle", "Update Thương Hiệu (ID: " + id + ")");
            return "admin/thuonghieu/brands_form";
        } catch (ThuongHieuNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/brands";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/brands/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<ThuongHieu> listThuongHieu = service.getAllThuongHieu();
        ThuongHieuCsvExporter exporter = new ThuongHieuCsvExporter();
        exporter.export(listThuongHieu,response);
    }

    @GetMapping("/admin/brands/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ThuongHieu> listThuongHieu = service.getAllThuongHieu();
        ThuongHieuExcelExporter exporter = new ThuongHieuExcelExporter();
        exporter.export(listThuongHieu,response);

    }


}
