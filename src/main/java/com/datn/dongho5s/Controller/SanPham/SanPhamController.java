package com.datn.dongho5s.Controller.SanPham;

import com.datn.dongho5s.Entity.*;
import com.datn.dongho5s.Exception.SanPhamNotFountException;
import com.datn.dongho5s.Service.AnhSanPhamService;
import com.datn.dongho5s.Service.DanhmucService;
import com.datn.dongho5s.Service.SanPhamService;
import com.datn.dongho5s.Service.ThuongHieuService;
import com.datn.dongho5s.UploadFile.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private DanhmucService danhmucService;
    @Autowired
    private AnhSanPhamService anhSanPhamService;

    @Autowired
    HttpServletRequest request;


    @GetMapping("/admin/products")
    public String listFirstPage(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin";
        }
       return listByPage(1,model,"tenSanPham","asc",null);
    }

    @GetMapping("/admin/products/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin";
        }
//        System.out.println("Thương Hiệu Được Lọc" + brandSelect);

        Page<SanPham> page = sanPhamService.listByPage(pageNum,sortField,sortDir,keyword);
        List<SanPham> listSanPham = page.getContent();


        long startCount = (pageNum -1) * SanPhamService.PRODUCT_PER_PAGE +1;
        long endCount = startCount + SanPhamService.PRODUCT_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listSanPham",listSanPham);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/sanpham/products";

    }


    @GetMapping("/admin/products/new")
    public String newProduct(Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin";
        }
        List<ThuongHieu> listThuongHieu = thuongHieuService.getAllThuongHieu();
        List<DanhMuc> listDanhMuc = danhmucService.listAll();
        SanPham sanPham = new SanPham();
        sanPham.setTrangThai(0);
        model.addAttribute("sanPham",sanPham);
        model.addAttribute("listThuongHieu",listThuongHieu);
        model.addAttribute("listDanhMuc",listDanhMuc);
        return "admin/sanpham/product_create";
    }


    @PostMapping("/admin/products/save")
    public String saveProduct(SanPham sanPham, RedirectAttributes ra,
                              @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin";
        }
        System.out.println(multipartFile.getOriginalFilename());
        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            sanPham.setMainImage(fileName);
            AnhSanPham anhSanPham = new AnhSanPham();
            SanPham savedSanPham = sanPhamService.save(sanPham);
            anhSanPham.setSanPham(savedSanPham);
            anhSanPham.setLink(multipartFile.getOriginalFilename());
            anhSanPhamService.save(anhSanPham);

            String uploadDir = "src/main/resources/static/assets/images/";;
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }else{
            ra.addFlashAttribute("chuadoianh","Hãy chọn ảnh");
            return "redirect:/admin/products/new";
//            sanPhamService.save(sanPham);
        }

        ra.addFlashAttribute("message","Thay Đổi Thành Công.");
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model,
                              RedirectAttributes ra){

        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("admin") == null ){
                return "redirect:/login-admin";
            }
            SanPham sanPham = sanPhamService.get(id);
            List<ThuongHieu> listThuongHieu = thuongHieuService.getAllThuongHieu();
            List<DanhMuc> listDanhMuc = danhmucService.listAll();

            model.addAttribute("listThuongHieu",listThuongHieu);
            model.addAttribute("listDanhMuc",listDanhMuc);
            model.addAttribute("sanPham", sanPham);

            return "admin/sanpham/product_edit";
        }catch (SanPhamNotFountException e){
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/admin/products";
        }
    }

    @PostMapping("/admin/products/update")
    public String updateProduct(SanPham sanPham, RedirectAttributes ra,
                                @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("admin") == null ){
            return "redirect:/login-admin";
        }
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            sanPham.setMainImage(fileName);
            AnhSanPham anhSanPham = new AnhSanPham();
            SanPham savedSanPham = sanPhamService.save(sanPham);
            anhSanPham.setSanPham(savedSanPham);
            anhSanPham.setLink(multipartFile.getOriginalFilename());
            anhSanPhamService.save(anhSanPham);

            String uploadDir = "src/main/resources/static/assets/images/";

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            // Nếu không có ảnh mới, giữ nguyên tên ảnh hiện tại từ trường ẩn
            sanPham.setMainImage(sanPham.getCurrentMainImage());
            sanPhamService.save(sanPham);
        }

        ra.addFlashAttribute("message", "Thay Đổi Thành Công.");
        return "redirect:/admin/products";
    }



}
