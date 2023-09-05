package com.datn.dongho5s.Controller.NhanVien;

import com.datn.dongho5s.Entity.ChucVu;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Exception.NhanVienNotFoundException;
import com.datn.dongho5s.Export.NhanVienCsvExporter;
import com.datn.dongho5s.Export.NhanVienExcelExporter;
import com.datn.dongho5s.Export.NhanVienPdfExporter;
import com.datn.dongho5s.Service.NhanVienService;
import com.datn.dongho5s.UploadFile.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class NhanVienController {

    @Autowired
    private NhanVienService service;
    @Autowired
    HttpServletRequest request;

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/users")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return listByPage(1,model,"email","asc",null);
    }

    @GetMapping("/admin/users/page/{pageNum}")
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
        Page<NhanVien> page = service.listByPage(pageNum, sortField, sortDir,keyword);
        List<NhanVien> listNhanVien = page.getContent();

        long startCount = (pageNum -1) * NhanVienService.USERS_PER_PAGE + 1;
        long endCount = startCount + NhanVienService.USERS_PER_PAGE-1;

        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listNhanVien",listNhanVien);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "admin/nhanvien/users";

    }

    @GetMapping("/admin/users/new")
    public String newUser(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        List<ChucVu> listChucVu = service.listChucVu();
        NhanVien nhanVien = new NhanVien();
        nhanVien.setEnabled(true);
        model.addAttribute("nhanVien", nhanVien);
        model.addAttribute("listChucVu",listChucVu);
        model.addAttribute("pageTitle","Thêm Mới Nhân Viên");
        return "admin/nhanvien/user_form";
    }

    @PostMapping("/admin/users/save")
    public String saveUser(NhanVien nhanVien, RedirectAttributes redirectAttributes,
                           @RequestParam("image")MultipartFile multipartFile) throws IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            nhanVien.setAnh(fileName);
            NhanVien savedNhanVien = service.save(nhanVien);
            String uploadDir = "user-photos/" + savedNhanVien.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }else{
            if(nhanVien.getAnh().isEmpty()) nhanVien.setAnh(null);
            service.save(nhanVien);
        }
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null ){
                return "redirect:/login-admin" ;
            }
            NhanVien nhanVien = service.get(id);
            List<ChucVu> listChucVu = service.listChucVu();
            model.addAttribute("nhanVien", nhanVien);
            model.addAttribute("pageTitle","Update Nhân Viên (ID : " + id + ")");
            model.addAttribute("listChucVu",listChucVu);
            return "admin/nhanvien/user_form";
        }catch (NhanVienNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/admin/users";
        }

    }


    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null ){
                return "redirect:/login-admin" ;
            }
            service.delete(id);
            redirectAttributes.addFlashAttribute("message","Người dùng ID" + id + "đã xóa thành công");
        }catch (NhanVienNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/enabled/{status}")
    public String updateNhanVienEnabledStatus(@PathVariable("id") Integer id,
                                              @PathVariable("status") boolean enabled,
                                              RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        service.updateNhanVienEnabledStatus(id, enabled);
        String status = enabled ? "online" : "offline";
        String message = "Nhân viên có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<NhanVien> listNhanVien = service.listAll();
        NhanVienCsvExporter exporter = new NhanVienCsvExporter();
        exporter.export(listNhanVien,response);
    }

    @GetMapping("/admin/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<NhanVien> listNhanVien = service.listAll();
        NhanVienExcelExporter exporter = new NhanVienExcelExporter();
        exporter.export(listNhanVien,response);

    }

    @GetMapping("/admin/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<NhanVien> listNhanVien = service.listAll();
        NhanVienPdfExporter exporter = new NhanVienPdfExporter();
        exporter.export(listNhanVien,response);

    }
}
