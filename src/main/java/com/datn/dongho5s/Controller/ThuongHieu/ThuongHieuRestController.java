package com.datn.dongho5s.Controller.ThuongHieu;

import com.datn.dongho5s.Service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ThuongHieuRestController {
    @Autowired
    private ThuongHieuService service;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/brands/check_name")
    public String checkDuplicateTen(@Param("id") Integer id , @Param("ten") String ten) {
        return service.checkUnique(id, ten) ? "OK" : "Duplicated";
    }
}
