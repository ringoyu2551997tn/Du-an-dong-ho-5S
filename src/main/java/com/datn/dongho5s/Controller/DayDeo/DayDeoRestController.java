package com.datn.dongho5s.Controller.DayDeo;

import com.datn.dongho5s.Service.DayDeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DayDeoRestController {
    @Autowired
    private DayDeoService service;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/straps/check_name")
    public String checkDuplicateTen(@Param("id") Integer id , @Param("ten") String ten) {
        return service.checkUnique(id, ten) ? "OK" : "Duplicated";
    }
}
