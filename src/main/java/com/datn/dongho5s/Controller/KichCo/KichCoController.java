package com.datn.dongho5s.Controller.KichCo;

import com.datn.dongho5s.Entity.KichCo;
import com.datn.dongho5s.Exception.KichCoNotFoundException;
import com.datn.dongho5s.Export.KichCoCsvExporter;
import com.datn.dongho5s.Export.KichCoExcelExporter;
import com.datn.dongho5s.Service.KichCoService;
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
public class KichCoController {
    @Autowired
    private KichCoService service;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/sizes")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"tenKichCo","asc",null);
    }

    @GetMapping("/admin/sizes/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField,@Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }

        Page<KichCo> page = service.listByPage(pageNum,sortField,sortDir,keyword);
        List<KichCo> listKichCo = page.getContent();
        long startCount = (pageNum -1) * KichCoService.SIZES_PER_PAGE +1;
        long endCount = startCount + KichCoService.SIZES_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listKichCo",listKichCo);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/kichco/sizes";

    }

    @GetMapping("/admin/sizes/{id}/enabled/{status}")
    public String updateKichCoEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status")boolean enabled,
                                             RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        service.updateKichCoEnabledStatus(id,enabled);
        String status = enabled ? "online" : "offline";
        String message = "Kích Cỡ có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/sizes";
    }

    @GetMapping("/admin/sizes/new")
    public String newKichCo(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        model.addAttribute("KichCo",new KichCo());
        model.addAttribute("pageTitle","Tạo Mới Kích Cỡ");
        return "admin/kichco/size_form";
    }

    @PostMapping("/admin/sizes/save")
    public String saveKichCo(KichCo KichCo, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        service.save(KichCo);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/sizes";
    }

    @GetMapping("/admin/sizes/edit/{id}")
    public String editKichCo(@PathVariable(name = "id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes){
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null ){
                return "redirect:/login-admin" ;
            }
            KichCo KichCo = service.get(id);
            model.addAttribute("KichCo", KichCo);
            model.addAttribute("pageTitle", "Update Kích Cỡ (ID: " + id + ")");
            return "admin/kichco/size_form";
        } catch (KichCoNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/sizes";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/sizes/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<KichCo> listKichCo = service.getAllKichCo();
        KichCoCsvExporter exporter = new KichCoCsvExporter();
        exporter.export(listKichCo,response);
    }

    @GetMapping("/admin/sizes/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<KichCo> listKichCo = service.getAllKichCo();
        KichCoExcelExporter exporter = new KichCoExcelExporter();
        exporter.export(listKichCo,response);
    }
}
