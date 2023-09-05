package com.datn.dongho5s.Controller.KhachHang;

import com.datn.dongho5s.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KhachHangRestControllerAdmin {
    @Autowired
    private KhachHangService service;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/customers/check_name")
    public String checkDuplicateTen(@Param("idKhachHang") Integer id , @Param("email") String email, @Param("soDienThoai") String soDT) {
        return service.checkUnique(id, email, soDT) ? "OK" : "Duplicated";
    }
}
