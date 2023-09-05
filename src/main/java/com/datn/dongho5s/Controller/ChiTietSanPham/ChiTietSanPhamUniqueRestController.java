package com.datn.dongho5s.Controller.ChiTietSanPham;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChiTietSanPhamUniqueRestController {
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/admin/productDetails/check_details_unique")
    public String checkUniqueDetails(
            @RequestParam String maChiTietSanPham,
            @RequestParam String tenSanPham,
            @RequestParam String tenDayDeo,
            @RequestParam String tenMauSac,
            @RequestParam String tenKichCo,
            @RequestParam String tenVatLieu
    ) {
        boolean isUnique = chiTietSanPhamService.isUniqueChiTietSanPham(
                maChiTietSanPham, tenSanPham, tenDayDeo, tenMauSac, tenKichCo, tenVatLieu
        );
        if (isUnique) {
            return "OK";
        } else {
            return "Duplicated";
        }
    }


    @PostMapping("/admin/productDetails/check_details_unique_update")
    public String checkUniqueDetailsUpdate(
            @RequestParam Integer idChiTietSanPham,
            @RequestParam String maChiTietSanPham,
            @RequestParam String tenSanPham,
            @RequestParam String tenDayDeo,
            @RequestParam String tenMauSac,
            @RequestParam String tenKichCo,
            @RequestParam String tenVatLieu
    ) {
        boolean isUnique = chiTietSanPhamService.isUniqueChiTietSanPhamUpdate(
                idChiTietSanPham ,maChiTietSanPham, tenSanPham, tenDayDeo, tenMauSac, tenKichCo, tenVatLieu
        );
        if (isUnique) {
            return "OK";
        } else {
            return "Duplicated";
        }
    }
}
