package com.datn.dongho5s.Controller.RestController;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Response.ChiTietSanPhamResponse;
import com.datn.dongho5s.Response.SanPhamDetailResponse;
import com.datn.dongho5s.Response.TrangChuResponse;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;
@CrossOrigin("*")
@Controller
@RequestMapping("/api/index")
public class TrangChuRestController {
    @Autowired
    SanPhamService sanPhamService;

    @ResponseBody
    @GetMapping("")
   public ResponseEntity<?> home(){
         List<SanPhamDetailResponse> listSPbanChay = sanPhamService.getSPchay();
        List<SanPhamDetailResponse> listSPmoiNhat = sanPhamService.getSPnew();
        List<SanPhamDetailResponse> listSPnoiBat = sanPhamService.getSPFeature();
        TrangChuResponse trangChuResponse = new TrangChuResponse(listSPbanChay,listSPmoiNhat,listSPnoiBat);
//        System.out.println(trangChuResponse);
        return ResponseEntity.status(HttpStatus.OK).body(trangChuResponse);
    }

    @ResponseBody
    @GetMapping("/getSPKM")
    public ResponseEntity<?> getSPKM(@PathParam("idCTSP") Integer idCTSP){
        List<ChiTietSanPhamResponse> chiTietSanPhamResponseList = sanPhamService.getSPchayKM(idCTSP);
        return ResponseEntity.status(HttpStatus.OK).body(chiTietSanPhamResponseList);
    }
}
