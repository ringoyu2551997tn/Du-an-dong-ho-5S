package com.datn.dongho5s.Controller.RestController.KhachHang;

import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Request.DiaChiRequest;
import com.datn.dongho5s.Response.ThongTinCaNhanResponse;
import com.datn.dongho5s.Response.ThongTinToCheckoutResponse;
import com.datn.dongho5s.Response.TimKiemSettingResponse;
import com.datn.dongho5s.Service.DiaChiService;
import com.datn.dongho5s.Service.impl.KhachHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/khach-hang")
public class KhachHangRestController {

    @Autowired
    KhachHangServiceImpl khachHangServiceImpl;

    @Autowired
    DiaChiService diaChiService;

    @GetMapping("/thong-tin-ca-nhan/{id}")
    public ResponseEntity<ThongTinCaNhanResponse> getThongTinCaNhanById (
            @PathVariable(value = "id") Integer id
    ){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(khachHangServiceImpl.getThongTinCaNhanById(id));
        } catch (Exception e){
            return new ResponseEntity<ThongTinCaNhanResponse>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/thong-tin-ca-nhan/update/{idKhachHang}")
    public ResponseEntity<KhachHang> updateThongTinCaNhan (@PathVariable("idKhachHang") Integer idKhachHnag,
            @RequestBody ThongTinCaNhanResponse thongTinCaNhanResponse
    ){
            return ResponseEntity.ok(khachHangServiceImpl.updateThongTinCaNhan(idKhachHnag,thongTinCaNhanResponse));
    
    }
    @GetMapping("/thong-tin/{id}")
    public ResponseEntity<ThongTinToCheckoutResponse> getThongTin (
            @PathVariable(value = "id") Integer id
    ){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(khachHangServiceImpl.getThongTinToCheckout(id));
        } catch (Exception e){
            return new ResponseEntity<ThongTinToCheckoutResponse>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getDiaChi")
    public ResponseEntity<?> getDiaCHi (
            @RequestBody DiaChiRequest diaChiRequest
            ){
        try {
            return ResponseEntity.status(HttpStatus.OK).body( diaChiService.getDiaChiCuThe(diaChiRequest));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
