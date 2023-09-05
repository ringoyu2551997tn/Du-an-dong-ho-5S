package com.datn.dongho5s.Controller.DayDeo;

import com.datn.dongho5s.Entity.DayDeo;
import com.datn.dongho5s.Exception.DayDeoNotFoundException;
import com.datn.dongho5s.Exception.ThuongHieuNotFoundException;
import com.datn.dongho5s.Service.DayDeoService;
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
public class DayDeoController {

    @Autowired
    private DayDeoService service;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/admin/straps")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"tenDayDeo","asc",null);
    }

    @GetMapping("/admin/straps/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField,@Param("sortDir") String sortDir,
                              @Param("keyword") String keyword) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        Page<DayDeo> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<DayDeo> listDayDeo = page.getContent();
        long startCount = (pageNum -1) * DayDeoService.STRAPS_PER_PAGE +1;
        long endCount = startCount + DayDeoService.STRAPS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listDayDeo", listDayDeo);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/daydeo/straps";
    }

    @GetMapping("/admin/straps/{id}/enabled/{status}")
    public String updateDayDeoEnabledStatus(@PathVariable("id") Integer id,
                                            @PathVariable("status") boolean enabled,
                                            RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        service.updateDayDeoEnabledStatus(id, enabled);
        String status = enabled ? "online" : "offline";
        String message = " Dây Đeo có id " + id + " Thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/admin/straps";
    }

    @GetMapping("/admin/straps/new")
    public String newDayDeo(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        model.addAttribute("dayDeo", new DayDeo());
        model.addAttribute("pageTitle", "Tạo Mới Dây Đeo");
        return "admin/daydeo/straps_form";
    }

    @PostMapping("/admin/straps/save")
    public String saveDayDeo(DayDeo dayDeo, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        service.save(dayDeo);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/straps";
    }

    @GetMapping("/admin/straps/edit/{id}")
    public String editDayDeo(@PathVariable(name = "id") Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes){

        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null ){
                return "redirect:/login-admin" ;
            }
            DayDeo dayDeo = service.get(id);
            model.addAttribute("dayDeo",dayDeo);
            model.addAttribute("pageTitle", "Update Dây Đeo (ID :" + id + ")");
            return "admin/dayDeo/straps_form";
        }catch (DayDeoNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/straps";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

}
