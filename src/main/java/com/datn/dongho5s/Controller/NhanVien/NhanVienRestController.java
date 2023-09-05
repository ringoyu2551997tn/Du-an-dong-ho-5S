package com.datn.dongho5s.Controller.NhanVien;

import com.datn.dongho5s.Service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class NhanVienRestController {
    @Autowired
    private NhanVienService service;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/users/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id ,@Param("email") String email){
        return service.isEmailUnique(id , email) ? "OK" : "Duplicated";
    }
}
