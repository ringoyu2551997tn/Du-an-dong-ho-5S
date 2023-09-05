package com.datn.dongho5s.Controller.RestController.DiaChi;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Request.DiaChiRequest;
import com.datn.dongho5s.Response.DiaChiResponse;
import com.datn.dongho5s.Service.DiaChiService;
import com.datn.dongho5s.Service.impl.DiaChiServiceImpl;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/dia-chi")
public class DiaChiRestController {
    private final DiaChiServiceImpl diaChiServiceImpl;
    private final DiaChiService diaChiService;

    @GetMapping("/find-all/{idKhachHang}")
    public ResponseEntity<?> getAllDiaChi(@PathVariable("idKhachHang") Integer idKhachHang) throws Exception {
        List<DiaChiResponse> lstDiaChiResponse = diaChiServiceImpl.getDiaChiByKhachHang(idKhachHang);
        return ResponseEntity.status(HttpStatus.OK).body(lstDiaChiResponse);
    }

    @PostMapping("/them-dia-chi/{idKhachHang}")
    public ResponseEntity<DiaChiResponse> createDiaChi (@PathVariable("idKhachHang") Integer idKhachHang,@RequestBody DiaChiRequest diaChiRequest) throws Exception {

        DiaChiResponse result = diaChiServiceImpl.createDiaChi(idKhachHang,diaChiRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/get-default-dia-chi/khach-hang={idKhachHang}")
    public ResponseEntity<List<DiaChi>> getDefaultDiaChi (@PathVariable(value = "idKhachHang") KhachHang khachHang){
        List<DiaChi> result = diaChiService.getAllDiaChiByKhachHang(khachHang);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/get-tinh-thanh")
    public ResponseEntity<HashMap<Integer,String>> getThanhPho (){
        return ResponseEntity.ok(DiaChiCache.hashMapTinhThanh);
    }

    @GetMapping("/get-quan-huyen/{idThanhPho}")
    public ResponseEntity<HashMap<Integer,String>> getQuanHuyen (@PathVariable ("idThanhPho") Integer id){
        try {
            return ResponseEntity.ok(DiaChiAPI.callGetQuanHuyenAPI(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-phuong-xa/{idQuanHuyen}")
    public ResponseEntity<HashMap<String,String>> getPhuongXa (@PathVariable ("idQuanHuyen") Integer id){
        try {
            return ResponseEntity.ok(DiaChiAPI.callGetPhuongXaAPI(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update/{idDiaChi}")
    public ResponseEntity<?> updateDC(@PathVariable("idDiaChi") Integer idDiaChi, @RequestBody DiaChiRequest diaChiRequest) throws Exception {
      DiaChiResponse diaChiResponse = diaChiServiceImpl.updateDC(idDiaChi,diaChiRequest);
        return ResponseEntity.status(HttpStatus.OK).body(diaChiResponse);
    }
    @PutMapping("/updateDefault/{idKhachHang}/{idDiaChi}")
    public ResponseEntity<?> updateDC(@PathVariable("idKhachHang") Integer idKhachHang,@PathVariable("idDiaChi") Integer idDiaCh) throws Exception {
      DiaChiResponse diaChiResponse = diaChiServiceImpl.updateDCDefault(idKhachHang,idDiaCh);
        return ResponseEntity.status(HttpStatus.OK).body(diaChiResponse);
    }
    @DeleteMapping("/delete/{idDiaChi}")
    public void delete(@PathVariable("idDiaChi") Integer idDiaCh) throws Exception {
       diaChiServiceImpl.delete(idDiaCh);
    }
}
