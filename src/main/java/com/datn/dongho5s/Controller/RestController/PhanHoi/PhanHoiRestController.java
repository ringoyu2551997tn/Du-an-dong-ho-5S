package com.datn.dongho5s.Controller.RestController.PhanHoi;

import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Request.PhanHoiRequest;
import com.datn.dongho5s.Response.PhanHoiResponse;
import com.datn.dongho5s.Response.TrangChuResponse;
import com.datn.dongho5s.Service.PhanHoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
public class PhanHoiRestController {

    @Autowired
    PhanHoiService phanHoiService;

    @GetMapping("/phan-hoi/get/{idSanPham}")
    public ResponseEntity<?> findAll(@PathVariable("idSanPham") Integer idSanPham){
       List<PhanHoiResponse> phanHoiResponses = phanHoiService.findAll(idSanPham);
        return ResponseEntity.status(HttpStatus.OK).body(phanHoiResponses);
    }

    @GetMapping("/phan-hoi/checkPhanHoi")
    public ResponseEntity<?> checkPhanHoi(@Param("idKhachHang") Integer idKhachHang , @Param("idSanPham") Integer idSanPham){
       boolean checkPhanHoi = phanHoiService.checkPhanHoi(idKhachHang,idSanPham);
        return ResponseEntity.status(HttpStatus.OK).body(checkPhanHoi);
    }

    @PostMapping("/api/phan-hoi/add")
    public ResponseEntity<?> add(@RequestBody PhanHoiRequest phanHoiRequest){
        PhanHoiResponse phanHoiResponse = phanHoiService.addPhanHoi(phanHoiRequest);
        return ResponseEntity.status(HttpStatus.OK).body(phanHoiResponse);
    }

//    @GetMapping("/phan-hoi/countHDCT")
//    public ResponseEntity<?> count(@Param("idKhachHang") Integer idKhachHang , @Param("idChiTietSanPham") Integer idChiTietSanPham){
//        Integer countPH = phanHoiService.countHDCT(idKhachHang,idChiTietSanPham);
//        System.out.println(countPH  );
//        return ResponseEntity.status(HttpStatus.OK).body(countPH);
//    }
}
