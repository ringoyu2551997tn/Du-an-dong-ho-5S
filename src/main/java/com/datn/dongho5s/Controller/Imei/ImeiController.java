package com.datn.dongho5s.Controller.Imei;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.KhuyenMai;
import com.datn.dongho5s.Entity.Seri;
import com.datn.dongho5s.Exception.KhuyenMaiNotFoundException;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.SeriService;
import com.datn.dongho5s.Service.impl.KhuyenMaiServiceImpl;
import com.datn.dongho5s.Utils.TrangThaiImei;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/admin/seri")
public class ImeiController {

    private final String UPLOAD_DIR = "./uploads/";
    private final Integer ITEM_PER_PAGE = 20;

    @Autowired
    SeriService seriService;
    @Autowired
    ChiTietSanPhamService ctspService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("")
    public String init() {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        return "admin/imei/imei";
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("keyword") String keyword) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        Page<Seri> page = seriService.searchSeri(pageNum, ITEM_PER_PAGE, keyword);
        List<Seri> listSeri = page.getContent();
        long startCount = pageNum * ITEM_PER_PAGE + 1;
        long endCount = startCount + ITEM_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItem", page.getTotalElements());
        model.addAttribute("listSeri", listSeri);
        model.addAttribute("keyword", keyword);
        System.out.println(listSeri.size());
        return "admin/imei/imei";

    }

    @GetMapping("/new")
    public String newSeri(Model model) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        model.addAttribute("seri", new Seri());
        model.addAttribute("pageTitle", "Tạo Mới Seri");
        return "admin/imei/imei_form";
    }

    @GetMapping("/edit/{id}")
    public String editSeri(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null ){
                return "redirect:/login-admin" ;
            }
            model.addAttribute("edit", true);
            Seri seri = seriService.get(id);
            if (seri == null) {
                redirectAttributes.addFlashAttribute("message", "Không tìm thấy Imei");
                return "redirect:/admin/seri";
            }
            model.addAttribute("seri", seri);
            model.addAttribute("pageTitle", "Update Imei (ID : " + id + ")");
            return "admin/imei/imei_form";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @PostMapping("/save")
    public String upTrangThaiImei(Seri seri, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        Seri updateSeri = seriService.get(seri.getIdSeri());
        updateSeri.setTrangThai(seri.getTrangThai());
        updateSeri.setIdImei(seri.getIdImei());
        seriService.save(updateSeri);
        redirectAttributes.addFlashAttribute("message", "Thay Đổi Thành Công");
        return "redirect:/admin/seri";
    }

    @PostMapping("/importExcept")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             Seri seri
            , RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin" ;
        }
        if (seri.getChiTietSanPham() == null || ctspService.getChiTietSanPhamByMa(seri.getChiTietSanPham().getMaChiTietSanPham())==null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn đúng chi tiết cần thêm Imei");
            return "redirect:/admin/seri";
        }
        if (file.isEmpty() && seri.getIdImei().isBlank()) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn 1 trong 2 cách thức nhập seri");
            return "redirect:/admin/seri";
        } else if (!file.isEmpty()) {
            try {
                List<Seri> listSave = new ArrayList<>();
                // Lưu file tạm thời
                File tempFile = File.createTempFile("temp", ".xlsx");
                file.transferTo(tempFile);

                // Đọc nội dung của file Excel
                Workbook workbook = new XSSFWorkbook(tempFile);
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        Seri result = Seri.builder()
                                .chiTietSanPham(ctspService.getChiTietSanPhamByMa(seri.getChiTietSanPham().getMaChiTietSanPham()))
                                .ngayNhap(new Timestamp(System.currentTimeMillis()))
                                .ngayBan(null)
                                .trangThai(TrangThaiImei.CHUA_BAN)
                                .build();
                        if (cell.getCellType() == CellType.STRING) {
                            String value = cell.getStringCellValue();
                            result.setIdImei(value);
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date dateValue = cell.getDateCellValue();
                                result.setIdImei(dateValue.toString());
                            } else {
                                double numericValue = cell.getNumericCellValue();
                                result.setIdImei(String.valueOf(numericValue));
                            }
                        }
                        if (result.getIdImei() != null) {
                            listSave.add(result);
                        }
                    }
                }
                seriService.saveMany(listSave);
                workbook.close();
                redirectAttributes.addFlashAttribute("message", "Thêm list Imei thành công");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        } else {
            Seri result = Seri.builder()
                    .idImei(seri.getIdImei())
                    .chiTietSanPham(ctspService.getChiTietSanPhamByMa(seri.getChiTietSanPham().getMaChiTietSanPham()))
                    .ngayNhap(new Timestamp(System.currentTimeMillis()))
                    .ngayBan(null)
                    .trangThai(TrangThaiImei.CHUA_BAN)
                    .build();
            seriService.save(result);
            redirectAttributes.addFlashAttribute("message", "Thêm Imei thành công");
        }

        return "redirect:/admin/seri";
    }


}
