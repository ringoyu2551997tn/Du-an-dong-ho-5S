//package com.datn.dongho5s.Controller.NhanVien;
//
//import com.datn.dongho5s.Entity.NhanVien;
//import com.datn.dongho5s.Security.NhanVienDetails;
//import com.datn.dongho5s.UploadFile.FileUploadUtil;
//import com.datn.dongho5s.Service.impl.NhanVienService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.IOException;
//
//@Controller
//public class AccountController {
//    @Autowired
//    private NhanVienService service;
//
//    @GetMapping("/account")
//    public String viewDetails(@AuthenticationPrincipal NhanVien loggerdNhanVien,
//                              Model model){
//        String email =  loggerdNhanVien.getUsername();
//        NhanVien nhanVien =  service.getByEmail(email);
//        model.addAttribute("nhanVien",nhanVien);
//
//        return"account_form";
//
//    }
//
//    @PostMapping("/account/update")
//    public String saveDetail(NhanVien nhanVien, RedirectAttributes redirectAttributes,
//                           @AuthenticationPrincipal NhanVienDetails loggedNhanVien,
//                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
//        if(!multipartFile.isEmpty()){
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            nhanVien.setAnh(fileName);
//            NhanVien savedNhanVien = service.nhanVienUpdateAccount(nhanVien);
//            String uploadDir = "user-photos/" + savedNhanVien.getId();
//
//            FileUploadUtil.cleanDir(uploadDir);
//            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
//        }else{
//            if(nhanVien.getAnh().isEmpty()) nhanVien.setAnh(null);
//            service.nhanVienUpdateAccount(nhanVien);
//        }
////        loggedNhanVien.setHo(nhanVien.getHo());
////        loggedNhanVien.setTen(nhanVien.getTen());
////        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
////        return "redirect:/account";
////    }
//}
