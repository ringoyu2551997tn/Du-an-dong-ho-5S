package com.datn.dongho5s.Controller.DanhMuc;

import com.datn.dongho5s.Service.DanhmucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class DanhMucRestController {
    @Autowired
    DanhmucService service;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/categories/check_name")
    public String checkDuplicateTen(@Param("id") Integer id ,@Param("ten") String ten) {
        return service.checkUnique(id, ten) ? "OK" : "Duplicated";
    }

}
