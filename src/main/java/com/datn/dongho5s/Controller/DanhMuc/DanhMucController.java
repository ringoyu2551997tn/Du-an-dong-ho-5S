package com.datn.dongho5s.Controller.DanhMuc;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Exception.DanhMucNotFoundException;
import com.datn.dongho5s.Export.DanhMucCsvExporter;
import com.datn.dongho5s.Export.DanhMucExcelExporter;
import com.datn.dongho5s.Service.DanhmucService;
import com.datn.dongho5s.Service.impl.DanhMucServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class DanhMucController {
    @Autowired
    private DanhmucService service;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/categories")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"ten","asc",null);
    }

    @GetMapping("/admin/categories/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField")String sortField , @Param("sortDir")String sortDir,
                             @Param("keyword")String keyword
    ){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        System.out.println("SortField: " + sortField);
        System.out.println("sortOrder: " + sortDir);
        Page<DanhMuc> page = service.listByPage(pageNum, sortField, sortDir,keyword);
        List<DanhMuc> listDanhMuc = page.getContent();

        long startCount = (pageNum -1) * DanhMucServiceImpl.CATEGORIES_PER_PAGE + 1;
        long endCount = startCount + DanhMucServiceImpl.CATEGORIES_PER_PAGE-1;

        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listDanhMuc",listDanhMuc);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "admin/danhmuc/categories";

    }

    @GetMapping("/admin/categories/{id}/enabled/{status}")
    public String updateDanhMucEnabledStatus(@PathVariable("id") Integer id,
                                              @PathVariable("status") boolean enabled,
                                              RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        service.updateDanhMucEnabledStatus(id, enabled);
        String status = enabled ? "online" : "offline";
        String message = "Danh Mục có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/categories";
    }

    @GetMapping("/admin/categories/new")
    public String newDanhMuc(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        model.addAttribute("danhMuc", new DanhMuc());
        model.addAttribute("pageTitle","Tạo Mới Danh Mục");
        return "admin/danhmuc/categories_form";
    }

    @PostMapping("/admin/categories/save")
    public String saveDanhMuc(DanhMuc danhMuc, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
         service.save(danhMuc);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null ){
                return "redirect:/login-admin" ;
            }
            DanhMuc danhMuc = service.get(id);
            model.addAttribute("danhMuc", danhMuc);
            model.addAttribute("pageTitle","Update Danh Mục (ID : " + id + ")");
            return "admin/danhmuc/categories_form";
        }catch (DanhMucNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/admin/categories";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }

    }

    @GetMapping("/admin/categories/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {

        List<DanhMuc> listDanhMuc = service.listAll();
        DanhMucCsvExporter exporter = new DanhMucCsvExporter();
        exporter.export(listDanhMuc,response);
    }

    @GetMapping("/admin/categories/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<DanhMuc> listDanhMuc = service.listAll();
        DanhMucExcelExporter exporter = new DanhMucExcelExporter();
        exporter.export(listDanhMuc,response);

    }


}
