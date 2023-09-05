package com.datn.dongho5s.Controller.RestController.SanPham;

import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Request.TimKiemRequest;
import com.datn.dongho5s.Response.SanPhamDetailResponse;
import com.datn.dongho5s.Response.TimKiemResponse;
import com.datn.dongho5s.Response.TimKiemSettingResponse;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/san-pham")
public class SanPhamRestController {
    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @PostMapping("/tim-kiem")
    public ResponseEntity<?> TimKiemSanPham (@RequestBody TimKiemRequest timKiemRequest){
//        try {
//        System.out.println(timKiemRequest.toString());
//            validDataTimKiem(timKiemRequest);
        System.out.println(timKiemRequest.toString());
            Set<TimKiemResponse> result = sanPhamService.getSanPhamByCondition(timKiemRequest);
            return ResponseEntity.status(HttpStatus.OK).body(result);
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
//        }
    }

    @GetMapping("/get-setting")
    public ResponseEntity<?> GetSettingTimKiem (){
        TimKiemSettingResponse result = chiTietSanPhamService.getTimKiemSetting();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/san-pham-detail/id-san-pham={idSP}")
    public ResponseEntity<?> GetSanPhamById(@PathVariable("idSP") Integer idSP){
        SanPhamDetailResponse result = sanPhamService.getDetailSanPhamById(idSP);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/cung-thuong-hieu={idTH}")
    public ResponseEntity<?> GetSanPhamCungTH(@PathVariable("idTH") SanPham sanPham){
        List<SanPham> result = sanPhamService.getSPCungTH(sanPham.getThuongHieu());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    void validDataTimKiem(TimKiemRequest req){
        if(req.getDayDeoId().size()==0)
            req.setDayDeoId(null);
        if(req.getDanhMucId().size()==0)
            req.setDanhMucId(null);
        if(req.getMauSacId().size()==0)
            req.setMauSacId(null);
        if(req.getThuongHieuId().size()==0)
            req.setThuongHieuId(null);
        if(req.getVatLieuId().size()==0)
            req.setVatLieuId(null);
        if(req.getSizeId().size()==0)
            req.setSizeId(null);
    }
}
